package top.wikl.component.service.mongo;

import org.springframework.data.domain.Page;
import top.wikl.component.model.mongo.KgNodeInfo;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:16
 */
public interface KgNodeInfoService {

    Page<KgNodeInfo> findList(int page, int size,String conceptId);

    KgNodeInfo insert(KgNodeInfo kgNodeInfo);

}
