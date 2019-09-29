package top.wikl.orientdb.enums;

/**
 * @author XYL
 * @title: WiklOrientDbDataTypeEnums
 * @description: OrientDB 支持的数据类型
 * @date 2019/9/27 15:15
 * @return
 * @since V1.0
 */
public enum WiklOrientDbDataTypeEnums {

    BOOLEAN("boolean", " boolean"),

    SHORT("short", " short"),

    DATE("date", " date"),

    DATETIME("datetime", " datetime"),

    BYTE("byte", " byte"),

    INTEGER("integer", " integer"),

    LONG("long", " long"),

    STRING("string", " string"),

    LINK("link", " link"),

    DECIMAL("decimal", " decimal"),

    DOUBLE("double", " double"),

    FLOAT("float", " float"),

    BINARY("binary", " binary"),

    EMBEDDED("embedded", " embedded"),

    LINKBAG("linkbag", " linkbag");

    private String key;

    private String value;

    WiklOrientDbDataTypeEnums(String key, String value) {
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
    public static WiklOrientDbDataTypeEnums getEnumByValue(String value) {
        for (WiklOrientDbDataTypeEnums enmuType : WiklOrientDbDataTypeEnums.values()) {
            if (enmuType.getValue().equals(value)) {
                return enmuType;
            }
        }
        return null;
    }

}

