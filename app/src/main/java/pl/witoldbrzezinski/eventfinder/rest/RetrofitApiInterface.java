package pl.witoldbrzezinski.eventfinder.rest;

import pl.witoldbrzezinski.eventfinder.model.EventJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static pl.witoldbrzezinski.eventfinder.rest.ApiClient.URL_EVENTS_SEARCH;

/**
 * Created by Wiciu on 17.06.2017.
 */

public interface RetrofitApiInterface {


    @GET(URL_EVENTS_SEARCH)
    Call<EventJson> getEventsByDateAndCity(@Query(ApiClient.PARAM_APP_KEY) String apiKey,
                                           @Query(ApiClient.PARAM_DATE) String date,
                                           @Query(ApiClient.PARAM_LOCATION) String location);

}
