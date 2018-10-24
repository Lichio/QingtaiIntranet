package qingtai.controller;

import org.qingtai.file.download.DownloadMethod;
import org.qingtai.file.upload.UploadMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import qingtai.base.controller.BaseController;


/**
 * qingtai.controller
 * Created on 2017/10/24
 *
 * @author Lichaojie
 */

@Controller
@RequestMapping(value = "/file")
public class FileController extends BaseController {

    private final int MAX_UPLOAD_SIZE_PER_FILE = 104857600; //每个上传文件限制为最大100M

    /**
     * 多文件上传
     * @param uploadFiles
     * @param prefix
     * @return
     */
    @RequestMapping(value = "/multiUpload")
    @ResponseBody
    public String multiUpload(@RequestParam("files") MultipartFile[] uploadFiles,
                         @RequestParam("path") String prefix){
        return UploadMethod.multiUpload(uploadFiles,prefix);
    }

    /**
     * 文件下载
     * @param prefix
     * @param filename
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(@RequestParam String prefix,
                                           @RequestParam String filename) throws Exception{
        return DownloadMethod.download(prefix,filename);
    }


}
