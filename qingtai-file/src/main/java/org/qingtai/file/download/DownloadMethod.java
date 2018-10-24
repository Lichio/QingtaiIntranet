package org.qingtai.file.download;

import org.apache.commons.io.FileUtils;
import org.qingtai.common.utils.Base64Util;
import org.qingtai.file.entity.FileLocation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * org.qingtai.file.download
 * Created on 2017/10/25
 *
 * @author Lichaojie
 */
public class DownloadMethod {


    /**
     * 基于ResponseEntity的下载实现
     * @param prefix
     * @param filename
     * @return
     * @throws Exception
     */
    public static ResponseEntity<byte[]> download(String prefix,String filename) throws Exception{
        String[] parts = filename.split(".");
        String path = prefix + parts[0];
        String nfilename = Base64Util.encode(path) + "." + parts[1];
        File file = new File(FileLocation.DIR_PREFIX + nfilename);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + filename);
        headers.setContentLength(file.length());
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, statusCode);
        return entity;
    }

    /**
     * Java通用下载实现
     * @param prefix
     * @param filename
     * @param response
     */
    public static void download(String prefix,String filename,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

        try {
            String[] parts = filename.split(".");
            String filePath = prefix + parts[0];
            String nfilename = Base64Util.encode(filePath) + "." + parts[1];
            filePath = FileLocation.DIR_PREFIX + nfilename;
            File file = new File(filePath);

            response.setHeader("Content-Length", Long.toString(file.length()));

            //打开本地文件流
            InputStream inputStream = new FileInputStream(file);
            //激活下载操作
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (Exception e){

        }
    }


}
