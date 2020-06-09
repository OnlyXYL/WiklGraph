package top.wikl.enums.files;

import java.io.Serializable;

/**
 * @author XYL
 * @title: FileCodeEnum
 * @description: 文件编码
 * @date 2019/11/6 17:47
 * @return
 * @since V1.0
 */
public enum FileCodeEnum implements Serializable {

    GBK("GBK", "GBK"),

    UTF_8("UTF-8", "UTF-8");

    private String key;

    private String value;

    FileCodeEnum(String key, String value) {
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
