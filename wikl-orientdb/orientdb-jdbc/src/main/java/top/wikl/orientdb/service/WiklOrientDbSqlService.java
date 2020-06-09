package top.wikl.orientdb.service;

import top.wikl.entity.graph.output.SingleRecordInfoResultData;
import top.wikl.entity.graph.output.WiklGraphInfo;
import top.wikl.entity.graph.output.WiklResultData;
import top.wikl.entity.graph.vo.SearchInstanceEdgeDetailsVo;
import top.wikl.entity.graph.vo.SearchInstanceNodeDetailsVo;
import top.wikl.entity.graph.vo.SearchOneDepthDataVo;

/**
 * @author XYL
 * @title: WiklOrientDbSqlService
 * @description: OrientDb 服务
 * @date 2019/9/27 13:54
 * @return
 * @since V1.0
 */
public interface WiklOrientDbSqlService {

    /**
     * 查询实例节点详情信息
     *
     * @param searchInstanceNodeDetailsVo
     * @return
     * @author XYL
     * @date 2019/9/27 14:06
     * @since V1.0
     */
    SingleRecordInfoResultData searchOneInstanceNodeDetails(SearchInstanceNodeDetailsVo searchInstanceNodeDetailsVo);

    /**
     * 查询线详情
     *
     * @param searchInstanceEdgeDetailsVo
     * @return
     * @author XYL
     * @date 2019/9/27 14:06
     * @since V1.0
     */
    SingleRecordInfoResultData searchOneInstanceEdgeDetails(SearchInstanceEdgeDetailsVo searchInstanceEdgeDetailsVo);

    /**
     * 查询图谱实例个数和关系个数
     *
     * @param currentUserId
     * @param kngraphId
     * @return
     * @author XYL
     * @date 2019/9/27 14:06
     * @since V1.0
     */
    WiklGraphInfo searchKngraphInstanceAndEdgeNumbers(String currentUserId, String kngraphId);

    /**
     * 根据点，查询深度为1 的关系
     *
     * @param searchOneDepthDataVo
     * @return
     * @author XYL
     * @date 2019/9/27 14:06
     * @since V1.0
     */
    WiklResultData searchOneDepthData(SearchOneDepthDataVo searchOneDepthDataVo);

}
