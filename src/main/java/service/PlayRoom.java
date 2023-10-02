package service;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class PlayRoom {
    private static int[][] direction;
    public static void main(String[] args) throws InterruptedException {
        UserPanel myUserPanel = new UserPanel();
        myUserPanel.InitUserPanel();

        // 忙等待以实现同步
        System.out.println("正在等待输入中。");
        while (!myUserPanel.inputDone) { sleep(100); }
        System.out.println("输入成功。");
        System.out.println(Arrays.toString(myUserPanel.getAntPosition()));
        direction = new int[32][5];

        int cnt = myUserPanel.getAntCount();
        int rounds = 1 << cnt;

        for (int i = 0; i < rounds; i++) {
            for (int j = 0; j < cnt; j++) {
                direction[i][cnt - j - 1] = (i >> j) % 2;
            }
        }
        System.out.println(Arrays.deepToString(direction));
        CreepingGame game = new CreepingGame (
                myUserPanel.getPoleLength(),
                myUserPanel.getAntCount(),
                myUserPanel.getVelocity(),
                myUserPanel.getAntPosition(),
                direction
        );

    }
}