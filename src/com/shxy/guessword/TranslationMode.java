package com.shxy.guessword;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TranslationMode extends Activity{
	
	private String SecretKey, UserPhone;
    private EditText wordEdit;
    private TextView translationText;
    private String word,trueWord,translation;
    private Button upButton,trueButton,errorButton;
    private SQLiteDatabase db;
    private SQLiteDatabase errordb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode2);
		initView();
		doTranslation();
	}
	  private void doTranslation() {
		 
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
						translation = c.getString(c.getColumnIndex("translation"));
					}
			 		c.close();
			 	}
			 	db.close();
			 	translationText.setText(translation);
			 	wordEdit.setText("");
			 	
		
	}
	private void initView() {
	        wordEdit = (EditText) findViewById(R.id.mode2_word);
	        translationText = (TextView) findViewById(R.id.mode2_translation);
	        upButton = (Button) findViewById(R.id.mode2_up);
	        trueButton = (Button) findViewById(R.id.mode2_true);
	        errorButton = (Button) findViewById(R.id.mode2_error);
	        
	        errorButton.setOnClickListener(new View.OnClickListener() {
	          
	            public void onClick(View view) {
	                upError();
	            }

				
	        });
	        upButton.setOnClickListener(new View.OnClickListener() {
	            
	            public void onClick(View view) {
	            	Log.i("ok","ok");
	                word = wordEdit.getText().toString();
	                
	                if (trueWord .equals(word) ){
	                    Toast.makeText(getApplicationContext(), "正确", Toast.LENGTH_SHORT).show();
	                    doTranslation();
	                    Log.i("ok","ok");
	                }else{
	                    Toast.makeText(getApplicationContext(), "错误", Toast.LENGTH_SHORT).show();
	                    Log.i("ok","ok");
	                }
	            }
	        });
	        
	        trueButton.setOnClickListener(new View.OnClickListener() {
	           
	            public void onClick(View view) {
	                wordEdit.setText(trueWord);
	            }
	        });
	    }
	  
	  private void upError() {
			// TODO Auto-generated method stub
			errordb = openOrCreateDatabase("error.db", MODE_PRIVATE,null);
			errordb.execSQL("insert into errortb(word,translation) values('"+trueWord+"','"+translation+"')");
			db.close();
			Toast.makeText(getApplicationContext(), "成功加入错题本!", 0).show();
		}
	  
	  private int getRandomNum() {
			int num = (int) (Math.random() * MainActivity.SUM) + 1;
			Log.i("wai", num+"");
			return num;
		}
	  

}
