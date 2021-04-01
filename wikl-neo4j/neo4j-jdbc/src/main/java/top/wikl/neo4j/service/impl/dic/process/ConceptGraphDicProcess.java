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
 * 概念图谱字典
 *
 * @author XYL
 * @version 1.2
 * @since 2021/2/25 0025 10:08
 */
@Service
public class ConceptGraphDicProcess extends AbstractDicHandler<GetDicInput, GetDicOutPut> {

    @Resource
    private Neo4jUtils neo4jUtils;

    public ConceptGraphDicProcess() {
        super(DicType.concept_graph_dic.getKey());
    }

    @Override
    public boolean support(String id) {
        if (DicType.concept_graph_dic.getKey().equals(id)) {
            return true;
        }
        return false;
    }

    @Override
    public GetDicOutPut dic(GetDicInput param) throws Exception {

        //图谱id
        Integer graphId = param.getGraphId();

        //根据图谱信息，从图数据库中获取所有概念，生成概念字典
        final String conceptSchema = WiklGraphLabelUtils.getConceptSchema(graphId);

        final String cypher = String.format("MATCH (n:%s) return n.name", conceptSchema);

        //执行neo4j
        final Result<List<Map<String, Value>>> result = neo4jUtils.query(cypher);

        //处理结果
        final Set<String> set = Neo4jResult.conceptGraphDic(result, graphId);

        String[] conceptNames = set.stream().toArray(String[]::new);

        //调用工具类
        FileUtil.writeStrToFile(conceptNames, param.getFilePath() + DicType.concept_graph_dic.getValue() + "." + param.getSuffix());

        return null;
    }
}
