package pig.dream.classgenerator;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.squareup.javapoet.*;
import pig.dream.utils.FileUtils;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * Created by zhukun on 2017/3/23.
 */
public class PresenterClassGenerator {

    private ProjectModel projectModel;

    public PresenterClassGenerator(ProjectModel projectModel) {
        this.projectModel = projectModel;
    }

    public void generate() throws IOException, FormatterException {

        TypeSpec contractInterface = TypeSpec.classBuilder(projectModel.presenterClassName)
                .superclass(ClassName.get(projectModel.packageName, projectModel.contractClassName + ".Presenter"))
                .addModifiers(Modifier.PUBLIC)
                .build();

        JavaFile javaFile = JavaFile.builder(projectModel.packageName, contractInterface)
                .build();

        if (projectModel.filePath != null && projectModel.filePath.isDirectory()) {
//            StringBuilder stringBuilder = new StringBuilder();
//            javaFile.writeTo(stringBuilder);
            String result = new Formatter().formatSource(javaFile.toString());

//            System.out.println(result);
            FileUtils.generateToFile(projectModel.filePath.getAbsolutePath(), projectModel.presenterClassName + ".java", result);
        } else {
            System.out.println("----------------------Presenter class Content:");
            javaFile.writeTo(System.out);
        }
    }

    public static class ClassInfo {
        public String packageName;
        public String className;
    }
}
