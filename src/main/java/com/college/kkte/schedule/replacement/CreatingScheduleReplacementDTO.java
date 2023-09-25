package com.college.kkte.schedule.replacement;

import com.college.kkte.mapper.Mapper;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatingScheduleReplacementDTO implements Mapper<CreatingScheduleReplacementDTO, ScheduleReplacement> {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postingDate;

    @NotNull
    private List<MultipartFile> resources;

    @Override
    public CreatingScheduleReplacementDTO toDto(ScheduleReplacement entity) {
        return CreatingScheduleReplacementDTO.builder()
                .postingDate(entity.getPostingDate())
                .build();
    }

    @Override
    public ScheduleReplacement toEntity(CreatingScheduleReplacementDTO dto) {
        return ScheduleReplacement.builder()
                .postingDate(dto.getPostingDate())
                .resource(toByteArray(dto.getResources()))
                .build();
    }

    private  byte[] toByteArray(List<MultipartFile> photos) {
        return null;
    }
}
