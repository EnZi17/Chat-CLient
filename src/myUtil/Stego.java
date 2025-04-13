package myUtil;


import java.awt.image.BufferedImage;

import java.io.IOException;


public class Stego {
	
	

    public static BufferedImage encodeTextToImage(String text, BufferedImage image) throws IOException {
        return embedText(image, text);
    }

    public static String decodeTextFromImage(BufferedImage image, int length) {
        // Thử đoán độ dài tối đa là 200 ký tự, bạn có thể cải tiến bằng cách nhúng độ dài trước
        return extractText(image, length); 
    }

    private static BufferedImage embedText(BufferedImage image, String text) {
        int bitMask = 0x00000001;
        int bit;
        int x = 0;
        int y = 0;

        for (int i = 0; i < text.length(); i++) {
            bit = (int) text.charAt(i);
            for (int j = 0; j < 8; j++) {
                int flag = bit & bitMask;
                if (flag == 1) {
                    image.setRGB(x, y, image.getRGB(x, y) | 0x00000001);
                } else {
                    image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);
                }
                bit >>= 1;

                x++;
                if (x >= image.getWidth()) {
                    x = 0;
                    y++;
                }
            }
        }
        return image;
    }

    private static String extractText(BufferedImage image, int length) {
        int bitMask = 0x00000001;
        int x = 0;
        int y = 0;
        StringBuilder resString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int bit = 0;
            for (int j = 0; j < 8; j++) {
                int flag = image.getRGB(x, y) & bitMask;
                bit = bit >> 1;
                if (flag == 1) {
                    bit = bit | 0x80;
                }
                x++;
                if (x >= image.getWidth()) {
                    x = 0;
                    y++;
                }
            }
            if (bit == 0) break; // dừng lại nếu không còn ký tự
            resString.append((char) bit);
        }
        return resString.toString();
    }
}
