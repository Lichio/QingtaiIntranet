package org.qingtai.file.upload;

import org.qingtai.common.utils.Base64Util;
import org.qingtai.file.entity.FileLocation;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * org.qingtai.file.upload
 * Created on 2017/10/25
 *
 * @author Lichaojie
 */
public class UploadMethod {


    /**
     * 上传多个文件
     * @param uploadFiles 待上传的文件
     * @param prefix 文件在客户端的目录（通常选择上传的多个文件都在同一个目录下）
     * @return
     */
    public static String multiUpload(MultipartFile[] uploadFiles, String prefix){
        for (MultipartFile uploadFile : uploadFiles){
            if (!uploadFile.isEmpty()){
                if (!upload(uploadFile,prefix)){
                    return "error : " + uploadFile.getOriginalFilename();
                }
            }else {
                return "error";
            }
        }

        return "ok";
    }

    /**
     * 上传单个文件
     * @param uploadFile 待上传的文件
     * @param prefix 文件在客户端的目录
     * @return
     */
    public static boolean upload(MultipartFile uploadFile,String prefix){
        if (!uploadFile.isEmpty()){

            String filename = uploadFile.getOriginalFilename();
            String[] parts = filename.split(".");
            String path = prefix + parts[0];
            filename = Base64Util.encode(path) + "." + parts[1];

            try{
                uploadFile.transferTo(new File(FileLocation.DIR_PREFIX + filename));
            }catch (Exception ex){
                return false;
            }
        }else {
            return false;
        }
        return true;
    }
}
