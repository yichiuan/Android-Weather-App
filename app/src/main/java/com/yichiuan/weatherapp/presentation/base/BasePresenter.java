package com.yichiuan.weatherapp.presentation.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter implements MvpPresenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        compositeDisposable.clear();
    }

    protected void addDisposable(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }
}
