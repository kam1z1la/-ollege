package com.college.kkte.news;

import com.college.kkte.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Slf4j
//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private List<NewsDto> newsDtoList;

//    @GetMapping("/home")
//    public Page<NewsDto> homePage(@RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "3") int size,
//                                  Model model) {
//        List<NewsDto> list = newsService.listToDto(newsRepository.findAll());
//         return newsService.getCollectionOfPages(list, PageRequest.of(page, size));
//    }

    @GetMapping("/home")
    public String homePage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "3") int size,
                           Model model) {
        newsDtoList = new LinkedList<>();
        NewsDto newsDto = new NewsDto();
        newsDtoList = newsService.getAll();
        model.addAttribute("allNews", newsDtoList)
                .addAttribute("newsOfPages",
                        newsService.getCollectionOfPages(newsDtoList, PageRequest.of(page, size)));
        return "news/home-page";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("news", new NewsDto(null, null, null,
                null, null, null));
        return "news/create-news-page";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/create")
    public String createPage(@ModelAttribute NewsDto news) {
        newsService.createNews(news);
        log.info("The news was successfully create");
        return "redirect:/news/home";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        NewsDto newsDto = newsService.findDtoById(id, newsDtoList);
        model.addAttribute("news", newsDto);
        return "news/update-news-page";
    }

    @PostMapping("/edit/{id}")
    public String editPage(@ModelAttribute NewsDto newsDto) {
        newsService.updateNews(newsDto);
        log.info("The news was successfully update");
        return "redirect:/news/home";
    }

    @GetMapping("/show/{id}")
    public String showMorePage(@PathVariable Long id, Model model) {
        NewsDto newsDto = newsService.findDtoById(id, newsDtoList);
        model.addAttribute("news", newsDto);
        return "news/show-more";
    }

    @PostMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteNewsById(id);
            log.info("The news was successfully deleted");
        } catch (NullPointerException e) {
            log.error("News not found", e);
        }
        return "redirect:/news/home";
    }
}
