package pig.dream.ui;

import com.intellij.openapi.vfs.VirtualFile;
import pig.dream.classgenerator.ClassGenerator;
import pig.dream.classgenerator.ProjectModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhukun on 2017/3/29.
 */
public class CreateMvpPanel extends JPanel implements DocumentListener, ItemListener, ActionListener {

    private JTextField textPackageName;
    private JTextField textTitleName;

    private JPanel panelContract = null;
    private JPanel panelPresenter = null;

    private JCheckBox cbToolBar = null;
    private JCheckBox cbSwipeBack = null;
    private JCheckBox cbPresenter = null;
    private JCheckBox cbStatusBar = null;
    private JTextField cClassNameTextField;

    JTextField pClassNameTextField;
    JTextField aClassNameTextField;

    private JButton btnCreate;

    private IConfirmListener confirmListener;
    private ProjectModel projectModel;

    public CreateMvpPanel(String title, String packageName, IConfirmListener confirmListener) {
        super();
        this.confirmListener = confirmListener;
        projectModel = new ProjectModel();
        projectModel.title = title;
        projectModel.packageName = packageName;

//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new FlowLayout(FlowLayout.LEFT));

        addCommonComponents(this);
        addActivityComponents(this);
        addContractComponents(this);
        addPresenterComponents(this);
        addCreateButton(this);
        initData();
    }

    public JButton getBtnCreate() {
        return btnCreate;
    }

    private void initData() {
        if (!TextUtils.isEmpty(projectModel.packageName)) {
            textPackageName.setText(projectModel.packageName);
        }
        if (!TextUtils.isEmpty(projectModel.title)) {
            textTitleName.setText(projectModel.title);
        }


        panelContract.setVisible(false);
        panelPresenter.setVisible(false);
    }



    private void addCommonComponents(JPanel panel) {
        JPanel panel02 = new JPanel(new GridBagLayout());
        Border titleBorder = BorderFactory.createTitledBorder("公共区");

        // 包名
        JLabel packageNameLabel = new JLabel("包名:  ", SwingConstants.LEFT);
        textPackageName = new JTextField(12);

        // 文件名
        JLabel fileNameLabel = new JLabel("标题:  ", SwingConstants.RIGHT);
        textTitleName = new JTextField(18);
        //关键是下面这两行代码
        Document document = textTitleName.getDocument();
        document.addDocumentListener(this);

        panel02.setBorder(titleBorder);
        panel02.add(packageNameLabel, GridBagConstraintsUtils.generate(0));
        panel02.add(textPackageName, GridBagConstraintsUtils.generate(1));
        panel02.add(fileNameLabel, GridBagConstraintsUtils.generate(2));
        panel02.add(textTitleName, GridBagConstraintsUtils.generate(3));

        panel.add(panel02);
    }


    private void addContractComponents(JPanel panel) {
        panelContract = new JPanel(new GridBagLayout());

        Border emptyPanl = BorderFactory.createEtchedBorder();
        Border titleBorder = BorderFactory.createTitledBorder(emptyPanl, "Contract区");

        // 类名
        JLabel classNameLabel = new JLabel("类名:  ");
        cClassNameTextField = new JTextField(18);

        // View接口
        JLabel viewInterfaceLabel = new JLabel("View接口:  ");
        JTextField viewInterfaceTextField = new JTextField(12);

        // View接口
        JLabel presenterInterfaceLabel = new JLabel("Presenter接口:  ");
        JTextField presenterInterfaceTextField = new JTextField(12);

        panelContract.setBorder(titleBorder);
        panelContract.add(classNameLabel, GridBagConstraintsUtils.generate(0));
        panelContract.add(cClassNameTextField, GridBagConstraintsUtils.generate(1));
//        panel02.add(viewInterfaceLabel, GridBagConstraintsUtils.generate(2));
//        panel02.add(viewInterfaceTextField, GridBagConstraintsUtils.generate(3));
//        panel02.add(presenterInterfaceLabel, GridBagConstraintsUtils.generate(4));
//        panel02.add(presenterInterfaceTextField, GridBagConstraintsUtils.generate(5));

        panel.add(panelContract);
    }

    private void addPresenterComponents(JPanel panel) {
        panelPresenter = new JPanel(new GridBagLayout());
        Border titleBorder = BorderFactory.createTitledBorder("Presenter区");

        // 类名
        JLabel classNameLabel = new JLabel("类名:  ");
        pClassNameTextField = new JTextField(18);

        // View接口
        JPanel viewInterfacePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel viewInterfaceLabel = new JLabel("View接口:  ");
        JTextField viewInterfaceTextField = new JTextField(12);
        viewInterfacePanel.add(viewInterfaceLabel);
        viewInterfacePanel.add(viewInterfaceTextField);

        // View接口
        JPanel presenterInterfacePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel presenterInterfaceLabel = new JLabel("Presenter接口:  ");
        JTextField presenterInterfaceTextField = new JTextField(12);
        presenterInterfacePanel.add(presenterInterfaceLabel);
        presenterInterfacePanel.add(presenterInterfaceTextField);


        panelPresenter.setBorder(titleBorder);
        panelPresenter.add(classNameLabel, GridBagConstraintsUtils.generate(0));
        panelPresenter.add(pClassNameTextField, GridBagConstraintsUtils.generate(1));

        panel.add(panelPresenter);
    }


