package top.wikl.orientdb.service.impl.dic.process;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import top.wikl.orientdb.enums.DicType;
import top.wikl.orientdb.model.GetDicInput;
import top.wikl.orientdb.model.GetDicOutPut;
import top.wikl.orientdb.service.impl.dic.AbstractDicHandler;
import top.wikl.orientdb.utils.WiklGraphLabelUtils;
import top.wikl.utils.excel.FileUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author XYL
 * @title: EntityConceptDicProcess
 * @description: TODO
 * @date 2020/5/14 10:23
 * @return
 * @since V1.0
 */
@Service
public class EntityConceptDicProcess extends AbstractDicHandler<GetDicInput, GetDicOutPut> {

    @Resource
    private ODatabasePool pool;

    public EntityConceptDicProcess() {
        super(DicType.instance_concept_dic.getKey());
    }

    @Override
    public boolean support(String id) {

        if (DicType.instance_concept_dic.getKey().equals(id)) {
                return true;
        }

        return false;
    }

    @Override
    public GetDicOutPut dic(GetDicInput param) throws Exception {
        Map<String, String> labelMarkMap = param.getLabelMarkMap();

        Integer graphId = param.getGraphId();

        Set<String> set = new HashSet<>();

        Map<String, String> label_conceptName = new HashMap<>();

        try (ODatabaseSession session = pool.acquire();) {

            //获取所有概念下的所有属性，组成属性字典
            String sql = "select  from " + WiklGraphLabelUtils.getConceptSchema(graphId) + " limit -1";

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

                        OVertex vertex = oResultInternal.getVertexProperty(name);

                        //获取概念店
                        String conceptName = vertex.getProperty("name").toString();

                        //对应的实例 label
                        String instanceLabel = vertex.getProperty("instanceLabel").toString();

                        label_conceptName.put(instanceLabel,conceptName);

                    }

                }

                //结果不在变量中
                if (!Optional.empty().equals(optional)) {

                    //select 方式查询结果处理
                    Optional<OElement> element = item.getElement();

                    OElement oElement = element.get();

                    //判断元素是否是节点
                    boolean vertex = oElement.isVertex();

                    //元素是点
                    if (vertex) {
                        Optional<OVertex> optionalOVertex = oElement.asVertex();

                        OVertex oVertex = optionalOVertex.get();

                        //获取概念店
                        String conceptName = oVertex.getProperty("name").toString();

                        //对应的实例 label
                        String instanceLabel = oVertex.getProperty("instanceLabel").toString();

                        label_conceptName.put(instanceLabel,conceptName);
                    }
                }


            }


            //获取实例
            String instance_sql = "select  from " + WiklGraphLabelUtils.getInstanceSchema(graphId) + " limit -1";

            OResultSet instanceResult = session.execute("sql", instance_sql);

            while (instanceResult.hasNext()){

                OResult result = instanceResult.next();

                //获取元素
                Optional<OElement> optional = result.getElement();

                if (Optional.empty().equals(optional)) {

                    OResultInternal oResultInternal = (OResultInternal) result;

                    //获取返回结果key
                    Set<String> propertyNames = oResultInternal.getPropertyNames();

                    for (String name : propertyNames) {

                        OVertex vertex = oResultInternal.getVertexProperty(name);

                        //对应的实例 label
                        String instanceLabel = vertex.getProperty("label").toString();

                        //显示属性key
                        String markPro = labelMarkMap.get(instanceLabel);

                        //显示属性值
                        String markProValue = vertex.getProperty(markPro).toString();

                        set.add(markProValue+" "+ label_conceptName.get(instanceLabel));

                    }

                }

                //结果不在变量中
                if (!Optional.empty().equals(optional)) {

                    //select 方式查询结果处理
                    Optional<OElement> element = result.getElement();

                    OElement oElement = element.get();

                    //判断元素是否是节点
                    boolean vertex = oElement.isVertex();

                    //元素是点
                    if (vertex) {
                        Optional<OVertex> optionalOVertex = oElement.asVertex();

                        OVertex oVertex = optionalOVertex.get();

                        //对应的实例 label
                        String instanceLabel = oVertex.getProperty("label").toString();

                        //显示属性key
                        String markPro = labelMarkMap.get(instanceLabel);

                        //显示属性值
                        String markProValue = oVertex.getProperty(markPro) + "";

                        if (StringUtils.isNotBlank(markProValue)) {
                            set.add(markProValue+" "+ label_conceptName.get(instanceLabel));
                        }
                    }
                }

            }

        }

        String[] conceptNames = set.stream().toArray(String[]::new);

        //调用工具类
        FileUtil.writeStrToFile(conceptNames, param.getFilePath() + DicType.instance_concept_dic.getValue() + "." + param.getSuffix());

        return null;
    }
}
