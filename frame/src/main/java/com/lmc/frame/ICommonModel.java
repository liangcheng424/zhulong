package com.lmc.frame;

public interface ICommonModel<T> {

    void getData(ICommonPresenter presenter,int whichApi,T... params);

}
