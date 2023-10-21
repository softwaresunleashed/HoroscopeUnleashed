package com.unleashed.android.horoscoperssunleashed.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.unleashed.android.horoscoperssunleashed.R;
import com.unleashed.android.horoscoperssunleashed.helpers.Connectivity;
import com.unleashed.android.horoscoperssunleashed.rss.RssAdapter;
import com.unleashed.android.horoscoperssunleashed.rss.RssItem;
import com.unleashed.android.horoscoperssunleashed.services.RssService;

import java.util.List;

/**
 * Created by gupta on 8/6/2015.
 */
public class RssFragment  extends Fragment {

    private ProgressBar progressBar;
    private ListView listView;
    private View view;
    private Context mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = activity.getApplicationContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Reference : https://androidresearch.wordpress.com/2013/06/01/creating-a-simple-rss-application-in-android-v2/


        //method to automatically save the fragment’s state across screen configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(R.layout.fragment_layout, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            listView = (ListView) view.findViewById(R.id.listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    RssAdapter adapter = (RssAdapter)adapterView.getAdapter(); // parent.getAdapter();
                    RssItem item = (RssItem) adapter.getItem(position);


//                    // Display the Description in a dialog
//                    // When creating a Dialog inside a fragment, take the context of Fragment.
//                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                    builder.setIcon(R.drawable.ic_launcher);
//                    builder.setCancelable(true);
//
//                    //Set the zodiac sign in the title of dialog box
//                    builder.setTitle(item.getTitle());
//
//                    // Set the description of today's zodiac.
//                    builder.setMessage(item.getDescription());
//
//                    // Dismiss Dialog
//                    builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            // Dismiss the dialog box
//                            dialogInterface.dismiss();
//
//                        }
//                    });
//
//                    try{
//                        // Create and Show the Dialog box finally
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                    }catch (Exception ex){
//                        ex.printStackTrace();
//                    }


//                    // Open the Link in an External Browser.
//                    Uri uri = Uri.parse(item.getLink());
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);

                    if(Connectivity.getNetworkInfo(mActivity).isConnected() == true){
                        // Open the Link in Apps Browser.
                        Uri uri = Uri.parse(item.getLink());
                        Intent intent = new Intent(getActivity(), IntegratedWebBrowser.class);
                        intent.setData(uri);        // set the url along with the intent.
                        startActivity(intent);
                    }else{
                        Toast.makeText(mActivity, "No Network. Try again later.", Toast.LENGTH_LONG).show();
                        //Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }



                }
            });

            startService();

        } else {
            // If we are returning from a configuration change:
            // "view" is still attached to the previous view hierarchy
            // so we need to remove it and re-attach it to the current one
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    /**
     * Once the {@link RssService} finishes its task, the result is sent to this ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            if (items != null) {
                RssAdapter adapter = new RssAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        };
    };


//    @Override
//    public void onItemClick(AdapterViewCompat<?> adapterViewCompat, View view, int i, long l) {
//        RssAdapter adapter = (RssAdapter) parent.getAdapter();
//        RssItem item = (RssItem) adapter.getItem(position);
//        Uri uri = Uri.parse(item.getLink());
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
//    }
}
