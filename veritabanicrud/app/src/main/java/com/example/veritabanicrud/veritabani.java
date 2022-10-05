package com.example.veritabanicrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class veritabani extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="ogrenciler";
    private static final int VERSION=1;


    //Yapıcı oluşturuldu
    public veritabani(Context c){
     super(c,DATABASE_NAME,null,VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tablonun sütunlarını oluşturduk.
          db.execSQL("CREATE TABLE ogrencibilgi(ad Text, soyad Text, yas Integer, sehir Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //burada oluşacak eski yeni versiyon güncellemeleri yapılır.
        db.execSQL("DROP TABLE IF EXISTS ogrecibilgi");
        onCreate(db);

    }
}
