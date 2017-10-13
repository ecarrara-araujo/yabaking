package br.com.ecarrara.yabaking.utils.rule;

import android.support.test.espresso.Espresso;

import com.squareup.rx2.idler.IdlingResourceScheduler;
import com.squareup.rx2.idler.Rx2Idler;

import org.junit.rules.ExternalResource;

import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class Rx2IdlingSchedulerRule extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        IdlingResourceScheduler ioIdlingScheduler =
                Rx2Idler.wrap(Schedulers.io(), "RxJava2 Io Idling Scheduler");
        IdlingResourceScheduler computationIdlingScheduler =
                Rx2Idler.wrap(Schedulers.io(), "RxJava2 Computation Idling Scheduler");
        IdlingResourceScheduler newThreadIdlingScheduler =
                Rx2Idler.wrap(Schedulers.io(), "RxJava2 New Thread Idling Scheduler");

        Espresso.registerIdlingResources(
                ioIdlingScheduler, computationIdlingScheduler, newThreadIdlingScheduler
        );

        RxJavaPlugins.setIoSchedulerHandler(scheduler1 -> ioIdlingScheduler);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler1 -> computationIdlingScheduler);
        RxJavaPlugins.setComputationSchedulerHandler(scheduler1 -> computationIdlingScheduler);
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler1 -> newThreadIdlingScheduler);
    }

    @Override
    protected void after() {
        RxJavaPlugins.reset();
    }
}
