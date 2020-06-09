package top.wikl.component.tools.samba;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import top.wikl.component.config.SmbCondition;
import top.wikl.component.config.SmbConfig;
import top.wikl.exception.NotFoundException;

import javax.annotation.Resource;
import java.io.*;
import java.util.Objects;

/**
 * 共享服务上附件的管理类
 *
 * @author XYL
 * @title: SmbManage
 * @description: TODO
 * @date 2020/4/22 11:49
 * @return
 * @since V1.0
 */
@Conditional(SmbCondition.class)
@Component
public class SmbManage {

    @Resource
    private SmbConfig smbConfig;

    public static void main(String[] args) {
        //存放文件的共享目录
        String remotePhotoUrl = "smb://ztshare:ztshare@10.0.43.91/share/夏亚岭/5bd6d6b86fef25190fbe675c/108-P10041M01_0001.ppt";

/*        try {
            smbGet1(remotePhotoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }*/

    }

    /**
     * 获取文件路径
     * <p>
     * eg:
     * smb://ztshare:ztshare@10.0.43.91/share/夏亚岭/5bd6d6b86fef25190fbe675c/108-P10041M01_0001.ppt
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/22 15:52
     * @since V1.1
     */
    public String getPath(String id) {

        String filePath = smbConfig.getFilePath();

        String hostIP = smbConfig.getHostIP();

        String userName = smbConfig.getUserName();

        String passWord = smbConfig.getPassWord();

        String path = "smb://" + userName + ":" + passWord + "@" + hostIP + filePath + id + "/";

        return path;
    }


    /**
     * 方法一：
     *
     * @param remoteUrl 远程路径 smb://192.168.75.204/test/新建 文本文档.txt
     * @throws IOException
     */
    public byte[] smbGet1(String remoteUrl) throws IOException {

        SmbFile smbFile_ = new SmbFile(remoteUrl);

        SmbFile[] smbFiles = smbFile_.listFiles();

        SmbFile smbFile = null;

        if (Objects.isNull(smbFiles)) {
            throw new NotFoundException("路径：【" + remoteUrl + "】下不存在文件！");
        }

        if (smbFiles.length >= 1) {
            System.out.printf("共享文件路径：%s，文件个数：%s \n", remoteUrl, smbFiles.length);
            smbFile = smbFiles[0];
        }

        SmbFileInputStream in = null;

        try {

            String filePath = remoteUrl + smbFile.getName();

            System.out.println("文件全路径：" + filePath);

            // 得到文件的大小
            int length = smbFile.getContentLength();
            byte[] buffer = new byte[length];
            in = new SmbFileInputStream(smbFile);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // 建立smb文件输入流
            while ((in.read(buffer)) != -1) {
                outputStream.write(buffer);
            }

            byte[] bytes = outputStream.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (Objects.nonNull(in)) {
                in.close();
            }

        }

        return null;
    }


    /**
     * // 从共享目录下载文件
     * 方法二：
     * 路径格式：smb://192.168.75.204/test/新建 文本文档.txt
     * smb://username:password@192.168.0.77/test
     *
     * @param remoteUrl 远程路径
     * @param localDir  要写入的本地路径
     */
    public static void smbGet(String remoteUrl, String localDir) {
        InputStream in = null;
        OutputStream out = null;
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl);
            if (remoteFile == null) {
                System.out.println("共享文件不存在");
                return;
            }
            String fileName = remoteFile.getName();
            File localFile = new File(localDir + File.separator + fileName);
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向共享目录上传文件
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/22 15:30
     * @since V1.1
     */
    public static void smbPut(String remoteUrl, String localFilePath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File localFile = new File(localFilePath);
            String fileName = localFile.getName();
            SmbFile remoteFile = new SmbFile(remoteUrl + "/" + fileName);
            in = new BufferedInputStream(new FileInputStream(localFile));
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
