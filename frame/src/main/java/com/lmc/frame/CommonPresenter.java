package com.lmc.frame;

public class CommonPresenter implements ICommonPresenter {

    private ICommonView view;
    private ICommonModel model;

    public CommonPresenter(ICommonView view, ICommonModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getData(int whichApi, Object... pPS) {
        model.getData(this, whichApi, pPS);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object... pD) {
        view.onSuccess(whichApi, loadType, pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        view.onFailed(whichApi, pThrowable);
    }
}
