package wgcloud.script.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/********************************************************
 * @Package wgcloud.script.application.controller
 * @author wg
 * @date 2020/5/15 14:46
 * @version
 * @Copyright
 ********************************************************/
@Controller
@RequestMapping(value = "/test1Controller")
public class Test1 {


    @RequestMapping(value = "/test1")
    @ResponseBody
    public void test1() {
        try {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine javaScript = scriptEngineManager.getEngineByName("JavaScript");

            javaScript.eval("print('hello world')");
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/test2")
    public void test2() {
        String s = "100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        System.out.println(s.toCharArray().length);
    }

    public static void main(String[] args) {



        int i = 0;
        int n = 0;
        for (int j = 0; j < 100; j++) {
            i = i++;
            n = ++n;
        }
        System.out.println(i);//打印什么？？？
        System.out.println(n);

    }
}
