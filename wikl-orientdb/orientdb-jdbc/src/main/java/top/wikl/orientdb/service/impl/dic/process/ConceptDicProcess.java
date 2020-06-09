package top.wikl.orientdb.service.impl.dic.process;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import org.springframework.stereotype.Service;
import top.wikl.orientdb.enums.DicType;
import top.wikl.orientdb.model.GetDicInput;
import top.wikl.orientdb.model.GetDicOutPut;
import top.wikl.orientdb.service.impl.dic.AbstractDicHandler;
import top.wikl.orientdb.utils.WiklGraphLabelUtils;
import top.wikl.utils.excel.FileUtil;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 概念字典
 *
 * @author XYL
 * @title: ConceptDicProcess
 * @description: TODO
 * @date 2020/5/14 10:13
 * @return
 * @since V1.0
 */
@Service
public class ConceptDicProcess extends AbstractDicHandler<GetDicInput, GetDicOutPut> {

    @Resource
    private ODatabasePool pool;

    public ConceptDicProcess() {
        super(DicType.concept_dic.getKey());
    }

    @Override
    public boolean support(String id) {
        if (DicType.concept_dic.getKey().equals(id)) {
            return true;
        }
        return false;
    }

    @Override
    public GetDicOutPut dic(GetDicInput param) throws Exception {

        //图谱id
        Integer graphId = param.getGraphId();

        Set<String> set = new HashSet<>();

        //根据图谱信息，从图数据库中获取所有概念，生成概念字典
        try (ODatabaseSession session = pool.acquire();) {

            String sql = "select name from " + WiklGraphLabelUtils.getConceptSchema(graphId) + " limit -1";

            OResultSet resultSet = session.execute("sql", sql);

            while (resultSet.hasNext()) {

                OResult item = resultSet.next();

                //获取元素
                Optional<OElement> optional = item.getElement();

                if (Optional.empty().equals(optional)) {

                    OResultInternal oResultInternal = (OResultInternal) item;

                    //获取返回结果key
                    Set<String> propertyNames = oResultInternal.getPropertyNames();

                    for (String name : propertyNames) {

                        //概念名
                        String conceptName = oResultInternal.getProperty(name).toString();

                        set.add(conceptName+" "+conceptName);
                    }

                }

            }

        }

        String[] conceptNames = set.stream().toArray(String[]::new);

        //调用工具类
        FileUtil.writeStrToFile(conceptNames, param.getFilePath() + DicType.concept_dic.getValue() + "." + param.getSuffix());

        return null;
    }
}
