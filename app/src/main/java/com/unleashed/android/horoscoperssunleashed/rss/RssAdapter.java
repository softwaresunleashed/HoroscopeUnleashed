package com.unleashed.android.horoscoperssunleashed.rss;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unleashed.android.horoscoperssunleashed.R;

import java.util.List;

/**
 * Created by gupta on 8/6/2015.
 */
public class RssAdapter extends BaseAdapter {
    private final List<RssItem> items;
    private final Context context;

    public RssAdapter(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.rss_items, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            holder.imageZodiac = (ImageView) convertView.findViewById(R.id.imageView);
            holder.itemDescription = (TextView)convertView.findViewById(R.id.itemDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String title = items.get(position).getTitle();
        holder.itemTitle.setText(title);

        int imgSrcId = getImageSourceId(title);
        if(imgSrcId != 0){
            holder.imageZodiac.setImageResource(imgSrcId);
        }

        String description = items.get(position).getDescription();
        holder.itemDescription.setText(description);

        return convertView;
    }

    private int getImageSourceId(String title) {
        String sub_str = title.substring(0,3).toLowerCase();  // get the first three char of the title to get zodiac signs.
        int id = 0;

        switch (sub_str){
            case "aqu":
                id = R.mipmap.aquarius;
                break;
            case "ari":
                id = R.mipmap.aries;
                break;
            case "can":
                id = R.mipmap.cancer;
                break;
            case "cap":
                id = R.mipmap.capricorn;
                break;
            case "gem":
                id = R.mipmap.gemini;
                break;
            case "leo":
                id = R.mipmap.leo;
                break;
            case "lib":
                id = R.mipmap.libra;
                break;
            case "pis":
                id = R.mipmap.pisces;
                break;
            case "sag":
                id = R.mipmap.sagittarius;
                break;
            case "sco":
                id = R.mipmap.scorpio;
                break;
            case "tau":
                id = R.mipmap.taurus;
                break;
            case "vir":
                id = R.mipmap.virgo;
                break;
        }

        return id;


    }

    static class ViewHolder {
        TextView itemTitle;
        ImageView imageZodiac;
        TextView itemDescription;
    }

}
