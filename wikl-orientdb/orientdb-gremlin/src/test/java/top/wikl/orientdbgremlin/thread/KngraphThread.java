package top.wikl.orientdbgremlin.thread;

public class KngraphThread implements Runnable {
    private String flag = "start";
    private String control = "";

    public void run() {
        // TODO Auto-generated method stub
        int i = 0;
        while (true) {
            if (flag.equals("start")) {
                i++;
                System.out.println("The KngraphThread is running " + i);
            } else if (flag.equals("wait")) {
                try {
                    System.out.println("===wait===");
                    synchronized (control) {
                        control.wait();
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void wait1() {
        this.flag = "wait";
    }

    public void start1() {
        this.flag = "start";
        if (flag.equals("start")) {
            synchronized (control) {
                control.notifyAll();
            }
        }
    }

}