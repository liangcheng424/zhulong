package com.lmc.frame;

public interface ICommonView<D> {

    /**
     * 成功回调
     * @param whichApi     成功接口的标识 （哪个接口成功了）
     * @param loadType     类型的回调 （正常加载 ，刷新 ，加载更多）
     * @param pD       一般是实体类的对象，但为了框架的灵活性，确保其他一些数据的偶发性回调，故没将长度写死
     */
    void onSuccess(int whichApi,int loadType,D... pD);


    /**
     * 失败的回调
     * @param whichApi
     * @param pThrowable
     */
    void onFailed(int whichApi,Throwable pThrowable);
}
