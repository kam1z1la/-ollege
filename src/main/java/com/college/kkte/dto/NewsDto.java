package com.college.kkte.dto;


import com.college.kkte.mapper.Mapper;
import com.college.kkte.news.News;
import lombok.*;
import org.hibernate.engine.spi.Managed;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto implements Mapper<NewsDto, News> {
        Long id;
        String header;
        String content;
        LocalTime createTimeAt;
        LocalDate createDateAt;
        byte[] photo;

    @Override
    public NewsDto toDto(News entity) {
        return new NewsDto(entity.getId(), entity.getHeader(),
                entity.getContent(), entity.getCreateTimeAt(),
                entity.getCreateDateAt(), entity.getPhoto());
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
