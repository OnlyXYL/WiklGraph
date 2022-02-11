package top.wikl.wikljava.tree;

import org.apache.commons.collections.list.TreeList;

import java.util.Iterator;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/10/27 0027 19:52
 */
public class TreeListTest {

    public static void main(String[] args) {

        final TreeList tree = createTree();

        final Iterator iterator = tree.iterator();

        while (iterator.hasNext()){

            final Object next = iterator.next();

            System.out.println(next.toString());
        }


    }


    public static TreeList createTree(){

        final TreeList treeList = new TreeList();
        treeList.add(0,"A");

        treeList.add(1,"B");

        return treeList;
    }

}
