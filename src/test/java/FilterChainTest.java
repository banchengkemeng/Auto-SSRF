import checker.filter.FilterChain;
import checker.filter.IFilter;
import checker.filter.node.RegexpFilter;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class FilterChainTest {

    private final FilterChain filterChain = new FilterChain()
            .addFilter("a", new RegexpFilter())
            .addFilter("b", new RegexpFilter())
            .addFilter("c", new RegexpFilter())
            .addFilter("d", new RegexpFilter());

    @Test
    public void testRemoveFilter() {
        printFilterChain();
        filterChain.removeFilter("c");
        printFilterChain();
    }

    @Test
    public void testTopMove() {
        printFilterChain();
        filterChain.topMove("d");
        printFilterChain();
    }

    @Test
    public void testBottomMove() {
        printFilterChain();
        filterChain.bottomMove("d");
        printFilterChain();
    }

    private void printFilterChain() {
        LinkedHashMap<String, IFilter> filterMap = filterChain.getFilterMap();
        System.out.println(filterMap);
        int index = 1;
        for (Map.Entry<String, IFilter> entry : filterMap.entrySet()) {
            System.out.printf("%d: %s\n", index++, entry.getKey());
        }
    }
}
