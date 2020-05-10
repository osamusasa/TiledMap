package xyz.osamusasa.map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ビットマップデータ
 *
 * 複数の画像を１つのファイルのまとめたものを処理する
 */
public class BitmapData {
    /**
     * ソース画像ファイル
     */
    private BufferedImage tile;

    /**
     * タイル１マスの幅
     */
    private int width;

    /**
     * タイル１マスの高さ
     */
    private int height;

    /**
     * コンストラクタ
     *
     * @param filePath 画像ファイルへのパス
     * @param width タイル１マスの幅
     * @param height タイル１マスの高さ
     */
    public BitmapData(String filePath, int width, int height) {
        tile = loadBitmap(filePath);
        this.width = width;
        this.height = height;
    }

    /**
     * 指定した位置のタイルの画像を取得
     *
     * @param x 取得するタイルのx座標
     * @param y 取得するタイルのy座標
     * @return 指定した位置のタイルの画像
     */
    BufferedImage getTile(int x, int y) {
        BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bimg.getGraphics();
        g.drawImage(tile.getSubimage(x*width, y*height, width, height), 0, 0, null);
        g.dispose();
        return bimg;
    }

    /**
     * 指定されたパスのファイルを読み込む
     *
     * @param fileName 開くビットマップファイルのパス
     * @return 開いた画像オブジェクト
     */
    static BufferedImage loadBitmap(String fileName) {
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
            return ImageIO.read(is);
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
     * 指定された色を透過色に置き換える
     *
     * @param img 画像
     * @param c 透過色に置き換える色
     */
    static void changeTransparent(BufferedImage img, Color c) {
        int w = img.getWidth();     //BufferedImageの幅
        int h = img.getHeight();    //BufferedImageの高さ
        int t = c.getRGB();         //透明色に変換する色のRGB値

        //RGB値を0(透明色)に置換
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (img.getRGB(x, y) == t) img.setRGB(x, y, 0);
            }
        }
    }

}
