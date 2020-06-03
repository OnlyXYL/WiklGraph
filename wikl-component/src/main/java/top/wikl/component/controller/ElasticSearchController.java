package top.wikl.component.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.wikl.component.model.elastic.AddDocument;
import top.wikl.component.model.elastic.SearchDocument;
import top.wikl.component.model.elastic.UpdateDocument;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author XYL
 * @title: ElasticSearchController
 * @description: TODO
 * @date 2020/6/2 14:14
 * @return
 * @since V1.0
 */
@Slf4j
@RequestMapping("/elastic")
@RestController
public class ElasticSearchController {

    @Resource
    private RestHighLevelClient client;

    /**
     * 添加文档
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/6/2 14:17
     * @since V1.1
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddDocument addDocument) {

        try {
            IndexRequest indexRequest = new IndexRequest(addDocument.getIndex());

            //设置id，如果为空，自动创建
            if (StringUtils.isNotBlank(addDocument.getId())) {
                indexRequest.id("1");
            }

            indexRequest.source(addDocument.getProperties());

            log.info("SQL", indexRequest);

            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

            return ResponseEntity.ok().body(indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 查询索引
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/6/2 19:18
     * @since V1.1
     */
    @GetMapping("/get/{index}/{id}")
    public ResponseEntity<?> get(@PathVariable("index") String index, @PathVariable("id") String id) {

        try {
            GetRequest getRequest = new GetRequest();
            getRequest.index(index);
            getRequest.id(id);

            log.info("SQL", getRequest);

            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok().body(getResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    /**
     * 更新
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/6/2 19:23
     * @since V1.1
     */
    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateDocument updateDocument) {

        try {
            UpdateRequest updateRequest = new UpdateRequest(
                    updateDocument.getIndex(),
                    updateDocument.getId())
                    .doc(updateDocument.getProperties());

            log.info("SQL", updateRequest);

            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);

            return ResponseEntity.ok().body(updateResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除索引
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/6/2 19:32
     * @since V1.1
     */
    @DeleteMapping("/delete/{index}/{id}")
    public ResponseEntity<?> delete(@PathVariable("index") String index, @PathVariable("id") String id) {

        try {
            DeleteRequest request = new DeleteRequest(
                    index,
                    id
            );

            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
            return ResponseEntity.ok().body(deleteResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchDocument searchDocument) {

        try {

            List<String> indices = searchDocument.getIndices();

            String[] toArray = indices.toArray(new String[indices.size()]);

            SearchRequest searchRequest = new SearchRequest(
                    toArray
            );

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery(searchDocument.getKey(), searchDocument.getValue()));

            //根据评分排序
            searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));

            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            return ResponseEntity.ok().body(searchResponse);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

}
