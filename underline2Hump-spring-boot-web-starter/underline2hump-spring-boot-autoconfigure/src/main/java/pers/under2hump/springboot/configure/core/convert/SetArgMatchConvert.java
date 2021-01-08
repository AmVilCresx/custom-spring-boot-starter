package pers.under2hump.springboot.configure.core.convert;

import org.springframework.stereotype.Component;
import pers.under2hump.springboot.configure.utils.ParameterUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description Set参数处理转换器, 仅支持基本类型及其包装类型或String
 * @author AmVilCresx
 */
@Component
public class SetArgMatchConvert extends ArgMatchConvert<Set<?>> {

    @Override
    public Set<?> doHandleConvert(String source) throws Exception {
        String[] array = ParameterUtils.splitContent4ArrayOrCollection(source, ",");
        if (array.length == 0) {
            return Collections.emptySet();
        }
        return Arrays.stream(array).collect(Collectors.toSet());
    }

    @Override
    public boolean match(Class<?> target) {
        return Set.class.isAssignableFrom(target);
    }
}
