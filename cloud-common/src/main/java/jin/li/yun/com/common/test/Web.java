package jin.li.yun.com.common.test;

import java.awt.*;

/**
 * @author WangJiao
 * @since 2021/07/01
 */
public class Web {
  public static void main(String[] args) {
    // 建立一个窗体的对象
    Frame w = new Frame();
    // 设置窗体大小
    w.setSize(1024, 768);
    // 设置窗体背景颜色
    w.setBackground(Color.BLACK);
    MyPanel mp = new MyPanel();
    w.add(mp);
    w.show();
  }
}

/** panel为父类，MyPanel为子类 */
class MyPanel extends Panel {
  /**
   * paint 叫做方法，Graphics是类，g是对象
   *
   * @param g g
   */
  @Override
  public void paint(Graphics g) {
    // 设置对象的颜色
    g.setColor(Color.WHITE);
    g.fillOval(30, 40, 200, 200);

    for (int i = 0; i < 300; i++) {

      g.drawString("*", (int) (Math.random() * 1024), (int) (Math.random() * 768));
      int c1, c2, c3;
      c1 = (int) (Math.random() * 255);
      c2 = (int) (Math.random() * 255);
      c3 = (int) (Math.random() * 255);

      Color c = new Color(c1, c2, c3);
      g.setColor(c);
    }
    g.setColor(Color.BLACK);
    g.fillOval(50, 40, 200, 200);
  }
}
