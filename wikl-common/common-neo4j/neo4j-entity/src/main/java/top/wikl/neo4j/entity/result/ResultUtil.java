package top.wikl.neo4j.entity.result;

/**
 * @ClassName: ResultUtil
 * @Description: 结果工具类
 * @date: 2020/8/6 18:18
 * @author DengYaJun
*/
public class ResultUtil {
    public static final Integer PAGESIZE_MAX = 500;
    public static final Integer PAGESIZE_DEFAULT = 10;

    public static <T> Result<T> success(T t) {
        return success(t, null);
    }

    public static <T> Result<T> success(T t, ResultPageInfo resultPageInfo) {
        Result<T> result = new Result();
        result.setStatus(ResultEnum.SUCCESS);
        result.setStatus_code(200);
        result.setMsg("接口调用成功！");
        result.setData(t);
        result.setPageInfo(resultPageInfo);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(ResultEnum status, String msg) {
        Result result = new Result();
        result.setStatus(status);
        result.setStatus_code(1000); //1000,程序默认错误
        result.setMsg(msg);
        result.setData(null);
        ResultPageInfo resultPageInfo = new ResultPageInfo("0", "0", "0");
        result.setPageInfo(resultPageInfo);
        return result;
    }

    public static Result error(ResultEnum status, Integer status_code, String msg) {
        Result result = new Result();
        result.setStatus(status);
        result.setStatus_code(status_code);
        result.setMsg(msg);
        ResultPageInfo resultPageInfo = new ResultPageInfo("0", "0", "0");
        result.setPageInfo(resultPageInfo);
        return result;
    }

}
