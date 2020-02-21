package top.wikl.orientdb.controller;

import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.record.OVertex;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author XYL
 * @title: OVertexPageConditon
 * @description: TODO
 * @date 2019/11/2 15:04
 * @return
 * @since V1.0
 */
@ApiModel(value = "分页条件")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OVertexPageConditon {

    /**
     * 总数
     */
    private int total;

    /**
     * 第一条记录的rid
     */
    private ORID firstRid;

    /**
     * 数据
     */
    private List<OVertex> datas;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 每页大小
     */
    private int pageSize;

    public OVertexPageConditon(int total, int pageSize) {
        this.total = total;
        this.pageSize = pageSize;

        //totalPage 总页数
        if(total%pageSize==0){
            //说明整除，正好每页显示pageSize条数据，没有多余一页要显示少于pageSize条数据的
            this.totalPage = total / pageSize;
        }else{
            //不整除，就要在加一页，来显示多余的数据。
            this.totalPage = total / pageSize +1;
        }

    }
}
