package com.hichemtech.android_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@SuppressWarnings("unused")
public class BarcodesGenerators {
    private static final int size_width = 660;
    private static final int size_height = 264;
    public static void generateCodeQr(
            Context context,
            String content,
            ImageView codeQrImage,
            @DrawableRes int drawable
    ) {
        codeQrImage.setImageBitmap(generateCodeQr(context, content, drawable));
    }
    public static void generateCodeQr(
            String content,
            ImageView codeQrImage,
            Bitmap logo
    ) {
        codeQrImage.setImageBitmap(generateCodeQr(content, logo));
    }
    public static void generateCodeQr(
            String content,
            ImageView codeQrImage
    ) {
        codeQrImage.setImageBitmap(generateCodeQr(content));
    }

    public static Bitmap generateCodeQr(String content) {
        return generateCodeQr(content, (Bitmap) null);
    }

    public static Bitmap generateCodeQr(
            String content,
            Bitmap logo
    ) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            if (logo == null) return bmp;
            return mergeBitmaps(logo, bmp);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap generateCodeQr(
            Context context,
            String content,
            @DrawableRes int drawable
    ) {
        Bitmap yourLogo = BitmapFactory.decodeResource(context.getResources(), drawable);
        return generateCodeQr(content, yourLogo);
    }

    public static Bitmap generateCodeBar(
            String content,
            WidthHeight widthHeight
    ) {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.CODE_128, widthHeight.getWidth(), widthHeight.getHeight());
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap generateCodeBar(
            String content
    ) {
        return generateCodeBar(content, new WidthHeight(size_width, size_height));
    }

    private static Bitmap mergeBitmaps(Bitmap logo, Bitmap qrcode) {

        Bitmap combined = Bitmap.createBitmap(qrcode.getWidth(), qrcode.getHeight(), qrcode.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        canvas.drawBitmap(qrcode, new Matrix(), null);

        Bitmap resizeLogo = Bitmap.createScaledBitmap(logo, canvasWidth / 5, canvasHeight / 5, true);
        int centreX = (canvasWidth - resizeLogo.getWidth()) /2;
        int centreY = (canvasHeight - resizeLogo.getHeight()) / 2;
        canvas.drawBitmap(resizeLogo, centreX, centreY, null);
        return combined;
    }

    public static class WidthHeight{
        private final int width;
        private final int height;

        public WidthHeight(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public WidthHeight(int sameDimension) {
            this.width = sameDimension;
            this.height = sameDimension;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
