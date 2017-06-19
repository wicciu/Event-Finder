package pl.witoldbrzezinski.eventfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinderActivity();

    }

    public void setFinderActivity(){
        Intent finderIntent = new Intent(this,FinderActivity.class);
        startActivity(finderIntent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setFinderActivity();
    }


}
