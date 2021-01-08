package pers.under2hump.springboot.configure.core.convert;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pers.under2hump.springboot.configure.utils.DateUtils;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * @description 日期类型参数转换器
 * @author AmVIlCresx
 */
@Component
public class DateArgMatchConvert extends ArgMatchConvert<Date> {

    private static Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    @Override
    public Date doHandleConvert(String value) throws Exception{
        if(!StringUtils.hasText(value)){
            return null;
        }

        if(isNumeric(value)){
            return new Date(Long.parseLong(value));
        }

        try{
            return DateUtils.DATE_FORMAT.get().parse(value);
        }catch(Exception e) {
            // 尝试使用 yyyy-MM-dd 格式来解析
            return DateUtils.parseWithPattern(value, DateUtils.YYYY_MM_DD);
        }
    }

    @Override
    public boolean match(Class<?> target) {
        return Date.class.isAssignableFrom(target);
    }

    public static boolean isNumeric(String str) {
        if (!StringUtils.hasText(str)){
            return false;
        }
        return NUMBER_PATTERN.matcher(str).matches();
    }
}
