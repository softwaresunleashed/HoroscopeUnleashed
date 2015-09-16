package com.unleashed.android.horoscoperssunleashed.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.unleashed.android.horoscoperssunleashed.constants.Constants;
import com.unleashed.android.horoscoperssunleashed.parser.HoroscopeDOTComRssParser;
import com.unleashed.android.horoscoperssunleashed.rss.RssItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;


/**
 * Created by gupta on 8/6/2015.
 */
public class RssService extends IntentService {
    private static final String RSS_LINK = "http://my.horoscope.com/astrology/daily-horoscopes-rss.html";//"http://www.pcworld.com/index.rss";
    public static final String ITEMS = "title";//"items";
    public static final String RECEIVER = "link";//"receiver";
    public static final String DESCRIPTION = "description";


    public RssService() {
        super("RssService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(Constants.APP_NAME_TAG, "Service started");
        List<RssItem> rssItems = null;
        try {
            HoroscopeDOTComRssParser parser = new HoroscopeDOTComRssParser();
            rssItems = parser.parse(getInputStream(RSS_LINK));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(Constants.APP_NAME_TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}
