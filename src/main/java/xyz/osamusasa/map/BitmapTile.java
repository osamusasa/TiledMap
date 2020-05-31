package xyz.osamusasa.map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * ビットマップを表示するタイル
 */
public class BitmapTile extends TableMap {
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
        super(2, 2, 3, 100, 100);

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
     * 描画関数
     *
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics g) {
        super.draw(g);
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
        fill(bgImg, 0);
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
        addImage(characterImg, 1, charX, charY);
    }

    /**
     * キャラクターを指定方向に一マス動かす
     *
     * @param dir 方向キーのキーコード
     */
    private void characterMove(int dir) {
        removeImage(1, charX, charY);
        switch (dir) {
            case KeyEvent.VK_LEFT:
                if (isConnectLeftRight) {
                    charX = (charX - 1 + getCol()) % getCol();
                } else {
                    charX = Math.max(charX - 1, 0);
                }
                break;
            case KeyEvent.VK_UP:
                if (isConnectUpDown) {
                    charY = (charY - 1 + getRow()) % getRow();
                } else {
                    charY = Math.max(charY - 1, 0);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (isConnectLeftRight) {
                    charX = (charX + 1) % getCol();
                } else {
                    charX = Math.min(charX + 1, getCol()-1);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (isConnectUpDown) {
                    charY = (charY + 1) % getRow();
                } else {
                    charY = Math.min(charY + 1, getRow()-1);
                }
                break;
            default:
                break;
        }
        addImage(characterImg, 1, charX, charY);
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
