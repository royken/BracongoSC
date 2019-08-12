package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Message;
import com.royken.bracongo.bracongosc.entities.Plainte;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by vr.kenfack on 06/09/2017.
 */

public class MessageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Message> messages;

    public MessageAdapter(Context mContext, List<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.message_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv_titre = (TextView)layout.findViewById(R.id.titre);
        TextView tv_description = (TextView) layout.findViewById(R.id.description);
        TextView tv_date = (TextView) layout.findViewById(R.id.date);
        tv_titre.setText(messages.get(position).getTitre());
        tv_description.setText(messages.get(position).getContenu());
        tv_date.setText( getDateString(new Date(messages.get(position).getDate())));

        tv_titre.setTag(position);
        tv_description.setTag(position);
        tv_date.setTag(position);
        return layout;
    }

    private String getDateString(Date date){
        Calendar gc = new GregorianCalendar();
        gc.setTime(date);
        String result = gc.get(Calendar.DAY_OF_MONTH)+"-"+(gc.get(Calendar.MONTH)+1)+"-"+gc.get(Calendar.YEAR);
        return result;
    }

    @Override
    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
