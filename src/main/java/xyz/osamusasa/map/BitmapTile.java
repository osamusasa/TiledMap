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
     * 行
     */
    private int row;
    /**
     * 列
     */
    private int col;

    /**
     * コンストラクタ
     * @param fileName ビットマップファイルのパス
     */
    public BitmapTile(String fileName) {
        super(300, 200);
        this.row = 2;
        this.col = 3;

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
     * 表示する枠を描画
     *
     * @param g グラフィックオブジェクト
     */
    @Override
    protected void drawBounds(Graphics g) {
        super.drawBounds(g);
        for (int i=1; i<row; i++) {
            g.drawLine(
                    posX,
                    posY + getDrawableHeight()/row*i,
                    posX + getDrawableWidth(),
                    posY + getDrawableHeight()/row*i
            );
        }
        for (int i=1; i<col; i++) {
            g.drawLine(
                    posX + getDrawableWidth()/col*i,
                    posY,
                    posX + getDrawableWidth()/col*i,
                    posY + getDrawableHeight()
            );
        }
    }

    /**
     * 描画関数
     *
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics g) {
        int w = getDrawableWidth() / col;
        int h = getDrawableHeight() / row;
        Image img = bitmap.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                g.drawImage(
                        img,
                        posX + w*j,
                        posY + h*i,
                        null
                );
            }
        }

        drawBounds(g);
    }
}
