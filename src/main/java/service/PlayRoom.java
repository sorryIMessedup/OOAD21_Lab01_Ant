package service;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class PlayRoom {
    private static int[][] direction;
    public static int time = 0;
    public static void main(String[] args) throws InterruptedException {
        UserPanel myUserPanel = new UserPanel();
        myUserPanel.InitUserPanel();

        // 忙等待以实现同步
        System.out.println("正在等待输入中。");
        while (!myUserPanel.inputDone) { sleep(100); }
        System.out.println("输入成功。");
        direction = new int[32][5];

        // 二进制生成所有可能的方向序列
        int cnt = myUserPanel.getAntCount();
        int rounds = 1 << cnt;
        for (int i = 0; i < rounds; i++)
            for (int j = 0; j < cnt; j++)
                direction[i][cnt - j - 1] = (i >> j) % 2;

        CreepingGame game = new CreepingGame (
                myUserPanel.getPoleLength(),
                myUserPanel.getAntCount(),
                myUserPanel.getVelocity(),
                myUserPanel.getAntPosition(),
                direction
        );
    }
}