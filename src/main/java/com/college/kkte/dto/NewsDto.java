package com.college.kkte.dto;


import com.college.kkte.mapper.Mapper;
import com.college.kkte.news.News;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto implements Mapper<NewsDto, News> {
    private Long id;
    private String header;
    private String content;
    private LocalTime createTimeAt;
    private LocalDate createDateAt;
    private byte[] photo;

    @Override
    public NewsDto toDto(News entity) {
        return NewsDto.builder()
                .id(entity.getId())
                .header(entity.getHeader())
                .content(entity.getContent())
                .createDateAt(entity.getCreateDateAt())
                .createTimeAt(entity.getCreateTimeAt())
                .photo(entity.getPhoto())
                .build();
    }

    @Override
    public News toEntity(NewsDto dto) {
        return News.builder()
                .id(dto.getId())
                .header(dto.getHeader())
                .content(dto.getContent())
                .createDateAt(dto.getCreateDateAt())
                .createTimeAt(dto.getCreateTimeAt())
                .photo(dto.getPhoto())
                .build();
    }
}
