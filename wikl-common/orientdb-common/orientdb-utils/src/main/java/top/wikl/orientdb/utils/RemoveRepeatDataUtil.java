package top.wikl.orientdb.utils;


import top.wikl.entity.graph.input.WiklNode;
import top.wikl.entity.graph.input.WiklRelation;
import top.wikl.entity.graph.output.WiklNodeInfo;
import top.wikl.entity.graph.output.WiklRelationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @author XYL
 * @date 2019/9/27 13:17
 * @return
 * @since V1.0
 */
public class RemoveRepeatDataUtil {
    // 删除ArrayList中重复元素，保持顺序
    public static ArrayList<WiklNodeInfo> removeDuplicateUser(List<WiklNodeInfo> OldNodes) {

        ArrayList<WiklNodeInfo> newNodes = new ArrayList<>();
        for (WiklNodeInfo node : OldNodes) {
            if (!("null").equals(node.getNodeName()) && !newNodes.contains(node)) {
                newNodes.add(node);
            }
        }
        return newNodes;
    }

    /**
     * List<String>去重
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 13:50
     * @since V1.0
     */
    public static List<String> removeRepeatString(List<String> oldList) {
        List<String> newList = new ArrayList<String>();
        for (String string : oldList) {
            if (!newList.contains(string)) {
                newList.add(string);
            }
        }
        return newList;
    }

    /**
     * 去除List内复杂字段重复对象
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 13:50
     * @since V1.0
     */
    public static List<WiklRelationInfo> RemoveRepeatRelationShipList(List<WiklRelationInfo> oldList) {
        List<WiklRelationInfo> list = new ArrayList<>();
        if (oldList.size() > 0) {
            for (WiklRelationInfo relationship : oldList) {
                //list去重复，内部重写equals
                if (!list.contains(relationship)) {
                    list.add(relationship);
                }
            }
        }
        return list;
    }

    /**
     * 去除List内复杂字段重复对象
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 13:50
     * @since V1.0
     */
    public static List<WiklRelation> removeRepeatRelationShipList(List<WiklRelation> oldList) {
        List<WiklRelation> list = new ArrayList<>();
        if (oldList.size() > 0) {
            for (WiklRelation relationship : oldList) {
                //list去重复，内部重写equals
                if (!list.contains(relationship)) {
                    list.add(relationship);
                }
            }
        }
        return list;
    }

    /**
     * 去除List内复杂字段重复对象
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 13:50
     * @since V1.0
     */
    public static List<WiklNode> removeRepeatNodeList(List<WiklNode> oldList) {
        List<WiklNode> list = new ArrayList<>();
        if (oldList.size() > 0) {
            for (WiklNode relationship : oldList) {
                //list去重复，内部重写equals
                if (!list.contains(relationship)) {
                    list.add(relationship);
                }
            }
        }
        return list;
    }

}
