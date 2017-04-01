package pig.dream.utils;

import com.google.common.io.Resources;
import com.intellij.openapi.util.ClassLoaderUtil;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * Created by zhukun on 2017/3/21.
 */
public class GenerateHelper {

    private static String MESSAGE = "";

    public static void generate(VirtualFile selectedFile) {
        if (selectedFile != null && selectedFile.isDirectory()) {
            String selectedFilePath = selectedFile.getPath();
            ActivityCreator.create().generate(selectedFilePath);
        }
    }
}
