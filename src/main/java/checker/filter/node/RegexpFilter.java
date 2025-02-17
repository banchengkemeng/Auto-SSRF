package checker.filter.node;

import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.filter.IFilter;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegexpFilter implements IFilter {

    private RegexpFilterLocationEnum regexpFilterLocationEnum;
    private String regexp;
    private Boolean blackList;

    @Override
    public boolean doFilter(HttpRequestResponse baseRequestResponse, Integer id) {
        // 位置
        HttpRequest request = baseRequestResponse.request();
        String target;
        switch (regexpFilterLocationEnum) {
            case REQUEST_PACKET: {
                target = request.toString();
                break;
            }
            case URL: {
                target = request.url();
                break;
            }
            case PATH: {
                target = request.path();
                break;
            }
            case DATA: {
                target = request.bodyToString();
                break;
            }
            case COOKIE: {
                target = getCookie(request.headers());
                break;
            }
            default: {
                throw new RuntimeException("不合法的过滤位置: " + regexpFilterLocationEnum);
            }
        }

        // 正则匹配, 使用DOTALL模式，.可以匹配换行符
        Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(target);
        boolean regexpResult = matcher.matches();

        // 白名单 or 黑名单
        // blackList = true, regexpResult = true => false
        // blackList = true, regexpResult = false => true
        // blackList = false, regexpResult = true => true
        // blackList = false, regexpResult = false => false
        // 异或

        return blackList ^ regexpResult;
    }

    private String getCookie(List<HttpHeader> headers) {
        for (HttpHeader header : headers) {
            if ("cookie".equalsIgnoreCase(header.name())) {
                return header.value();
            }
        }
        return "";
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public enum RegexpFilterLocationEnum {
        REQUEST_PACKET("requestPacket", "请求包"),
        URL("url", "URL(协议://域名:端口(80/443不加端口)/路径)"),
        PATH("path", "路径(包括GET参数)"),
        DATA("data", "POST参数/请求体"),
        COOKIE("cookie", "COOKIE参数");

        private final String key;
        private final String text;

        public static RegexpFilterLocationEnum getByKey(String key) {
            for (RegexpFilterLocationEnum value : values()) {
                if (value.getKey().equals(key)) {
                    return value;
                }
            }
            return null;
        }

        public static List<RegexpFilterLocationEnum> getEnumList() {
            return Arrays.asList(values());
        }
    }
}



