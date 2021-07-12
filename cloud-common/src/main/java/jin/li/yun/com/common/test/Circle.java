package jin.li.yun.com.common.test;

/**
 * Circle类
 *
 * @author WangJiao
 * @since 2021/07/02
 */
public class Circle {
  /** 圆周率π */
  private static final Double PAIN = 3.14;
  /** 圆半径 */
  private Double radius;

  public Circle() {}

  public Circle(Double radius) {
    this.radius = radius;
  }

  public Double getRadius() {
    return radius;
  }

  public void setRadius(Double radius) {
    this.radius = radius;
  }

  /**
   * 圆周长
   *
   * @return perimeter
   */
  public Double getPerimeter() throws Exception {
    if (this.radius < 0) {
      throw new Exception("半径不能小于0！！！");
    }
    return 2 * PAIN * this.radius;
  }

  /**
   * 圆面积 <br>
   * 圆面积公式π*r^2 ： 即 PAIN * (radius * radius); <br>
   * 幂函数表示法： PAIN * Math.pow(radius,2);
   *
   * @return area
   */
  public Double getArea() throws Exception {
    if (this.radius < 0) {
      throw new Exception("半径不能小于0！！！");
    }
    return PAIN * Math.pow(this.radius, 2);
  }
}
