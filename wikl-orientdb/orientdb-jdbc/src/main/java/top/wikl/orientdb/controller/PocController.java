package top.wikl.orientdb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.orientdb.enums.DicType;
import top.wikl.orientdb.model.GetDicInput;
import top.wikl.orientdb.service.PocService;

import javax.annotation.Resource;

/**
 * @author XYL
 * @title: PocController
 * @description: TODO
 * @date 2020/5/14 10:47
 * @return
 * @since V1.0
 */
@RequestMapping(value = "/ojdbc")
@RestController
public class PocController {

    @Resource
    private PocService pocService;

    /**
     * 生成字典
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/14 10:48
     * @since V1.1
     */
    @GetMapping("/dic/{graphId}/{dicType}")
    public ResponseEntity<?> dic(@PathVariable("graphId") Integer graphId, @PathVariable("dicType") String dicType) {

        GetDicInput dicInput = new GetDicInput();

        dicInput.setGraphId(graphId);

        dicInput.setDicType(DicType.getEnumByKey(dicType));

        dicInput.setFilePath("C:\\Users\\XYL\\Desktop\\字典\\");

        dicInput.setSuffix("dic");

        //调用方法
        pocService.getDic(dicInput);

        return ResponseEntity.ok(true);
    }

}
