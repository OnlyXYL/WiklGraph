package top.wikl.enums.log;

/**
 * 日志级别枚举
 *
 * @author XYL
 * @title: LogLevel
 * @description: TODO
 * @date 2020/5/28 9:57
 * @return
 * @since V1.0
 */
public enum LogLevel {

    DEBUG("debug", "debug"),

    WARN("warn", "warn"),

    INFO("info", "info"),

    ERROR("error", "error"),

    ALL("all", "all");

    private String key;

    private String value;

    LogLevel(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
