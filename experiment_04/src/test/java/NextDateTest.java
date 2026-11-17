import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NextDateTest {
    private final NextDate nd = new NextDate();

    // ========== 等价类划分测试用例 ==========
    @Test
    void testValidNormalDay() {
        // 普通有效日期
        assertEquals("2025-05-06", nd.getNextDate(2025,5,5));
    }

    @Test
    void testValidMonthEnd() {
        // 月末进位
        assertEquals("2025-04-01", nd.getNextDate(2025,3,31));
    }

    @Test
    void testValidYearEnd() {
        // 年末进位
        assertEquals("2026-01-01", nd.getNextDate(2025,12,31));
    }

    @Test
    void testLeapFeb28() {
        // 闰年2月28
        assertEquals("2024-02-29", nd.getNextDate(2024,2,28));
    }
    @Test
    void testLeapFeb29() {
        // 闰年2月29
        assertEquals("2024-03-01", nd.getNextDate(2024,2,29));
    }
    @Test
    void testCommonFeb28() {
        // 平年2月28
        assertEquals("2023-03-01", nd.getNextDate(2023,2,28));
    }

    @Test
    void test1582Oct4() {
        // 核心特殊用例：1582-10-04 下一天是10-15
        assertEquals("1582-10-15", nd.getNextDate(1582,10,4));
    }

    @Test
    void testInvalid1582Oct10() {
        // 无效等价类：消失十天中的一天
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->nd.getNextDate(1582,10,10));
        assertTrue(e.getMessage().contains("1582年10月5日至14日不存在"));
    }

    @Test
    void testInvalidYearLow() {
        // 年份小于1582
        assertThrows(IllegalArgumentException.class, ()->nd.getNextDate(1581,12,31));
    }
    @Test
    void testInvalidYearHigh() {
        // 年份大于3000
        assertThrows(IllegalArgumentException.class, ()->nd.getNextDate(3001,1,1));
    }
    @Test
    void testInvalidMonth() {
        // 月份非法
        assertThrows(IllegalArgumentException.class, ()->nd.getNextDate(2025,13,1));
        assertThrows(IllegalArgumentException.class, ()->nd.getNextDate(2025,0,1));
    }
    @Test
    void testInvalidDay() {
        // 日期超出当月上限
        assertThrows(IllegalArgumentException.class, ()->nd.getNextDate(2025,4,31));
    }

    // ========== 边界值分析测试用例（最小值、最小值邻值、最大值、最大值邻值） ==========
    @Test
    void testBoundaryMinDate() {
        // 最小合法日期：1582-01-01
        assertEquals("1582-01-02", nd.getNextDate(1582,1,1));
    }
    @Test
    void testBoundaryMaxDate() {
        // 最大合法日期：3000-12-31
        assertThrows(IllegalArgumentException.class, ()->nd.getNextDate(3000,12,31));
    }
    @Test
    void testBoundary1582Oct15() {
        // 1582-10-15（消失十天之后第一天）
        assertEquals("1582-10-16", nd.getNextDate(1582,10,15));
    }
    @Test
    void testBoundaryFeb28Leap() {
        assertEquals("2024-02-29", nd.getNextDate(2024,2,28));
    }
    @Test
    void testBoundaryFeb29Leap() {
        assertEquals("2024-03-01", nd.getNextDate(2024,2,29));
    }
    @Test
    void testBoundaryApr30() {
        // 4月30边界
        assertEquals("2025-05-01", nd.getNextDate(2025,4,30));
    }
}
