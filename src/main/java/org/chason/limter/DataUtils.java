package org.chason.limter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类
 * Author: chason
 * Date: 2019/5/3 21:55
 **/
public class DataUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMATTER_2 = DateTimeFormatter.ofPattern("mm:ss");

    /**
     * 从long时间转换成LocalDateTime
     *
     * @param longValue
     * @return
     */
    public static LocalDateTime getDateStrFromLong(long longValue) {
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue),
                ZoneId.systemDefault());
        return date;
    }

}
