package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 自定义业务异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:42
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends BaseAppException {

    private String clazz = BusinessException.class.toString();

    private String code = HttpStatus.INTERNAL_SERVER_ERROR.value() + "";

    public BusinessException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public BusinessException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public BusinessException(String message) {
        super(message);
    }

    @Override
    public BusinessException execute(String message, AppExceptionResult exceptionResult) {

        BusinessException exception = new BusinessException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
