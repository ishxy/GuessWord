package com.shxy.guessword;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GuessMode extends Activity {

	private EditText letterTest;
	private String letter;
	private Button upButton;
	private Button trueButton;
	private String trueWord, trueLetter, showWord;
	private TextView wordText;
	private String SecretKey, UserPhone;
	private InfoBean model;
	private int sum;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode1);
		sum = MainActivity.SUM;
		Toast.makeText(getApplicationContext(), sum+"", 0).show();
		
		initView();
		setListener();
		doGuess();
	}

	private void setListener() {
		upButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				letter = letterTest.getText().toString(); 
				if (trueLetter.equals(letter)) {
					Toast.makeText(getApplicationContext(), "成功", 0).show();
					doGuess();
					letterTest.setText("");
				}else{
					Toast.makeText(getApplicationContext(), "失败", 0).show();
				}
			}
		});

		trueButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				letterTest.setText(trueLetter);
			}
		});
	}
		
	private void doGuess(){
		db = openOrCreateDatabase("word.db", MODE_PRIVATE, null);
		 Cursor c = db.rawQuery("select * from wordtb where _id="
					+ getRandomNum() + ";", null);
		 	if (c!=null) {
		 		while (c.moveToNext()) {
					Log.i("info", "_id:"+c.getInt(c.getColumnIndex("_id")));
					Log.i("info", "name:"+c.getString(c.getColumnIndex("word")));
					Log.i("info", "age:"+c.getString(c.getColumnIndex("translation")));
					Log.i("info", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					trueWord = c.getString(c.getColumnIndex("word"));
				}
		 		c.close();
		 	}
		 	db.close();
		 	
		 	showWord = stringFactory(trueWord);
		 	wordText.setText(showWord);
	
	}
	
	private String stringFactory(String string){
		int num = (int) (Math.random() * string.length()) + 1;
		StringBuilder builder = new StringBuilder(string);
		builder.replace(num-1, num, "*");
		Log.i("nei", num+"");
		trueLetter = string.charAt(num-1)+"";
		Log.i("char",trueLetter);
		return builder.toString();
	}

	private void initView() {
		upButton = (Button) findViewById(R.id.mode1_up);
		trueButton = (Button) findViewById(R.id.mode1_true);
		letterTest = (EditText) findViewById(R.id.mode1_guess);
		wordText = (TextView) findViewById(R.id.mode1_word);
	}

	private int getRandomNum() {
		int num = (int) (Math.random() * sum) + 1;
		Log.i("wai", num+"");
		return num;
	}

}
