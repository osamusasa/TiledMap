package xyz.osamusasa.map;

import java.awt.*;

public class SingleSquareTile extends TiledMap {

    /**
     * タイルの色
     */
    private Color color;

    public SingleSquareTile(Color c) {
        this.color = c;
    }

    /**
     * 描画
     * @param g グラフィックオブジェクト
     */
    @Override
    void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(posX, posY, getDrawableWidth(), getDrawableHeight());
    }
}
