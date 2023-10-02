package entity;

import utils.Constants;
import utils.LoadBufferedImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.antImgPath;

public class Ant {
    private int antId; // 蚂蚁的id
    private int direction; // 方向
    private int velocity; // 速度
    private int position; // 在木杆上的位置
    private final BufferedImage antImg; // 图片路径

    // Constructor
    public Ant(int antId, int direction, int velocity, int position) {
        this.antId = antId;
        this.direction = direction;
        this.velocity = velocity;
        this.position = position;
        this.antImg = LoadBufferedImage.loadBufferedImage(antImgPath[this.antId]);
    }

    // Getters & Setters
    public int getAntId() {
        return antId;
    }

    public void setAntId(int antId) {
        this.antId = antId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    // 根据蚂蚁方向使其运动并判断是否已到达尽头
    public void creep(Pole pole) {
        if (direction == Constants.antDirection.TO_LEFT.ordinal()) {
            position -= velocity;
            if (position <= 100) { // 走到左尽头
                position = 100;
                direction = Constants.antDirection.STOPPED.ordinal();
            }
        } else if (direction == Constants.antDirection.TO_RIGHT.ordinal()) {
            position += velocity;
            if (position >= pole.getPoleLength() + 100) { // 右尽头
                position = pole.getPoleLength() + 100;
                direction = Constants.antDirection.STOPPED.ordinal();
            }
        }
    }

    // 判断蚂蚁是否已到达某端尽头
    public boolean isReach() {
        return direction == Constants.antDirection.STOPPED.ordinal();
    }

    // 改变蚂蚁行进方向
    public void changeDirection() {
        if (direction == Constants.antDirection.TO_RIGHT.ordinal()) {
            direction = Constants.antDirection.TO_LEFT.ordinal();
        } else if (direction == Constants.antDirection.TO_LEFT.ordinal()) {
            direction = Constants.antDirection.TO_RIGHT.ordinal();
        }
    }

    // 绘制蚂蚁
    public void drawAnt(Graphics graphics) {
        graphics.drawImage(antImg, position, 200, null);
    }
}
