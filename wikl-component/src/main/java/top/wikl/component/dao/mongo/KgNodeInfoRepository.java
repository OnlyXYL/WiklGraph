package top.wikl.component.dao.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import top.wikl.component.model.mongo.KgNodeInfo;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:14
 */
public interface KgNodeInfoRepository extends MongoRepository<KgNodeInfo,String> {


    Page<KgNodeInfo> findByConceptId(String conceptId, Pageable pageable);
}
