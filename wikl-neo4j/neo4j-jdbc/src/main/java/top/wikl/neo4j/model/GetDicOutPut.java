package top.wikl.neo4j.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XYL
 * @title: GetDicOutPut
 * @description: TODO
 * @date 2020/5/13 19:57
 * @return
 * @since V1.0
 */
@Data
public class GetDicOutPut implements Serializable {

    private String key;

    private String value;

}
