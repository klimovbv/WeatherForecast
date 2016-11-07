package com.example.boss.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.boss.sunshine.sync.SunshineSyncAdapter;


public class MainActivity extends FragmentActivity {

    private final String FORECASTFRAGMENT_TAG = "FFTAG";
    private String mLocation;
    private boolean mUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLocation = Utility.getPreferredLocation(this);
        mUnits = Utility.isMetric(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment(), FORECASTFRAGMENT_TAG)
                    .commit();
        }

        SunshineSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String location = Utility.getPreferredLocation(this);
        Boolean units = Utility.isMetric(this);
        ForecastFragment ff = (ForecastFragment)getSupportFragmentManager()
                .findFragmentByTag(FORECASTFRAGMENT_TAG);

        if (mUnits != units){
            ff.onUnitsChange();
            mUnits = units;
        }

        if (location != null && !location.equals(mLocation)) {
            if ( null != ff ) {
                ff.onLocationChanged();
            }
            mLocation = location;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_map) {
            openPreferredLocationMap();
        }



        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationMap(){
        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPreferences.getString(getString(R.string.pref_location_key),
                getString(R.string.location_default));*/
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", mLocation)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Log.d("myLogs", "Couldn't call " + mLocation);
        }

    }

    public void newVoidForPull2() {
        int a = 10;
        b++;
    }

    public void newVoidForPull3() {
        int a = 10;
    }


}
