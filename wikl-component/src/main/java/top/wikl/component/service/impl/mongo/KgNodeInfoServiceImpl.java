package top.wikl.component.service.impl.mongo;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import top.wikl.component.dao.mongo.KgNodeInfoRepository;
import top.wikl.component.model.mongo.KgNodeInfo;
import top.wikl.component.service.mongo.KgNodeInfoService;

import javax.annotation.Resource;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:16
 */
@Service
public class KgNodeInfoServiceImpl implements KgNodeInfoService {

    @Resource
    private KgNodeInfoRepository kgNodeInfoRepository;

    @Override
    public KgNodeInfo insert(KgNodeInfo kgNodeInfo) {
        return kgNodeInfoRepository.insert(kgNodeInfo);
    }

    @Override
    public Page<KgNodeInfo> findList(int page, int size, String conceptId) {
        Pageable pageable = PageRequest.of(page, size);

        //实现自定义条件查询并且分页查询
        Page<KgNodeInfo> all = kgNodeInfoRepository.findByConceptId(conceptId, pageable);

        return all;
    }
}
