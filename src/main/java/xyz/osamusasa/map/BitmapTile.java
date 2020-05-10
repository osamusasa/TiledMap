package xyz.osamusasa.map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * ビットマップを表示するタイル
 */
public class BitmapTile extends TiledMap {
    /**
     * ビットマップデータ
     */
    private BitmapData bitmapData;

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
     * プライベートコンストラクタ
     *
     * コンストラクタの共通処理
     */
    private BitmapTile(){
        super(300, 200);
        this.row = 2;
        this.col = 3;

        isConnectUpDown = true;
        isConnectLeftRight = true;
    }

    /**
     * コンストラクタ
     * @param fileName ビットマップファイルのパス
     */
    public BitmapTile(String fileName) {
        this();

        bgImg = BitmapData.loadBitmap(fileName);

    }

    /**
     * コンストラクタ
     *
     * @param bitmapData 使用する {@code BitmapData} オブジェクト
     */
    public BitmapTile(BitmapData bitmapData) {
        this();

        this.bitmapData = bitmapData;
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
     * マップに背景をセット
     *
     * @param x 背景の画像の {@code BitmapData} オブジェクトのx座標
     * @param y 背景の画像の {@code BitmapData} オブジェクトのy座標
     */
    public void addBackground(int x, int y) {
        bgImg = bitmapData.getTile(x,y);
    }

    /**
     * マップにキャラクターをセット
     *
     * @param fileName キャラクターのビットマップファイルのパス
     */
    public void addCharacter(String fileName) {
        addCharacter(BitmapData.loadBitmap(fileName));
    }

    /**
     * マップにキャラクターをセット
     *
     * @param x キャラクターの画像の {@code BitmapData} オブジェクトのx座標
     * @param y キャラクターの画像の {@code BitmapData} オブジェクトのy座標
     */
    public void addCharacter(int x, int y) {
        addCharacter(bitmapData.getTile(x,y));
    }

    /**
     * マップにキャラクターをセット
     *
     * @param image キャラクターを表す画像
     */
    private void addCharacter(BufferedImage image) {
        BitmapData.changeTransparent(image, new Color(255, 174, 200));
        characterImg = image;
        charX = 0;
        charY = 0;
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
