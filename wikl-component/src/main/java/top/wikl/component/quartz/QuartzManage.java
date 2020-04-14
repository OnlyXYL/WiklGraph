package top.wikl.component.quartz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;

/**
 * @author XYL
 * @title: QuartzManage
 * @description: TODO
 * @date 2020/4/11 16:57
 * @return
 * @since V1.0
 */
@Slf4j
@Configuration
public class QuartzManage implements QuarztInterface {

    @Resource
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void startJob(QuartzJob quartz) {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey key = new JobKey(quartz.getJobName());
        try {
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pauseJob(QuartzJob quartz) {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey key = new JobKey(quartz.getJobName());

        try {
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(QuartzJob quartz) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobKey key = new JobKey(quartz.getJobName());

        try {
            scheduler.deleteJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 任务交给调度器
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/11 17:26
     * @since V1.1
     */
    @Override
    public void addJob(QuartzJob quartz) {

        log.debug("addJob(jobClass={}, jobName={})", quartz.getJobClass().getName(), quartz.getJobName());

        try {

            //构建job信息
            JobDetail job = JobBuilder.newJob(quartz.getJobClass())
                    .withIdentity(quartz.getJobName())
                    .storeDurably(true)
                    .build();

            Trigger trigger;

            //判断是否存在触发配置
            if (StringUtils.isNotBlank(quartz.getCronExpression())) {
                // 触发时间点
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression().trim());

                trigger = TriggerBuilder.newTrigger()
                        .withIdentity("trigger" + quartz.getJobName(), null)
                        .startNow().withSchedule(cronScheduleBuilder).build();
            } else {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity("trigger" + quartz.getJobName(), null)
                        .build();
            }

            //交由Scheduler安排触发
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            scheduler.scheduleJob(job, trigger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
