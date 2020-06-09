package top.wikl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

/**
 * 文件格式异常 包括后缀和编码
 *
 * @EqualsAndHashCode callSuper = true,会调用父类属性
 *
 * @Data 使用该注解，会含有@EqualsAndHashCode，而该属性不会调用父类属性，因为在对象比较中会出现问题，
 *
 * 修复此问题的方法很简单：
 * 1. 使用@Getter @Setter @ToString代替@Data并且自定义equals(Object other) 和 hashCode()方法，比如有些类只需要判断主键id是否相等即足矣。
 * 2. 或者使用在使用@Data时同时加上@EqualsAndHashCode(callSuper=true)注解。
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:46
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
