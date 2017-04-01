package pig.dream.utils;

import pig.dream.classgenerator.ClassGenerator;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by zhukun on 2017/3/21.
 */
public class ActivityCreator {

    private static final int SIZE=1024;
    private String packageName = "pig.dream";
    private String className = "ATest";
    private String fileName = className + ".java";

    private ArrayList<StringBuilder> stringBuilders = new ArrayList<>();
    private ActivityCreator() {}

    public static ActivityCreator create() {
        return new ActivityCreator();
    }

    public void generate(String dir) {
//        File file = FileUtils.createFile(dir, fileName);
//        FileOutputStream fos = null;
//        BufferedOutputStream bos=null;
//        try {
//            fos = new FileOutputStream(file, false);
//            bos=new BufferedOutputStream(fos,SIZE*4);
//            bos.write(ClassGenerator.create().packageName(packageName).className(className).generate().getBytes("utf-8"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            CloseUtils.close(bos);
//            CloseUtils.close(fos);
//        }
    }

    public void writePackageName() {
        StringBuilder sb = new StringBuilder();
        sb.append("package pig.dream.utils;");
        sb.append("\n");
        stringBuilders.add(sb);
    }

    public void writeClassName() {

    }
}
