package org.qingtai.file.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * org.qingtai.file.utils
 * Created on 2017/10/20
 *
 * @author Lichaojie
 */
public class FileReaderUtil {
    /**
     * 读文件，返回文件内容
     * @param fileName 文件名,使用绝对路径（多模块下被引用的模块被打成jar包，此时无法根据Class文件的相对路径寻找文件）
     * @return
     */
    public static String read(String fileName){
        String context = "";
        String result = "";
        BufferedReader br = null;
        try {
            // 根据编译之后FileReaderUtil.class的相对路径寻找文件名为fileName的文件
            // br = new BufferedReader(new FileReader(new File(FileReaderUtil.class.getResource(fileName).getPath())));
            br = new BufferedReader(new FileReader(fileName));
            while(br.ready()) {
                context += br.readLine() + "\n";
            }

            // 用UTF-8对context进行解码，
            // result = new String(context.getBytes(),"GBK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return context;
    }

    public static void main(String[] args){
        String path = "E:\\Java\\Code\\IdeaProjects\\QingtaiIntranet\\qingtai-file\\src\\main\\resources\\template\\email\\simpleTemplate";
        System.out.println(FileReaderUtil.read(path));

    }
}
