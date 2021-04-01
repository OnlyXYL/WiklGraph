package top.wikl.neo4j.service.impl;

import org.springframework.stereotype.Service;
import top.wikl.neo4j.mapper.KgConceptMapper;
import top.wikl.neo4j.model.GetDicInput;
import top.wikl.neo4j.model.KgGetLabelAndMark;
import top.wikl.neo4j.service.PocService;
import top.wikl.neo4j.service.impl.dic.DicHandler;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @title: PocServiceImpl
 * @description: TODO
 * @date 2020/5/13 20:03
 * @return
 * @since V1.0
 */
@Service
public class PocServiceImpl implements PocService {

    @Resource
    private List<DicHandler> interfaces;

    @Resource
    private KgConceptMapper kgConceptMapper;

    @Override
    public void getDic(GetDicInput getDicInput) {

        try {

            List<KgGetLabelAndMark> labelAndMark = kgConceptMapper.getLabelAndMark(getDicInput.getGraphId());

            Map<String, String> labelMarkMap = labelAndMark.stream().collect(Collectors.toMap(KgGetLabelAndMark::getLabel, KgGetLabelAndMark::getMarkProperty));

            getDicInput.setLabelMarkMap(labelMarkMap);

            //根据不同的字典，获取不同的数据，返回控制台
            for (DicHandler handler : interfaces) {

                if (handler.support(getDicInput.getDicType().getKey())) {

                    handler.dic(getDicInput);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
