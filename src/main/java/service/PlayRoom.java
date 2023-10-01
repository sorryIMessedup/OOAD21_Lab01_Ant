package service;

import static java.lang.Thread.sleep;

public class PlayRoom {
    public static void main(String[] args) throws InterruptedException {
        UserPanel myUserPanel = new UserPanel();
        myUserPanel.InitUserPanel();
        System.out.println("正在等待输入中。");
        while (!myUserPanel.inputDone) {
            sleep(100);
        }
        System.out.println("输入成功。");

        CreepingGame game = new CreepingGame();
    }
}