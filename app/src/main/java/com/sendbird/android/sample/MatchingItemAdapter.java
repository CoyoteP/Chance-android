package com.sendbird.android.sample;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Coyote on 2018/01/23.
 */

public class MatchingItemAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<MatchingItem> tweetList;


    public MatchingItemAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setTweetList(ArrayList<MatchingItem> tweetList) {
        this.tweetList = tweetList;
    }

    @Override
    public int getCount() {
        return tweetList.size();
    }

    @Override
    public Object getItem(int position) {
        return tweetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tweetList.get(position).getId();
    }

    public ArrayList<MatchingItem> getTweetList(){
        return tweetList;
    }
    static class ViewHolder {
        TextView name;
        Button chanButton;
        TextView tags;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_matching, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.chanButton = (Button) convertView.findViewById(R.id.chatButton);
            holder.tags = (TextView) convertView.findViewById(R.id.tags);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(tweetList.get(position).getUsername());
        //holder.tags.setSingleLine();
        //holder.tags.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.tags.setText(tweetList.get(position).getTags().toString());
        holder.chanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.chatButton);
            }
        });
        return convertView;
    }

}
