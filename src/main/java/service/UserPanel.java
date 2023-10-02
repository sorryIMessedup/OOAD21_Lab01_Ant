package service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserPanel extends JFrame {
    private int poleLength; // 木杆长度
    private int antCount; // 蚂蚁数量
    private int velocity; // 蚂蚁速度
    private double[] antPosition; // 蚂蚁的位置
    public boolean inputDone; // 是否已输入完成

    // 构建用户面板
    public void InitUserPanel() {
        inputDone = false;
        setSize(750, 420); // 窗口大小
        setLocationRelativeTo(null); // 居中
        setTitle("Ants Creeping Game (OOAD21 Lab01)"); // 标题
        setLayout(new FlowLayout(FlowLayout.CENTER, 150, 20));

        JLabel poleLengthLabel = new JLabel("木杆长度");
        poleLengthLabel.setFont(new Font("Serif", Font.BOLD, 20));
        JTextField poleLengthText = new JTextField("300", 15);
        poleLengthText.setFont(new Font(null, Font.PLAIN, 20));
        add(poleLengthLabel);
        add(poleLengthText);

        JLabel antCountLabel = new JLabel("蚂蚁数量");
        antCountLabel.setFont(new Font("Serif", Font.BOLD, 20));
        JTextField antCountText = new JTextField("5", 15);
        antCountText.setFont(new Font(null, Font.PLAIN, 20));
        add(antCountLabel);
        add(antCountText);

        JLabel velocityLabel = new JLabel("蚂蚁速度");
        velocityLabel.setFont(new Font("Serif", Font.BOLD, 20));
        JTextField velocityText = new JTextField("5", 15);
        velocityText.setFont(new Font(null, Font.PLAIN, 20));
        add(velocityLabel);
        add(velocityText);

        JLabel antPositionLabel = new JLabel("初始位置");
        antPositionLabel.setFont(new Font("Serif", Font.BOLD, 20));
        JTextField posText = new JTextField("30 80 110 160 250", 15);
        posText.setFont(new Font(null, Font.PLAIN, 20));
        add(antPositionLabel);
        add(posText);

        JButton button = new JButton("确定并开始");
        button.setFont(new Font("Serif", Font.BOLD, 20));
        add(button);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        setVisible(true);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 读取用户输入的数据
                try {
                    poleLength = Integer.parseInt(poleLengthText.getText());
                    antCount = Integer.parseInt(antCountText.getText());
                    velocity = Integer.parseInt(velocityText.getText());
                    antPosition = (stringToDoubleArr(posText.getText()));
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null,
                            "请输入合法整数！",
                            "警告",
                            JOptionPane.ERROR_MESSAGE);
                }
                Boolean flag1 = checkPoleLength(poleLength);
                Boolean flag2 = checkAntCount(antCount);
                Boolean flag3 = checkVelocity(velocity);
                Boolean flag4 = checkPositions(antPosition);
                if (flag1 && flag2 && flag3 && flag4) {
                    inputDone = true;
                    setVisible(false);
                }
            }
        });
    }

    // 将输入位置的string类型数组转化为double型数组
    private static double[] stringToDoubleArr(String str) {
        String[] arr = str.split("\\s+");
        double[] doubleArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            doubleArr[i] = Integer.parseInt(arr[i]);
        }
        return doubleArr;
    }

    // 校验柱子长度
    private Boolean checkPoleLength(int poleLength) {
        if (poleLength <= 0) {
            JOptionPane.showMessageDialog(null,
                    "输入木杆长度应大于0",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // 校验蚂蚁数量
    private Boolean checkAntCount(int antCount) {
        if (antCount <= 0 || antCount > 5) {
            JOptionPane.showMessageDialog(null,
                    "输入蚂蚁数量应当大于0",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // 校验速度
    private Boolean checkVelocity(int velocity) {
        // 速度要能够整除木杆长度
        if (velocity <= 0 || velocity > poleLength) {
            JOptionPane.showMessageDialog(null,
                    "输入的速度应当大于0",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // 校验蚂蚁位置
    private Boolean checkPositions(double[] positions) {
        if (positions.length != antCount) {
            JOptionPane.showMessageDialog(null,
                    "您输入的位置数与蚂蚁数不匹配，请确保它们一致！",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        for (int i = 0; i != positions.length; i++) {
            // 每个位置都应大于等于0，且不能超出木杆长度范围
            if (positions[i] < 0 || positions[i] > poleLength) {
                JOptionPane.showMessageDialog(null,
                        "您输入的位置不合法，请确保每个位置大于0，且不超出木杆的长度！",
                        "错误",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    // Getters
    public int getPoleLength() {
        return poleLength;
    }
    public int getAntCount() {
        return antCount;
    }
    public int getVelocity() {
        return velocity;
    }
    public double[] getAntPosition() {
        return antPosition;
    }
}
