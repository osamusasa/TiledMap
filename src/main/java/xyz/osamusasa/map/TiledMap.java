package xyz.osamusasa.map;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TiledMap {
    private int posX;
    private int posY;

    private int width;
    private int height;

    private Color color;

    private boolean isClicked;
    private int prevX;
    private int prevY;

    Rectangle r;

    public TiledMap(Color c) {
        this.color = c;

        this.posX = 500;
        this.posY = 500;
        this.width = 100;
        this.height = 100;

        this.isClicked = false;
    }

    void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(posX, posY, width, height);
    }

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
