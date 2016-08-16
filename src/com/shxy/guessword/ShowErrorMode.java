package com.shxy.guessword;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowErrorMode extends Activity {

	private ListView mListView;
	private List<InfoBean> data;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode3);
		mListView = (ListView) findViewById(R.id.mode3_listview);
		data = new ArrayList<InfoBean>();
		initData();
		mListView.setAdapter(new MyAdapter(data));
	}

	private void initData() {
		db = openOrCreateDatabase("error.db", MODE_PRIVATE, null);
		 Cursor c = db.rawQuery("select * from errortb", null);
		 	if (c!=null) {
		 		while (c.moveToNext()) {
					Log.i("info", "_id:"+c.getInt(c.getColumnIndex("_id")));
					Log.i("info", "name:"+c.getString(c.getColumnIndex("word")));
					Log.i("info", "age:"+c.getString(c.getColumnIndex("translation")));
					Log.i("info", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					InfoBean infoBean = new InfoBean();
					infoBean.setTrueWord(c.getString(c.getColumnIndex("word")));
					infoBean.setWordId(c.getInt(c.getColumnIndex("_id")));
					infoBean.setTranslation(c.getString(c.getColumnIndex("translation")));
					data.add(infoBean);
				}
		 		c.close();
		 	}
		 	db.close();
	}

	private class MyAdapter extends BaseAdapter {

		private List<InfoBean> data;

		public MyAdapter(List<InfoBean> data) {
			this.data = data;
		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int i) {
			return i;
		}

		public long getItemId(int i) {
			return i;
		}

		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.showerroeitem, null);
				viewHolder = new ViewHolder();
				viewHolder.wordText = (TextView) convertView
						.findViewById(R.id.mode3_word);
				viewHolder.translationText = (TextView) convertView
						.findViewById(R.id.mode3_translatrion);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.wordText.setText(data.get(i).getTrueWord());
			viewHolder.translationText.setText(data.get(i).getTranslation());
			return convertView;
		}

		public class ViewHolder {
			public TextView wordText;
			public TextView translationText;
		}
	}

}
