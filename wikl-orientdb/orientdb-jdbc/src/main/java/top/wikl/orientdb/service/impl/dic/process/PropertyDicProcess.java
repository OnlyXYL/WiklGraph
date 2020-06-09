package top.wikl.orientdb.service.impl.dic.process;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.record.OVertex;
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
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author XYL
 * @title: PropertyDicProcess
 * @description: TODO
 * @date 2020/5/14 10:19
 * @return
 * @since V1.0
 */
@Service
public class PropertyDicProcess extends AbstractDicHandler<GetDicInput, GetDicOutPut> {

    @Resource
    private ODatabasePool pool;

    public PropertyDicProcess() {
        super(DicType.property_dic.getKey());
    }

    @Override
    public boolean support(String id) {

        if (DicType.property_dic.getKey().equals(id)) {
            return true;
        }

        return false;
    }

    @Override
    public GetDicOutPut dic(GetDicInput param) throws Exception {

        Integer graphId = param.getGraphId();

        Map<String, String> labelMarkMap = param.getLabelMarkMap();

        Set<String> set = new HashSet<>();

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

                        //获取属性
                        Set<String> names = vertex.getPropertyNames();

                        for (String property : names) {

                            if (property.startsWith("p_")) {

                                String propertyValue = vertex.getProperty(property).toString();

                                set.add(propertyValue + " " + propertyValue);
                            }

                        }

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

                        String instanceLabel = oVertex.getProperty("instanceLabel").toString();

                        String markPro = labelMarkMap.get(instanceLabel);

                        //获取属性
                        Set<String> names = oVertex.getPropertyNames();

                        for (String property : names) {

                            if (property.startsWith("p_") && !property.equals(markPro)) {

                                String propertyValue = oVertex.getProperty(property).toString();

                                set.add(propertyValue + " " + propertyValue);
                            }
                        }
                    }
                }

            }

        }

        String[] conceptNames = set.stream().toArray(String[]::new);

        //调用工具类
        FileUtil.writeStrToFile(conceptNames, param.getFilePath() + DicType.property_dic.getValue() + "." + param.getSuffix());


        return null;
    }
}
