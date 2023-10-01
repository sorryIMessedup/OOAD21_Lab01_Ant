package service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserPanel extends JFrame {
    private int poleLength; // 木杆长度
    private int antCount; // 蚂蚁数量
    private int velocity; // 蚂蚁速度
    private int[] antPosition; // 蚂蚁的位置

    // 构建用户面板
    public void InitUserPanel() {
        setSize(720, 480); // 窗口大小
        setLocationRelativeTo(null); // 居中
        setTitle("Ants Creeping Game (OOAD21 Lab01)"); // 标题
        setLayout(new FlowLayout(FlowLayout.CENTER, 150, 20));

        JLabel poleLengthLabel = new JLabel("木杆长度");
        poleLengthLabel.setFont(new Font("Serif", Font.BOLD, 28));
        JTextField poleLengthText = new JTextField("300", 15);
        poleLengthText.setFont(new Font(null, Font.PLAIN, 20));
        add(poleLengthLabel);
        add(poleLengthText);

        JLabel labelCount = new JLabel("蚂蚁数量");
        JTextField antCountText = new JTextField("5", 15);
        antCountText.setFont(new Font(null, Font.PLAIN, 20));
        labelCount.setFont(new Font("Serif", Font.BOLD, 28));
        add(labelCount);
        add(antCountText);

        JLabel labelVelocity = new JLabel("蚂蚁速度");
        JTextField velocityText = new JTextField("5", 15);
        velocityText.setFont(new Font(null, Font.PLAIN, 20));
        labelVelocity.setFont(new Font("Serif", Font.BOLD, 28));
        add(labelVelocity);
        add(velocityText);

        JLabel labelPos = new JLabel("初始位置");
        JTextField posText = new JTextField("30 80 110 160 250", 15);
        posText.setFont(new Font(null, Font.PLAIN, 20));
        labelPos.setFont(new Font("Serif", Font.BOLD, 28));
        add(labelPos);
        add(posText);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
    }
}
