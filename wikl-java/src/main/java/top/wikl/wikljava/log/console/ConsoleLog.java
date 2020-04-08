package top.wikl.wikljava.log.console;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Map;

/**
 * @author XYL
 * @title: ConsoleLog
 * @description: TODO
 * @date 2020/4/7 15:36
 * @return
 * @since V1.0
 */
public class ConsoleLog {

    /**
     * 日志输出到文件和console
     *
     * @param message  日志信息
     * @param fileName 文件名
     * @return
     * @author XYL
     * @date 2020/4/7 16:10
     * @since V2.0
     */
    public static void outAll(Map<String, String> message, String fileName) throws Exception {

        PrintStream oldPrintStream = System.out;

        FileOutputStream bos = new FileOutputStream(fileName + ".txt");

        MultiOutputStream multi = new MultiOutputStream(new PrintStream(bos), oldPrintStream);

        System.setOut(new PrintStream(multi));

        for (Map.Entry<String, String> entry : message.entrySet()) {

            System.out.printf("属性：%s，值：%s", entry.getKey(), entry.getValue());
        }

    }


    /**
     * 日志输出到文件
     *
     * @param message  日志信息
     * @param fileName 文件名
     * @return
     * @author XYL
     * @date 2020/4/8 10:38
     * @since V2.0
     */
    public static void outToFile(Map<String, String> message, String fileName) throws Exception {

        FileOutputStream bos = new FileOutputStream(fileName + ".txt");

        MultiOutputStream multi = new MultiOutputStream(new PrintStream(bos));

        System.setOut(new PrintStream(multi));

        for (Map.Entry<String, String> entry : message.entrySet()) {

            System.out.printf("属性：%s，值：%s", entry.getKey(), entry.getValue());
        }
    }

    /**
     * 输出日志到控制台
     *
     * @param message 日志信息
     * @return
     * @author XYL
     * @date 2020/4/8 10:42
     * @since V2.0
     */
    public static void outToConsole(Map<String, String> message) throws Exception {

        PrintStream oldPrintStream = System.out;

        MultiOutputStream multi = new MultiOutputStream(oldPrintStream);

        System.setOut(new PrintStream(multi));

        for (Map.Entry<String, String> entry : message.entrySet()) {

            System.out.printf("属性：%s，值：%s", entry.getKey(), entry.getValue());
        }

    }

}
