package top.wikl.wikljava.tree;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/11/28 0028 0:02
 */
public class TestCommonFactors {

    /**
     * 词性
     */
    public final static List<String> TYPE_LIST = Arrays.asList("b_", "o_", "v_", "po");

    public static void main(String[] args) {

        String coreWords = "地层.测试.成果.数据";

        final ArrayList<String> strings = new ArrayList<>();
        strings.add("地层.测试.引用.参数.数据");
        strings.add("地层.测试.引用.物性.数据");

        Map<String, Object> map = new LinkedHashMap<>();


        List<String> siblingNodeCopy = new ArrayList<>();

        Collections.copy(siblingNodeCopy, TYPE_LIST);

        System.out.println(siblingNodeCopy.toString());



//        appendCommon(coreWords, strings, null, map);

    }

    /**
     * 同兄弟节点提取公因式
     *
     * @param split
     * @param children
     * @return void
     * @author XYL
     * @since 11:12 2021/11/28 0028
     **/
    public static Map<String, Object> siblingNode(String[] split, List<String> children) {

        TreeNode<String> treeNode;

        TreeNode<String> addNode;

        //1. 正向
        //正向提取公因式
        final Map<String, Object> map = positiveFindCommonFactors(null, null, split, children);

        final Integer positiveEndPosition = (Integer) map.get("positiveEndPosition");

        treeNode = Optional.ofNullable(map.get("treeNode")).map(s -> (TreeNode<String>) s).orElse(null);

        addNode = Optional.ofNullable(map.get("addNode")).map(s -> (TreeNode<String>) s).orElse(null);

        //2. 反向
        //开始反向查找，反向查找的条件：标识位下标大于0；反向查找的词性用整体词（从开始位置到当前词）
        final Map hashMap = reverseFindCommonFactors(positiveEndPosition, treeNode, addNode, split, children);

        final Integer reverseEndPosition = (Integer) hashMap.get("reverseEndPosition");

        treeNode = (TreeNode<String>) hashMap.get("treeNode");

        addNode = (TreeNode<String>) hashMap.get("addNode");

        final HashMap<String, Object> result = new HashMap<>(3);

        result.put("treeNode", treeNode);
        result.put("addNode", addNode);
        result.put("reverseEndPosition", reverseEndPosition);

        return result;
    }

    /**
     * 同子节点提取公因式
     *
     * @param split
     * @param children
     * @return void
     * @author XYL
     * @since 12:00 2021/11/28 0028
     **/
    public static Map<String, Object> childNode(TreeNode<String> treeNode,
                                                TreeNode<String> addNode,
                                                String[] split,
                                                List<String> children) {

        //1. 正向
        //正向提取公因式
        final Map<String, Object> map = positiveFindCommonFactors(treeNode, addNode, split, children);

        final Integer positiveEndPosition = (Integer) map.get("positiveEndPosition");

        treeNode = Optional.ofNullable(map.get("treeNode")).map(s -> (TreeNode<String>) s).orElse(null);

        addNode = Optional.ofNullable(map.get("addNode")).map(s -> (TreeNode<String>) s).orElse(null);

        //2. 反向
        //开始反向查找，反向查找的条件：标识位下标大于0；反向查找的词性用整体词（从开始位置到当前词）
        final HashMap hashMap = reverseFindCommonFactors(positiveEndPosition, treeNode, addNode, split, children);

        final Integer reverseEndPosition = (Integer) hashMap.get("reverseEndPosition");

        addNode = (TreeNode<String>) hashMap.get("addNode");

        final HashMap<String, Object> result = new HashMap<>(3);

        result.put("treeNode", treeNode);
        result.put("addNode", addNode);
        result.put("reverseEndPosition", reverseEndPosition);

        return result;
    }


