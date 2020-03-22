package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import top.wikl.entity.AppExceptionResult;

/**
 * 所有异常基类
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:22
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class BaseAppException extends RuntimeException {

    /**
     * 信息
     */
    String message;

    /**
     * 异常类的路径
     */
    String path;

    String reason;

    public BaseAppException(String message) {
        super(message);
        this.message = message;
    }

    public BaseAppException(String message, AppExceptionResult exceptionResult) {
        super(message);
        this.message = message;

        this.path = exceptionResult.getPath();
        this.reason = exceptionResult.getReason();
    }

    /**
     * 判断类名是否相等
     *
     * @param className
     * @author WangYuxiao
     * @date 19/1/18
     */
    public boolean equalsClassName(String className) {
        if (this.getClass().getName().equals(className)) {
            return true;
        }
        return false;
    }

    public BaseAppException execute(String message, AppExceptionResult exceptionResult) {

        BaseAppException baseAppException = new BaseAppException(message);
        baseAppException.setMessage(message);
        baseAppException.setPath(exceptionResult.getPath());
        baseAppException.setReason(exceptionResult.getReason());

        return baseAppException;
    }

}
