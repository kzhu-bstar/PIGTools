package pig.dream.utils;

/**
 * Created by zhukun on 2017/3/16.
 */
public class Log {
    public static final boolean DEBUG = true;

    public static void d(String output) {
        if (DEBUG) {
            System.out.println(output);
        }
    }

    public static void e(Exception e) {
        e.printStackTrace();
    }
}
