package top.wikl.component.service.mongo;

import top.wikl.component.model.mongo.KgMongoConcept;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:25
 */
public interface KgConceptService {

    KgMongoConcept insert(KgMongoConcept kgMongoConcept);
}
