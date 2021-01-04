package top.wikl.component.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.wikl.component.model.mongo.KgMongoConcept;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:23
 */
public interface KgMongoConceptRepository extends MongoRepository<KgMongoConcept,String> {
}
