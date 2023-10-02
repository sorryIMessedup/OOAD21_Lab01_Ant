package entity;

import utils.LoadBufferedImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.poleImgPath;

public class Pole {
    private int poleLength; // 木杆的长度
    private final BufferedImage poleImg; // 木杆图片素材

    // Constructor
    public Pole(int length) {
        this.poleLength = length;
        this.poleImg = LoadBufferedImage.loadBufferedImage(poleImgPath);
    }

    // Getters & Setters
    public int getPoleLength() {
        return poleLength;
    }

    public void setPoleLength(int poleLength) {
        this.poleLength = poleLength;
    }

    public void drawPole(Graphics g) {
        g.fillRect(0,0,750,420);
        g.drawImage(poleImg, 113, 200, poleLength + 10, 20, null);
    }
}
