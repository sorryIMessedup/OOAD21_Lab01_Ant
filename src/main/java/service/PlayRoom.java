package service;

import java.math.BigInteger;
import static java.lang.Thread.sleep;

public class PlayRoom {
    private static BigInteger time = BigInteger.valueOf(0); // 时间
    private static int round;
    public static void main(String[] args) throws InterruptedException {
        UserPanel myUserPanel = new UserPanel();
        myUserPanel.InitUserPanel();

        // 忙等待以实现同步
        System.out.println("正在等待输入中。");
        while (!myUserPanel.inputDone) { sleep(100); }
        System.out.println("输入成功。");

        int cnt = myUserPanel.getAntCount();

        int[] antDirection = {1, 1, 1, 1, 1};

        CreepingGame game = new CreepingGame(
                myUserPanel.getPoleLength(),
                myUserPanel.getAntCount(),
                myUserPanel.getVelocity(),
                myUserPanel.getAntPosition(),
                antDirection
        );
    }
}