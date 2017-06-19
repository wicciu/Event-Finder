package pl.witoldbrzezinski.eventfinder;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wiciu on 17.06.2017.
 */


   public class FinderActivity extends AppCompatActivity {



   private static final String TAG = FinderActivity.class.getSimpleName();


   Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());

   @BindView(R.id.cityEditText)
   EditText cityEditText;
   @BindView(R.id.dateButton)
   Button dateButton;
   @BindView(R.id.searchButton)Button searchButton;
   @BindView(R.id.finder_toolbar)
   Toolbar finderToolbar;

   String place ="";
   String eventDate = "";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.finder_layout);
      ButterKnife.bind(this);
      bindView();
      setListeners();
   }

   private void bindView(){
      cityEditText.setText(place);
      setSupportActionBar(finderToolbar);
   }

   @Override
   public void onBackPressed() {
      super.onBackPressed();
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
   }


   public void setListeners(){
      dateButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(FinderActivity.this, date,myCalendar.get(Calendar.YEAR)
                    ,myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
         }
      });

      searchButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            try{
               Intent intent = new Intent(getBaseContext(),EventsActivity.class);
               intent.putExtra("place",cityEditText.getText().toString());
               intent.putExtra("date",updateLabelText());
               startActivity(intent);
            }
            catch (Exception e){
               Log.e(TAG,e.toString());
            }
         }
      });
   }

   private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear,
                            int dayOfMonth) {
         myCalendar.set(Calendar.YEAR, year);
         myCalendar.set(Calendar.MONTH, monthOfYear);
         myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
         updateLabelText();
      }


   };

   private String updateLabelText() {
      String myFormat = "yyyy-MM-dd";
      SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
      dateButton.setText(sdf.format(myCalendar.getTime()));
      eventDate = sdf.format(myCalendar.getTime());
      return eventDate;
   }


}
