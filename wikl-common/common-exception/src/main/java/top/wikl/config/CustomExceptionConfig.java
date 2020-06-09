package top.wikl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wikl.exception.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置使用自定义的 errorDecoder
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:50
 * @return
 * @since V1.0
 */
@Configuration
public class CustomExceptionConfig {

    /**
     * 自定义异常解码
     *
     * @param
     * @author WangYuxiao
     * @date 19/1/18
     */
    @Bean
    public CustomErrorDecoder customerErrorDecoder() {
        CustomErrorDecoder customerErrorDecoder = new CustomErrorDecoder();
        return customerErrorDecoder;
    }

    @Bean
    public List<BaseAppException> customExceptionList() {
        List<BaseAppException> customExceptionList = new ArrayList<>();

        customExceptionList.add(matchErrorException());
        customExceptionList.add(fileContentErrorException());
        customExceptionList.add(dataBaseException());
        customExceptionList.add(caculateErrorException());
        customExceptionList.add(inputException());
        customExceptionList.add(fileExtensionException());
        customExceptionList.add(fileCodeException());
        customExceptionList.add(businessException());
        customExceptionList.add(notFoundException());
        customExceptionList.add(fileNameTooLongException());
        return customExceptionList;
    }

    public BusinessException businessException() {
        return new BusinessException();

    }

    public CaculateErrorException caculateErrorException() {
        return new CaculateErrorException();
    }

    public DataBaseException dataBaseException() {
        return new DataBaseException();
    }

    public FileCodeException fileCodeException() {
        return new FileCodeException();
    }

    public FileContentErrorException fileContentErrorException() {
        return new FileContentErrorException();
    }

    public FileExtensionException fileExtensionException() {
        return new FileExtensionException();
    }

    public FileNameTooLongException fileNameTooLongException() {
        return new FileNameTooLongException();
    }

    public InputException inputException() {
        return new InputException();
    }

    public MatchErrorException matchErrorException() {
        return new MatchErrorException();
    }

    public NotFoundException notFoundException() {
        return new NotFoundException();
    }

}
