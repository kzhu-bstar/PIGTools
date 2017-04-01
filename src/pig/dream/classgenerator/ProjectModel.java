package pig.dream.classgenerator;

import java.io.File;

/**
 * Created by zhukun on 2017/3/26.
 */
public class ProjectModel {

    public static final int FLAG_ALL_DISABLE = 0x00000000;
    public static final int FLAG_TOOLBAR_ENABLE = 0x00000001;
    public static final int FLAG_SWIPEBACK_ENABLE = 0x00000002;
    public static final int FLAG_PRESENTER_ENABLE = 0x00000004;
    public static final int FLAG_STATUSBAR_ENABLE = 0x00000008;

    private String version;
    public File filePath;
    public String packageName;
    public String title;
    public String activityClassName;
    public String presenterClassName;
    public String contractClassName;

    private int functionFlag = 0;


    public boolean enableFunctionFlag(int flag) {
        return (this.functionFlag & flag) == flag;
    }

    public void addFunctionFlag(int flag) {
        if (this.functionFlag == FLAG_ALL_DISABLE) {
            this.functionFlag = flag;
        } else {
            this.functionFlag |= flag;
        }
    }

    public void cleanFunctionFlag(int flag) {
        if (this.functionFlag != FLAG_ALL_DISABLE) {
            this.functionFlag &= ~flag;;
        }
    }

    public void println() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("functionFlag flag ");
        stringBuilder.append(this.functionFlag);
        stringBuilder.append("\nFLAG_PRESENTER_ENABLE ");
        stringBuilder.append(enableFunctionFlag(FLAG_PRESENTER_ENABLE));
        stringBuilder.append("\nFLAG_STATUSBAR_ENABLE ");
        stringBuilder.append(enableFunctionFlag(FLAG_STATUSBAR_ENABLE));
        stringBuilder.append("\nFLAG_TOOLBAR_ENABLE ");
        stringBuilder.append(enableFunctionFlag(FLAG_TOOLBAR_ENABLE));
        stringBuilder.append("\nFLAG_SWIPEBACK_ENABLE ");
        stringBuilder.append(enableFunctionFlag(FLAG_SWIPEBACK_ENABLE));

        System.out.println(stringBuilder.toString());
    }

}
