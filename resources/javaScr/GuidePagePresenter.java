package com.xunyu.xunyuev.ui.guide;

/**
 * 用户导航条
 *
 * Created by zhukun on 2017/3/14.
 */

public class GuidePagePresenter extends GuidePageContract.Presenter {

    @Override
    void activateTask() {
        this.mvpView.showTitle("Hello World");
    }
}
