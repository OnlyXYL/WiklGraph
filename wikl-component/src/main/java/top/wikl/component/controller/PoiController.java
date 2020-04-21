package top.wikl.component.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.component.service.PoiService;
import top.wikl.utils.date.WiklDateUtil;
import top.wikl.utils.excel.FileDownLoad;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author XYL
 * @title: ExcelController
 * @description: TODO
 * @date 2020/4/1 19:24
 * @return
 * @since V1.0
 */
@RequestMapping(value = "/poi")
@RestController
public class PoiController {

    @Resource
    private PoiService poiService;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportConceptRelationData() {

        String fileName = WiklDateUtil.formatDate(new Date(), "yyyyMMdd") + ".zip";

        //excel文件路径
        String filePath = "C:\\Users\\XYL\\Desktop\\知识分类.xlsx";

        String sheetName = "指标信息";

        byte[] excel = poiService.excel(filePath, sheetName);

        try {
            ResponseEntity<byte[]> responseEntity = FileDownLoad.downloadFile(excel, fileName);

            return responseEntity;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
