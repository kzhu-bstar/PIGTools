package pig.dream.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Created by zhukun on 2017/3/16.
 */
public class FolderSelectHelper {

    private static ArrayList<String> list = new ArrayList<>();
    static {
    }


    public static boolean isCorrectFolderSelected(Project project, VirtualFile selectedFile) {
        Log.d("=====================================================");
//        String projectName = project.getName();
        String baseName = project.getBasePath();

        if (selectedFile != null && selectedFile.isDirectory()) {
            String selectedFilePath = selectedFile.getPath();
            if (selectedFilePath.startsWith(baseName) && (selectedFilePath.length() > baseName.length())) {
                String path = selectedFilePath.substring(baseName.length() + 1, selectedFilePath.length());
                Log.d("path: " + path);
                String[] split = path.split(File.separator);
                for (String str: split) {
                    Log.d("Split: " + str);
                }
                // Android Studio
                // ProjectName/ModuleName/src/main/java/packageName/className
                // ModuleName src main java packageName className
                if (split.length > 4) {
                    if ("src".equals(split[1]) && "main".equals(split[2]) && "java".equals(split[3])) {
                        // 目录结构正确
                        return true;
                    }

                }

                // Intellij IDEA
                if (split.length >= 1) {
                    if ("src".equals(split[0])) {
                        // 目录结构正确
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
