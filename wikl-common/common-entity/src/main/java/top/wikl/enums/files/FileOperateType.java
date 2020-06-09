package top.wikl.enums.files;

/**
 * @author XYL
 * @title: FileOperateType
 * @description: 文件上传操作类型
 * @date 2019/10/23 16:59
 * @return
 * @since V1.0
 */
public enum FileOperateType {

    check("0", "校验"),
    upload("1", "上传");

    private String key;

    private String value;

    FileOperateType(String key, String value) {
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
