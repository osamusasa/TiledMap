package xyz.osamusasa.map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapTile extends TiledMap {
    /**
     * ビットマップオブジェクト
     */
    private Image bitmap;

    /**
     * コンストラクタ
     * @param fileName ビットマップファイルのパス
     */
    public BitmapTile(String fileName) {
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
            bitmap = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println("対応してない種類の画像ファイル");
                }
            }
        }
    }

    /**
     * 描画関数
     *
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics g) {
        g.drawImage(bitmap, posX, posY, null);
        drawBounds(g);
    }
}
