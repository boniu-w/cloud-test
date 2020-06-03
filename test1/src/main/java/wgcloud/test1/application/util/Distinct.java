package wgcloud.test1.application.util;

import java.util.*;

/********************************************************
 * @Package wgcloud.test1.application.util
 * @author wg
 * @date 2020/6/1 9:27
 * @version
 * @Copyright
 ********************************************************/
public class Distinct {

    /*************************************************************
     * 字符串数组去重
     * @author: wg
     * @time: 2020/6/1 9:52
     *************************************************************/
    public static void main(String[] args) {

        String[] a = new String[1000000];

        long l = System.currentTimeMillis();

        for (int i = 0; i < a.length; i++) {
            a[i] = UUID.randomUUID().toString().replace("-", "");
        }

        long n = System.currentTimeMillis() - l;
        System.out.println("用时: " + n + "毫秒");


        HashSet<String> set = new HashSet<>(Arrays.asList(a));
        System.out.println("strings.size() : " + set.size());

    }


}
