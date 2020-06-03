package wgcloud.test1.application.util;

import java.util.Scanner;

/********************************************************
 * @Package wgcloud.test1.application.util
 * @author wg
 * @date 2020/5/27 11:03
 * @version
 * @Copyright
 ********************************************************/
public class Sum {

    public static void main(String[] args) {


        String next = "77000.00 \n" +
          "338800.00 \n" +
          "1031800.00 \n" +
          "270600.00 \n" +
          "50400.00 \n" +
          "36000.00 \n" +
          "49200.00 \n" +
          "82000.00 \n" +
          "82000.00 \n" +
          "65600.00 \n" +
          "82000.00 \n" +
          "82000.00 \n" +
          "41000.00 \n" +
          "41000.00 \n" +
          "100000.00 \n" +
          "41000.00 \n" +
          "41000.00 \n" +
          "46200.00 \n" +
          "46200.00 \n" +
          "38500.00 \n" +
          "254200.00 \n" +
          "41000.00 \n" +
          "42000.00 \n" +
          "49200.00 \n";
        String[] split = next.split("\\u000A");

        float sum = 0f;
        for (int i = 0; i < split.length; i++) {
            sum+= Float.valueOf(split[i]) ;

        }

        System.out.println("sum: "+sum);

    }
}
