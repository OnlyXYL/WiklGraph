package top.wikl.component.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import top.wikl.component.service.TableService;
import top.wikl.entity.component.WiklRequest;
import top.wikl.entity.component.WiklResponse;
import top.wikl.entity.component.WiklTable;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class TableServiceImpl implements TableService {

    @Override
    public List<WiklTable> tableList() {
        List<WiklTable> list = new LinkedList();
        try {
            ClassLoader classLoader = TableService.class.getClassLoader();
            URL resource = classLoader.getResource("data.json");
            Map map = new ObjectMapper().readValue(resource, Map.class);
            //得到host，用于模拟http请求
            String host = String.valueOf(map.get("host"));
            //解析paths
            LinkedHashMap<String, LinkedHashMap> paths = (LinkedHashMap) map.get("paths");
            if (paths != null) {
                Iterator<Map.Entry<String, LinkedHashMap>> iterator = paths.entrySet().iterator();
                while (iterator.hasNext()) {
                    WiklTable table = new WiklTable();
                    List<WiklRequest> wiklRequestList = new LinkedList<>();
                    String requestType = "";

                    Map.Entry<String, LinkedHashMap> next = iterator.next();
                    String url = next.getKey();//得到url
                    LinkedHashMap<String, LinkedHashMap> value = next.getValue();
                    //得到请求方式，输出结果类似为 get/post/delete/put 这样
                    Set<String> requestTypes = value.keySet();
                    for (String str : requestTypes) {
                        requestType += str + "/";
                    }
                    Iterator<Map.Entry<String, LinkedHashMap>> it2 = value.entrySet().iterator();
                    //解析请求
                    Map.Entry<String, LinkedHashMap> get = it2.next();//得到get
                    LinkedHashMap getValue = get.getValue();
                    String title = (String) ((List) getValue.get("tags")).get(0);//得到大标题
                    String tag = String.valueOf(getValue.get("summary"));
                    //请求体
                    ArrayList parameters = (ArrayList) getValue.get("parameters");
                    if (parameters != null && parameters.size() > 0) {
                        for (int i = 0; i < parameters.size(); i++) {
                            WiklRequest wiklRequest = new WiklRequest();
                            LinkedHashMap<String, Object> param = (LinkedHashMap) parameters.get(i);
                            wiklRequest.setDescription(String.valueOf(param.get("description")));
                            wiklRequest.setName(String.valueOf(param.get("name")));
                            wiklRequest.setType(String.valueOf(param.get("type")));
                            wiklRequest.setParamType(String.valueOf(param.get("in")));
                            wiklRequest.setRequire((Boolean) param.get("required"));
                            wiklRequestList.add(wiklRequest);
                        }
                    }
                    //返回体，比较固定
                    List<WiklResponse> responseList = listResponse();
                    //模拟一次HTTP请求,封装请求体和返回体，如果是Restful的文档可以再补充
                    if (requestType.contains("post")) {
                        Map<String, String> stringStringMap = toPostBody(wiklRequestList);
                        table.setRequestParam(stringStringMap.toString());
//                        String post = NetUtil.post(host + url, stringStringMap);

                        table.setResponseParam(post);
                    } else if (requestType.contains("get")) {
                        String s = toGetHeader(wiklRequestList);
                        table.setResponseParam(s);
//                        String getStr = NetUtil.get(host + url + s);
                        table.setResponseParam(getStr);
                    }

                    //封装Table
                    table.setTitle(title);
                    table.setUrl(url);
                    table.setTag(tag);
                    table.setResponseForm("application/json");
                    table.setRequestType(StringUtils.removeEnd(requestType, "/"));
                    table.setWiklRequestList(wiklRequestList);
                    table.setWiklResponseList(responseList);
                    list.add(table);
                }
            }
            return list;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //封装返回信息，可能需求不一样，可以自定义
    private List<WiklResponse> listResponse() {
        List<WiklResponse> responseList = new LinkedList<WiklResponse>();
        responseList.add(new WiklResponse("受影响的行数", "counts", null));
        responseList.add(new WiklResponse("结果说明信息", "msg", null));
        responseList.add(new WiklResponse("是否成功", "success", null));
        responseList.add(new WiklResponse("返回对象", "data", null));
        responseList.add(new WiklResponse("错误代码", "errCode", null));
        return responseList;
    }

    //封装post请求体
    private Map<String, String> toPostBody(List<WiklRequest> list) {
        Map<String, String> map = new HashMap<>(16);
        if (list != null && list.size() > 0) {
            for (WiklRequest wiklRequest : list) {
                String name = wiklRequest.getName();
                String type = wiklRequest.getType();
                switch (type) {
                    case "string":
                        map.put(name, "string");
                        break;
                    case "integer":
                        map.put(name, "0");
                        break;
                    case "double":
                        map.put(name, "0.0");
                        break;
                    default:
                        map.put(name, "null");
                        break;
                }
            }
        }
        return map;
    }

    //封装get请求头
    private String toGetHeader(List<WiklRequest> list) {
        StringBuffer stringBuffer = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (WiklRequest wiklRequest : list) {
                String name = wiklRequest.getName();
                String type = wiklRequest.getType();
                switch (type) {
                    case "string":
                        stringBuffer.append(name+"&=string");
                        break;
                    case "integer":
                        stringBuffer.append(name+"&=0");
                        break;
                    case "double":
                        stringBuffer.append(name+"&=0.0");
                        break;
                    default:
                        stringBuffer.append(name+"&=null");
                        break;
                }
            }
        }
        String s = stringBuffer.toString();
        if ("".equalsIgnoreCase(s)){
            return "";
        }
        return "?" + StringUtils.removeStart(s, "&");
    }
}