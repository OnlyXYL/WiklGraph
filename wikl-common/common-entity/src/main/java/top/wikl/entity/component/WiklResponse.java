package top.wikl.entity.component;

/**
 * @param
 * @author XYL
 * @date 2019/12/17 14:28
 * @return
 * @since V1.0
 */
public class WiklResponse {
    /**
     * 返回参数
     */
    private String description;

    /**
     * 参数名
     */
    private String name;

    /**
     * 说明
     */
    private String remark;

    public WiklResponse(String description, String name, String remark) {
        this.description = description;
        this.name = name;
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
