package com.shxy.guessword;

/**
 * Created by 22873 on 2016/8/15.
 */
public class InfoBean {
    private int WordId;
    private String TrueWord;
    private String Translation;
   

    public String getTrueWord() {
        return TrueWord;
    }

    public void setTrueWord(String trueWord) {
        TrueWord = trueWord;
    }

    public int getWordId() {
        return WordId;
    }

    public void setWordId(int wordId) {
        WordId = wordId;
    }


    public String getTranslation() {
        return Translation;
    }

    public void setTranslation(String translation) {
        Translation = translation;
    }
}
