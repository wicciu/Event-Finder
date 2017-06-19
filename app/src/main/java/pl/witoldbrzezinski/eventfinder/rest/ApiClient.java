package pl.witoldbrzezinski.eventfinder.rest;

/**
 * Created by Wiciu on 17.06.2017.
 */

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.witoldbrzezinski.eventfinder.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://api.eventful.com";
    public static final String PATH_JSON = "json";
    public static final String PATH_EVENTS = "events";
    public static final String PATH_SEARCH = "search";

    public static final String API_KEY = "LZJjsbdcHnVhG7Z3";
    public static final String PARAM_APP_KEY = "app_key";

    public static final String URL_EVENTS_SEARCH = "/" + PATH_JSON + "/" + PATH_EVENTS + "/" + PATH_SEARCH;

    public static final String PARAM_LOCATION = "location";
    public static final String PARAM_DATE = "date";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideApiClient() )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient provideApiClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY :
                HttpLoggingInterceptor.Level.NONE);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        return okHttpClient;
    }
/*
    public List<Event> parseEventsFromJson(String eventfulResponse) throws JSONException {

        JSONObject jsonResponse = new JSONObject(eventfulResponse);
        JSONObject eventsMain = jsonResponse.getJSONObject(JSON_KEY_EVENTS);
        JSONObject eventsPackage = eventsMain.getJSONObject(JSON_KEY_PACKAGE_EVENTS);
        JSONArray events = eventsPackage.getJSONArray(JSON_KEY_EVENTS);

        List<Event> eventsList = new ArrayList<>();

        for (int i = 0; i < events.length(); i++) {
            JSONObject currentEvent = events.getJSONObject(i);

            String date = currentEvent.getString(JSON_KEY_DATE_EVENT);
            String description = currentEvent.getString(JSON_KEY_DESCRIPTION_EVENT);
            String title = currentEvent.getString(JSON_KEY_TITLE_EVENT);

            Event eventFound = new Event();
            eventsList.add(eventFound);
        }

        return eventsList;
    }
*/
}
