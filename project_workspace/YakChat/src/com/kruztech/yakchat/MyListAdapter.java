package com.kruztech.yakchat;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {

 Context context;
 List<RowItem> rowItems;

 MyListAdapter(Context context, List<RowItem> rowItems) {
  this.context = context;
  this.rowItems = rowItems;
 }

 @Override
 public int getCount() {
  return rowItems.size();
 }

 @Override
 public Object getItem(int position) {
  return rowItems.get(position);
 }

 @Override
 public long getItemId(int position) {
  return rowItems.indexOf(getItem(position));
 }

 /* private view holder class */
 private class ViewHolder {
  TextView user_name;
  TextView message;
  TextView timeStamp;
 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {

  ViewHolder holder = null;

  LayoutInflater mInflater = (LayoutInflater) context
    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
  if (convertView == null) {
   convertView = mInflater.inflate(R.layout.list_item, null);
   holder = new ViewHolder();

   holder.user_name = (TextView) convertView
     .findViewById(R.id.user_name);
   holder.message = (TextView) convertView.findViewById(R.id.message);
   holder.timeStamp = (TextView) convertView
     .findViewById(R.id.timeStamp);

   RowItem row_pos = rowItems.get(position);

   holder.user_name.setText(row_pos.getUser_name());
   holder.message.setText(row_pos.getMessage());
   holder.timeStamp.setText(row_pos.getTimeStamp());

   convertView.setTag(holder);
  } else {
   holder = (ViewHolder) convertView.getTag();
  }

  return convertView;
 }

}