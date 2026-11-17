public class NextDate {
    /**
     * 计算下一天日期
     * @param year 年
     * @param month 月
     * @param day 日
     * @return String 格式 yyyy-MM-dd
     * @throws IllegalArgumentException 日期非法时抛出
     */
    public String getNextDate(int year, int month, int day) {
        // 1.年份范围校验
        if (year < 1582 || year > 3000) {
            throw new IllegalArgumentException("年份超出范围，必须在1582~3000之间");
        }
        // 2.月份校验
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("月份非法，必须1~12");
        }
        // 3.日期基础校验
        int maxDay = getMaxDay(year, month);
        if (day < 1 || day > maxDay) {
            throw new IllegalArgumentException("日期超出当月合法范围");
        }
        // 4.【重点】1582-10-05 ~ 10-14 非法
        if (year == 1582 && month == 10 && day >=5 && day <=14) {
            throw new IllegalArgumentException("1582年10月5日至14日不存在，非法日期");
        }

        // 开始计算下一天
        int nextY = year;
        int nextM = month;
        int nextD = day + 1;

        // 当月未满，直接+1
        if (nextD <= maxDay) {
            // 特殊：1582-10-04 +1 直接跳到10-15
            if (year == 1582 && month == 10 && day == 4) {
                nextD = 15;
            }
            return String.format("%04d-%02d-%02d", nextY, nextM, nextD);
        } else {
            // 日期进位，月份+1
            nextD = 1;
            nextM = month + 1;
            if (nextM > 12) {
                nextM = 1;
                nextY = year + 1;
            }
            return String.format("%04d-%02d-%02d", nextY, nextM, nextD);
        }
    }

    // 获取某个月最大天数，处理闰年
    private int getMaxDay(int year, int month) {
        return switch (month) {
            case 1,3,5,7,8,10,12 -> 31;
            case 4,6,9,11 -> 30;
            case 2 -> isLeapYear(year) ? 29 : 28;
            default -> 0;
        };
    }

    // 闰年判断：能被400整除，或者能被4整除且不能被100整除
    private boolean isLeapYear(int y) {
        return (y % 400 == 0) || (y % 4 == 0 && y % 100 != 0);
    }
}
