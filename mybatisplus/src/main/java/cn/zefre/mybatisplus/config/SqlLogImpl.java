package cn.zefre.mybatisplus.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @author pujian
 * @date 2022/9/21 13:18
 */
@Slf4j
public class SqlLogImpl extends StdOutImpl {

    private static final String COLUMNS = "<==    Columns:";

    private static final String ROWS = "<==        Row:";

    private static final String LEFT_EQUAL = "<==";

    private static final String RIGHT_EQUAL = "==>";
    private static final String TOTAL = "<==      Total";

    private static Marker marker = MarkerFactory.getDetachedMarker("SQL" + "===*" + "SQL");

    public SqlLogImpl(String clazz) {
        super(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return super.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return super.isTraceEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        print(s);
    }

    @Override
    public void error(String s) {
        print(s);
    }

    @Override
    public void debug(String s) {
        print(s);
    }

    @Override
    public void trace(String s) {
        print(s);
    }

    @Override
    public void warn(String s) {
        print(s);
    }

    public static void print(String s) {
        boolean noPrint = s.startsWith(COLUMNS) || s.startsWith(ROWS) || s.startsWith(TOTAL);
        if (inPrintList(s) && !noPrint) {
            log.info(marker, s);
        }
    }

    private static boolean inPrintList(String s) {
        return s.startsWith(LEFT_EQUAL) || s.startsWith(RIGHT_EQUAL);
    }
}
