package org.qingtai.email.main;

/**
 * @author Lichaojie
 * Created on 2017/10/20.
 */
public class ExternalSend extends BaseSend {

    /**
     * 利用青苔的对外邮箱发送邮件
     * @param from
     * @param authorization
     * @param host
     */
    public ExternalSend(String from,String authorization,String host){
        super(from,authorization,host);
    }
}
