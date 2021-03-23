package top.wikl.orientdb.enums;

/**
 * @author XYL
 * @Title: DicType
 * @ProjectName SemanticCube
 * @Description: 智能油田字典枚举 0 实例字典  1:概念字典 2:实体-概念字典 3:属性字典ID 4: 属性-概念字典ID： 5:概念-概念类型字典ID
 * @date 2019/7/1714:25
 */
public enum DicType {

    /**
     * 概念字典
     */
    concept_dic("1", "概念字典"),

    /**
     * 属性字典
     */
    property_dic("2", "属性字典"),

    /**
     * 实例字典
     */
    instance_dic("3", "实例字典"),

    /**
     * 属性-概念字典
     */
    property_concept_dic("4", "属性-概念字典"),

    /**
     * 实例-概念字典
     */
    instance_concept_dic("5", "实例-概念字典"),

    /**
     * 枚举字典
     */
    enum_dic("6", "枚举字典"),

    /**
     * 概念图谱字典
     */
    concept_graph_dic("7","概念-图谱字典");

    private String key;

    private String value;

    DicType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 取得枚举
     *
     * @param key
     * @return
     */
    public static DicType getEnumByKey(String key) {
        for (DicType enmuType : DicType.values()) {
            if (enmuType.getKey().equals(key)) {
                return enmuType;
            }
        }
        return null;
    }
}
