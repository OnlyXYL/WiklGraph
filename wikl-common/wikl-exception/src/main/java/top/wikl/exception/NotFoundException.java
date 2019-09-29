package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 无数据异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:49
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class NotFoundException extends BaseAppException {

    private static final long serialVersionUID = 129802202779921394L;

    private String clazz = NotFoundException.class.toString();

    private String code = HttpStatus.INTERNAL_SERVER_ERROR.value() + "";

    public NotFoundException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public NotFoundException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public NotFoundException execute(String message, AppExceptionResult exceptionResult) {

        NotFoundException exception = new NotFoundException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}