    private void addActivityComponents(JPanel panel) {
        JPanel panel02 = new JPanel(new GridBagLayout());
        Border titleBorder = BorderFactory.createTitledBorder("Activity区");

        // 类名
        JLabel classNameLabel = new JLabel("类名:  ");
        aClassNameTextField = new JTextField(18);

        // View接口
        JLabel viewInterfaceLabel = new JLabel("功能:  ");
        viewInterfaceLabel.setLocation(0, 0);
        JTextField viewInterfaceTextField = new JTextField(12);

        cbToolBar = new JCheckBox("ToolBar");
        cbSwipeBack= new JCheckBox("SwipeBack");
        cbPresenter = new JCheckBox("Presenter");
        cbStatusBar = new JCheckBox("StatusBar");

        JPanel checkPanel = new JPanel(new GridLayout(0,1));
        checkPanel.add(cbPresenter);
        checkPanel.add(cbStatusBar);
        checkPanel.add(cbToolBar);
        checkPanel.add(cbSwipeBack);
        cbToolBar.addItemListener(this);
        cbSwipeBack.addItemListener(this);
        cbPresenter.addItemListener(this);
        cbStatusBar.addItemListener(this);

        panel02.setBorder(titleBorder);
        panel02.add(classNameLabel, GridBagConstraintsUtils.generate(0));
        panel02.add(aClassNameTextField, GridBagConstraintsUtils.generate(1));
        panel02.add(viewInterfaceLabel, GridBagConstraintsUtils.generate(2));
        panel02.add(checkPanel, GridBagConstraintsUtils.generate(3));

        panel.add(panel02);
    }

    private void addCreateButton(JPanel panel) {
        JPanel panel02 = new JPanel(new GridBagLayout());
        btnCreate = new JButton("创建");
        btnCreate.addActionListener(this);
        panel02.add(btnCreate, GridBagConstraintsUtils.generate(0));
        panel.add(panel02);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        Document doc = e.getDocument();
        String s = null; //返回文本框输入的内容
        try {
            s = doc.getText(0, doc.getLength());
            modifyClassName(s);
            System.out.println("insert text " + s);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        Document doc = e.getDocument();
        String s = null; //返回文本框输入的内容
        try {
            s = doc.getText(0, doc.getLength());
            modifyClassName(s);
            System.out.println("remove text " + s);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        Document doc = e.getDocument();
        String s = null; //返回文本框输入的内容
        try {
            s = doc.getText(0, doc.getLength());
            modifyClassName(s);
            System.out.println("change text " + s);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    private void modifyClassName(String header) {
        cClassNameTextField.setText(header + "Contract");
        pClassNameTextField.setText(header + "Presenter");
        aClassNameTextField.setText(header + "Activity");
    }

    // JCheckBox listener
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object cb = e.getItem();
        if (cb == cbPresenter) {
            if (cbPresenter.isSelected()) {
                projectModel.addFunctionFlag(ProjectModel.FLAG_PRESENTER_ENABLE);
                panelContract.setVisible(true);
                panelPresenter.setVisible(true);
            } else {
                projectModel.cleanFunctionFlag(ProjectModel.FLAG_PRESENTER_ENABLE);
                panelContract.setVisible(false);
                panelPresenter.setVisible(false);
            }
        } else if (cb == cbToolBar) {
            if (cbToolBar.isSelected()) {
                projectModel.addFunctionFlag(ProjectModel.FLAG_TOOLBAR_ENABLE);
            } else {
                projectModel.cleanFunctionFlag(ProjectModel.FLAG_TOOLBAR_ENABLE);

            }
        } else if (cb == cbStatusBar) {
            if (cbStatusBar.isSelected()) {
                projectModel.addFunctionFlag(ProjectModel.FLAG_STATUSBAR_ENABLE);
            } else {
                projectModel.cleanFunctionFlag(ProjectModel.FLAG_STATUSBAR_ENABLE);

            }
        } else if (cb == cbSwipeBack) {
            if (cbSwipeBack.isSelected()) {
                projectModel.addFunctionFlag(ProjectModel.FLAG_SWIPEBACK_ENABLE);
            } else {
                projectModel.cleanFunctionFlag(ProjectModel.FLAG_SWIPEBACK_ENABLE);
            }
        }

    }

    // JButton click
    @Override
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target == btnCreate) {
            projectModel.activityClassName = aClassNameTextField.getText();
            projectModel.presenterClassName = pClassNameTextField.getText();
            projectModel.contractClassName = cClassNameTextField.getText();
            projectModel.println();

            if (confirmListener != null) {
                confirmListener.onConfirm(projectModel);
            }

//            try {
//                ClassGenerator.create().projectModel(projectModel).generate();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
        }
    }
}
