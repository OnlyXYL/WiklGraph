package top.wikl.component.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.component.config.SmbConfig;
import top.wikl.component.tools.samba.SmbManage;
import top.wikl.utils.excel.FileDownLoad;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author XYL
 * @title: SmbController
 * @description: TODO
 * @date 2020/4/22 16:08
 * @return
 * @since V1.0
 */
@RequestMapping(value = "/smb")
@RestController
public class SmbController {

    @Resource
    private SmbManage smbManage;

    @Resource
    private SmbConfig smbConfig;

    @GetMapping("/down/{id}")
    public ResponseEntity<byte[]> down(@PathVariable("id") String id) {

        String remotePhotoUrl = "smb://ztshare:ztshare@10.0.43.91/share/夏亚岭/5bd6d6b86fef25190fbe675c/108-P10041M01_0001.ppt";

        try {

            String path = smbManage.getPath(id);

            Map<Integer, Map<String, String>> graphIds = smbConfig.getGraphIds();

            byte[] bytes = smbManage.smbGet1(path);

            ResponseEntity<byte[]> responseEntity = FileDownLoad.downloadFile(bytes, "108-P10041M01_0001.ppt");

            return responseEntity;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
