package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

// BufferedImage是其Image的实现类，是一个带缓冲区图像类，主要作用是将一幅图片加载到内存中
// 方便对图像缓冲区中的图像进行操作
public class LoadBufferedImage {
    public static BufferedImage loadBufferedImage(String imgPath) {
        try {
            return ImageIO.read(new FileInputStream(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
