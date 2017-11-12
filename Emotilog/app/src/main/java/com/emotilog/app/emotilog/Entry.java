package com.emotilog.app.emotilog;

import java.text.SimpleDateFormat;

/**
 * Created by Filippo on 08/11/2017.
 */

public class Entry {
    /*    // Labels table name
        public static final String TABLE="Entry";

        // Labels Table Columns names
        public static final String KEY_ID="id";
        public static final String KEY_TEXT="text";
        public static final String KEY_DATE_TIME="datetime"; //dd/mm/yyyy hh:mm
        public static final String KEY_PHOTO="pathphoto"; //es. "@drawable/angry_bw.png";
        public static final String KEY_FEALING="fealing";
        public static final String KEY_LOCATION="location"; //es. "39.953564, 8.673308";
    */
    // property help us to keep data
    public int ID;
    public String TEXT;
    public String DATE_TIME; //dd/mm/yyyy hh:mm
    public byte[] PHOTO; //es. "@drawable/angry_bw.png";
    public int FEALING;
    public String LOCATION; //es. "39.953564, 8.673308";


    public Entry(){
    }
    public int getId(){
        return this.ID;
    }
    public String getText(){
        return this.TEXT;
    }

    public String getDate_Time(){
        return this.DATE_TIME;
    }

    public byte[] getPhoto(){
        return this.PHOTO;
    }

    public int getFealing(){
        return this.FEALING;
    }

    public String getLocation(){
        return this.LOCATION;
    }
}


