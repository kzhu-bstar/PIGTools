package pig.dream.classgenerator;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;
import com.squareup.javapoet.*;
import pig.dream.utils.FileUtils;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * 生产Activity
 *
 * Created by zhukun on 2017/3/23.
 * @version 1.0
 */
public class ActivityClassGenerator {

    private ProjectModel projectModel;
    private StringBuilder functionFlagSb = new StringBuilder();

    public ActivityClassGenerator(ProjectModel projectModel) {
        this.projectModel = projectModel;
    }

    public void generate() throws IOException, FormatterException {
        MethodSpec getContentView = MethodSpec.methodBuilder("getContentView")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class)
                .addAnnotation(Override.class)
                .addStatement("return 0")
                .build();

        MethodSpec init = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addAnnotation(Override.class)
                .build();


        TypeSpec.Builder activityClassBuilder = TypeSpec.classBuilder(projectModel.activityClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(getContentView)
                .addMethod(init);

        generatePresenter(activityClassBuilder);
        generateOnInsetsChangedMethod(activityClassBuilder);
        TypeSpec activityClass = generateInitFunctionFlagMethod(activityClassBuilder).build();

        JavaFile javaFile = JavaFile.builder(projectModel.packageName, activityClass)
                .build();

        if (projectModel.filePath != null && projectModel.filePath.isDirectory()) {
//            StringBuilder stringBuilder = new StringBuilder();
//            javaFile.writeTo(stringBuilder);
            String result = new Formatter().formatSource(javaFile.toString());

//            System.out.println(result);
            FileUtils.generateToFile(projectModel.filePath.getAbsolutePath(), projectModel.activityClassName + ".java", result);
        } else {
            System.out.println("----------------------Activity class Content:");
            javaFile.writeTo(System.out);
        }
    }



    private TypeSpec.Builder generatePresenter(TypeSpec.Builder activityClassBuilder) {
        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_STATUSBAR_ENABLE)) {
            // ignore
        }

        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_TOOLBAR_ENABLE)) {
            // ignore
        }

        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_SWIPEBACK_ENABLE)) {
            // ignore
        }

        ClassName baseActivityName = ClassName.bestGuess("pig.dream.baselib.layout.BaseActivity");
        TypeName superClassName = null;
        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_PRESENTER_ENABLE)) {
            ClassName presenterClassName = ClassName.get(projectModel.packageName, projectModel.presenterClassName);
            superClassName = ParameterizedTypeName.get(baseActivityName, presenterClassName);

            ClassName viewClassName = ClassName.get(projectModel.packageName, projectModel.contractClassName + ".view");
            activityClassBuilder.addSuperinterface(viewClassName);
        } else {
            superClassName = baseActivityName;
        }

        activityClassBuilder.superclass(superClassName);

        return activityClassBuilder;
    }

    private TypeSpec.Builder generateInitFunctionFlagMethod(TypeSpec.Builder activityClassBuilder) {
        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_STATUSBAR_ENABLE)) {
            addfunctionFlagSb("BaseUI.FLAG_STATUSBAR_ENABLE");
        }

        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_TOOLBAR_ENABLE)) {
            addfunctionFlagSb("BaseUI.FLAG_TOOLBAR_ENABLE");
        }

        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_SWIPEBACK_ENABLE)) {
            addfunctionFlagSb("BaseUI.FLAG_SWIPEBACK_ENABLE");
        }

        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_PRESENTER_ENABLE)) {
            addfunctionFlagSb("BaseUI.FLAG_PRESENTER_ENABLE");
        }

        if (functionFlagSb.length() != 0) {
            MethodSpec initFunctionFlag = MethodSpec.methodBuilder("initFunctionFlag")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(int.class)
                    .addAnnotation(Override.class)
                    .addStatement("return " + functionFlagSb.toString())
                    .build();

            activityClassBuilder.addMethod(initFunctionFlag);
        }

        return activityClassBuilder;
    }

    private TypeSpec.Builder generateOnInsetsChangedMethod(TypeSpec.Builder activityClassBuilder) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("onInsetsChanged")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get("android.graphics", "Rect"), "insets")
                .returns(void.class)
                .addAnnotation(Override.class);
        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_STATUSBAR_ENABLE)
                && projectModel.enableFunctionFlag(ProjectModel.FLAG_TOOLBAR_ENABLE)) {
            builder.addStatement("getView(R.id.rl_toolbar_parent).setPadding(0, insets.top, 0, 0)");
        }

        activityClassBuilder.addMethod(builder.build());
        return activityClassBuilder;
    }

    private void addfunctionFlagSb(String content) {
        if (functionFlagSb.length() == 0) {
            functionFlagSb.append(content);
        } else {
            functionFlagSb.append(" | ");
            functionFlagSb.append(content);
        }
    }
}
