package top.wikl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 异常返回结果
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:41
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppExceptionResult implements Serializable {

    private static final long serialVersionUID = 5393129000268393738L;

    private String timestamp;

    /**
     * 异常class全路径
     */
    String clazz;

    /**
     * 简洁错误信息英文
     */
    String message;

    /**
     * 可读业务错误信息
     */
    String convertedMessage;

    /**
     * RestFul服务路径
     */
    String path;

    /**
     * 产生原因,default为 @responseStatus的 reason
     */
    String reason;

    String fallBack;

    public AppExceptionResult(String message) {
        this.message = message;
    }

}
