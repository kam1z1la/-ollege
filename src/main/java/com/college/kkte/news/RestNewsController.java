package com.college.kkte.news;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news/")
public class RestNewsController {
    private final NewsService newsService;

    @PostMapping("delete-new-confirming")
    public boolean deleteNew(@RequestParam long id){
        newsService.deleteNewsById(id);
        return true;
    }

}
