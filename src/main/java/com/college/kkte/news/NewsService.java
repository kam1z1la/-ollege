package com.college.kkte.news;

import com.college.kkte.dto.NewsDto;
import com.college.kkte.mapper.Mapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService implements Mapper<News, NewsDto> {
    private final NewsRepository newsRepository;

    public Page<NewsDto> getCollectionOfPages(List<NewsDto> list, Pageable pageable) {
        List<NewsDto> pageList = list.stream().skip(pageable.getOffset()).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(pageList, pageable, list.size());
    }

    @Modifying
    @Transactional
    public void deleteNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow();
        newsRepository.delete(news);
    }

    @Modifying
    @Transactional
    public void updateNews(NewsDto news) {
        try {
            News oldNews = newsRepository.findById(news.id()).orElseThrow();
            oldNews.setHeader(news.header());
            oldNews.setContent(news.content());
            oldNews.setPhoto(news.photo());
            newsRepository.save(oldNews);
        } catch (NullPointerException e) {
            log.error("Error in update news", e);
        }
    }

    public boolean isNewsExists(String header) {
        return newsRepository.findByHeader(header).isPresent();
    }

    public NewsDto findDtoById(Long id, List<NewsDto> listDto) {
        return listDto.stream().filter(dto -> dto.id().equals(id)).findFirst()
                .orElseThrow(() -> new NullPointerException("Don't find dto"));
    }

    @Override
    public News toEntity(NewsDto dto) {
        return News.builder()
                .id(dto.id())
                .header(dto.header())
                .content(dto.content())
                .createDateAt(dto.createDateAt())
                .createTimeAt(dto.createTimeAt())
                .photo(dto.photo())
                .build();
    }

    @Override
    public NewsDto toDto(News entity) {
        return new NewsDto(entity.getId(), entity.getHeader(),
                entity.getContent(), entity.getCreateTimeAt(),
                entity.getCreateDateAt(), entity.getPhoto());
    }
}
