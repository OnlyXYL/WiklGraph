package top.wikl.component.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordTools {

    public static void main(String[] args) {

        String file = "C:\\Users\\XYL\\Desktop\\国家电网公司变电运维管理规定(试行)-第1分册--油浸式变压器(电抗器)运维细则(1).doc";

        List<String> strings = readWordToList(file);


    }

    /**
     * @param outputUrl 模板保存路径
     */
    public static void changeWord(String outputUrl, String Text) {

        try {
            //获取word文档解析对象
            XWPFDocument doucument = new XWPFDocument(POIXMLDocument.openPackage(outputUrl));
            //获取段落文本对象
            List<XWPFParagraph> paragraph = doucument.getParagraphs();
            //获取首行run对象
            XWPFRun run = paragraph.get(0).getRuns().get(0);
            //设置文本内容
            run.setText(Text);
            //生成新的word
            File file = new File(outputUrl);

            FileOutputStream stream = new FileOutputStream(file);
            doucument.write(stream);
            stream.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeWord(List<String> paragraphs, String path) throws IOException {
        XWPFDocument doc = new XWPFDocument();// 创建Word文件
        XWPFParagraph p = doc.createParagraph();// 新建一个段落
        p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
        p.setBorderBottom(Borders.DOUBLE);//设置下边框
        p.setBorderTop(Borders.DOUBLE);//设置上边框
        p.setBorderRight(Borders.DOUBLE);//设置右边框
        p.setBorderLeft(Borders.DOUBLE);//设置左边框
        XWPFRun r = p.createRun();//创
//        r.setText("清华入学通知书");
//        r.setBold(true);//设置为粗体
//        r.setColor("FF0000");//设置颜色
        for (String paragraph : paragraphs) {
            XWPFParagraph p2 = doc.createParagraph();//创建段落文本
            XWPFRun r2 = p2.createRun();
            r2.setText(paragraph);
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();//创建文件夹
        }
        String OS = System.getProperty("os.name").toLowerCase();
        System.out.println(OS);
        File file2 = null;
        if ("linux".equals(OS)) {
            file2 = new File(path + "/标注.docx");
        } else if (OS.contains("windows")) {
            file2 = new File(path + "\\标注.docx");
        }
        if (!file2.exists()) {
            file2.createNewFile();//创建文件
        }
        FileOutputStream out = null;
        if ("linux".equals(OS)) {
            out = new FileOutputStream(path + "/标注.docx");
        } else if (OS.contains("windows")) {
            out = new FileOutputStream(path + "\\标注.docx");
        }
        doc.write(out);
        out.close();
    }

    public static String readWord(String path) {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
            } else if (path.endsWith(".docx")) {
                FileInputStream fs = new FileInputStream(new File(path));
                XWPFDocument xdoc = new XWPFDocument(fs);
                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                buffer = extractor.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static List<String> readWordToList(String path) {

        ArrayList<String> buffer = new ArrayList<>();

        try {
            if (path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);

                String[] paragraphTexts = ex.getParagraphText();
                for (String paragraphText : paragraphTexts) {

                    if (StringUtils.isNotBlank(paragraphText)) {
                        buffer.add(paragraphText);
                    }
                }
            } else if (path.endsWith(".docx")) {
                FileInputStream fs = new FileInputStream(new File(path));
                XWPFDocument xdoc = new XWPFDocument(fs);
                List<XWPFParagraph> paragraphs = xdoc.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {

                    String text = paragraph.getText();

                    if (StringUtils.isNotBlank(text)) {

                        buffer.add(text);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
