package com.xunyu.xunyuev.ui.guide;

import pig.dream.baselib.mvp.BasePresenter;
import pig.dream.baselib.mvp.MvpView;

/**
 *
 * Created by zhukun on 2017/3/14.
 */

public interface GuidePageContract {
    interface View extends MvpView {
        void showTitle(String title);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void activateTask();
    }
}
