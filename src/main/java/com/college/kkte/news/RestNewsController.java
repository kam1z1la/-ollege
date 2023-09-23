package com.college.kkte.news;

import com.college.kkte.qrcode.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

//    @GetMapping("share/{id}")
//    public boolean sharePage(@PathVariable Long id, Model model) throws WriterException, IOException {
//        String url = "http://localhost:9999/news/show/" + id;
//        model.addAttribute("url",url)
//                .addAttribute("base64Image", new QRCodeGenerator().getQRCode(
//                        new QRCodeGenerator().createImage(url, 200,200)
//                ));
//        return true;
//    }
}
