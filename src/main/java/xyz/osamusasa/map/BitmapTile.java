package xyz.osamusasa.map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapTile extends TiledMap {
    /**
     * 背景ビットマップオブジェクト
     */
    private Image bgImg;

    /**
     * キャラクタービットマップオブジェクト
     */
    private Image characterImg;

    /**
     * キャラクターの位置のX座標
     */
    private int charX;
    /**
     * キャラクターの位置のY座標
     */
    private int charY;

    /**
     * 行
     */
    private int row;
    /**
     * 列
     */
    private int col;

    /**
     * 上下がループしてるか
     */
    private boolean isConnectUpDown;
    /**
     * 左右がループしてるか
     */
    private boolean isConnectLeftRight;

    /**
     * コンストラクタ
     * @param fileName ビットマップファイルのパス
     */
    public BitmapTile(String fileName) {
        super(300, 200);
        this.row = 2;
        this.col = 3;

        bgImg = loadBitmap(fileName);
        isConnectUpDown = true;
        isConnectLeftRight = true;
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
        Image img = bgImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);

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

        if (characterImg != null) {
            g.drawImage(
                    characterImg.getScaledInstance(
                            w,
                            h,
                            Image.SCALE_SMOOTH),
                    posX + w*charX,
                    posY + h*charY,
                    null
            );
        }

        drawBounds(g);
    }

    /**
     * マップにキャラクターをセット
     *
     * @param fileName キャラクターのビットマップファイルのパス
     */
    public void addCharacter(String fileName) {
        Image img = loadBitmap(fileName);
        BufferedImage bimg = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics g = bimg.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        changeTransparent(bimg, new Color(255, 174, 200));
        characterImg = bimg;
        charX = 0;
        charY = 0;
    }

    /**
     * 指定されたパスのファイルを読み込む
     *
     * @param fileName 開くビットマップファイルのパス
     * @return 開いた画像オブジェクト
     */
    private static Image loadBitmap(String fileName) {
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
    private static void changeTransparent(BufferedImage img, Color c) {
        int w = img.getWidth();	//BufferedImageの幅
        int h = img.getHeight();	//BufferedImageの高さ
        int t = c.getRGB();	//透明色に変換する色のRGB値

        //RGB値を0(透明色)に置換
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (img.getRGB(x, y) == t) img.setRGB(x, y, 0);
            }
        }
    }

    /**
     * キャラクターを指定方向に一マス動かす
     *
     * @param dir 方向キーのキーコード
     */
    private void characterMove(int dir) {
        switch (dir) {
            case KeyEvent.VK_LEFT:
                if (isConnectLeftRight) {
                    charX = (charX - 1 + col) % col;
                } else {
                    charX = Math.max(charX - 1, 0);
                }
                break;
            case KeyEvent.VK_UP:
                if (isConnectUpDown) {
                    charY = (charY - 1 + row) % row;
                } else {
                    charY = Math.max(charY - 1, 0);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (isConnectLeftRight) {
                    charX = (charX + 1) % col;
                } else {
                    charX = Math.min(charX + 1, col-1);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (isConnectUpDown) {
                    charY = (charY + 1) % row;
                } else {
                    charY = Math.min(charY + 1, row-1);
                }
                break;
            default:
                break;
        }
    }

    /**
     * キーが押下されたときに呼ばれる
     *
     * @param code キーコード
     */
    @Override
    protected void keyPressed(int code) {
        super.keyPressed(code);
        if (37<=code&&code<=40) {
            characterMove(code);
        }
//        System.out.println(code);
    }
}
