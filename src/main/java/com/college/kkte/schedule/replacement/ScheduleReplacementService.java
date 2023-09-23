package com.college.kkte.schedule.replacement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleReplacementService {
    private final ScheduleReplacementRepository scheduleReplacementRepository;

    public List<ScheduleReplacement> getAllScheduleReplacementByPostingDate(Date potingDate){
        return scheduleReplacementRepository.getAllByPostingDate(potingDate);
    }

    public boolean addNewScheduleReplacement(CreatingScheduleReplacementDTO dto) throws IOException {
        ScheduleReplacement scheduleReplacement = dto.toEntity(dto);
        byte[] s = dto.getResources().get(0).getResource().getContentAsByteArray();
        scheduleReplacement.setResource(s);
        scheduleReplacement = scheduleReplacementRepository.save(scheduleReplacement);
        return scheduleReplacement.getId() != null;
    }
}
