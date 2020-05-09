package xyz.osamusasa.map;

import java.awt.*;
import java.awt.event.*;

public abstract class TiledMap {
    /**
     * タイルの左上のX座標
     */
    protected int posX;
    /**
     * タイルの左上のY座標
     */
    protected int posY;

    /**
     * 幅
     */
    private int width;
    /**
     * 高さ
     */
    private int height;

    /**
     * ドラッグ中であるか
     */
    private boolean isClicked;
    /**
     * ドラッグ中の前回位置のX座標
     */
    private int prevX;
    /**
     * ドラッグ中の前回位置のY座標
     */
    private int prevY;

    /**
     * 拡大倍率
     */
    private double magnification;

    /**
     * コンストラクタ
     */
    public TiledMap() {
        this.posX = 500;
        this.posY = 500;
        this.width = 100;
        this.height = 100;

        this.isClicked = false;
        this.magnification = 1.0;
    }

    /**
     * コンストラクタ
     * @param width 幅
     * @param height 高さ
     */
    public TiledMap(int width, int height) {
        this();

        this.width = width;
        this.height = height;
    }

    /**
     * 描画関数
     * @param g グラフィックオブジェクト
     */
    abstract void draw(Graphics g);

    /**
     * 表示する枠を描画
     * @param g グラフィックオブジェクト
     */
    protected void drawBounds(Graphics g) {
        g.drawRect(posX, posY, getDrawableWidth(), getDrawableHeight());
    }

    /**
     * 指定された点がタイルの範囲に入っているかを判定
     * @param x 点のX座標
     * @param y 点のY座標
     * @return 指定された点がタイルの範囲に入っていたら {@code true},
     *         範囲外であれば {@code false}.
     */
    boolean contains(int x, int y) {
        int w = (int)(this.width * magnification);
        int h = (int)(this.height * magnification);
//        if ((w | h) < 0) {
//            // At least one of the dimensions is negative...
//            return false;
//        }
        // Note: if either dimension is zero, tests below must return false...
        int x_ = this.posX;
        int y_ = this.posY;
        if (x < x_ || y < y_) {
            return false;
        }
        w += x_;
        h += y_;
        //    overflow  || intersect
        return ((w < x_ || w > x) &&
                (h < y_ || h > y));
    }

    /**
     * 描画されるときの幅を取得
     * @return 描画されるときの幅
     */
    int getDrawableWidth() {
        return (int)(width * magnification);
    }

    /**
     * 描画されるときの高さを取得
     * @return 描画されるときの高さ
     */
    int getDrawableHeight() {
        return (int)(height * magnification);
    }

    /**
     * マウスリスナーを取得
     * @return {@code MouseListener} オブジェクト
     */
    final MouseListener getMouseListener() {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                TiledMap.this.mouseClicked(e.getX(), e.getY());
            }

            public void mousePressed(MouseEvent e) {
                TiledMap.this.mousePressed(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
                TiledMap.this.mouseReleased(e.getX(), e.getY());
            }

            public void mouseEntered(MouseEvent e) {
                TiledMap.this.mouseEntered(e.getX(), e.getY());
            }

            public void mouseExited(MouseEvent e) {
                TiledMap.this.mouseExited(e.getX(), e.getY());
            }
        };
    }

    /**
     * マウスモーションリスナーを取得
     * @return {@code MouseMotionListener} オブジェクト
     */
    final MouseMotionListener getMouseMotionListener() {
        return new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                TiledMap.this.mouseDragged(e.getX(), e.getY());
            }

            public void mouseMoved(MouseEvent e) {
                TiledMap.this.mouseMoved(e.getX(), e.getY());
            }
        };
    }

    /**
     * マウスホイールリスナーを取得
     * @return {@code MouseWheelListener} オブジェクト
     */
    final MouseWheelListener getMouseWheelListener(){
        return new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                TiledMap.this.mouseWheelMoved(e.getWheelRotation());
            }
        };
    }

    /**
     * キーリスナーを取得
     * @return {@code KeyListener} オブジェクト
     */
    final KeyListener getKeyListener(){
        return new KeyListener() {
            public void keyTyped(KeyEvent e) {
                TiledMap.this.keyTyped(e.getKeyCode());
            }

            public void keyPressed(KeyEvent e) {
                TiledMap.this.keyPressed(e.getKeyCode());
            }

            public void keyReleased(KeyEvent e) {
                TiledMap.this.keyReleased(e.getKeyCode());
            }
        };
    }

    /**
     * マウスが左クリックされたときに呼ばれる
     * @param x カーソルの位置のx座標
     * @param y カーソルの位置のy座標
     */
    protected void mouseClicked(int x, int y) {}
    /**
     * マウスが押下されたときに呼ばれる
     * @param x カーソルの位置のx座標
     * @param y カーソルの位置のy座標
     */
    protected void mousePressed(int x, int y) {
        isClicked = true;
        prevX = x;
        prevY = y;
    }
    /**
     * マウスが離れたときに呼ばれる
     * @param x カーソルの位置のx座標
     * @param y カーソルの位置のy座標
     */
    protected void mouseReleased(int x, int y) {
        isClicked = false;
    }
    /**
     * マウスが領域に入ったときに呼ばれる
     * @param x カーソルの位置のx座標
     * @param y カーソルの位置のy座標
     */
    protected void mouseEntered(int x, int y) {}
    /**
     * マウスが領域から出たときに呼ばれる
     * @param x カーソルの位置のx座標
     * @param y カーソルの位置のy座標
     */
    protected void mouseExited(int x, int y) {}
    /**
     * マウスがドラッグしている間呼ばれる
     * @param x カーソルの位置のx座標
     * @param y カーソルの位置のy座標
     */
    protected void mouseDragged(int x, int y) {
        if (isClicked) {
            posX += x - prevX;
            posY += y - prevY;
            prevX = x;
            prevY = y;
        }
    }
    /**
     * マウスが動いている間呼ばれる
     * @param x カーソルの位置のx座標
     * @param y カーソルの位置のy座標
     */
    protected void mouseMoved(int x, int y) {}
    /**
     * マウスのホイールが動いている間呼ばれる
     * @param rotation ホイールが動いている方向
     */
    protected void mouseWheelMoved(int rotation) {
        magnification *= ( 1 - rotation * 0.1);
    }
    /**
     * キーがタイプされたときに呼ばれる
     * @param code キーコード
     */
    protected void keyTyped(int code) {}
    /**
     * キーが押下されたときに呼ばれる
     * @param code キーコード
     */
    protected void keyPressed(int code) {}
    /**
     * キーが離れたときに呼ばれる
     * @param code キーコード
     */
    protected void keyReleased(int code) {}

}
