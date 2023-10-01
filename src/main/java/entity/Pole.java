package entity;

import java.util.Objects;

public class Pole {
    private int length; // 木杆的长度

    // Constructor
    public Pole(int length) {
        this.length = length;
    }

    // Getters & Setters
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }

}
