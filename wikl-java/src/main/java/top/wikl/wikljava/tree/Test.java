package top.wikl.wikljava.tree;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/10/27 0027 16:19
 */
public class Test {

    private final static Map<String, TreeNode> ontologyTreeMap = new ConcurrentHashMap<>(5);

    public static void main(String[] args) {

        String words = "井下/pd.作业/v_.施工/v_.准备/ps.作业/v.工艺/b_.设计/v_";
//        String words = "井位/pp.设计/v_.区块/o_.单元/o_.名称/pp.";

        final List<String> convert = convert(words);

        final Iterator<String> iterator = convert.iterator();

        iterator.remove();

        final List<String> strings = convert.subList(0, convert.size() - 1);

        System.out.println(strings.toString());

        System.out.println("++++++++++++");

        final List<String> list = convert.subList(convert.size() - 1, convert.size());

        System.out.println(list.toString());

        createTree(convert);

        final TreeNode treeNode = ontologyTreeMap.get(convert.get(0));

        System.out.println("=============测试遍历=================");

        //遍历树
        iterator(treeNode);

        System.out.println("树高" + treeNode.getHeight() + "，当前第" + treeNode.getLevel() + "层");

        System.out.println("=============测试搜索=================");

        final TreeNode node = findTreeNode(treeNode, "单元");

        //遍历树
        iterator(node);

        System.out.println("树高" + node.getHeight() + "，当前第" + node.getLevel() + "层");
    }


    /**
     * 遍历树
     *
     * @param treeNode
     * @return void
     * @author XYL
     * @since 16:37 2021/10/27 0027
     **/
    public static void iterator(TreeNode treeNode) {
        TreeNodeIterator<TreeNode> iterator = new TreeNodeIterator<TreeNode>(treeNode);

        while (iterator.hasNext()) {

            final TreeNode<TreeNode> next = iterator.next();

            System.out.println(next.toString());
        }
    }


    /**
     * 查找树
     *
     * @param treeNode
     * @param name
     * @return top.wikl.wikljava.tree.TreeNode
     * @author XYL
     * @since 16:40 2021/10/27 0027
     **/
    public static TreeNode findTreeNode(TreeNode treeNode, String name) {

        final TreeNode node = treeNode.findTreeNode((Comparable<String>) treeData -> {
            if (treeData == null) {
                return 1;
            }
            boolean nodeOk = treeData.contains(name);
            return nodeOk ? 0 : 1;
        });

        return node;
    }

    /**
     * 数据转换
     *
     * @param coreWords
     * @return java.util.List<java.lang.String>
     * @author XYL
     * @since 16:40 2021/10/27 0027
     **/
    public static List<String> convert(String coreWords) {
        String regex = "[(\\/_)|(\\/m)]?";

        final String all = coreWords.replaceAll("[.]", "");

        String[] split = all.split(regex);

        LinkedList<String> strings = new LinkedList<>(Arrays.asList(split));

        List<String> collect = strings.stream().map(s -> s.replaceAll("/[a-z]*", "")).collect(Collectors.toList());

        return collect;
    }

    /**
     * 创建树
     *
     * @param coreWords
     * @return void
     * @author XYL
     * @since 16:41 2021/10/27 0027
     **/
    public static void createTree(List<String> coreWords) {

        String root = coreWords.get(0);

        //获取第一个，作为根
        TreeNode<String> rootNode = new TreeNode<>(root);

        ontologyTreeMap.put(root, rootNode);

        for (int i = 1; i < coreWords.size(); i++) {

            TreeNode<String> treeNode = rootNode.addChild(coreWords.get(i));

            rootNode = treeNode;

        }
    }

}
