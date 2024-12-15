import cn.hutool.crypto.digest.MD5;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AutoSSRFBurpExtensionTest {
    @Test
    public void testListToString() {
        List<String> list = Arrays.asList("a", "b");
        System.out.println(list);
    }

    @Test
    public void testHash() {
        String hash = MD5.create().digestHex("test");
        System.out.println(hash);
    }

}
