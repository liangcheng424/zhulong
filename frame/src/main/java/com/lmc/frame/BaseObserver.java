package com.lmc.frame;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver implements Observer {
    private Disposable mDisposable;
    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(Object value) {
        onSuccess(value);
        dispose();
    }

    protected abstract void onSuccess(Object value);

    @Override
    public void onError(Throwable e) {
        onFiled(e);
        dispose();
    }

    protected abstract void onFiled(Throwable e);

    @Override
    public void onComplete() {
        dispose();
    }

    public void dispose(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }
}
