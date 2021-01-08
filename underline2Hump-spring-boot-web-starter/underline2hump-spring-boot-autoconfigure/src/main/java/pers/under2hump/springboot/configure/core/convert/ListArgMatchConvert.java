package pers.under2hump.springboot.configure.core.convert;

import org.springframework.stereotype.Component;
import pers.under2hump.springboot.configure.utils.ParameterUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description List参数处理转换器, 仅支持基本类型及其包装类型或String
 * @author AmVilCresx
 * @date 2020/11/10
 */
@Component
public class ListArgMatchConvert extends ArgMatchConvert<List<?>> {

    @Override
    public List<?> doHandleConvert(String source) throws Exception {
        String[] array = ParameterUtils.splitContent4ArrayOrCollection(source, ",");
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(array).collect(Collectors.toList());
    }

    @Override
    public boolean match(Class<?> target) {
        return List.class.isAssignableFrom(target);
    }
}
