package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 匹配异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:32
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class MatchErrorException extends BaseAppException {

    private static final long serialVersionUID = -1499640277703726114L;

    private String clazz = MatchErrorException.class.toString();

    private String code = HttpStatus.INTERNAL_SERVER_ERROR.value() + "";

    public MatchErrorException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public MatchErrorException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public MatchErrorException(String message) {
        super(message);
    }

    @Override
    public MatchErrorException execute(String message, AppExceptionResult exceptionResult) {

        MatchErrorException exception = new MatchErrorException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
