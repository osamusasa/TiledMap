import xyz.osamusasa.map.SingleSquareTile;
import xyz.osamusasa.map.TiledMapView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("draw graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //from https://nat.hatenadiary.com/entry/20050513/p1
        //--
        java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        // 変数desktopBoundsにデスクトップ領域を表すRectangleが代入される
        java.awt.Rectangle desktopBounds = env.getMaximumWindowBounds();
        //--
        //windowサイズを画面の最大サイズに設定
        frame.setBounds(desktopBounds);

        TiledMapView map = new TiledMapView(new SingleSquareTile(Color.BLUE));
        frame.getContentPane().add(map);

        frame.setVisible(true);
    }
}
