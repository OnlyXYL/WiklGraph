package top.wikl.wikljava.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 树
 *
 * @author XYL
 * @version 1.2
 * @since 2021/10/25 0025 21:45
 */
public class TreeNode<T> implements Iterable<TreeNode<T>> {

    /**
     * 临时id，提取公因式时，找唯一点时用
     */
    public String tempId;

    /**
     * 树节点
     */
    public T data;

    /**
     * 父节点，根没有父节点
     */
    public TreeNode<T> parent;

    /**
     * 子节点，叶子节点没有子节点
     */
    public List<TreeNode<T>> children;

    /**
     * 保存了当前节点及其所有子节点，方便查询
     */
    private List<TreeNode<T>> elementsIndex;

    /**
     * 树高
     */
    private int height;

    /**
     * 构造函数
     *
     * @param data
     */
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.elementsIndex = new LinkedList<TreeNode<T>>();
        this.elementsIndex.add(this);
        height = 1;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public List<TreeNode<T>> getElementsIndex() {
        return elementsIndex;
    }

    public void setElementsIndex(List<TreeNode<T>> elementsIndex) {
        this.elementsIndex = elementsIndex;
    }

    /**
     * 判断是否为根：根没有父节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断是否为叶子节点：子节点没有子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 添加一个子节点
     *
     * @param child
     * @return
     */
    public TreeNode<T> addChild(T child) {

        TreeNode<T> childNode = new TreeNode<T>(child);

        childNode.parent = this;

        this.children.add(childNode);

        this.registerChildForSearch(childNode);

        return childNode;
    }

    /**
     * 获取当前节点的层
     *
     * @return
     */
    public int getLevel() {
        if (this.isRoot()) {
            return 0;
        } else {
            return parent.getLevel() + 1;
        }
    }

    /**
     * 递归为当前节点以及当前节点的所有父节点增加新的节点
     *
     * @param node
     */
    private void registerChildForSearch(TreeNode<T> node) {

        height++;

        elementsIndex.add(node);
        if (parent != null) {
            parent.registerChildForSearch(node);
        }
    }

    /**
     * 从当前节点及其所有子节点中搜索某节点
     *
     * @param cmp
     * @return
     */
    public TreeNode<T> findTreeNode(Comparable<T> cmp) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }

        return null;
    }


    /**
     * 删除节点和它下面的晚辈
     *
     * @param
     * @return void
     * @author XYL
     * @since 20:22 2021/10/27 0027
     **/
    public void deleteNode() {
        final TreeNode<T> parent = this.parent;
        if (parent != null) {
            parent.deleteChildNode(this);
        }
    }

    /**
     * 删除当前节点的某个子节点
     *
     * @param childId
     * @return void
     * @author XYL
     * @since 20:22 2021/10/27 0027
     **/
    public void deleteChildNode(TreeNode<T> childId) {

        final List<TreeNode<T>> childrenList = this.children;

        int childNumber = childrenList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childrenList.get(i);
            if (child.data == childId.data) {
                this.children.remove(i);
                return;
            }
        }
    }

    /**
     * 动态的插入一个新的节点到当前树中，作为晚辈，具体怎么晚，看指定的父
     *
     * @param treeNode
     * @return boolean
     * @author XYL
     * @since 20:39 2021/10/27 0027
     **/
    public boolean insertYoungerNode(TreeNode<T> treeNode) {
        final TreeNode parent = treeNode.parent;
        if (parent != null && (this.data == parent.data)) {
            addChild(treeNode.data);
            return true;
        } else {
            List<TreeNode<T>> childList = this.children;
            int childNumber = childList.size();
            boolean insertFlag;

            for (int i = 0; i < childNumber; i++) {
                TreeNode childNode = childList.get(i);
                insertFlag = childNode.insertYoungerNode(treeNode);
                if (insertFlag == true)
                    return true;
            }
            return false;
        }
    }


    /**
     * 返回当前节点的父辈节点集合
     *
     * @param
     * @return java.util.List<top.wikl.wikljava.tree.TreeNode>
     * @author XYL
     * @since 21:52 2021/10/27 0027
     **/
    public List<TreeNode> getElders() {
        List<TreeNode> elderList = new ArrayList<TreeNode>();
        TreeNode parentNode = this.parent;
        if (parentNode == null) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders());
            return elderList;
        }
    }

    /**
     * 返回当前节点的晚辈集合
     *
     * @param
     * @return java.util.List<top.wikl.wikljava.tree.TreeNode>
     * @author XYL
     * @since 21:53 2021/10/27 0027
     **/
    public List<TreeNode> getYounger() {
        List<TreeNode> juniorList = new ArrayList<TreeNode>();
        final List<TreeNode<T>> childList = this.children;
        if (childList == null) {
            return juniorList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode junior = childList.get(i);
                juniorList.add(junior);
                juniorList.addAll(junior.getYounger());
            }
            return juniorList;
        }
    }

    /**
     * 获取当前节点的迭代器
     *
     * @return
     */
    @Override
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIterator<T> iterator = new TreeNodeIterator<T>(this);
        return iterator;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[tree data null]";
    }

}