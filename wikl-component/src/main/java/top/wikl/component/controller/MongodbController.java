package top.wikl.component.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.component.model.mongo.KgBuildFileRecord;
import top.wikl.component.model.mongo.KgMongoConcept;
import top.wikl.component.model.mongo.KgNodeInfo;
import top.wikl.component.service.mongo.KgBuildFIleRecordService;
import top.wikl.component.service.mongo.KgConceptService;
import top.wikl.component.service.mongo.KgNodeInfoService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 16:44
 */
@RequestMapping("/mongo")
@RestController
public class MongodbController {

    @Resource
    private KgBuildFIleRecordService kgBuildFIleRecordService;

    @Resource
    private KgConceptService kgConceptService;

    @Resource
    private KgNodeInfoService kgNodeInfoService;

    @GetMapping("/insert")
    public ResponseEntity<?> insert() {

        //1. 插入记录

        KgBuildFileRecord record = new KgBuildFileRecord();
        record.setCreateBy(1);
        record.setCreateTime(new Date());
        record.setGraphId(11);

        KgBuildFileRecord insert = kgBuildFIleRecordService.insert(record);

        //2. 插入概念

        KgMongoConcept kgMongoConcept = new KgMongoConcept();

        kgMongoConcept.setRecordId(insert.getId());
        kgMongoConcept.setCreateTime(new Date());
        kgMongoConcept.setCreateBy(1);
        kgMongoConcept.setConceptName("员工");

        KgMongoConcept concept = kgConceptService.insert(kgMongoConcept);

        //3. 插入详情
        KgNodeInfo info = new KgNodeInfo();
        info.setConceptId(concept.getId());
        info.setCheckPass(true);

        HashMap<String, Object> pro = new HashMap<>(5);

        pro.put("姓名", "张三");
        pro.put("性别", "男");
        pro.put("年龄", 18);
        pro.put("地址", "北京");
        pro.put("国家", "中国");

        info.setProperties(pro);

        KgNodeInfo nodeInfo = kgNodeInfoService.insert(info);

        System.out.println(nodeInfo.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<KgNodeInfo>> page() {

        String conceptId = "5feafb038f3f5b087f0e9719";

        Page<KgNodeInfo> list = kgNodeInfoService.findList(0, 10,conceptId);

        return ResponseEntity.ok(list);
    }
}
