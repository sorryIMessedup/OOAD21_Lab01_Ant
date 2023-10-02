package utils;

public class Constants {
    public enum antDirection {
        TO_LEFT, TO_RIGHT, STOPPED
    }
    public enum gameState {
        GAME_READY, GAME_RUNNING, GAME_STOPPED
    }

    public static final String poleImgPath="src/main/resource/poleImg.png";
    public static final String[] antLeftImgPath = {
            "src/main/resource/antLeftImages/0.png",
            "src/main/resource/antLeftImages/1.png",
            "src/main/resource/antLeftImages/2.png",
            "src/main/resource/antLeftImages/3.png",
            "src/main/resource/antLeftImages/4.png",
    };
    public static final String[] antRightImgPath = {
            "src/main/resource/antRightImages/0.png",
            "src/main/resource/antRightImages/1.png",
            "src/main/resource/antRightImages/2.png",
            "src/main/resource/antRightImages/3.png",
            "src/main/resource/antRightImages/4.png"
    };
}
