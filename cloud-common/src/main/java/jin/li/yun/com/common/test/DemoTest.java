package jin.li.yun.com.common.test;

import java.util.Scanner;

/**
 * @author WangJiao
 * @since 2021/08/03
 */
public class DemoTest {
    public static void main(String[] args){
        int n;
         long sum = 1;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();

        for (int i = 1; i <= n; ++i)
        {
            sum *= i;
        }
        System.out.printf("%d\n", sum);
    }

}
