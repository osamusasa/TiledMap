package xyz.osamusasa.map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.NoSuchElementException;

/**
 * 複数のレイヤーを持つ２次元の表でタイルを表示する
 */
public class TableMap extends TiledMap {
    /**
     * 行数
     */
    private int row;
    /**
     * 列数
     */
    private int col;

    /**
     * 一枚のタイルの幅
     */
    private int unitWidth;

    /**
     * 一枚のタイルの高さ
     */
    private int unitHeight;

    /**
     * 描画するイメージの配列
     */
    private Image[][][] tiledImages;


    /**
     * コンストラクタ
     *
     * @param layer レイヤーの数
     * @param row 行数
     * @param col 列数
     * @param unitWidth 単位当たりの幅
     * @param unitHeight 単位当たりの高さ
     */
    public TableMap(int layer, int row, int col, int unitWidth, int unitHeight) {
        super(unitWidth * col, unitHeight * row);

        this.row = row;
        this.col = col;
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
        tiledImages = new Image[layer][col][row];

        fill(null);
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
    @Override
    void draw(Graphics g) {
        int w = getDrawableWidth() / col;
        int h = getDrawableHeight() / row;

        for (Image[][] imgs: tiledImages) {
            for (int j = 0; j < imgs.length; j++) {
                for (int k = 0; k < imgs[j].length; k++) {
                    if (imgs[j][k]!=null) {
                        g.drawImage(
                                imgs[j][k].getScaledInstance(w, h, Image.SCALE_SMOOTH),
                                posX + w*j,
                                posY + h*k,
                                null
                        );
                    }
                }
            }
        }
    }

    /**
     * 画像を指定されたタイルに追加する
     *
     * @param img 追加する画像
     * @param layer 追加するレイヤー
     * @param x 追加する位置のx座標
     * @param y 追加する位置にy座標
     */
    public void addImage(Image img, int layer, int x, int y) {
        tiledImages[layer][x][y] = img;
    }

    /**
     * 指定されたタイルの画像を除去する
     *
     * 指定されたタイルの画像を{@code null}にし、表示されなくする
     *
     * @param layer 追加するレイヤー
     * @param x 追加する位置のx座標
     * @param y 追加する位置にy座標
     */
    public void removeImage(int layer, int x, int y) {
        tiledImages[layer][x][y] = null;
    }

    /**
     * 指定された画像ですべてのタイルを埋める
     *
     * すべての画像は同じインスタンスを指す
     *
     * @param img 埋める画像
     */
    public void fill(Image img) {
        for (int i=0;i<tiledImages.length; i++) {
            for (int j=0; j<tiledImages[i].length; j++) {
                for (int k=0; k<tiledImages[i][j].length; k++) {
                    tiledImages[i][j][k] = img;
                }
            }
        }
    }

    /**
     * 指定されたレイヤーのすべてのタイルを指定された画像で埋める
     *
     * すべての画像は同じインスタンスを指す
     *
     * @param img 埋める画像
     * @param layer 埋めるレイヤー
     */
    public void fill(Image img, int layer) {
        for (int i=0; i<tiledImages[layer].length; i++) {
            for (int j=0; j<tiledImages[layer][i].length; j++) {
                tiledImages[layer][i][j] = img;
            }
        }
    }

    /**
     * 指定されたレイヤーのすべてのタイルを指定された画像で埋める
     *
     * {@code isDeepCopy} が {@code true} のときはそれぞれの画像は異なるインスタンスを指す
     * {@code isDeepCopy} が {@code false} のときは画像は同じインスタンスを指す
     *
     * @param img 埋める画像
     * @param layer 埋めるレイヤー
     * @param isDeepCopy 元のImageオブジェクトを元に新しいImageオブジェクトを作成するか
     */
    public void fill(Image img, int layer, boolean isDeepCopy) {
        if (isDeepCopy) {
            for (int i=0; i<tiledImages[layer].length; i++) {
                for (int j=0; j<tiledImages[layer][i].length; j++) {
                    tiledImages[layer][i][j] = deepCopy(img);
                }
            }
        } else {
            for (int i=0; i<tiledImages[layer].length; i++) {
                for (int j=0; j<tiledImages[layer][i].length; j++) {
                    tiledImages[layer][i][j] = img;
                }
            }
        }
    }

    /**
     * 表示されたウィンドウ内の位置から対応する画像を返す
     *
     * 複数のレイヤがあるときは{@code null}でない一番上のレイヤーの画像を返す
     *
     * @param x ウィンドウ内の位置のx座標
     * @param y ウィンドウ内の位置のy座標
     * @return 位置に対応する画像
     */
    protected Image getDisplayedTile(int x, int y) throws NoSuchElementException {
        int w = getDrawableWidth() / col;
        int h = getDrawableHeight() / row;

        for (int j = 0; j < col; j++) {
            if (posX + w*j <= x && x < posX + w*(j+1)) {
                for (int k = 0; k < row; k++) {
                    if (posY + h*k <= y && y < posY + h*(k+1)) {
                        return top(j,k);
                    }
                }
            }
        }

        throw new NoSuchElementException("範囲外");
    }

    /**
     * ゲッター {@code row}
     * @return 行数
     */
    protected int getRow(){
        return row;
    }
    /**
     * ゲッター {@code col}
     * @return 列数
     */
    protected int getCol(){
        return col;
    }

    /**
     * 指定された位置のタイルの複数のレイヤーのうち {@code null} でない一番上の画像が返される
     *
     * @param x x座標
     * @param y y座標
     * @return {@code null} でない一番上の画像
     */
    protected Image top(int x, int y) {
        for (int i=tiledImages.length-1; i>=0; i--) {
            if (tiledImages[i][x][y] != null) {
                return tiledImages[i][x][y];
            }
        }

        return null;
    }

    //----------------
    //staticオブジェクト
    //----------------

    /**
     * Imageオブジェクトのディープコピーを作成する
     *
     * {@see https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage}
     *
     * @param img コピー元のImageオブジェクト
     * @return コピー先のImageオブジェクト
     */
    public static BufferedImage deepCopy(Image img) {
        BufferedImage bi = createBufferedImage(img);
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * ImageオブジェクトからBufferedImageオブジェクトに変換する
     *
     * {@see http://www.ne.jp/asahi/hishidama/home/tech/java/image}
     *
     * @param img Imageオブジェクト
     * @return BufferedImageオブジェクト
     */
    public static BufferedImage createBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage)img;
        }
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics g = bimg.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return bimg;
    }
}
