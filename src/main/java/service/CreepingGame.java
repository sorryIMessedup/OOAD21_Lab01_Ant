package service;

import entity.Ant;
import entity.Pole;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;
import static utils.Constants.gameState;

public class CreepingGame extends Frame {
    private Pole pole; // 杆子
    private final int poleLength; // 杆子长度
    private List<Ant> antList; // 当前蚂蚁列表
    private final int antCount; // 蚂蚁数量
    private final int velocity; // 速度
    private final double[] antPosition; // 蚂蚁位置数组
    private final int[][] antDirection; // 蚂蚁方向数组
    private static int state; // 游戏状态
    private static int round; // 游戏回合

    private int minTime = (int) 2e5;
    private int maxTime = (int) -2e5;

    private final BufferedImage bufImg = new BufferedImage(750, 420, BufferedImage.TYPE_4BYTE_ABGR);

    // Constructor
    public CreepingGame(int poleLength, int antCount, int velocity, double[] positions, int[][] antDirection) {
        this.poleLength = poleLength;
        this.antCount = antCount;
        this.velocity = velocity;
        this.antPosition = positions;
        this.antDirection = antDirection;
        initFrame();
        initGame();
    }

    // 初始化Frame
    private void initFrame() {
        setSize(750, 420); // 设置窗口大小
        setLocationRelativeTo(null); // 居中
        setTitle("Ants Creeping Game (OOAD21 Lab01)"); // 标题
        // 监听Jframe窗体关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit(0);
            }
        });
        // 窗口默认不可见，设置为可见
        setVisible(true);
    }

    // 初始化游戏中各个对象
    private void initGame() {
        PlayRoom.time = 0;
        round = 0;
        pole = new Pole(poleLength);
        antList = new ArrayList<>();

        for (int i = 0; i < antCount; i++) {
            Ant ant = new Ant(i, antDirection[round][i], velocity, (int)antPosition[i] + 100);
            antList.add(ant);
        }
        changeState(gameState.GAME_READY.ordinal());
        new Thread(() -> {
            while (round < (1 << antCount)) {
                repaint();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // repaint()会调用update()函数
    @Override
    public void update(Graphics g) {
        pole.drawPole(bufImg.getGraphics()); // 绘制木杆
        if (round < (1 << antCount)) {
            if (state == gameState.GAME_READY.ordinal()) {
                PlayRoom.time = 0;
                for (int i = 0; i < antCount && round < (1 << antCount); i++) {
                    antList.get(i).setPosition((int)antPosition[i] + 100);
                    antList.get(i).setDirection(antDirection[round][i]);
                }
                changeState(gameState.GAME_RUNNING.ordinal());
            } else if (state == gameState.GAME_RUNNING.ordinal()) {
                for (Ant ant : antList) {
                    ant.creep(pole);
                    ant.drawAnt(bufImg.getGraphics());
                }
                testCollision();
                PlayRoom.time++;
                if (isAllReach()) {
                    changeState(gameState.GAME_STOPPED.ordinal());
                }
            } else if (state == gameState.GAME_STOPPED.ordinal()) {
                if (PlayRoom.time < minTime) minTime = PlayRoom.time;
                if (PlayRoom.time > maxTime) maxTime = PlayRoom.time;
                round++;
                System.out.println(("第" + round + "轮结束，用时：" + PlayRoom.time/10));
                changeState(gameState.GAME_READY.ordinal());
            }
            g.setColor(Color.black);
            g.setFont(new Font("仿宋", Font.PLAIN, 20));
            g.drawImage(bufImg, 0, 0, null);

            if (maxTime < 0) {
                g.drawString("      最短时间：" + "无", 430, 160);
                g.drawString("      最长时间：" + "无", 430, 190);
            } else {
                g.drawString("      最短时间：" + minTime/10, 430, 160);
                g.drawString("      最长时间： " + maxTime/10, 430, 190);
            }
            g.drawString("      第" + round + "轮已耗时： " + PlayRoom.time/10, 430, 220);
            g.drawString("当前轮游戏蚂蚁初始方向为：", 50, 80);

            if (round < (1 << antCount)) {
                StringBuilder dirFont = new StringBuilder();
                for (int t : antDirection[round]) {
                    if (t == 0) dirFont.append("左 ");
                    else dirFont.append("右 ");
                }
                g.drawString(String.valueOf(dirFont), 50, 110);
            }
        }
        if (round >= (1 << antCount)) {
            g.setFont(new Font("宋体", Font.BOLD, 28));
            g.drawString("游戏结束！请关闭窗口", 100, 350);
        }
    }

    // 改变游戏状态
    private static void changeState(int gameState) {
        CreepingGame.state = gameState;
    }

    // 检测所有蚂蚁是否出界
    private boolean isAllReach() {
        for (Ant ant : antList) {
            if (ant.getPosition() > 100 && ant.getPosition() < 100 + poleLength) {
                return false;
            }
        }
        return true;
    }

    // 检测所有蚂蚁是否碰撞
    private void testCollision() {
        for (int i = 0; i < antList.size(); i++) {
            if (!antList.get(i).isReach()) {
                for (int j = i + 1; j < antList.size(); j++) {
                    if (AntCollision(i, j)) {
                        antList.get(i).changeDirection();
                        antList.get(j).changeDirection();
                        antList.get(i).setPosition(antList.get(i).getPosition() + 0.1 * velocity);
                        antList.get(j).setPosition(antList.get(j).getPosition() + 0.1 * velocity);
                        break;
                    }
                }
            }
        }
    }

    // 检测两只蚂蚁是否碰撞
    private boolean AntCollision(int id1, int id2) {
        double left, right, distance;
        // 两只蚂蚁各自的位置，找出左边的蚂蚁和右边的蚂蚁
        double posAnt1 = antList.get(id1).getPosition();
        double posAnt2 = antList.get(id2).getPosition();
        if (posAnt1 <= posAnt2) {
            left = id1;
            right = id2;
            distance = posAnt2 - posAnt1;
        } else {
            left = id2;
            right = id1;
            distance = posAnt1 - posAnt2;
        }
        // 距离为0且方向相反
        if (distance == 0 && antList.get((int)left).getDirection() + antList.get((int)right).getDirection() == 1) {
            return true;
        } else // 距离小于0.1秒内的位移，且相向而行
            return distance <= 0.1 * velocity && (antList.get((int)left).getDirection() == 1 && antList.get((int)right).getDirection() == 0);
    }
}
