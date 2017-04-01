package pig.dream.classgenerator;

import com.google.googlejavaformat.java.FormatterException;

import java.io.IOException;

/**
 * Created by zhukun on 2017/3/21.
 */
public class ClassGenerator {

    private ProjectModel projectModel;
    private ClassGenerator() {
    }

    public static ClassGenerator create() {
        return new ClassGenerator();
    }

    public ClassGenerator projectModel(ProjectModel projectModel) {
        this.projectModel = projectModel;
        return this;
    }

    public void generate() throws IOException {
        ActivityClassGenerator activityClassGenerator = new ActivityClassGenerator(this.projectModel);
        try {
            activityClassGenerator.generate();
        } catch (FormatterException e) {
            e.printStackTrace();
        }

        if (projectModel.enableFunctionFlag(ProjectModel.FLAG_PRESENTER_ENABLE)) {
            ContractClassGenerator contractClassGenerator = new ContractClassGenerator(this.projectModel);
            try {
                contractClassGenerator.generate();
            } catch (FormatterException e) {
                e.printStackTrace();
            }

            PresenterClassGenerator presenterClassGenerator = new PresenterClassGenerator(this.projectModel);
            try {
                presenterClassGenerator.generate();
            } catch (FormatterException e) {
                e.printStackTrace();
            }
        }
    }

}
