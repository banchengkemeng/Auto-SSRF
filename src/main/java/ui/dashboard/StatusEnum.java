package ui.dashboard;

import lombok.Getter;

@Getter
public enum StatusEnum {
    WAITING("waiting", "待检测"),
    CHECKING("checking", "检测中"),
    ERROR("error", "检测出错"),
    FAILED("failed", "无漏洞"),
    SUCCESS("success", "疑似漏洞");

    private final String key;

    private final String text;

    StatusEnum(String key, String text) {
        this.key = key;
        this.text = text;
    }
}