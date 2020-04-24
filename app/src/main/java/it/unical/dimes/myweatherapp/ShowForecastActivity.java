package it.unical.dimes.myweatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.unical.dimes.myweatherapp.model.ForecastObject;
import it.unical.dimes.myweatherapp.model.SimpleForecastObject;

public class ShowForecastActivity extends AppCompatActivity {

    private static final String TAG = "ShowForecastActivity";
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.measurement_units_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        ViewPagerAdapter myAdapter = (ViewPagerAdapter) mViewPager.getAdapter();
        SingleDayFragment singleDayFragment = (SingleDayFragment) myAdapter.getItem(0);
        FiveDayFragment fiveDayFragment = (FiveDayFragment) myAdapter.getItem(1);

        if (item.getItemId() == R.id.action_show_in_map) {
            String uri = String.format(Locale.ITALIAN,
                    "geo:%f,%f",
                    singleDayFragment.getmSingleDayForecast().getLatitude(),
                    singleDayFragment.getmSingleDayForecast().getLongitude());

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        } else {

                if (item.getItemId() == R.id.action_switch_to_imperial) {
                    singleDayFragment.getmMaxTempTextView().setText(String.valueOf(Math.round(singleDayFragment.getmSingleDayForecast().getMaxTempAsFahrenheit())) + "°");
                    singleDayFragment.getmMinTempTextView().setText(String.valueOf(Math.round(singleDayFragment.getmSingleDayForecast().getMinTempAsFahrenheit())) + "°");
                    singleDayFragment.getmWindUnitsTextView().setText("mph");
                    singleDayFragment.getmWindValueTextView().setText(new DecimalFormat("#.#").format(singleDayFragment.getmSingleDayForecast().getWindSpeedAsMph()));

                    // qui modifico anche roba per fiveDayFragment
                    fiveDayFragment.getmExpandableListViewAdapter().convertToImperial();
                    fiveDayFragment.getmExpandableListViewAdapter().notifyDataSetChanged();

                } else if (item.getItemId() == R.id.action_switch_to_metric) {
                    singleDayFragment.getmMaxTempTextView().setText(String.valueOf(Math.round(singleDayFragment.getmSingleDayForecast().getMaxTempAsCelsius())) + "°");
                    singleDayFragment.getmMinTempTextView().setText(String.valueOf(Math.round(singleDayFragment.getmSingleDayForecast().getMinTempAsCelsius())) + "°");
                    singleDayFragment.getmWindUnitsTextView().setText("km/h");
                    singleDayFragment.getmWindValueTextView().setText(singleDayFragment.getmSingleDayForecast().getWindSpeedAsKmh().toString());

                    // qui modifico anche roba per fiveDayFragment
                    fiveDayFragment.getmExpandableListViewAdapter().convertToMetric();
                    fiveDayFragment.getmExpandableListViewAdapter().notifyDataSetChanged();
                }

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_forecast);
        //Toast.makeText(this, getIntent().getStringExtra(Intent.EXTRA_RETURN_RESULT), Toast.LENGTH_LONG).show();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String[] forecasts = getIntent().getStringArrayExtra(Intent.EXTRA_RETURN_RESULT);
        try {

            ForecastObject mainForecastObject = new ForecastObject(new JSONObject(forecasts[0]));
            JSONArray fiveDayArray = new JSONObject(forecasts[1]).getJSONArray("daily");
            mViewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(mViewPager, mainForecastObject, new JSONObject(forecasts[1]).getJSONArray("daily"));
            //

            mTabLayout = (TabLayout) findViewById(R.id.tabs);
            mTabLayout.setupWithViewPager(mViewPager);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setupViewPager(ViewPager viewPager, ForecastObject singleDayForecast, JSONArray fiveDayForecast) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SingleDayFragment(singleDayForecast), "Oggi");
        adapter.addFragment(new FiveDayFragment(fiveDayForecast), "settimanale");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
