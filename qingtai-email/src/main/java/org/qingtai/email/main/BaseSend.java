package org.qingtai.email.main;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.qingtai.email.entity.EmailMetaData;
import org.qingtai.email.entity.RegisterMetaData;
import org.qingtai.file.utils.FileReaderUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author Lichaojie
 * Created on 2017/10/20.
 */
public class BaseSend {

    //发送端邮箱
    private String from;
    //发送端邮箱的授权码
    private String authorization;
    //邮件服务器
    private String hostname;

    public BaseSend(){
        from = "594100293@qq.com";
        authorization = "voiyccolaqljbfed";
        hostname = "smtp.qq.com";
    }

    public BaseSend(String from,String authorization,String hostname){
        this.from = from;
        this.authorization = authorization;
        this.hostname = hostname;
    }

    /**
     * 根据邮件模板生成邮件主体
     * @param emailMetaData
     * @return
     */
    public String getContext(EmailMetaData emailMetaData){
        Map<String,String> dic = emailMetaData.getDic();
        Set<String> set = dic.keySet();
        String template = FileReaderUtil.read(emailMetaData.getTemplate());
        for(String key : set) {
            String value = dic.get(key);
            template = template.replaceAll(key, value);
        }
        return template;
    }

    /**
     * 默认以HTML格式发送
     * @param emailMetaData
     * @return
     */
    public int send(EmailMetaData emailMetaData){

        try {
            Email email = new HtmlEmail();
            //Email email = new SimpleEmail();
            email.setCharset("UTF-8");
            email.setHostName(hostname);
            email.setSmtpPort(25);
            // 使用我的邮箱发送信息
            email.setAuthenticator(new DefaultAuthenticator(from, authorization));

            email.setSSLOnConnect(true);
            email.setFrom(from);
            email.setSubject(new String(emailMetaData.getSubject().getBytes(),"UTF-8"));
            email.setMsg(getContext(emailMetaData));
            email.addTo(emailMetaData.getTo());
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
            //send(emailMetaData);
            return -1;//发送失败
        }
        return 0;//发送成功
    }

    public static void main(String[] args){
        new BaseSend().send(new RegisterMetaData("leen","lich_hb@163.com"));
        //new BaseSend().send(new RegisterMetaData("lxx","843989276@qq.com"));//843989276@qq.com
    }

}
