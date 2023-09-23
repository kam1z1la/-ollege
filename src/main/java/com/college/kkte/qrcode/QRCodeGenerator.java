package com.college.kkte.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class QRCodeGenerator {

    public BufferedImage createImage(String text, int width, int height) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public String getQRCode(BufferedImage image) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", bao);
        byte[] imageBytes = bao.toByteArray();

        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(imageBytes);
    }
}
