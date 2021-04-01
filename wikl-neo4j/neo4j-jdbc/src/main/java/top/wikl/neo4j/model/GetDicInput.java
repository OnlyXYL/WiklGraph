package top.wikl.neo4j.model;

import lombok.Data;
import top.wikl.neo4j.enums.DicType;

import java.util.Map;

/**
 * @author XYL
 * @title: GetDicInput
 * @description: TODO
 * @date 2020/5/13 20:01
 * @return
 * @since V1.0
 */
@Data
public class GetDicInput {

    /**
     * 图谱id
     */
    private Integer graphId;

    /**
     * 字典类型
     */
    private DicType dicType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * label,key
     */
    private Map<String, String> labelMarkMap;
}
