package com.unleashed.android.horoscoperssunleashed.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.unleashed.android.horoscoperssunleashed.R;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button btn = (Button)findViewById(R.id.showbrowser);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent res = new Intent();
//                String mPackage = "com.unleashed.android.horoscoperssunleashed.ui";
//                String mClass = ".IntegratedWebBrowser";
//                res.setComponent(new ComponentName(mPackage, mPackage + mClass));
//                startActivity(res);
//
//            }
//        });



        if (savedInstanceState == null) {
            addRssFragment();
        }


    }

    private void addRssFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RssFragment fragment = new RssFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
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



        switch (id){

            // Respond to the action bar's Up/Home button
            // Or menu icon in action bar.
            // https://developer.android.com/training/basics/actionbar/adding-buttons.html

            case R.id.action_exit_app:
                finish();
                System.exit(0);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
