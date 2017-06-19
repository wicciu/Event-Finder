package pl.witoldbrzezinski.eventfinder;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.witoldbrzezinski.eventfinder.adapters.EventsAdapter;
import pl.witoldbrzezinski.eventfinder.model.Event;
import pl.witoldbrzezinski.eventfinder.model.EventJson;
import pl.witoldbrzezinski.eventfinder.rest.ApiClient;
import pl.witoldbrzezinski.eventfinder.rest.RetrofitApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.witoldbrzezinski.eventfinder.rest.ApiClient.API_KEY;


/**
 * Created by Wiciu on 17.06.2017.
 */

public class EventsActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.events_recycler_view)
    RecyclerView eventsRecyclerView = null;
    @BindView(R.id.events_toolbar)
    Toolbar eventsToolbar;

    public static final int PLEASE_WAIT_DIALOG = 1;

    private LinearLayoutManager mLayoutManager =null;
    private RecyclerView.Adapter eventsAdapter =null;

    ProgressDialog progressDialog;

    private String date;
    private String place;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_list_layout);
        ButterKnife.bind(this);
        setSupportActionBar(eventsToolbar);
        setRecyclerViewData();
        getIntentData();
        progressDialog = onCreateDialog(PLEASE_WAIT_DIALOG);
        progressDialog.show();
        getRequest();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if (intent==null)
            return;
        Bundle bundle = intent.getExtras();
        if (bundle==null) return;
        date = bundle.getString("date");
        place = bundle.getString("place");
    }


    private void setRecyclerViewData(){
        mLayoutManager = new LinearLayoutManager(this);
        eventsRecyclerView.setLayoutManager(mLayoutManager);
        eventsAdapter = new EventsAdapter(Collections.EMPTY_LIST);
        eventsRecyclerView.setAdapter(eventsAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        eventsRecyclerView.addItemDecoration(itemDecoration);
    }

    private void getRequest(){
        RetrofitApiInterface apiService = ApiClient.getClient().create(RetrofitApiInterface.class);
        Call<EventJson> call = apiService.getEventsByDateAndCity(API_KEY,date,place);

        call.enqueue(new Callback<EventJson>() {
            @Override
            public void onResponse(Call<EventJson> call, Response<EventJson> response) {
                try {
                    List<Event> eventList = new ArrayList<Event>();
                    eventList = response.body().getEvents().getEvent();
                    eventsAdapter = new EventsAdapter(eventList);
                    eventsRecyclerView.setAdapter(eventsAdapter);
                }catch (Exception e){
                    createToastForNoEvents();
                    e.printStackTrace();
                }
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<EventJson> call, Throwable t) {
                createToastForInternetError();
                Log.e(TAG, t.toString());
                finish();
            }
        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,FinderActivity.class);
        startActivity(intent);
    }

    public void createToastForNoEvents(){
        Toast.makeText(getBaseContext(),R.string.no_events,Toast.LENGTH_LONG).show();
    }

    public void createToastForInternetError(){
        Toast.makeText(getBaseContext(),R.string.no_internet,Toast.LENGTH_LONG).show();
    }


    public ProgressDialog onCreateDialog(int dialogId) {
        switch (dialogId) {
            case PLEASE_WAIT_DIALOG:
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setTitle(R.string.connecting);
                dialog.setMessage(EventsActivity.this.getString(R.string.wait));
                dialog.setCancelable(true);
                return dialog;
            default:
                break;
        }
        return null;
    }

}
