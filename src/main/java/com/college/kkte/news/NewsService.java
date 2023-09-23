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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService  {
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
            News oldNews = newsRepository.findById(news.getId()).orElseThrow();
            oldNews.setHeader(news.getHeader());
            oldNews.setContent(news.getContent());
            oldNews.setPhoto(news.getPhoto());
            newsRepository.save(oldNews);
        } catch (NullPointerException e) {
            log.error("Error in update news", e);
        }
    }

    public void createNews(NewsDto newsDto){
        newsRepository.save(News.builder()
                .header(newsDto.getHeader())
                .content(newsDto.getContent())
                .createTimeAt(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))
                .createDateAt(LocalDate.now())
                .photo(newsDto.getPhoto())
                .build());
    }

    public boolean isNewsExists(String header) {
        return newsRepository.findByHeader(header).isPresent();
    }

    public List<NewsDto> getAll(){
        List<NewsDto> newsDtoList = new ArrayList<>();
        NewsDto newsDto = new NewsDto();
        newsDtoList = newsDto.toDtoList(newsRepository.findAllNews());
        return newsDtoList;
    }

    public NewsDto findDtoById(Long id, List<NewsDto> listDto) {
        return listDto.stream().filter(dto -> dto.getId()   .equals(id)).findFirst()
                .orElseThrow(() -> new NullPointerException("Don't find dto"));
    }


}
