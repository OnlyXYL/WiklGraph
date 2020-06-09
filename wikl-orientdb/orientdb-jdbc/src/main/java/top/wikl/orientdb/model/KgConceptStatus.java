package top.wikl.orientdb.model;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum KgConceptStatus implements IEnum<String> {

    CONFIG("1", "已配置"), NOT_CONFIG("0", "未配置");

    private String code;

    private String desc;

    KgConceptStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.code;
    }
}
