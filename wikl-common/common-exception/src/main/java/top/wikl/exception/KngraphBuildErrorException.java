package top.wikl.exception;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.wikl.entity.AppExceptionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 图谱构建异常
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:49
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class KngraphBuildErrorException extends BaseAppException {

    private String clazz = InputException.class.toString();

    private String code = HttpStatus.BAD_REQUEST.value() + "";

    public KngraphBuildErrorException(String message, String clazz, String code) {
        super(message);
        this.clazz = clazz;
        this.code = code;
    }

    public KngraphBuildErrorException(String message, AppExceptionResult exceptionResult) {
        super(message, exceptionResult);

        this.setPath(exceptionResult.getPath());
        this.setReason(exceptionResult.getReason());
    }

    public KngraphBuildErrorException(String message) {
        super(message);
    }

    /**
     * 返回错误信息
     *
     * @param bindingResult
     * @return
     */
    public KngraphBuildErrorException(BindingResult bindingResult) {

        List<String> jsonList = new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(fieldError -> {
            jsonList.add(fieldError.getDefaultMessage());
        });

        String s = JSON.toJSONString(jsonList);
        this.setMessage(s);

    }

    @Override
    public KngraphBuildErrorException execute(String message, AppExceptionResult exceptionResult) {

        KngraphBuildErrorException exception = new KngraphBuildErrorException(message);

        exception.setMessage(message);
        exception.setPath(exceptionResult.getPath());
        exception.setReason(exceptionResult.getReason());

        return exception;
    }
}
