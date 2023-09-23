package com.college.kkte.schedule.replacement;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

@RequestMapping("/schedule-replacement")
@RequiredArgsConstructor
@Controller
public class ScheduleReplacementController {
    private final ScheduleReplacementService scheduleReplacementService;

    @GetMapping("schedule-replacement-page")
    public String getScheduleReplacement(Model model){

        List<ScheduleReplacement> scheduleReplacements = scheduleReplacementService.getAllScheduleReplacementByPostingDate(new Date(123, 8, 30));
        String base64String = Base64.getEncoder().encodeToString(scheduleReplacements.get(0).getResource());
        model.addAttribute("photo", base64String);
        //model.addAttribute("photo", scheduleReplacements.get(1).getResource().toString());
        return "/schedule-replacement/schedule-replacement-page";
    }

    @GetMapping("editor")
    public String editor(Model model){


        return "/editor";
    }

    @GetMapping("test")
    public String test(Model model){


        return "/test";
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
