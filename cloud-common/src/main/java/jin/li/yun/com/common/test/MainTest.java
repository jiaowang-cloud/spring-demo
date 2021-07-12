package jin.li.yun.com.common.test;

import jin.li.yun.com.common.test.Circle;

import java.util.Scanner;

/**
 * @author WangJiao
 * @since 2021/07/02
 */

public class MainTest {
  public static void main(String[] args) throws Exception {

    Scanner scanner = new Scanner(System.in);
    System.out.println("========输入半径后按回车键结束【ENTER】键===========");
    System.out.print("请输入圆半径radius: ");
    double radius = scanner.nextDouble();
    Circle circle = new Circle(radius);
    Double perimeter = circle.getPerimeter();
    System.out.println("圆周长: " + perimeter);
    Double area = circle.getArea();
    System.out.println("圆面积: " + area);
  }
}
