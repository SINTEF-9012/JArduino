package org.sintef.jarduino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.fortysevendeg.android.swipelistview.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jonathan
 * Date: 29/08/13
 * Time: 12:11
 */

/*
 *  This class is responsible for the rendering of the LogItems into the log list.
 */

public class LogAdapter extends ArrayAdapter<LogItem> {
    public LogAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public LogAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public LogAdapter(Context context, int textViewResourceId, LogItem[] objects) {
        super(context, textViewResourceId, objects);
    }

    public LogAdapter(Context context, int resource, int textViewResourceId, LogItem[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public LogAdapter(Context context, int textViewResourceId, List<LogItem> objects) {
        super(context, textViewResourceId, objects);
    }

    public LogAdapter(Context context, int resource, int textViewResourceId, List<LogItem> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public void add (String str, LogObject object){
        add(new LogItem(str, object));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LogItem item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.logitem, parent, false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.example_row_tv_title);
            holder.delete = (Button) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView)parent).recycle(convertView, position);

        holder.tvTitle.setText(item.toString());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogAdapter.this.remove(item);
                LogAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvTitle;
        Button delete;
    }
}
