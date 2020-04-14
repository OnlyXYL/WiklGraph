package top.wikl.component.quartz;

import lombok.Data;

/**
 * @author XYL
 * @title: QuartzJob
 * @description: TODO
 * @date 2020/4/11 17:13
 * @return
 * @since V1.0
 */
@Data
public class QuartzJob {

    private Class jobClass;

    private String jobName;

    private String cronExpression;
}
