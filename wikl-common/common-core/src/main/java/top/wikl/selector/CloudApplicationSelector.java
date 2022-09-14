package top.wikl.selector;

import cn.com.bmsmart.configure.MyAuthExceptionConfigure;
import cn.com.bmsmart.configure.MyOAuth2FeignConfigure;
import cn.com.bmsmart.configure.MyServerProtectConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ProjectName CloudApplicationSelector
 * @author Administrator
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/4/3 0003 19:27
 */
public class CloudApplicationSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                MyAuthExceptionConfigure.class.getName(),
                MyOAuth2FeignConfigure.class.getName(),
                MyServerProtectConfigure.class.getName()
        };
    }
}