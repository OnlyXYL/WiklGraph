package top.wikl.neo4j.entity;
import java.io.Serializable;

/**
 * @ClassName: IsoDuration
 * @Description: neo4j原生接口封装-时间
 * @date: 2020/8/6 18:14
 * @author DengYaJun
*/
public class IsoDuration  implements Serializable {
    private long months;
    private  long days;
    private  long seconds;
    private  int nanoseconds;

    public long getMonths() {
        return months;
    }

    public void setMonths(long months) {
        this.months = months;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public int getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(int nanoseconds) {
        this.nanoseconds = nanoseconds;
    }
}
