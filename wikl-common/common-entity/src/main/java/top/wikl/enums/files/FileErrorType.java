package top.wikl.enums.files;

/**
 * @author XYL
 * @title: FileErrorType
 * @description: 文件错误状态枚举
 * @date 2019/11/8 11:18
 * @return
 * @since V1.0
 */
public enum FileErrorType  {

    /**
     * 错误
     */
    ERROR("1", "错误"),

    /**
     * 警告
     */
    WARN("2", "警告"),

    /**
     * 通过
     */
    RIGHT("3", "通过");

    private String key;

    private String value;

    FileErrorType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
