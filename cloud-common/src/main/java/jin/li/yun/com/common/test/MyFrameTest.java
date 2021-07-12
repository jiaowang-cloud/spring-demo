package jin.li.yun.com.common.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JFrame窗体
 *
 * @author ThinkPad
 * @since 2021/07/02
 */
public class MyFrameTest extends JFrame {

  private JMenu menuFile;
  private JMenu menuEdit;

  private JMenuBar mb;

  private JMenuItem itemSave;
  private JMenuItem itemCopy;
  private JMenuItem pasteCopy;
  private JMenuItem itemClose;
  private JMenuItem itemOpen;

  public MyFrameTest() {
    super("欢迎来到...");

    Container c = getContentPane();

    mb = new JMenuBar();

    menuFile = new JMenu("文件(F)");
    menuEdit = new JMenu("编辑(E)");

    // 添加文件菜单
    itemOpen = new JMenuItem("打开");
    itemOpen.setMnemonic('O');

    itemSave = new JMenuItem("保存");
    itemSave.setMnemonic('S');

    itemClose = new JMenuItem("关闭");
    itemClose.setMnemonic('C');

    //  添加编辑
    itemCopy = new JMenuItem("复制");

    pasteCopy = new JMenuItem("粘贴");

    menuFile.addActionListener(new HandLer());

    itemOpen.addActionListener(new HandLer());

    itemSave.addActionListener(new HandLer());

    itemCopy.addActionListener(new HandLer());

    pasteCopy.addActionListener(new HandLer());

    itemClose.addActionListener(new HandLer());

    // 添加菜单项
    menuFile.add(itemOpen);
    // 在菜单中添加分隔条
    menuFile.addSeparator();
    menuFile.add(itemSave);

    menuFile.addSeparator();
    menuFile.add(itemClose);

    menuEdit.add(itemCopy);
    menuEdit.addSeparator();
    menuEdit.add(pasteCopy);
    mb.add(menuFile);
    mb.add(menuEdit);
    // 设置菜单栏
    setJMenuBar(mb);

    final JLabel label = new JLabel();

    label.setBounds(0, 0, 0, 0);

    // 窗体背景
    label.setIcon(null);

    // 获取当前屏幕大小
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    this.setBounds((screenSize.width - 400) / 2, (screenSize.height - 400) / 2, 400, 400);

    this.setVisible(true);
  }

  class HandLer implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JMenuItem mi = (JMenuItem) e.getSource();

      if (mi == itemClose) {
        // 退出程序
        System.exit(0);
      }
    }
  }

  public static void main(String[] args) {
    new MyFrameTest();
  }
}
