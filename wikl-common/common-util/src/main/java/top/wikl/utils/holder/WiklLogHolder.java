package top.wikl.utils.holder;

/**
 * @author XYL
 * @title: WiklLogHolder
 * @description: TODO
 * @date 2019/10/30 18:20
 * @return
 * @since V1.0
 */
public class WiklLogHolder {

    private static final ThreadLocal<Long> timeThreadLocal = new ThreadLocal<>();

    public static void set(Long time){
        timeThreadLocal.set(time);
    }

    public static Long get(){
        return timeThreadLocal.get();
    }

    public static void remove(){
        timeThreadLocal.remove();
    }

}
