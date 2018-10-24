package org.qingtai.email.entity;

import java.util.Map;

/**
 * @author Lichaojie
 * Created on 2017/10/20.
 */
public class EmailMetaData {

    private String to;
    private String subject;
    private String template;
    private Map<String,String> dic;

    public String getTo() {
        return to;
    }

    public EmailMetaData setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailMetaData setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public EmailMetaData setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Map<String, String> getDic() {
        return dic;
    }

    public EmailMetaData setDic(Map<String, String> dic) {
        this.dic = dic;
        return this;
    }
}
