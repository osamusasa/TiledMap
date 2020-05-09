package xyz.osamusasa.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TiledMapView extends JComponent {
    /**
     * マップ
     */
    private TiledMap map;

    /**
     * コンストラクタ
     * @param map 描画するマップオブジェクト
     */
    public TiledMapView(TiledMap map) {
        this.map = map;
        setMouseListener(map);
        setMouseMotionListener(map);
        setMouseWheelListener(map);
        setKeyListener(map);
    }

    /**
     * Invoked by Swing to draw components.
     * Applications should not invoke <code>paint</code> directly,
     * but should instead use the <code>repaint</code> method to
     * schedule the component for redrawing.
     * <p>
     * This method actually delegates the work of painting to three
     * protected methods: <code>paintComponent</code>,
     * <code>paintBorder</code>,
     * and <code>paintChildren</code>.  They're called in the order
     * listed to ensure that children appear on top of component itself.
     * Generally speaking, the component and its children should not
     * paint in the insets area allocated to the border. Subclasses can
     * just override this method, as always.  A subclass that just
     * wants to specialize the UI (look and feel) delegate's
     * <code>paint</code> method should just override
     * <code>paintComponent</code>.
     *
     * @param g the <code>Graphics</code> context in which to paint
     * @see #paintComponent
     * @see #paintBorder
     * @see #paintChildren
     * @see #getComponentGraphics
     * @see #repaint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        map.draw(g);
        requestFocusInWindow();
    }

    /**
     * マウスリスナーをセット
     * @param map 対象の {@code TiledMap} オブジェクト
     */
    private void setMouseListener(final TiledMap map) {
        final MouseListener ml = map.getMouseListener();
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (map.contains(e.getX(), e.getY())) {
                    ml.mouseClicked(e);
                }
            }

            public void mousePressed(MouseEvent e) {
                if (map.contains(e.getX(), e.getY())) {
                    ml.mousePressed(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (map.contains(e.getX(), e.getY())) {
                    ml.mouseReleased(e);
                }
            }

            public void mouseEntered(MouseEvent e) {
                if (map.contains(e.getX(), e.getY())) {
                    ml.mouseEntered(e);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (map.contains(e.getX(), e.getY())) {
                    ml.mouseExited(e);
                }
            }
        });
    }

    /**
     * マウスモーションリスナーをセット
     * @param map 対象の {@code TiledMap} オブジェクト
     */
    private void setMouseMotionListener(final TiledMap map) {
        final MouseMotionListener mml = map.getMouseMotionListener();
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                mml.mouseDragged(e);
                repaint();
            }

            public void mouseMoved(MouseEvent e) {
                mml.mouseMoved(e);
            }
        });
    }

    /**
     * マウスホイールリスナーをセット
     * @param map 対象の {@code TiledMap} オブジェクト
     */
    private void setMouseWheelListener(final TiledMap map) {
        final MouseWheelListener mwl = map.getMouseWheelListener();
        addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (map.contains(e.getX(), e.getY())) {
                    mwl.mouseWheelMoved(e);
                    repaint();
                }
            }
        });
    }

    /**
     * キーリスナーをセット
     * @param map 対象の {@code TiledMap} オブジェクト
     */
    private void setKeyListener(final TiledMap map) {
        final KeyListener kl = map.getKeyListener();
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                kl.keyTyped(e);
            }

            public void keyPressed(KeyEvent e) {
                kl.keyPressed(e);
                repaint();
            }

            public void keyReleased(KeyEvent e) {
                kl.keyReleased(e);
            }
        });
    }
}
