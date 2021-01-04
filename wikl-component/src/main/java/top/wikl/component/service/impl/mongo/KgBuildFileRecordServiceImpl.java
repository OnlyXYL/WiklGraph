package top.wikl.component.service.impl.mongo;

import org.springframework.stereotype.Service;
import top.wikl.component.dao.mongo.KgBuildFileRecordRepository;
import top.wikl.component.model.mongo.KgBuildFileRecord;
import top.wikl.component.service.mongo.KgBuildFIleRecordService;

import javax.annotation.Resource;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:25
 */
@Service
public class KgBuildFileRecordServiceImpl implements KgBuildFIleRecordService {

    @Resource
    private KgBuildFileRecordRepository kgBuildFileRecordRepository;

    @Override
    public KgBuildFileRecord insert(KgBuildFileRecord kgBuildFileRecord) {
       return kgBuildFileRecordRepository.insert(kgBuildFileRecord);
    }
}
