package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 文件格式异常 包括后缀和编码
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:46
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FileCodeException extends BaseAppException {

    private static final long serialVersionUID = -4086493508007846304L;

    private String clazz = FileCodeException.class.toString();

    private String code = HttpStatus.BAD_REQUEST.value() + "";

    public FileCodeException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public FileCodeException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public FileCodeException(String message) {
        super(message);
    }

    @Override
    public FileCodeException execute(String message, AppExceptionResult exceptionResult) {

        FileCodeException exception = new FileCodeException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
