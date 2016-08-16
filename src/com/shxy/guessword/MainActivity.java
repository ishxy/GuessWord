package com.shxy.guessword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends Activity {

	private Button mode1;
	private Button mode2;
	private Button mode3;
	private SQLiteDatabase db;
	private SQLiteDatabase wordDatabase;
	public static int SUM = 0;
	private InfoListBean list;
	private List<InfoBean> data;
	private String url = "http://183.175.14.250:5001/chaolu";
	private Back back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		back = new Back() {

			public void ok() {
				mode1.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(MainActivity.this,
								GuessMode.class));
					}
				});

				mode2.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(MainActivity.this,
								TranslationMode.class));
					}
				});

				mode3.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity(new Intent(MainActivity.this,
								ShowErrorMode.class));
					}
				});
			}
		};
		if (isFtrst()) {
			initDb();
			SharedPreferences preferences = getSharedPreferences("guessword",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("first", "ok");
			editor.commit();
		}
		SUM = 3;
		initView();
	}

	private boolean isFtrst() {
		// TODO Auto-generated method stub

		SharedPreferences sh = getSharedPreferences("guessword", MODE_PRIVATE);
		String first = sh.getString("first", "error");
		if (first.equals("error")) {
			return true;
		} else
			return false;
	}

	private void initDb() {
		db = openOrCreateDatabase("word.db", MODE_PRIVATE, null);
		db.execSQL("create table if not exists wordtb (_id integer primary key autoincrement, word text not null , translation text not null )");
		new MyTask().execute(url);

		// db.execSQL("insert into wordtb(word,translation) values('food','食物')");
		// db.execSQL("insert into wordtb(word,translation) values('help','帮助')");
		wordDatabase = openOrCreateDatabase("error.db", MODE_PRIVATE, null);
		wordDatabase
				.execSQL("create table if not exists errortb (_id integer primary key autoincrement, word text not null , translation text not null )");

	}

	private void initView() {
		mode1 = (Button) findViewById(R.id.main_mode1);
		mode2 = (Button) findViewById(R.id.main_mode2);
		mode3 = (Button) findViewById(R.id.main_mode3);
		if (isFtrst()==false) {
			mode1.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(MainActivity.this, GuessMode.class));
				}
			});

			mode2.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(MainActivity.this,
							TranslationMode.class));
				}
			});

			mode3.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent(MainActivity.this, ShowErrorMode.class));
				}
			});
		}
		
	}

	public String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result;
	}

	class MyTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return sendPost(arg0[0], "");
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			list = new Gson().fromJson(result, InfoListBean.class);
			data = list.getData();
			for (int i = 0; i < data.size(); i++) {
				db.execSQL("insert into wordtb(word,translation) values('"
						+ data.get(i).getTrueWord() + "','"
						+ data.get(i).getTranslation() + "')");
			}
			Toast.makeText(getApplicationContext(), "database is ok!", 0)
					.show();
			 back.ok();
		}

	}

	public interface Back {
		void ok();
	}

}
