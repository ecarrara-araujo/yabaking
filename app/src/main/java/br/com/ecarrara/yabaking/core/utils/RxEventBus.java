package br.com.ecarrara.yabaking.core.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxEventBus<T> {

    private PublishSubject<T> subject = PublishSubject.create();

    public void publish(T event) {
        subject.onNext(event);
    }

    public Observable<T> eventBus() {
        return subject;
    }

}
