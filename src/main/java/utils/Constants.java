package utils;

public class Constants {
    public enum antDirection {
        TO_LEFT, TO_RIGHT, STOPPED
    }
    public enum gameState {
        GAME_READY, GAME_RUNNING, GAME_STOPPED
    }

    public static final String poleImgPath="src/main/resource/img.png";
    public static final String[] antImgPath={
            "src/main/resource/antImages/4.png",
            "src/main/resource/antImages/5.png",
            "src/main/resource/antImages/6.png",
            "src/main/resource/antImages/7.png",
            "src/main/resource/antImages/8.png",
    };
}
