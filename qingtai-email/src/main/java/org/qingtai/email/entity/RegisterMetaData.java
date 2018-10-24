package org.qingtai.email.entity;

import org.qingtai.common.utils.TimeFormatUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lichaojie
 * Created on 2017/10/20.
 */
public class RegisterMetaData extends EmailMetaData{
    public RegisterMetaData(String username,String email){
        super.setTemplate("E:\\Java\\Code\\IdeaProjects\\QingtaiIntranet\\qingtai-file\\src\\main\\resources\\template\\email\\simpleTemplate");
        super.setTo(email);
        Map<String,String> map = new HashMap<String, String>();
        map.put("%username",username);
        map.put("%time", TimeFormatUtil.nowDateFormat());
        super.setDic(map);
        super.setSubject("欢迎加入青苔团队");

    }
}
