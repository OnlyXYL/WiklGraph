package top.wikl.neo4j.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: Path
 * @Description: neo4j原生接口封装-路径
 * @date: 2020/8/6 18:14
 * @author DengYaJun
*/
public class Path implements Serializable {
    Node startNode;
    Node endNode;
    Collection<top.wikl.neo4j.entity.Relationship> relationships=new ArrayList();
    Collection<Node> nodes=new ArrayList();

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public Collection<top.wikl.neo4j.entity.Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<top.wikl.neo4j.entity.Relationship> relationships) {
        this.relationships = relationships;
    }

    public void addRelationship(top.wikl.neo4j.entity.Relationship relationship){
        this.relationships.add(relationship);
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Iterable<Node> nodes) {
        nodes.forEach(n->this.nodes.add(n));
    }
    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public int size(){
       return  this.relationships.size();
    }
    public boolean containsNode(Node node){
        return this.nodes.contains(node);
    }
    public boolean containsRelationship(top.wikl.neo4j.entity.Relationship relationship){
        return this.relationships.contains(relationship);
    }
}
