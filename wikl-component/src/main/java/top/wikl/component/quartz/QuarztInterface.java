package top.wikl.component.quartz;

/**
 * @author XYL
 * @title: QuarztInterface
 * @description: TODO
 * @date 2020/4/11 17:27
 * @return
 * @since V1.0
 */
public interface QuarztInterface {

    /**
     * 添加任务到调度器
     *
     * @param quartz
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/11 17:30
     * @since V1.1
     */
    void addJob(QuartzJob quartz);

    /**
     * 启动任务
     *
     * @param quartz
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/11 17:29
     * @since V1.1
     */
    void startJob(QuartzJob quartz);

    /**
     * 暂停任务
     *
     * @param quartz
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/11 17:30
     * @since V1.1
     */
    void pauseJob(QuartzJob quartz);

    /**
     * 删除任务
     *
     * @param quartz
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/11 17:30
     * @since V1.1
     */
    void deleteJob(QuartzJob quartz);
}
