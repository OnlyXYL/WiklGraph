package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 计算出错异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:44
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
@Data
public class CaculateErrorException extends BaseAppException {

    private static final long serialVersionUID = 6845351144501959685L;

    private String clazz = CaculateErrorException.class.toString();

    private String code = HttpStatus.INTERNAL_SERVER_ERROR.value() + "";

    public CaculateErrorException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public CaculateErrorException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public CaculateErrorException(String message) {
        super(message);
    }

    @Override
    public CaculateErrorException execute(String message, AppExceptionResult exceptionResult) {

        CaculateErrorException exception = new CaculateErrorException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
