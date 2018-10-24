package org.qingtai.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * org.qingtai.common.utils
 * Created on 2017/10/20
 *
 * @author Lichaojie
 */
public class TimeFormatUtil {

    /**
     * 格式化一个Date
     * @param date
     * @return
     */
    public static String timeFormat(Date date) {
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(date);
    }

    /**
     * 格式化当前时间
     * @return
     */
    public static String nowTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 格式化当前日期
     * @return
     */
    public static String nowDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
