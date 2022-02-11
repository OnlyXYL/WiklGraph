package top.wikl.entity;

/**
 * @author Administrator
 * @version 1.0.0
 * @ProjectName MyConstant
 * @Description TODO
 * @createTime 2021/4/3 0003 19:27
 */
public class MyConstant {


    //=================zuul验证===================
    /**
     * Zuul请求头TOKEN名称（不要有空格）
     */
    public static final String ZUUL_TOKEN_HEADER = "ZuulToken";
    /**
     * Zuul请求头TOKEN值
     */
    public static final String ZUUL_TOKEN_VALUE = "semanticube:zuul:123456";


    //==================验证码=======================
    /**
     * gif类型
     */
    public static final String GIF = "gif";
    /**
     * png类型
     */
    public static final String PNG = "png";

    /**
     * 验证码 key前缀
     */
    public static final String CODE_PREFIX = "semanticube.captcha.";
    /**
     * 异步线程池名称
     */
    public static final String ASYNC_POOL = "semanticubeAsyncThreadPool";

    /**
     * 排序规则：降序
     */
    public static final String ORDER_DESC = "descending";


    //=====================模板============================

    /**
     * 实体与概念字典模板的表头
     */
    public static final String[] ENTITY_CONCEPT_DIC_TEMPLATE_HEADS = new String[]{"实体", "概念"};

    /**
     * 三元组字典模板表头
     */
    public static final String[] TRIPLE_DIC_TEMPLATE_HEADS = new String[]{"短句", "起点实体","起点概念","关系","终点实体","终点概念"};

    /**
     * 字典模板后缀
     */
    public static final String[] DIC_TEMPLATE_EXTENSION = new String[]{"xls", "xlsx"};

}