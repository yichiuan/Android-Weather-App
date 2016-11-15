package com.yichiuan.weatherapp.presentation.base;

import rx.subscriptions.CompositeSubscription;

public class BasePresenter implements MvpPresenter {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }
}
