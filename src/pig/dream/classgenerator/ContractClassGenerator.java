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
public class ContractClassGenerator {

    private ProjectModel projectModel;

    public ContractClassGenerator(ProjectModel projectModel) {
        this.projectModel = projectModel;
    }

    public void generate() throws IOException, FormatterException {
        TypeSpec viewInterface = TypeSpec.interfaceBuilder("View")
                .addSuperinterface(ClassName.bestGuess("pig.dream.baselib.mvp.BaseView"))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .build();

        ClassName  className = ClassName.bestGuess("pig.dream.baselib.mvp.BasePresenter");
        ClassName an = ClassName.bestGuess(projectModel.contractClassName+ ".View");
        TypeName basePresenter = ParameterizedTypeName.get(className, an);
        TypeSpec presenterAbstract = TypeSpec.classBuilder("Presenter")
                .superclass(basePresenter)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.ABSTRACT)
                .build();

        TypeSpec contractInterface = TypeSpec.interfaceBuilder(projectModel.contractClassName)
                .addModifiers(Modifier.PUBLIC)
                .addType(viewInterface)
                .addType(presenterAbstract)
                .build();

        JavaFile javaFile = JavaFile.builder(projectModel.packageName, contractInterface)
                .build();
        if (projectModel.filePath != null && projectModel.filePath.isDirectory()) {
//            StringBuilder stringBuilder = new StringBuilder();
//            javaFile.writeTo(stringBuilder);
            String result = new Formatter().formatSource(javaFile.toString());

//            System.out.println(result);
            FileUtils.generateToFile(projectModel.filePath.getAbsolutePath(), projectModel.contractClassName + ".java", result);
        } else {
            System.out.println("----------------------Contract class Content:");
            javaFile.writeTo(System.out);
        }
    }

    public static class ClassInfo {
        public String packageName;
        public String className;
    }
}
