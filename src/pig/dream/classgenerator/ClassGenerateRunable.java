package pig.dream.classgenerator;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

/**
 * Created by zhukun on 2017/3/29.
 */
public class ClassGenerateRunable extends WriteCommandAction.Simple {

    private ProjectModel projectModel;

    public ClassGenerateRunable(Project project, ProjectModel projectModel, PsiFile... files) {
        super(project, files);
        this.projectModel = projectModel;
    }

    protected ClassGenerateRunable(Project project, String commandName, PsiFile... files) {
        super(project, commandName, files);
    }

    protected ClassGenerateRunable(Project project, String name, String groupID, PsiFile... files) {
        super(project, name, groupID, files);
    }

    @Override
    protected void run() throws Throwable {
        ClassGenerator.create().projectModel(projectModel).generate();
    }


}
