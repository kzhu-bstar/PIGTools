package pig.dream;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by zhukun on 2017/3/27.
 */
public class AndroidFindStyleApplicationsDialog extends DialogWrapper {

    protected AndroidFindStyleApplicationsDialog(@Nullable Project project) {
        super(project);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return null;
    }
}
