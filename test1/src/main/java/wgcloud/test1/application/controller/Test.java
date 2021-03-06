package wgcloud.test1.application.controller;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wgcloud.test1.application.annotation.Log;
import wgcloud.test1.application.entity.BankFlow;
import wgcloud.test1.application.entity.Result;
import wgcloud.test1.application.service.AspectService;
import wgcloud.test1.application.util.ExcelUtil;
import wgcloud.test1.application.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;


/**
 * @author wg
 * @Package wgcloud.test1.application.controller
 * @date 2020/4/10 10:07
 * @Copyright
 */
@Controller
@RequestMapping(value = "/test")
@EnableAspectJAutoProxy
public class Test {

    private Result result;


    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {

        System.out.println(result);

        result = test2();
        String name = result.getName();


        result = new Result();
        System.out.println(result);

        System.out.println(name);

        return name;
    }

    /**
     * 通过反射取得entity属性值
     *
     * @author: wg
     * @time: 2020/4/13 16:28
     */
    @RequestMapping(value = "/getEntityParam")
    @ResponseBody
    public String getEntityParam() {


        try {
            Class<?> aClass = Class.forName(" wgcloud.test1.application.entity.Result");
            Class<Result> resultClass = Result.class;

            Field[] declaredFields = resultClass.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                String name = declaredFields[i].getName();

                Result result = resultClass.newInstance();


            }


            Object newInstance = aClass.newInstance();
            if (newInstance instanceof Result) {
                ((Result) newInstance).setName("123");
                String name = ((Result) newInstance).getName();
                System.out.println(name);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return "hello";

    }

    public Result test2() {
        result.setName("wg");
        return result;
    }

    @PostMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) {
        List<BankFlow> bankFlowList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            BankFlow bankFlow = new BankFlow();
            bankFlow.setId(UUID.randomUUID().toString());
            bankFlow.setTick("---" + i);
            bankFlowList.add(bankFlow);

        }


        System.out.println(bankFlowList.toString());

        String[] titles = {"id", "1231", "123123"};
        String fileName = "导出流水表";
        String sheetName = "导出流水";


        String[][] content = new String[bankFlowList.size()][titles.length];

        for (int i = 0; i < bankFlowList.size(); i++) {
            content[i] = new String[titles.length];
            BankFlow bankFlow = bankFlowList.get(i);
            if (bankFlow.getTick().equals("conditionExcel")) {
                content[i][0] = "123123";
                content[i][1] = "asdjhfsakdjfh";
                content[i][2] = "dkhfklsdjf";
            }

        }


        HSSFWorkbook hssfWorkbook = this.getHSSFWorkbook(sheetName, titles, content, null);

        try {

            this.setResponseHeader(response, fileName);
            OutputStream outputStream = response.getOutputStream();
            hssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @author: wg
     * @time: 2020/4/15 10:26
     */
    public HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    @RequestMapping(value = "/exportUtilTest")
    public void exportUtilTest(HttpServletResponse response) {
        List<BankFlow> list = new ArrayList<>();
        String sheetName = "123";

        for (int i = 0; i < 10; i++) {
            BankFlow bankFlow = new BankFlow();
            bankFlow.setTick("conditionExcel" + i);
            bankFlow.setTransactionAmount(i + 100);
            list.add(bankFlow);
        }


        Class<BankFlow> bankFlowClass = BankFlow.class;
        ExcelUtil excelUtil = new ExcelUtil(bankFlowClass);
        excelUtil.exportExcel(list, sheetName, response);

    }


    @Autowired
    AspectService aspectService;

    /*************************************************************
     * 切面
     * @author: wg
     * @time: 2020/4/28 13:20
     *************************************************************/
    @RequestMapping(value = "/testAspect/{userName}")
    @ResponseBody
    public void testAspect(@PathVariable String userName) {
        aspectService.add(userName);
        System.out.println("----------userName" + userName);
    }

    /*************************************************************
     * testHeaders
     * @author: wg
     * @time: 2020/5/25 18:03
     *************************************************************/
    @RequestMapping(value = "/testHeaders")
    @ResponseBody
    public String testHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            System.out.println(">>>>>  " + s);
        }

        Field[] declaredFields = headerNames.getClass().getDeclaredFields();
        String s = Arrays.toString(declaredFields);
        System.out.println(s);


        String contextPath = request.getContextPath();
        System.out.println("contextPath -> " + contextPath);

        String authType = request.getAuthType();
        System.out.println("authType -> " + authType);

        String requestedSessionId = request.getRequestedSessionId();
        System.out.println("requestedSessionId -> " + requestedSessionId);

        String remoteAddr = request.getRemoteAddr();
        System.out.println("remoteAddr -> " + remoteAddr);


        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            System.out.println("ip: " + ip);
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");

        String header = request.getHeader("Proxy-Client-IP");
        System.out.println("header: " + header);

        String header1 = request.getHeader("WL-Proxy-Client-IP");
        System.out.println("header1: " + header1);

        System.out.println("ip: " + ip);

        return ip;
    }


    /*************************************************************
     * getHostIP
     * @author: wg
     * @time: 2020/5/25 18:04
     *************************************************************/
    @RequestMapping(value = "/getHostIP")
    @ResponseBody
    private String getHostIP() {

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                System.out.println("------------------");
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    String hostName = inetAddress.getHostName();
                    System.out.println("hostName: " + hostName);

                    if (inetAddress instanceof Inet4Address) {
                        String hostAddress = inetAddress.getHostAddress();
                        System.out.println("hostAddress: " + hostAddress);


                        Field[] declaredFields = inetAddress.getClass().getFields();
                        for (Field declaredField : declaredFields) {
                            System.out.println("declaredField: " + declaredField);
                        }

                    }

                }
            }


        } catch (SocketException e) {
            e.printStackTrace();
        }


        Enumeration<NetworkInterface> allNetInterfaces = null;
        String resultIP = null;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            System.out.println("netInterface.getName(): " + netInterface.getName());


            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address) {
                    if (resultIP == null) {
                        resultIP = ip.getHostAddress();
                    }
                    System.out.println("本机地址是：" + ip.getHostAddress());

                    //if (resultIP != null || resultIP != ""){
                    //    resultIP = ip.getHostAddress();
                    //}
                }
            }
        }
        return resultIP;

    }



    /*************************************************************
     * getLocalHost
     * @author: wg
     * @time: 2020/5/25 18:04
     *************************************************************/
    @RequestMapping(value = "getHostIP1")
    @ResponseBody
    private String getHostIP1() {
        try {
            String tempIP = "127.0.0.1";
            tempIP = InetAddress.getLocalHost().getHostAddress();

            System.out.println(tempIP);

            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements()) {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements()) {
                    ip = addrs.nextElement();
                    if (ip != null
                      && ip instanceof Inet4Address
                      && ip.isSiteLocalAddress()
                      && !ip.getHostAddress().equals(tempIP)) {
                        return ip.getHostAddress();
                    }
                }
            }

            return tempIP;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
