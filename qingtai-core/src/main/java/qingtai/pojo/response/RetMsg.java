package qingtai.pojo.response;

/**
 * qingtai.pojo.response
 * Created on 2017/10/25
 *
 * @author Lichaojie
 */
public class RetMsg {

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 状态
     */
    private Boolean status;

    public int getCode() {
        return code;
    }

    public RetMsg setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RetMsg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public RetMsg setStatus(boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "RetMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
