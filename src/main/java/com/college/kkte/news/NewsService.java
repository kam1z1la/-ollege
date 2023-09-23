package com.college.kkte.news;

import com.college.kkte.dto.NewsDto;
import com.college.kkte.qrcode.QRCodeGenerator;
import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public Page<NewsDto> getCollectionOfPages(List<NewsDto> dtoList, Pageable pageable) {
        List<NewsDto> pageList = dtoList.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(pageList, pageable, dtoList.size());
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

    public void createNews(NewsDto newsDto) {
        newsRepository.save(News.builder()
                .header(newsDto.getHeader())
                .content(newsDto.getContent())
                .createTimeAt(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))
                .createDateAt(LocalDate.now())
                .photo(newsDto.getPhoto())
                .build());
    }

    public List<NewsDto> getAllNews() {
        return new NewsDto().toDtoList(newsRepository.findAll());
    }

    public String generateQRCode(String url) throws WriterException, IOException {
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        BufferedImage image = qrCodeGenerator.createImage(url, 200, 200);
        return qrCodeGenerator.getQRCode(image);
    }

    public NewsDto findDtoById(Long id, List<NewsDto> listDto) {
        return listDto.stream().filter(dto -> dto.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NullPointerException("Don't find dto"));
    }

    public News findByIdHeader(String header) {
        return newsRepository.findByHeader(header).get();
    }
}
