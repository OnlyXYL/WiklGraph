package top.wikl.component.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.wikl.component.model.mongo.KgBuildFileRecord;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:23
 */
public interface KgBuildFileRecordRepository extends MongoRepository<KgBuildFileRecord,String> {
}
