package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 数据库交互异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:43
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class DataBaseException extends BaseAppException {

    private static final long serialVersionUID = 7314548019727403671L;

    private String clazz = DataBaseException.class.toString();

    private String code = HttpStatus.INTERNAL_SERVER_ERROR.value() + "";

    public DataBaseException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public DataBaseException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public DataBaseException(String message) {
        super(message);
    }

    @Override
    public DataBaseException execute(String message, AppExceptionResult exceptionResult) {

        DataBaseException exception = new DataBaseException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
