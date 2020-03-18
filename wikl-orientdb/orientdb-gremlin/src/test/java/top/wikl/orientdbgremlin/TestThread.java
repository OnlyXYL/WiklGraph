package top.wikl.orientdbgremlin;

import lombok.extern.slf4j.Slf4j;
import top.wikl.orientdbgremlin.thread.MyThread;

/**
 * @author XYL
 * @Title: TestThread
 * @ProjectName WiklGraph
 * @Description: TODO
 * @date 2019/5/1315:00
 */
@Slf4j
public class TestThread {

    public static void main(String[] args) throws Exception {

        MyThread myThread = new MyThread() {
            protected void runPersonelLogic() {

                for (int i = 0; i < 5000; i++) {
                    log.info("i --> {}", i);
                }
                this.setSuspend(true);
                log.info("线程状态--> {}", " running");
            }
        };


        myThread.start();
        Thread.sleep(10);
        myThread.setSuspend(true);

        log.info("线程 state--> {}", myThread.getState());

        log.info("myThread has stopped");
//        myThread.setSuspend(false);

        log.info("线程 state--> {}", myThread.getState());

    }

}
