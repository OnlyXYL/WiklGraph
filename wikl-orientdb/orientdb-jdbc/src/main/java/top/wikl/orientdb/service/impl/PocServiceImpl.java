package top.wikl.orientdb.service.impl;

import org.springframework.stereotype.Service;
import top.wikl.orientdb.mapper.KgConceptMapper;
import top.wikl.orientdb.model.GetDicInput;
import top.wikl.orientdb.model.KgGetLabelAndMark;
import top.wikl.orientdb.service.PocService;
import top.wikl.orientdb.service.impl.dic.DicHandler;
import top.wikl.orientdb.utils.WiklGraphLabelUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            Map<String, String> labelMarkMap = new HashMap<>(labelAndMark.size() + 1);

            if (labelMarkMap != null) {
                labelAndMark.forEach(en -> labelMarkMap.put(en.getLabel(), en.getMarkProperty()));
            }

            labelMarkMap.put(WiklGraphLabelUtils.getConceptSchema(getDicInput.getGraphId()), "name");

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
