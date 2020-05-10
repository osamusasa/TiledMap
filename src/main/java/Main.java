import xyz.osamusasa.map.BitmapData;
import xyz.osamusasa.map.BitmapTile;
import xyz.osamusasa.map.TiledMapView;

import javax.swing.*;

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

        //TiledMapView map = new TiledMapView(new SingleSquareTile(Color.BLUE));
        BitmapData bd = new BitmapData("src\\main\\resources\\tile.bmp", 100, 100);
        //BitmapTile bt = new BitmapTile("src\\main\\resources\\test.bmp");
        BitmapTile bt = new BitmapTile(bd);
        bt.addBackground(0,0);
        bt.addCharacter(1,0);
        //bt.addCharacter("src\\main\\resources\\chara.bmp");
        TiledMapView map = new TiledMapView(bt);
        frame.getContentPane().add(map);

        frame.setVisible(true);
    }
}
