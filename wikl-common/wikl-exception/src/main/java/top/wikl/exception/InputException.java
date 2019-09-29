package top.wikl.exception;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入参数异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:48
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InputException extends BaseAppException {

    private static final long serialVersionUID = -6783753494261912117L;

    private String clazz = InputException.class.toString();

    private String code = HttpStatus.BAD_REQUEST.value() + "";

    public InputException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public InputException(String message, AppExceptionResult exceptionResult) {
        super(message);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public InputException(String message) {
        super(message);
    }

    /**
     * 返回错误信息
     *
     * @param bindingResult
     * @return
     */
    public InputException(BindingResult bindingResult) {

        List<String> jsonList = new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(fieldError -> {
            jsonList.add(fieldError.getDefaultMessage());
        });

        String s = JSON.toJSONString(jsonList);
        this.setMessage(s);

    }

    @Override
    public InputException execute(String message, AppExceptionResult exceptionResult) {

        InputException exception = new InputException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}