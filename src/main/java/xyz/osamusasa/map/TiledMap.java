package xyz.osamusasa.map;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TiledMap {
    /**
     * タイルの左上のX座標
     */
    private int posX;
    /**
     * タイルの左上のY座標
     */
    private int posY;

    /**
     * 幅
     */
    private int width;
    /**
     * 高さ
     */
    private int height;

    /**
     * タイルの色
     */
    private Color color;

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

    Rectangle r;

    public TiledMap(Color c) {
        this.color = c;

        this.posX = 500;
        this.posY = 500;
        this.width = 100;
        this.height = 100;

        this.isClicked = false;
    }

    /**
     * 描画
     * @param g グラフィックオブジェクト
     */
    void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(posX, posY, width, height);
    }

    /**
     * 指定された点がタイルの範囲に入っているかを判定
     * @param x 点のX座標
     * @param y 点のY座標
     * @return 指定された点がタイルの範囲に入っていたら {@code true},
     *         範囲外であれば {@code false}.
     */
    boolean contains(int x, int y) {
        int w = this.width;
        int h = this.height;
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
     * マウスリスナーを取得
     * @return {@code MouseListener} オブジェクト
     */
    MouseListener getMouseListener() {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {
                isClicked = true;
                prevX = e.getX();
                prevY = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                isClicked = false;
            }

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}
        };
    }

    /**
     * マウスモーションリスナーを取得
     * @return {@code MouseMotionListener} オブジェクト
     */
    MouseMotionListener getMouseMotionListener() {
        return new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if (isClicked) {
                    posX += e.getX() - prevX;
                    posY += e.getY() - prevY;
                    prevX = e.getX();
                    prevY = e.getY();
                }
            }

            public void mouseMoved(MouseEvent e) {}
        };
    }
}
