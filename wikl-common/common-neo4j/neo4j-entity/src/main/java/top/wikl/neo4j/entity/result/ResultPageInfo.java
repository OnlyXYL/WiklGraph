package top.wikl.neo4j.entity.result;

import java.io.Serializable;

/**
 * @ClassName: ResultPageInfo
 * @Description: 结果分页
 * @date: 2020/8/6 18:19
 * @author DengYaJun
*/
public class ResultPageInfo implements Serializable{
    public ResultPageInfo() {
    }

    public ResultPageInfo(String totalRecord, String currentPage, String pageSize) {
            this.totalRecord = totalRecord;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
    }


    public  String totalRecord; //总记录数
    public String currentPage;//当前页
    public String pageSize;//页面最大记录数

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
