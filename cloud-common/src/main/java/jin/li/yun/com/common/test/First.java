package jin.li.yun.com.common.test;

import java.applet.Applet;
import java.awt.*;

/**
 * @author WangJiao
 * @since 2021/07/01
 */
public class First extends Applet {
  int width, height;
  /** 缓冲区对象 */
  Image image;

  Graphics g1;

  /** Applet初始化时调用 */
  @Override
  public void init() {
    // 设置背景
    setBackground(Color.black);
    this.setSize(350, 310);
    // 获得窗口宽度
    width = getSize().width;
    height = getSize().height;
    System.out.println("width: " + width);
    System.out.println("height: " + height);
    // 创建图像对象
    image = createImage(width, height);
    System.out.println("image:" + image);
    this.g1 = image.getGraphics();
  }

  @Override
  public void paint(Graphics g) {
    g1.clearRect(0, 0, width, height);
    g1.setColor(Color.blue);

    // 控制横向变化
    for (int i = 0; i <= 90; i++) {
      // 控制竖向变化
      for (int j = 0; j <= 90; j++) {
        // 转换为直角坐标
        double r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * 18;
        // 为了在中间显示，加了偏移量
        double x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + width / 2;
        // 为了在中间显示，加了偏移量
        double y = -r * Math.sin(Math.PI / 45 * j) + height / 4;
        // 绘制点
        g1.fillOval((int) x, (int) y, 2, 2);
      }
    }
    // 显示缓存区的可变Image对象
    g.drawImage(image, 0, 0, this);
  }

  public static void main(String[] args) {
    // 建立一个窗体的对象
    Frame w = new Frame();
    // 设置窗体大小
    w.setSize(1024, 768);
    // 设置窗体背景颜色
    w.setBackground(Color.BLACK);
    First mp = new First(); //
    w.add(mp);
    w.show();
  }
}
