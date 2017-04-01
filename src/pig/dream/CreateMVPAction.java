package pig.dream;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiNameHelper;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import org.jetbrains.jps.model.java.JavaSourceRootType;
import pig.dream.classgenerator.ClassGenerateRunable;
import pig.dream.classgenerator.ProjectModel;
import pig.dream.ui.CreateActivityFrame;
import pig.dream.ui.CreateMvpPanel;
import pig.dream.ui.IConfirmListener;
import pig.dream.utils.FolderSelectHelper;
import pig.dream.utils.GenerateHelper;
import pig.dream.utils.Log;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhukun on 2017/3/16.
 */
public class CreateMVPAction extends AnAction implements IConfirmListener {

    private JFrame createMvpDialog;
    private Project project;
    private String srcPath;
    private String filePath;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        DataContext context = e.getDataContext();
        project = CommonDataKeys.PROJECT.getData(context);
        if (project == null) {
            return;
        }
        IdeView view = LangDataKeys.IDE_VIEW.getData(context);
        if (view == null) {
            return;
        }
        final PsiDirectory directory = view.getOrChooseDirectory();
        if (directory == null) {
            return;
        }
        Log.d("Project getBasePath: " + project.getBasePath());
        ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        PsiDirectory[] psiDirectories = view.getDirectories();
        String packageName = null;
        String currentDirName = null;
        if (psiDirectories.length > 0) {
            PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(psiDirectories[0]);
            packageName = psiPackage.getQualifiedName();
            currentDirName = psiPackage.getName();
            Log.d("PackageName " + packageName + " Dir name; " + currentDirName);
        }

        if (packageName == null || currentDirName == null) {
            return;
        }

        String selectPaht = directory.getVirtualFile().getPath();
        if (selectPaht == null || !selectPaht.contains("src")) {
            return;
        }
        srcPath = selectPaht;
        PsiDirectory parentDirectory = directory;
        while (!srcPath.endsWith("src")) {
            parentDirectory = parentDirectory.getParentDirectory();
            if (parentDirectory == null) {
                srcPath = null;
                break;
            }
            srcPath = parentDirectory.getVirtualFile().getPath();
        }
        Log.d("selectPath " + srcPath + " srcPath ");
        VirtualFile selectedFile = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        filePath = selectedFile.getPath();
//        CreateActivityDialog createActivityDialog = new CreateActivityDialog(mProject, directory);
//        createActivityDialog.show();
//        CreateActivityFrame createActivityFrame = new showMvpDialog(currentDirName, packageName, selectedFile);
//        createActivityFrame.setVisible(true);
        showMvpDialog(currentDirName, packageName, selectedFile);
//        if (FolderSelectHelper.isCorrectFolderSelected(project, selectedFile)) {
//            showInfoDialog("Selectors were generated into 'drawable' folder", e);
//            GenerateHelper.generate(selectedFile);
//            String projectName = project.getName();
//            StringBuilder sb = new StringBuilder();
//            VirtualFile[] files = ProjectRootManager.getInstance(project).getContentSourceRoots();
//            for (VirtualFile vf : files) {
//                sb.append(vf.getUrl()).append("\n");
//            }
////            Messages.showInfoMessage("sb:" + sb.toString(), "Project");
//        } else {
//            if (selectedFile != null) {
//                showErrorDialog("You need to select folder with image resources, for example 'drawables-xhdpi' " +
//                        "" + selectedFile.getName(), e);
//            }
//        }

    }

    @Override
    public void update(AnActionEvent event) {
        //根据扩展名是否是jar，显示隐藏此Action
        boolean enabled = isAvailable(event.getDataContext());
        Presentation presentation = event.getPresentation();
        presentation.setVisible(enabled);
        presentation.setEnabled(enabled);
    }

    private boolean isAvailable(@NotNull DataContext dataContext) {
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        IdeView view = LangDataKeys.IDE_VIEW.getData(dataContext);
        if (project == null || view == null || (view.getDirectories().length == 0)) {
            return false;
        }
        ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        for (PsiDirectory dir : view.getDirectories()) {
            if (projectFileIndex.isUnderSourceRootOfType(dir.getVirtualFile(), JavaModuleSourceRootTypes.SOURCES)
                    && (checkPackageExists(dir))) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkPackageExists(PsiDirectory directory) {
        PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(directory);
        if (psiPackage == null) {
            return false;
        }
        String name = psiPackage.getQualifiedName();
        Log.d("checkPackageExists getQualifiedName " + name);
        return (StringUtil.isEmpty(name) || (PsiNameHelper.getInstance(directory.getProject()).isQualifiedName(name)));
    }

    private void showInfoDialog(String text, AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()));

        if (statusBar != null) {
            JBPopupFactory.getInstance()
                    .createHtmlTextBalloonBuilder(text, MessageType.INFO, null)
                    .setFadeoutTime(10000)
                    .createBalloon()
                    .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                            Balloon.Position.atRight);
        }
    }

    private void showErrorDialog(String text, AnActionEvent e) {
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(e.getDataContext()
        ));

        if (statusBar != null) {
            JBPopupFactory.getInstance()
                    .createHtmlTextBalloonBuilder(text, MessageType.ERROR, null)
                    .setFadeoutTime(10000)
                    .createBalloon()
                    .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                            Balloon.Position.atRight);
        }
    }

    private void showMvpDialog(String title, String packageName, VirtualFile selectedFile) {
        CreateMvpPanel panel = new CreateMvpPanel(title, packageName, this);
        createMvpDialog = new JFrame();
        createMvpDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createMvpDialog.getRootPane().setDefaultButton(panel.getBtnCreate());
        createMvpDialog.setSize(new Dimension(320, 600));
        createMvpDialog.setContentPane(panel);
//        createMvpDialog.pack();
        createMvpDialog.setLocationRelativeTo(null);
        createMvpDialog.setVisible(true);
    }

    protected void closeDialog() {
        if (createMvpDialog == null) {
            return;
        }
        createMvpDialog.setVisible(false);
        createMvpDialog.dispose();
    }

    @Override
    public void onConfirm(ProjectModel projectModel) {
        closeDialog();
        projectModel.filePath = new File(filePath);
        new ClassGenerateRunable(project, projectModel).execute();
    }
}