    /**
     * 提取单个子节点的公因式，作为一个树，条件到父节点上
     * <p>
     * 一个子节点对应一棵树
     * <p>
     * 注意：新树追加到父节点下时，保持tempId不变
     *
     * @param coreWords   当前节点
     * @param siblingNode 核心词的兄弟节点
     * @param children    核心词的子节点
     * @param map         原始节点对应的公因式的末级节点
     * @return java.lang.String
     * @author XYL
     * @since 23:19 2021/11/27 0027
     **/
    public static TreeNode<String> appendCommon(String coreWords,
                                                List<String> siblingNode,
                                                List<String> children,
                                                Map<String, Object> map) {

        //0. 兄弟节点去除当前节点
        siblingNode.removeIf(s -> s.endsWith(coreWords));

        //概念切分
        final String[] split = coreWords.split("[.]");

        //1. 同级别提取
        final Map<String, Object> siblingNodeResult = siblingNode(split, siblingNode);

        final Integer positiveEndPosition = (Integer) siblingNodeResult.get("positiveEndPosition");

        TreeNode<String> treeNode = Optional.ofNullable(siblingNodeResult.get("treeNode")).map(s -> (TreeNode<String>) s).orElse(null);

        TreeNode<String> addNode = Optional.ofNullable(siblingNodeResult.get("addNode")).map(s -> (TreeNode<String>) s).orElse(null);

        //2. 和子节点提取
        final Map<String, Object> childNodeResult = childNode(treeNode, addNode, split, children);

        treeNode = Optional.ofNullable(childNodeResult.get("treeNode")).map(s -> (TreeNode<String>) s).orElse(null);

        addNode = Optional.ofNullable(childNodeResult.get("addNode")).map(s -> (TreeNode<String>) s).orElse(null);

        final Integer reverseEndPosition = (Integer) siblingNodeResult.get("reverseEndPosition");

        //处理剩余数据
        if (reverseEndPosition > positiveEndPosition) {

            for (int i = positiveEndPosition + 1; i < reverseEndPosition; i++) {

                final String word = split[i];

                TreeNode<String> addChild = addNode.addChild(word);

                addNode = addChild;
            }
        }

        //3. 记录当前节点和对应的新树节点
        treeNode.setTempId("UUID");

        map.put(coreWords, addNode);

        return treeNode;
    }

    /**
     * 反向拼接
     *
     * @param split
     * @param i
     * @return java.lang.String
     * @author XYL
     * @since 0:21 2021/11/28 0028
     **/
    public static String reverseAppend(String[] split, int i) {

        final StringBuffer buffer = new StringBuffer();

        final int length = split.length;

        for (int j = i; j < length; j++) {

            buffer.append(split[j]);

        }
        final String toString = buffer.toString();
        return toString;
    }

