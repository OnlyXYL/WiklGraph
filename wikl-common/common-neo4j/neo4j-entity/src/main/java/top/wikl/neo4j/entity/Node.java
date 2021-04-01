package top.wikl.neo4j.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @ClassName: Node
 * @Description: neo4j原生接口封装-节点
 * @date: 2020/8/6 18:14
 * @author DengYaJun
*/
public class Node implements Serializable {
    Map<String,Object> properties;
    Collection<String> labels=new HashSet();
    long id;

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Collection<String> getLabels() {
        return labels;
    }

    public void setLabels(Iterable<String> labels) {
        labels.forEach(l->this.labels.add(l));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public boolean hasLabel(String label){
        return labels.contains(label);
    }
    public boolean containsKey(String key){
        return this.properties.containsKey(key);
    }
    public int size(){
        return properties.size();
    }
    public Iterable<String> keys(){
        return properties.keySet();
    }
    public Iterable<Object> values(){
        return properties.values();
    }
}
