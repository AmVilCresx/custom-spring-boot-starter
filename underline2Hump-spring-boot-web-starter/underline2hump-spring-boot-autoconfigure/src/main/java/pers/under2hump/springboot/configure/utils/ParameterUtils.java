package pers.under2hump.springboot.configure.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import pers.under2hump.springboot.configure.core.model.ParamMiddleTransfer;

import java.util.List;

/**
 * @description 方法参数处理工具类
 * @author AmVilCresx
 */
public class ParameterUtils {

    private final static String UNDER_LINE = "_";

    private final static String JSON_STR_START= "{";

    private final static String DEFAULT_DELIMITER = ",";

    /**
     * 下划线转驼峰并且首字母小写
     *
     * @param source 原字符串
     * @return 处理后的字符串
     */
    public static String underLine2HumpForParameter(String source) {
        if(!StringUtils.hasText(source) || !source.contains(UNDER_LINE)){
            return source;
        }
        while (source.contains(UNDER_LINE)) {
            int idx = source.indexOf(UNDER_LINE);
            source = source.replaceFirst(UNDER_LINE, "");
            // 下标不是最后一位
            if (idx != -1 && idx <= source.length() - 1) {
                char[] chs = source.toCharArray();
                chs[idx] = Character.toUpperCase(chs[idx]);
                // 首字母小写
                chs[0] = Character.toLowerCase(chs[0]);
                source = String.valueOf(chs);
            }
        }
        return source;
    }

    /**
     * 将Json自字符串解析成 {@link ParamMiddleTransfer} 对象
     *
     * @param paramSoftDos List<ParamMiddle>
     * @param source 待解析的字符串
     * @param parent 父级对象
     */
    public static void parseMsgOfBody(List<ParamMiddleTransfer> paramSoftDos, String source, String parent) {
        JSONObject object = JSONObject.parseObject(source);
        object.forEach((k, v) -> {
            ParamMiddleTransfer softDo = new ParamMiddleTransfer(k, v, parent);
            paramSoftDos.add(softDo);
            String strValue;
            if (StringUtils.hasText(strValue = String.valueOf(v)) && strValue.startsWith(JSON_STR_START)) {
                parseMsgOfBody(paramSoftDos, strValue, k);
            }
        });
    }

    /**
     *
     * @param content 字符串
     * @return String[]
     */
    public static String[] splitContent4ArrayOrCollection(String content, String delimiter){
        String[] defRes = new String[0];
        if(!StringUtils.hasText(content)){
            return defRes;
        }
        content = content.replaceAll("\\[","").replaceAll("]", "");
        if(!StringUtils.hasText(content)){
            return defRes;
        }
        if(!content.contains(DEFAULT_DELIMITER)){
            return new String[]{content};
        }
        return content.split(delimiter);
    }
}