    /**
     * 反向提取公因式
     *
     * @param positiveEndPosition
     * @param treeNode
     * @param addNode
     * @param split
     * @param children
     * @return int
     * @author XYL
     * @since 23:59 2021/11/27 0027
     **/
    public static HashMap reverseFindCommonFactors(int positiveEndPosition,
                                                   TreeNode<String> treeNode,
                                                   TreeNode<String> addNode,
                                                   String[] split,
                                                   List<String> children) {

        int reverseEndPosition = 0;

        final HashMap<String, Object> map = new HashMap<>(2);

        if (positiveEndPosition > 0) {

            final int length = split.length;

            int length1 = length - 1;

            for (int i = length1; i >= positiveEndPosition; i--) {

                String baseWord = split[i];

                //拼接整体词
                String speechWord = append(split, i);

                //使用整体词查词性
                final String speech = partSpeech(speechWord);

                String check = reverseAppend(split, i);

                if (TYPE_LIST.contains(speech)) {

              /*      if (i < length1) {
                        //第二个词开始，如果前一个词不能作为公因式提取，则下个词开始拼接前一个词作为公因式提取的基础
//                        speechWord = split[i - 1] + baseWord;
                        speechWord = reverseAppend(split, i);
                    }*/

                    //词性满足的话，判断是由能作为公因式提取（找子节点中是否有以当前词结尾的节点，有的话可以作为公因式提取）；使用基础次词判断能不能提公因式
                    final HashMap<String, List<String>> commonEndWith = checkCommonEndWith(check, children);

                    //获取当前公因式下的节点
                    final List<String> commonList = commonEndWith.get(speechWord);

                    if (!CollectionUtils.isEmpty(commonList)) {

                        //找到公因式后，后续公因式提取的节点范围发生变化，是上一个公因式的范围
                        children = commonList;

                        //可以作为公因式，创建树
                        if (treeNode == null) {
                            treeNode = new TreeNode<>(baseWord);

                            //设置节点唯一id
                            treeNode.setTempId("uuid");

                            addNode = treeNode;
                        } else {
                            TreeNode<String> addChild = addNode.addChild(baseWord);

                            //设置节点唯一id
                            addChild.setTempId("uuid");

                            addNode = addChild;

                        }
                    } else {

                        //退出，记录当前位置，开始反向找，找到当前位置为止
                        reverseEndPosition = i - 1;

                        break;
                    }
                } else {
                    //当前词词性不能作为公因式提取，判断是否有相同的节点，有的话继续，没有的话，推出，进行反向找

                    final HashMap<String, List<String>> commonEndWith = checkCommonEndWith(check, children);

                    //获取当前公因式下的节点
                    final List<String> commonList = commonEndWith.get(speechWord);

                    if (!CollectionUtils.isEmpty(commonList)) {

                        //找到公因式后，后续公因式提取的节点范围发生变化，是上一个公因式的范围
                        children = commonList;

                        continue;
                    } else {
                        //词性不满足，同时还没有公共项，直接跳出反向查找
                        reverseEndPosition = i - 1;
                        break;
                    }
                }
            }

            reverseEndPosition = length - reverseEndPosition;
        }

        map.put("reverseEndPosition", reverseEndPosition);
        map.put("addNode", addNode);
        map.put("treeNode", treeNode);

        return map;
    }

    /**
     * 获取词性
     *
     * @param word
     * @return java.lang.String
     * @author XYL
     * @since 0:03 2021/11/28 0028
     **/
    public static String partSpeech(String word) {

        final HashMap<String, String> map = new HashMap<>(4);

        map.put("地层", "o_");
        map.put("地层测试", "v_");
        map.put("地层测试成果", "");
        map.put("地层测试成果数据", "po");

        final String partSpeech = map.get(word);

        return partSpeech;
    }

    /**
     * 找最大匹配
     *
     * @param split
     * @param i
     * @return java.lang.String
     * @author XYL
     * @since 15:42 2021/11/21 0021
     **/
    public static String append(String[] split, int i) {

        final StringBuffer buffer = new StringBuffer();

        for (int j = 0; j <= i; j++) {

            buffer.append(split[j]);

        }
        final String toString = buffer.toString();
        return toString;
    }

