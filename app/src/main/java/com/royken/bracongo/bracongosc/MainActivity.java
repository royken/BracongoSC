package com.royken.bracongo.bracongosc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.activity.ClientDetailFragment;
import com.royken.bracongo.bracongosc.activity.ClientFragment;
import com.royken.bracongo.bracongosc.activity.HistoAchatsAnneeFragment;
import com.royken.bracongo.bracongosc.activity.HistoAchatsMoisFragment;
import com.royken.bracongo.bracongosc.activity.LoadCircuitActivity;
import com.royken.bracongo.bracongosc.activity.MaterielFragment;
import com.royken.bracongo.bracongosc.activity.MessageFragment;
import com.royken.bracongo.bracongosc.activity.PlainteFragment;
import com.royken.bracongo.bracongosc.activity.RemiseFragment;
import com.royken.bracongo.bracongosc.activity.VenteFragment;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;

public class MainActivity extends AppCompatActivity implements ClientFragment.OnFragmentInteractionListener, VenteFragment.OnFragmentInteractionListener, PlainteFragment.OnFragmentInteractionListener,MessageFragment.OnFragmentInteractionListener,ClientDetailFragment.OnFragmentInteractionListener, RemiseFragment.OnFragmentInteractionListener, HistoAchatsMoisFragment.OnFragmentInteractionListener, HistoAchatsAnneeFragment.OnFragmentInteractionListener, MaterielFragment.OnFragmentInteractionListener {

    private static final String ARG_CLIENTID = "idClient";
    private DatabaseHelper databaseHelper = null;
    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";
    SharedPreferences settings ;
    Dao<Client, Integer> clientsDao;

    private int idClient;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
     /*   mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    */
        idClient = getIntent().getIntExtra(ARG_CLIENTID,0);
        Log.i("ID SENT", idClient+"");
        Fragment fragment = ClientDetailFragment.newInstance(idClient);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,fragment);
        //ft.addToBackStack(null);
        ft.commit();


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
            return true;
        }
        if (id == R.id.action_circuit) {
            settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("com.bracongo.data", false);
            editor.commit();
            Intent intent = new Intent(MainActivity.this,
                    LoadCircuitActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            MainActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
/*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
        */
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    ClientFragment tab1 = ClientFragment.newInstance(idClient);
                    return tab1;
                case 1:
                    VenteFragment tab2 = VenteFragment.newInstance(idClient);
                    return tab2;
                case 2:
                    PlainteFragment tab3 = PlainteFragment.newInstance(idClient);
                    return tab3;
                case 3:
                    MessageFragment tab4 = MessageFragment.newInstance(idClient);
                    return tab4;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Client";
                case 1:
                    return "Ventes";
                case 2:
                    return "Plaintes";
                case 3:
                    return "Messages";
            }
            return null;
        }
    }


}
