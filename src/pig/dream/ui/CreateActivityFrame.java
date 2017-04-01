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
 * Created by zhukun on 2017/3/16.
 */
public class CreateActivityFrame extends JFrame implements DocumentListener, ItemListener, ActionListener {

    private JTextField textPackageName;
    private JTextField textTitleName;

    private JPanel panelContract = null;
    private JPanel panelPresenter = null;

    private JCheckBox cbToolBar = null;
    private JCheckBox cbSwipeBack = null;
    private JCheckBox cbPresenter = null;
    private JCheckBox cbStatusBar = null;

    private JButton btnCreate;

    private ProjectModel projectModel;

    public CreateActivityFrame(String title, String packageName, VirtualFile selectedFile) {
        //实例化一个JDialog类对象，指定对话框的父窗体，窗体标题和类型
        super();

        projectModel = new ProjectModel();
        projectModel.title = title;
        projectModel.packageName = packageName;
        projectModel.filePath = new File(selectedFile.getPath());

        setTitle("创建Activity");
        setSize(320, 600);//设置容器的大小
        int windowWidth = this.getWidth(); //获得窗口宽
        int windowHeight = this.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        int screenWidth = kit.getScreenSize().width; //获取屏幕的宽
        int screenHeight = kit.getScreenSize().height; //获取屏幕的高
        setLocation(screenWidth/2 - windowWidth/2, 0);//设置窗口居中显示
        //设置窗体的关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        panel.setLayout(null);
        // 添加面板
        add(panel);
        // 调用用户定义的方法并添加组件到面板
//        placeComponents(panel);
        addCommonComponents(panel);
        addActivityComponents(panel);
        addContractComponents(panel);
        addPresenterComponents(panel);
        addCreateButton(panel);
        initData();
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

    private void placeComponents(JPanel panel) {
        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("Activity Name:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("Contract Name:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);


        // 创建 JLabel
        JLabel presenterLabel = new JLabel("Presenter Name:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        presenterLabel.setBounds(10,80,80,25);
        panel.add(presenterLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField presenterText = new JTextField(20);
        presenterText.setBounds(100,80,165,25);
        panel.add(presenterText);


        // 创建登录按钮;
        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 110, 80, 25);
        panel.add(loginButton);
    }

    private void addCommonComponents(JPanel panel) {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
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

    JTextField cClassNameTextField;

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

    JTextField pClassNameTextField;

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

    JTextField aClassNameTextField;

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

            try {
                ClassGenerator.create().projectModel(projectModel).generate();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
