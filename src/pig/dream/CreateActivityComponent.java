package pig.dream;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * Created by zhukun on 2017/3/16.
 */
public class CreateActivityComponent implements ApplicationComponent {
    public CreateActivityComponent() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "CreateActivityComponent";
    }

    public void openCreateActivityDilaog() {
        Messages.showMessageDialog(

                "Hello World!",

                "Sample",

                Messages.getInformationIcon()

        );
    }
}
