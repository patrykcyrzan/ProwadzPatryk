package pl.cyrzan.prowadzpatryk.eventbus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.functions.Action1;

/**
 * Created by Patryk on 18.02.2017.
 */

@Module
public class EventBusModule {

    @Provides
    @Singleton
    public RxBus provideRxBus() {
        return new RxBus();
    }
}

