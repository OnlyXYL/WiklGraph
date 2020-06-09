package top.wikl.component.controller;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.wikl.component.service.PoiService;
import top.wikl.component.utils.WordTools;
import top.wikl.utils.date.WiklDateUtil;
import top.wikl.utils.excel.FileDownLoad;
import top.wikl.utils.json.JsonUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/word")
    public ResponseEntity<?> readWord(){

        String file = "C:\\Users\\XYL\\Desktop\\国家电网公司变电运维管理规定(试行)-第1分册--油浸式变压器(电抗器)运维细则(1).doc";

        try {
//            List<String> wordText = ParseWordUtil.getWordText(file);

            List<String> strings = WordTools.readWordToList(file);

            String s = JsonUtils.parseObjToJson(strings);

            return ResponseEntity.ok().body(s);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 按段落解析一个word文档
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/doc/upload")
    public Map uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String textFileName = file.getOriginalFilename();

        //创建一个map对象存放word中的内容
        Map wordMap = new LinkedHashMap();
        try {

            //判断文件格式
            if (textFileName.endsWith(".doc")) {
                InputStream fis = file.getInputStream();

                //使用HWPF组件中WordExtractor类从Word文档中提取文本或段落
                WordExtractor wordExtractor = new WordExtractor(fis);
                int i = 1;

                //获取段落内容
                for (String words : wordExtractor.getParagraphText()) {
                    System.out.println(words);
                    wordMap.put("DOC文档，第（" + i + "）段内容", words);
                    i++;
                }
                fis.close();
            }


            if (textFileName.endsWith(".docx")) {

                //创建一个临时文件
                File uFile = new File("tempFile.docx");
                if (!uFile.exists()) {
                    uFile.createNewFile();
                }

                //复制文件内容
                FileCopyUtils.copy(file.getBytes(), uFile);

                //包含所有POI OOXML文档类的通用功能，打开一个文件包。
                OPCPackage opcPackage = POIXMLDocument.openPackage("tempFile.docx");

                //使用XWPF组件XWPFDocument类获取文档内容
                XWPFDocument document = new XWPFDocument(opcPackage);
                List<XWPFParagraph> paras = document.getParagraphs();
                int i = 1;
                for (XWPFParagraph paragraph : paras) {
                    String words = paragraph.getText();
                    System.out.println(words);
                    wordMap.put("DOCX文档，第（" + i + "）段内容", words);
                    i++;
                }
                uFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(wordMap);
        return wordMap;
    }

}
