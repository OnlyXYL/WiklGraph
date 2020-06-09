package top.wikl.component.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.component.service.PocService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author XYL
 * @title: PocController
 * @description: TODO
 * @date 2020/5/9 15:44
 * @return
 * @since V1.0
 */
@RequestMapping(value = "/poc")
@RestController
public class PocController {

    @Resource
    private PocService pocService;

    /**
     * gw_db poc
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/9 15:45
     * @since V1.1
     */
    @GetMapping("/gw_db/{fileName}/{scolumn}/{ecolumn}")
    public ResponseEntity<Map<String, String>> gw_bd(@PathVariable("fileName") String fileName, @PathVariable("scolumn") String scolumn, @PathVariable("ecolumn") String ecolumn) {

        //文件
        String filePath = "E:\\work\\工作\\SmartKG\\开发\\数据\\国家电网\\变电最终数据\\最终数据\\整理后原数据\\" + fileName + ".xls";

        //调用方法
        Map<String, String> map = pocService.gw_bd(filePath, scolumn, ecolumn);

        return ResponseEntity
                .ok()
                .body(
                        map
                );
    }
}
