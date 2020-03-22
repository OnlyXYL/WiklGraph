package top.wikl.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author XYL
 * @Title: JsonUtils
 * @ProjectName SemanticCube
 * @Description: TODO
 * @date 2018/12/611:15
 */
public class JsonUtils {
    /**
     * Jackson library
     * <p>
     * 判断字符串是不是json
     *
     * @param jsonInString
     * @return
     */
    public static boolean isJSONValid(String jsonInString) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 把Java对象转换成json字符串
     *
     * @param object 待转化为JSON字符串的Java对象
     * @return json 串 or null
     */

    public static String parseObjToJson(Object object) {

        String string = null;

        try {

            string = JSONObject.toJSONString(object);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return string;

    }


    /**
     * 将Json字符串信息转换成对应的Java对象
     *
     * @param json json字符串对象
     * @param c    对应的类型
     */

    public static <T> T parseJsonToObj(String json, Class<T> c) {

        try {

            JSONObject jsonObject = JSON.parseObject(json);

            return JSON.toJavaObject(jsonObject, c);

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        return null;

    }
}
