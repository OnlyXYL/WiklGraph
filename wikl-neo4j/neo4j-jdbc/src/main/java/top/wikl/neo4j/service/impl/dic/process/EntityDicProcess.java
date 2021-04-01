package top.wikl.neo4j.service.impl.dic.process;

import org.springframework.stereotype.Service;
import top.wikl.neo4j.WiklGraphLabelUtils;
import top.wikl.neo4j.entity.Value;
import top.wikl.neo4j.entity.result.Result;
import top.wikl.neo4j.enums.DicType;
import top.wikl.neo4j.model.GetDicInput;
import top.wikl.neo4j.model.GetDicOutPut;
import top.wikl.neo4j.service.impl.dic.AbstractDicHandler;
import top.wikl.neo4j.utils.Neo4jResult;
import top.wikl.neo4j.utils.Neo4jUtils;
import top.wikl.utils.excel.FileUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author XYL
 * @title: EntityDicProcess
 * @description: TODO
 * @date 2020/5/14 10:20
 * @return
 * @since V1.0
 */
@Service
public class EntityDicProcess extends AbstractDicHandler<GetDicInput, GetDicOutPut> {

    @Resource
    private Neo4jUtils neo4jUtils;

    public EntityDicProcess() {
        super(DicType.instance_dic.getKey());
    }

    @Override
    public boolean support(String id) {

        if (DicType.instance_dic.getKey().equals(id)) {
            return true;
        }

        return false;
    }

    @Override
    public GetDicOutPut dic(GetDicInput param) throws Exception {

        Map<String, String> labelMarkMap = param.getLabelMarkMap();

        Integer graphId = param.getGraphId();

        final String instanceSchema = WiklGraphLabelUtils.getInstanceSchema(graphId);

        //查询实例
        final String instance_cypher = String.format("MATCH (n:%s) return n", instanceSchema);

        //执行neo4j
        final Result<List<Map<String, Value>>> result = neo4jUtils.query(instance_cypher);

        //处理结果
        final Set<String> set = Neo4jResult.entityDic(result, labelMarkMap);

        String[] conceptNames = set.stream().toArray(String[]::new);

        //调用工具类
        FileUtil.writeStrToFile(conceptNames, param.getFilePath() + DicType.instance_dic.getValue() + "." + param.getSuffix());

        return null;
    }
}
