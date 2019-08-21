package top.wikl.orientdbgremlin;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XYL
 * @Title: MapTest
 * @ProjectName WiklGraph
 * @Description: TODO
 * @date 2019/5/1616:38
 */
public class MapTest {
    public static void main(String[] args) {

        Map map = new HashMap();
        map.put("success", "true");
        map.put("currentUser", "zhang");

        //net.sf.json.JSONObject 将Map转换为JSON方法
        String string = JSON.toJSONString(map);

        System.out.println(string);
    }
}
