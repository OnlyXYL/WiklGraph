package top.wikl.neo4j.service.impl.dic.process;

import org.springframework.stereotype.Service;
import top.wikl.neo4j.enums.DicType;
import top.wikl.neo4j.model.GetDicInput;
import top.wikl.neo4j.model.GetDicOutPut;
import top.wikl.neo4j.service.impl.dic.AbstractDicHandler;

/**
 * @author XYL
 * @title: EnumDicProcess
 * @description: TODO
 * @date 2020/5/14 10:24
 * @return
 * @since V1.0
 */
@Service
public class EnumDicProcess extends AbstractDicHandler<GetDicInput, GetDicOutPut> {

    public EnumDicProcess() {
        super(DicType.enum_dic.getKey());
    }

    @Override
    public boolean support(String id) {

        if (DicType.enum_dic.getKey().equals(id)) {
            return true;
        }

        return false;
    }

    @Override
    public GetDicOutPut dic(GetDicInput param) throws Exception {
        return null;
    }
}
