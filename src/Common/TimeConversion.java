package Common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 문자열과 LocalDateTime 변환
 */
public class TimeConversion {
    /**
     * 문자열 -> LocalDateTime(yyyy-MM-dd HH:mm)
     * 
     * @param inputTimeString 변경할 문자열
     * @return 변한된 LocalDateTime 리턴
     */
    public LocalDateTime inputToTimeString(String inputTimeString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(inputTimeString, inputFormatter);

        return dateTime;
    }

    /**
     * 문자열 -> LocalDateTime()yyyy-MM-dd'T'HH:mm
     * 
     * @param str 변환할 문자열
     * @return 변한된 LocalDateTime 리턴
     */
    public LocalDateTime strToTime(String dateTimeString) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = dateTime.format(formatter);

        return formattedDateTime;
    }
}
