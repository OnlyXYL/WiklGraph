package top.wikl.wikljava.tree;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/10/27 0027 17:40
 */
public class TestTree {

    public static void main(String[] args) {

        final TreeNode treeNode = createTree();

        iterator(treeNode);

        System.out.println("树高：" + treeNode.getHeight());

        System.out.println("==================搜索  F ================");

        final TreeNode F = findTreeNode(treeNode, "F");

        iterator(F);

        System.out.println("==================遍历树====================");

        iterator(treeNode);

        System.out.println("==================删除  F ================");

        F.deleteNode();

        System.out.println("==================遍历删除后的树====================");

        iterator(treeNode);

        System.out.println("================== C 下插入 F ====================");

        //插入节点
        final TreeNode younger = insertYounger(treeNode, "C", "G", "F");

        System.out.println("==================遍历树====================");

        iterator(treeNode);

        final TreeNode g = findTreeNode(treeNode, "G");

        final List<TreeNode> elders = g.getElders();

        System.out.println("==================遍历树 G 的 父辈 ====================");

        final List<Object> collect = elders.stream().map(TreeNode::getData).collect(Collectors.toList());

        System.out.println(collect.toString());

        System.out.println("==================遍历树 A 的 晚辈 ====================");

        final List<TreeNode> youngerYounger = treeNode.getYounger();

        final List<Object> objects = youngerYounger.stream().map(TreeNode::getData).collect(Collectors.toList());

        System.out.println(objects.toString());


        System.out.println("================== 把 B 树放到 D 树下， 同时删除 B 树 ====================");

        final TreeNode b = findTreeNode(treeNode, "B");

        TreeNode add = findTreeNode(treeNode, "D");


        final Iterator iterator = b.iterator();

        while (iterator.hasNext()) {

            final String string = iterator.next().toString();

            final TreeNode node = add.addChild(string);

            add = node;
        }

        b.deleteNode();

        System.out.println("==================遍历树====================");

        iterator(treeNode);

        System.out.println("================== D 树的子节点 ====================");

        final TreeNode d = findTreeNode(treeNode, "D");

        final String s = d.getChildren().toString();

        System.out.println(s);

    }

    public void iteratorTree(TreeNode treeNode, String rootNode) {

        final TreeNode node = findTreeNode(treeNode, rootNode);

        final List<TreeNode> children = node.getChildren();



    }


    /**
     * 插入节点
     *
     * @param treeNode 树
     * @param current  当前树
     * @param parent   新节点的父节点
     * @param add      新节点
     * @return void
     * @author XYL
     * @since 21:10 2021/10/27 0027
     **/
    public static TreeNode<String> insertYounger(TreeNode<String> treeNode, String current, String parent, String add) {

        TreeNode parentNode;

        //当前节点
        TreeNode currentNode = findTreeNode(treeNode, current);

        final TreeNode<String> insertNode = new TreeNode<>(add);

        if (!current.equals(parent)) {
            //新节点的父节点
            parentNode = findTreeNode(treeNode, parent);
        } else {
            parentNode = currentNode;
        }

        insertNode.setParent(parentNode);

        currentNode.insertYoungerNode(insertNode);

        return insertNode;

    }

    /**
     * 弹出树
     *
     * @param treeNode
     * @param
     * @return top.wikl.wikljava.tree.TreeNode<java.lang.String>
     * @author XYL
     * @since 17:53 2021/10/27 0027
     **/
    public void insert(TreeNode<String> treeNode, String index, String insert) {

        TreeNodeIterator<String> iterator = new TreeNodeIterator<>(treeNode);


        while (iterator.hasNext()) {

            final TreeNode<String> next = iterator.next();

            String indent = createIndent(next.getLevel());
            System.out.println(indent + next.data);
        }
    }

    private static String createIndent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(' ');
        }
        return sb.toString();
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

            String indent = createIndent(next.getLevel());
            System.out.println(indent + next.data);
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
     * 创建树
     *
     * @param
     * @return top.wikl.wikljava.tree.TreeNode
     * @author XYL
     * @since 17:42 2021/10/27 0027
     **/
    public static TreeNode createTree() {
        TreeNode<String> A = new TreeNode<>("A");

        final TreeNode<String> B = A.addChild("B");
        final TreeNode<String> C = A.addChild("C");
        final TreeNode<String> D = A.addChild("D");

        final TreeNode<String> E = B.addChild("E");

        final TreeNode<String> F = C.addChild("F");

        final TreeNode<String> G = C.addChild("G");

        F.addChild("H");
        F.addChild("I");
        F.addChild("J");

        return A;
    }


}
