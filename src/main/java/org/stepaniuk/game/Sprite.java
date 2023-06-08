package org.stepaniuk.game;

import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
@Log4j2
public class Sprite {
    // Метод, що розрізає зображення та повертає масив BufferedImage з уже розрізаним зображенням
    private static BufferedImage[] cutImageIntoPieces(File photo, int N, int widthOfGrid, int heightOfGrid) throws IOException {
        BufferedImage image = resizeImage(ImageIO.read(photo), widthOfGrid, heightOfGrid);
        BufferedImage[] imgs = new BufferedImage[N * N];

        // Рівномірно розбиваємо оригінальне зображення на підзображення
        int subimageWidth = widthOfGrid / N;
        int subimageHeight = heightOfGrid / N;

        int current_img = 0;

        // Ітеруємось по рядках та стовпцях для кожного підзображення
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Створюємо підзображення
                imgs[current_img] = new BufferedImage(subimageWidth, subimageHeight, image.getType());
                Graphics2D img_creator = imgs[current_img].createGraphics();

                // Координати початку вихідного зображення
                int src_first_x = subimageWidth * j;
                int src_first_y = subimageHeight * i;

                // Координати кінця підзображення
                int dst_corner_x = subimageWidth * j + subimageWidth;
                int dst_corner_y = subimageHeight * i + subimageHeight;

                img_creator.drawImage(image, 0, 0, subimageWidth, subimageHeight, src_first_x, src_first_y,
                        dst_corner_x, dst_corner_y, null);
                current_img++;
            }
        }
        log.info("Картинка була розрізана на "+imgs.length+" частин");
        return imgs;
    }

    // Метод для зміни розміру зображення
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        log.info("Розмір картинки був змінений на ("+width+","+height+")");
        return resizedImage;
    }

    // Метод, що конвертує масив BufferedImage в масив типу ImageIcon
    public static ImageIcon[] convertToIcons(int sizeOfGame) {
        BufferedImage[] images;
        try {
            images = cutImageIntoPieces(new File("src/main/resources/1.png"), sizeOfGame, 500, 500);
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        ImageIcon[] icons = new ImageIcon[images.length];

        for (int i = 0; i < icons.length - 1; i++) {
            Image image = images[i];
            ImageIcon scaledIcon = new ImageIcon(image);
            icons[i] = scaledIcon;
        }

        // Встановлюємо останню ImageIcon як null, щоб представити порожній простір
        icons[icons.length - 1] = null;
        log.info("Картинка була перетворена на масив ImageIcon");
        return icons;
    }
}

