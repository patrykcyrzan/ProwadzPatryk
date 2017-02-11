package pl.cyrzan.prowadzpatryk.api;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Patryk on 08.02.2017.
 */

@Singleton
public class ApiManager {

    private final ApiService apiService;

    @Inject
    ApiManager(ApiService apiService) {
        this.apiService = apiService;
    }


}
