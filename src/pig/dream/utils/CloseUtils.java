package pig.dream.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by zhukun on 2017/3/21.
 */
public class CloseUtils {

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
