package top.wikl.neo4j.entity;

import java.util.Map;

/**
 * @ClassName: Relationship
 * @Description: neo4j原生接口封装-关系
 * @date: 2020/8/6 18:15
 * @author DengYaJun
*/
public class Relationship {
    long startNodeId;
    long endNodeId;
    long id;
    String type;
    Map<String,Object> properties;

    public long getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(long startNodeId) {
        this.startNodeId = startNodeId;
    }

    public long getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(long endNodeId) {
        this.endNodeId = endNodeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean hasType(String type){
        return this.type.equals(type);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Iterable<String> keys(){
        return properties.keySet();
    }

    public Iterable<Object> values(){
        return properties.values();
    }
}
