package top.wikl.component.service.impl.mongo;

import org.springframework.stereotype.Service;
import top.wikl.component.dao.mongo.KgMongoConceptRepository;
import top.wikl.component.model.mongo.KgMongoConcept;
import top.wikl.component.service.mongo.KgConceptService;

import javax.annotation.Resource;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:25
 */
@Service
public class KgConceptServiceImpl implements KgConceptService {

    @Resource
    private KgMongoConceptRepository kgMongoConceptRepository;

    @Override
    public KgMongoConcept insert(KgMongoConcept kgMongoConcept) {
        return kgMongoConceptRepository.insert(kgMongoConcept);
    }
}
