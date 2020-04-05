package top.wikl.utils.excel;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author XiaYaLing
 * @Title: FileZip
 * @ProjectName SemanticCube
 * @Description: TODO
 * @date 2018/9/2018:56
 */
public class FileZip {

    public static byte[] zip(Map<String, byte[]> data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            for (Map.Entry<String, byte[]> entry1 : data.entrySet()) {
                String fileName = entry1.getKey();
                byte[] bytes = entry1.getValue();
                ZipEntry entry = new ZipEntry(fileName);
                entry.setSize(bytes.length);
                zip.putNextEntry(entry);
                zip.write(bytes);
            }
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
}
