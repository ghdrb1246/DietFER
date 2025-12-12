package Common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 문자열과 LocalDateTime 변환
public class TimeConversion {
    /**
     * 문자열 -> LocalDateTime
     * 
     * @param str 변환할 믄자열
     * @return 변한된 LocalDateTime 리턴
     */
    public LocalDateTime strToTime(String dateTimeString) {
        // String dateTimeString = "2023-06-16T12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        return dateTime;
    }
    
    /**
     * LocalDateTime -> 문자열
     * 
     * @param str 변환할 LocalDateTime
     * @return 변한된 문자열 리턴
     */
    public String timeToStr(LocalDateTime dateTime) {
        // LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        return formattedDateTime;
    }
}
