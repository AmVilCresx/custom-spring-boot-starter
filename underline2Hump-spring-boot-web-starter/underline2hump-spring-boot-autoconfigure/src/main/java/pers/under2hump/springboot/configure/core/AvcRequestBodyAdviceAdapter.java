package pers.under2hump.springboot.configure.core;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import pers.under2hump.springboot.configure.annotation.IgnoreToHump;
import pers.under2hump.springboot.configure.core.model.ParamMiddleTransfer;
import pers.under2hump.springboot.configure.utils.ParameterUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @description 解析body中的消息体
 * @author AmVilCresx
 */
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class AvcRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final Logger logger = LoggerFactory.getLogger(AvcRequestBodyAdviceAdapter.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = methodParameter.getMethod();
        if (Objects.isNull(method) || method.isAnnotationPresent(IgnoreToHump.class)){
            return false;
        }
        Class<?> paramClassType = methodParameter.getParameterType();
        return AbstractHttpMessageConverter.class.isAssignableFrom(converterType)
                && !(ClassUtils.isPrimitiveOrWrapper(paramClassType) || CharSequence.class.isAssignableFrom(paramClassType));
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        String jsonStr = parseInputMessage(inputMessage);
        jsonStr = parseBodyToJson(jsonStr);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonStr.getBytes(StandardCharsets.UTF_8));
        return new MappingJacksonInputMessage(inputStream, inputMessage.getHeaders());
    }

    public String parseInputMessage(HttpInputMessage inputMessage){
        Objects.requireNonNull(inputMessage);
        StringBuilder sb = new StringBuilder();
        try{
            InputStream inputStream = inputMessage.getBody();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }catch (Exception e) {
            // do nothing
        }

        return sb.toString();
    }

    public String parseBodyToJson(String str) {
        List<ParamMiddleTransfer> paramSoftDos = new ArrayList<>();
        String root = UUID.randomUUID().toString();
        ParameterUtils.parseMsgOfBody(paramSoftDos, str, root);

        if (logger.isDebugEnabled()) {
            logger.debug("body消息体中的层级关系用对象表示如下: ");
            paramSoftDos.forEach(item -> logger.debug(item.toString()));
        }

        if(!CollectionUtils.isEmpty(paramSoftDos)) {
            Map<String,List<ParamMiddleTransfer>> paramMapWithParent = paramSoftDos.stream().collect(Collectors.groupingBy(ParamMiddleTransfer::getParent));
            JSONObject jsonObject = new JSONObject();
            paramMapWithParent.forEach((k, v) -> {
                JSONObject perResult = resolveRecursion(paramMapWithParent, v);
                if(StringUtils.endsWithIgnoreCase(k,root)){
                    jsonObject.put(root, perResult);
                }
            });
            if (logger.isDebugEnabled()){
                logger.debug("解析后的body体中的字符串: {}", jsonObject.getString(root));
            }
            return jsonObject.getString(root);
        }
        return "";
    }

    public JSONObject resolveRecursion(Map<String,List<ParamMiddleTransfer>> paramMapWithParent, List<ParamMiddleTransfer> softDos){
        JSONObject parentObj = new JSONObject();
        softDos.forEach(softDo -> {
            String parentName = softDo.getParamName();
            List<ParamMiddleTransfer> loopSoftDos = paramMapWithParent.get(parentName);
            String originalParamName = softDo.getParamName();
            String humpParamName = ParameterUtils.underLine2HumpForParameter(originalParamName);
            if(CollectionUtils.isEmpty(loopSoftDos)){
                parentObj.put(humpParamName, softDo.getValue());
                if(!Objects.equals(originalParamName, humpParamName)) {
                    parentObj.put(originalParamName, softDo.getValue());
                }
                return;
            }
            // 说明还有下级
            parentObj.put(humpParamName, resolveRecursion(paramMapWithParent, loopSoftDos));
        });
        return parentObj;
    }
}
