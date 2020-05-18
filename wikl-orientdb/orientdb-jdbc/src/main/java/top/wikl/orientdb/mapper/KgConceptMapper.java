package top.wikl.orientdb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.wikl.orientdb.model.KgConcept;
import top.wikl.orientdb.model.KgGetLabelAndMark;

import java.util.List;

/**
 * 图谱-概念表 Mapper 接口
 *
 * @author gaokai
 * @data 2019-09-25
 */
@Mapper
public interface KgConceptMapper extends BaseMapper<KgConcept> {

    List<KgGetLabelAndMark> getLabelAndMark(Integer graphId);


}
