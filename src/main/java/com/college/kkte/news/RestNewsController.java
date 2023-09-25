package com.college.kkte.news;

import com.college.kkte.dto.QrCodeDto;
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


}
