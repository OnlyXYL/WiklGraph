package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 文件名过长异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:47
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FileNameTooLongException extends BaseAppException {

    private static final long serialVersionUID = -1268996398127446960L;

    private String clazz = FileNameTooLongException.class.toString();

    private String code = HttpStatus.BAD_REQUEST.value() + "";

    public FileNameTooLongException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public FileNameTooLongException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public FileNameTooLongException(String message) {
        super(message);
    }

    @Override
    public FileNameTooLongException execute(String message, AppExceptionResult exceptionResult) {

        FileNameTooLongException exception = new FileNameTooLongException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
