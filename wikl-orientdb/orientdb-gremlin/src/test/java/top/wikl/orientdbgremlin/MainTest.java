package top.wikl.orientdbgremlin;

import top.wikl.orientdbgremlin.thread.KngraphThread;

public class MainTest {

    /**
     * 这个线程操作另一个线程的暂停或开始
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        KngraphThread th1 = new KngraphThread();
        Thread t1 = new Thread(th1);
        t1.start();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //thread线程暂停
        th1.wait1();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //thread线程继续运行
        th1.start1();
        //th1.wait1();
        //th1.start1();
    }

}