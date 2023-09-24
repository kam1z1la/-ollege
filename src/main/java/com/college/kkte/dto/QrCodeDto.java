package com.college.kkte.dto;

import com.college.kkte.qrcode.QRCodeGenerator;

public record QrCodeDto(
        String qrCode,
        String url
) {
}
