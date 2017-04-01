package pig.dream.utils;

import java.io.*;

/**
 * Created by zhukun on 2017/3/21.
 */
public class FileUtils {

    public static File generateToFile(String dir, String fileName, String content) {
        File file = new File(dir, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PrintWriter out = new PrintWriter(file);
            try {
                out.print(content);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
