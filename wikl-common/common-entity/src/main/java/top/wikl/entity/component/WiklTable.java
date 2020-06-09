package top.wikl.entity.component;

import java.util.List;

/**
 * @param
 * @author XYL
 * @date 2019/12/17 14:44
 * @return
 * @since V1.0
 */
public class WiklTable {

    /**
     * 大标题
     */
    private String title;
    /**
     * 小标题
     */
    private String tag;
    /**
     * url
     */
    private String url;

    /**
     * 响应参数格式
     */
    private String responseForm;

    /**
     * 请求方式
     */
    private String requestType;

    /**
     * 请求体
     */
    private List<WiklRequest> wiklRequestList;

    /**
     * 返回体
     */
    private List<WiklResponse> wiklResponseList;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回值
     */
    private String responseParam;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponseForm() {
        return responseForm;
    }

    public void setResponseForm(String responseForm) {
        this.responseForm = responseForm;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public List<WiklRequest> getWiklRequestList() {
        return wiklRequestList;
    }

    public void setWiklRequestList(List<WiklRequest> wiklRequestList) {
        this.wiklRequestList = wiklRequestList;
    }

    public List<WiklResponse> getWiklResponseList() {
        return wiklResponseList;
    }

    public void setWiklResponseList(List<WiklResponse> wiklResponseList) {
        this.wiklResponseList = wiklResponseList;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }
}