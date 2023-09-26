package com.college.kkte.news;

import com.college.kkte.dto.NewsDto;
import com.college.kkte.qrcode.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private List<NewsDto> newsDtoList;


    @GetMapping("/main")
    public String mainPage(){
        return "main-page";
    }



    @GetMapping("/home")
    public String homePage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "3") int size,
                           Model model) {
        newsDtoList = newsService.getAllNews();
        model.addAttribute("allNews", newsDtoList)
                .addAttribute("newsOfPages",
                        newsService.getCollectionOfPages(newsDtoList, PageRequest.of(page, size)));
        return "news/home-page";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("news", new NewsDto());
        model.addAttribute("pathRequest", "/news/create");
        return "news/redactor-news-page";
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
        model.addAttribute("pathRequest", "/news/edit/"+newsDto.getId());
        return "news/redactor-news-page";
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

    @GetMapping("share{id}")
    public String sharePage(@RequestParam long id,
                               Model model) throws WriterException, IOException {

        String url = "http://localhost:9999/news/show/" + id;

        String qrCode = new QRCodeGenerator()
                .getQRCode(new QRCodeGenerator()
                        .createImage(url, 200,200));
        model.addAttribute("qrCode", qrCode);
        model.addAttribute("url", url);
        return "news/share-page";
    }
}
