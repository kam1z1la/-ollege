package com.college.kkte.schedule.replacement;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RequestMapping("/schedule-replacement")
@RequiredArgsConstructor
@Controller
public class ScheduleReplacementController {
    private final ScheduleReplacementService scheduleReplacementService;

    @PostMapping("schedule-replacement-page")
    public String getScheduleReplacement(@ModelAttribute GetDateDto dto,
                                         BindingResult result,
                                         Model model){
        if(result.hasErrors()){
            model.addAttribute("getDateDto", new GetDateDto(new Date()));
            return "/schedule-replacement/schedule-replacement-page";
        }
        List<ScheduleReplacement> scheduleReplacements = scheduleReplacementService.getAllScheduleReplacementByPostingDate(dto.date());
        if(scheduleReplacements.isEmpty()){
            return "/schedule-replacement/schedule-replacement-page";
        }
        String base64String = Base64.getEncoder().encodeToString(scheduleReplacements.get(0).getResource());
        model.addAttribute("photo", base64String);
        return "/schedule-replacement/schedule-replacement-page";
    }

    @GetMapping("schedule-replacement-page")
    public String getScheduleReplacement(Model model){
        model.addAttribute("getDateDto", new GetDateDto(new Date()));
        return "/schedule-replacement/schedule-replacement-page";
    }

    @GetMapping("remove")
    public String removeById(@RequestParam("id") long id){
        if(scheduleReplacementService.removeScheduleReplacement(id))
            return "success";
        return "success";
    }

    @GetMapping("/creating-schedule-replacement-page")
    public String creatingScheduleReplacementPage(Model model){
        model.addAttribute("creatingScheduleReplacementDTO", new CreatingScheduleReplacementDTO());

        return "/schedule-replacement/creating-schedule-replacement";
    }

    @PostMapping("/create-schedule-replacement")
    public String createScheduleReplacement(@ModelAttribute("creatingScheduleReplacementDTO")
                                              CreatingScheduleReplacementDTO creatingScheduleReplacementDTO,
                                              Model model,
                                              BindingResult result) throws IOException {
        if(result.hasErrors()){
            model.addAttribute("creatingScheduleReplacementDTO", creatingScheduleReplacementDTO);
            return "/schedule-replacement/creating-schedule-replacement";
        }
        scheduleReplacementService.addNewScheduleReplacement(creatingScheduleReplacementDTO);
        String base64String = Base64.getEncoder().encodeToString(creatingScheduleReplacementDTO.getResources().get(0).getBytes());
        model.addAttribute("photo", base64String);
        return "/success";

    }

}
