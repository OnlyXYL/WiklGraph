package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 文件扩展名异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:47
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FileExtensionException extends BaseAppException {

    private static final long serialVersionUID = 3629006615656183062L;

    private String clazz = FileExtensionException.class.toString();

    private String code = HttpStatus.BAD_REQUEST.value() + "";

    public FileExtensionException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public FileExtensionException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public FileExtensionException(String message) {
        super(message);
    }

    @Override
    public FileExtensionException execute(String message, AppExceptionResult exceptionResult) {

        FileExtensionException exception = new FileExtensionException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
