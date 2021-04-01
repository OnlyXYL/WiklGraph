package top.wikl.neo4j;


import org.neo4j.driver.types.IsoDuration;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Point;
import org.neo4j.driver.types.Relationship;

/**
 * @ClassName: Neo4jConvertor
 * @Description: neo4j原生结果类型转换
 * @date: 2020/8/6 18:11
 * @author DengYaJun
*/
public class Neo4jConvertor {
    public static Neo4jConvertor build(){
        return new Neo4jConvertor();
    }
    /**
     * 转换neo4j的node
     * @param node_origin
     * @return
     */
    public top.wikl.neo4j.entity.Node convertNeo4jNodeToNode(Node node_origin) {
        top.wikl.neo4j.entity.Node node = new top.wikl.neo4j.entity.Node();
        node.setProperties(node_origin.asMap());
        node.setId(node_origin.id());
        node.setLabels(node_origin.labels());
        return node;
    }

    /**
     * 转换neo4j的relastionship
     * @param relationship_origin
     * @return
     */
    public top.wikl.neo4j.entity.Relationship convertNeo4jRelationshipToRelationship(Relationship relationship_origin) {
        top.wikl.neo4j.entity.Relationship relationship = new top.wikl.neo4j.entity.Relationship();
        relationship.setId(relationship_origin.id());
        relationship.setProperties(relationship_origin.asMap());
        relationship.setStartNodeId(relationship_origin.startNodeId());
        relationship.setEndNodeId(relationship_origin.endNodeId());
        relationship.setType(relationship_origin.type());
        return relationship;
    }

    /**
     * 转换neo4j的Point2D
     * @param point_origin
     * @return
     */
    public top.wikl.neo4j.entity.Point2D convertNeo4jPoint2DToPoint2D(Point point_origin) {
        top.wikl.neo4j.entity.Point2D point = new top.wikl.neo4j.entity.Point2D();
        point.setSrid(point_origin.srid());
        point.setX(point_origin.x());
        point.setY(point_origin.y());
        return point;
    }

    /**
     * 转换neo4j的Point3D
     * @param point_origin
     * @return
     */
    public top.wikl.neo4j.entity.Point3D convertNeo4jPoint2DToPoint3D(Point point_origin) {
        top.wikl.neo4j.entity.Point3D point = new top.wikl.neo4j.entity.Point3D();
        point.setSrid(point_origin.srid());
        point.setX(point_origin.x());
        point.setY(point_origin.y());
        point.setZ(point_origin.z());
        return point;
    }

    /**
     * 转换neo4j的Duration
     * @param duration_origin
     * @return
     */
    public top.wikl.neo4j.entity.IsoDuration convertNeo4jDurationToDuration(IsoDuration duration_origin) {
        top.wikl.neo4j.entity.IsoDuration duration = new top.wikl.neo4j.entity.IsoDuration();
        duration.setDays(duration_origin.days());
        duration.setMonths(duration_origin.months());
        duration.setSeconds(duration_origin.seconds());
        duration.setNanoseconds(duration_origin.nanoseconds());
        return duration;
    }
}
