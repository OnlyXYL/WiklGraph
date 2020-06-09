package top.wikl.orientdb.error.model;


/**
 * 功能描述: 概念属性类型
 *
 * @author gaokai
 * @date 2019/9/26 15:33
 */
public enum KgPropertyDataType {
    DATE("0", "日期"),
    TEXT("1", "文本"),
    DIGIT("3", "数值");

    private String code;

    private String desc;

    KgPropertyDataType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