    /**
     * 正向查找公因式
     *
     * @param split    核心词切分后的数组
     * @param children 核心词对应父节点的所有子节点，也就是核心词的兄弟节点集合
     * @return int
     * @author XYL
     * @since 23:52 2021/11/27 0027
     **/
    public static Map<String, Object> positiveFindCommonFactors(TreeNode<String> treeNode,
                                                                TreeNode<String> addNode,
                                                                String[] split, List<String> children) {

        final HashMap<String, Object> map = new HashMap<>(2);

        int positiveEndPosition = 0;

        final int length = split.length;

        for (int i = 0; i < length; i++) {

            String word = split[i];

            String speechWord = word;

            if (i > 0) {
                //第二个词开始，如果前一个词不能作为公因式提取，则下个词开始拼接前一个词作为公因式提取的基础
                speechWord = append(split, i);

//                speechWord = split[i - 1] + word;
            }

            //获取词性
            final String speech = partSpeech(speechWord);

            if (TYPE_LIST.contains(speech)) {
                //当前词能作为公因式提取，找子节点

                //判断能不能提取公因式。能作为公因式的话，记录同组数据，下个公因式的提取范围是上个公因式组内的数据。
                final HashMap<String, List<String>> commonStartWith = checkCommonStartWith(speechWord, children);

                //获取当前公因式下的节点
                final List<String> commonList = commonStartWith.get(speechWord);

                if (!CollectionUtils.isEmpty(commonList)) {

                    //找到公因式后，后续公因式提取的节点范围发生变化，是上一个公因式的范围
                    children = commonList;

                    //可以作为公因式，创建树
                    if (treeNode == null) {
                        treeNode = new TreeNode<>(word);

                        //设置节点唯一id
                        treeNode.setTempId("uuid");

                        addNode = treeNode;
                    } else {
                        TreeNode<String> addChild = addNode.addChild(word);

                        //设置节点唯一id
                        addChild.setTempId("uuid");

                        addNode = addChild;

                    }
                } else {

                    //退出，记录当前位置，开始反向找，找到当前位置为止
                    positiveEndPosition = i - 1;

                    break;
                }

            } else {
                //当前词词性不能作为公因式提取，判断是否有相同的节点，有的话继续，没有的话，推出，进行反向找

                final HashMap<String, List<String>> commonStartWith = checkCommonStartWith(speechWord, children);

                //获取当前公因式下的节点
                final List<String> commonList = commonStartWith.get(speechWord);

                if (!CollectionUtils.isEmpty(commonList)) {

                    //找到公因式后，后续公因式提取的节点范围发生变化，是上一个公因式的范围
                    children = commonList;

                    continue;
                } else {

                    //记录正向查找的结束位置，也就是反向查找的结束位置
                    positiveEndPosition = i - 1;

                    break;
                }
            }
        }

        map.put("positiveEndPosition", positiveEndPosition);
        map.put("treeNode", treeNode);
        map.put("addNode", addNode);

        return map;
    }

    /**
     * 正向判断
     * <p>
     * 判断当前词有没有公共项，作为能不能提取公因式的条件
     * <p>
     * 需要子节点中有以当前词开头的节点，只要有，就可以提取
     * <p>
     * 注意：
     * 1. 记录同组节点
     * 2. 替换下个公因式的查找范围
     *
     * @param word
     * @param children
     * @return boolean
     * @author XYL
     * @since 23:04 2021/11/27 0027
     **/
    public static HashMap<String, List<String>> checkCommonStartWith(String word, List<String> children) {

        final int size = children.size();

        final LinkedList<String> list = new LinkedList<>();

        for (int j = 0; j < size; j++) {

            //子节点
            String child = children.get(j);

            child = child.replaceAll("[.]", "");

            //判断子节点是否以当前词开头
            if (child.startsWith(word)) {

                list.add(word);
            }
        }

        final HashMap<String, List<String>> map = new HashMap<>(1);

        if (!CollectionUtils.isEmpty(list)) {
            //记录当前公因式组内的节点
            map.put(word, list);
        }

        return map;
    }

    /**
     * 反向判断
     *
     * @param word
     * @param children
     * @return boolean
     * @author XYL
     * @since 23:32 2021/11/27 0027
     **/
    public static HashMap<String, List<String>> checkCommonEndWith(String word, List<String> children) {

        final int size = children.size();

        final LinkedList<String> list = new LinkedList<>();

        for (int j = 0; j < size; j++) {

            //子节点
            String child = children.get(j);

            child = child.replaceAll("[.]", "");

            //判断子节点是否以当前词开头
            if (child.endsWith(word)) {

                list.add(word);
            }
        }

        final HashMap<String, List<String>> map = new HashMap<>(1);

        if (!CollectionUtils.isEmpty(list)) {
            //记录当前公因式组内的节点
            map.put(word, list);
        }

        return map;
    }


}
