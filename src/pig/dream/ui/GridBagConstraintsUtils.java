package pig.dream.ui;

import java.awt.*;

/**
 * Created by zhukun on 2017/3/22.
 */
public class GridBagConstraintsUtils {

    public static GridBagConstraints generate(int index) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = index % 2;
        gridBagConstraints.gridy = index / 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        return gridBagConstraints;
    }
}
