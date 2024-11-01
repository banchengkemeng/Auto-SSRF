package ui.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KV {

    /**
     * 属性名
     */
    private String key;

    /**
     * 显示名
     */
    private String value;
}
