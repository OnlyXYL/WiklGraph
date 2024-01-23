package top.wikl.neo4j.service.impl.dic.process;

import top.wikl.neo4j.model.NewDicInput;
import top.wikl.neo4j.model.NewDicOut;
import top.wikl.neo4j.service.impl.dic.AbstractDicHandler;

/**
 * @author XYL
 * @date 2024/01/23 16:03
 * @since
 */
public class NewDicProcess extends AbstractDicHandler<NewDicInput, NewDicOut> {

    public NewDicProcess() {
        super("new");
    }

    @Override
    public boolean support(String id) {
        return false;
    }

    @Override
    public NewDicOut dic(NewDicInput param) throws Exception {
        return null;
    }
}
