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
    private int poleLength; // 杆子长度
    private List<Ant> antList; // 当前蚂蚁列表
    private int antCount; // 蚂蚁数量
    private int velocity; // 速度
    private int[] antPosition; // 蚂蚁位置数组
    private int[] antDirection; // 蚂蚁方向数组
    private static int state; // 游戏状态
    private static int round; // 游戏回合
    private int roundTime; // 一轮游戏的时间
    private int minTime = (int) 2e5;
    private int maxTime = (int) -2e5;

    private final BufferedImage bufImg = new BufferedImage(750, 420, BufferedImage.TYPE_4BYTE_ABGR);

    // Constructor
    public CreepingGame(int poleLength, int antCount, int velocity, int[] positions, int[] antDirection) {
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
        setSize(750,420); // 设置窗口大小
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
        roundTime = 0;
        round = 0;
        pole = new Pole(poleLength);
        antList = new ArrayList<>();

        for (int i = 0; i < antCount; i++) {
            Ant ant = new Ant(i,antDirection[i],velocity,antPosition[i] + 100);
            antList.add(ant);
        }
        changeState(gameState.GAME_READY.ordinal());
        new Thread(() -> {
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // repaint()会调用update()函数
    @Override
    public void update (Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("微软雅黑",Font.PLAIN,20));
        pole.drawPole(bufImg.getGraphics()); // 绘制木杆

        if (state == gameState.GAME_READY.ordinal()) {
            roundTime = 0;
            for (int i = 0; i < antCount; i++) {
                antList.get(i).setPosition(antPosition[i] + 100);
                antList.get(i).setDirection(antDirection[i]);
            }
            changeState(gameState.GAME_RUNNING.ordinal());
        }
        else if (state == gameState.GAME_RUNNING.ordinal()) {
            for (Ant ant : antList) {
                ant.creep(pole);
                ant.drawAnt(bufImg.getGraphics());
            }
            for (Ant ant2 : antList) {
                System.out.print(ant2.getPosition()+" ");
            }
            System.out.print('\n');
            testCollision();
            roundTime++;
            if (isAllReach()) {
                changeState(gameState.GAME_STOPPED.ordinal());
            }
        }
        else if (state == gameState.GAME_STOPPED.ordinal()) {
            if (roundTime < minTime) minTime = roundTime;
            if (roundTime > maxTime) maxTime = roundTime;
            round++;
            System.out.println(("第"+round+"轮结束，用时："+roundTime));
            changeState(gameState.GAME_READY.ordinal());
        }

        g.drawImage(bufImg, 0, 0, null);
        g.drawString("最短时间：" + minTime, 430, 160);
        g.drawString("最长时间： " + maxTime, 430, 210);
        g.drawString("当前轮已耗时： " + roundTime, 430, 260);
    }

    // 改变游戏状态
    private static void changeState(int gameState) {
        CreepingGame.state = gameState;
    }

    // 检测所有蚂蚁是否出界
    private boolean isAllReach() {
        for (int m = 0; m < antList.size(); m++) {
            if (antList.get(m).getPosition() > 100 && antList.get(m).getPosition() < 100 + poleLength) {
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
                        break;
                    }
                }
            }
        }
    }

    // 检测两只蚂蚁是否碰撞
    private boolean AntCollision(int id1, int id2) {
        int left, right, distance;
        // 两只蚂蚁各自的位置
        int posAnt1 = antList.get(id1).getPosition();
        int posAnt2 = antList.get(id2).getPosition();
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
        if (distance == 0 && antList.get(left).getDirection() + antList.get(right).getDirection() == 1) {
            return true;
        } else
            return distance <= velocity && (antList.get(left).getDirection() == 1 && antList.get(right).getDirection() == 0);
    }
}
