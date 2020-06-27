package com.example.he.studenmanagement.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.he.studenmanagement.R;

import java.util.List;

/**
 * Created by he on 2020/06/23.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private int resourceId;

    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.student_name = (TextView) view.findViewById(R.id.student_name);
            viewHolder.student_id = (TextView) view.findViewById(R.id.student_id);
            viewHolder.student_image = (ImageView) view.findViewById(R.id.student_image);
            viewHolder.student_image.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.student_name.setText(item.getName());
        viewHolder.student_id.setText(item.getProgress());
        String sex = item.getComplete();
        if (sex.equals("是")) {
            viewHolder.student_image.setImageResource(R.drawable.ok);

        } else if (sex.equals("否")) {
            viewHolder.student_image.setImageResource(R.drawable.ready);
        }


        return view;

    }

    class ViewHolder {
        ImageView student_image;
        TextView student_name;
        TextView student_id;
    }

}
