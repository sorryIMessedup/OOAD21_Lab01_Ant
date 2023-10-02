package entity;

import utils.Constants;

import java.awt.*;

public class Ant {
    private int antId; // 蚂蚁的id
    private int direction; // 方向
    private int velocity; // 速度
    private int position; // 在木杆上的位置

    // Constructor
    public Ant(int antId, int direction, int velocity, int position) {
        this.antId = antId;
        this.direction = direction;
        this.velocity = velocity;
        this.position = position;
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

    // 判断蚂蚁是否已到达某端尽头
    public boolean isReach() {
        return direction == Constants.antDirection.STOP.ordinal();
    }

    // 改变蚂蚁行进方向
    public void changeDirection() {
        if(direction == Constants.antDirection.TO_RIGHT.ordinal()) {
            direction = Constants.antDirection.TO_LEFT.ordinal();
        }else if(direction == Constants.antDirection.TO_LEFT.ordinal()) {
            direction = Constants.antDirection.TO_RIGHT.ordinal();
        }
    }
    // 绘制蚂蚁
    public void drawAnt(Graphics graphics) {
        graphics.setColor(new Color(124,234,142));
        graphics.drawImage(antImg, position,200,null);
    }
}
