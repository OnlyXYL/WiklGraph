package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 文件内容错误
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:46
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FileContentErrorException extends BaseAppException {

    private static final long serialVersionUID = -8491727590070535225L;

    private String clazz = FileContentErrorException.class.toString();

    private String code = HttpStatus.BAD_REQUEST.value() + "";


    public FileContentErrorException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public FileContentErrorException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public FileContentErrorException(String message) {
        super(message);
    }

    @Override
    public FileContentErrorException execute(String message, AppExceptionResult exceptionResult) {

        FileContentErrorException exception = new FileContentErrorException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }

}
