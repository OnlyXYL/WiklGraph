package top.wikl.orientdb.enums;

/**
 * 图谱节点类型
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:24
 * @return
 * @since V1.0
 */
public enum WiklGraphType {

    concept("concept", "概念"),

    instance("instance", "实例"),

    others("others", "其他");

    private String key;

    private String value;

    WiklGraphType(String key, String value) {
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
     * @param value
     * @return
     */
    public static WiklGraphType getEnumByValue(String value) {
        for (WiklGraphType enmuType : WiklGraphType.values()) {
            if (enmuType.getValue().equals(value)) {
                return enmuType;
            }
        }
        return null;
    }

}
