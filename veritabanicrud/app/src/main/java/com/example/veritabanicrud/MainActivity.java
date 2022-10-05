package com.example.veritabanicrud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button kaydetBtn,gosterBtn,silBtn,guncelleBtn;
    EditText adEdt,soyadEdt, yasEdt,sehirEdt;
    TextView bilgiler;


    ///veritabanını v1 nesenesine aktardık.
    private veritabani v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /// veri tabanının bellekte yer tutmasını sağlasın
        v1 = new veritabani( this);

        kaydetBtn = findViewById(R.id.buttonKayit);
        gosterBtn = findViewById(R.id.buttonGoster);
        silBtn = findViewById(R.id.buttonSil);
        guncelleBtn = findViewById(R.id.buttonGuncelle);
        adEdt = findViewById(R.id.editTextAd);
        soyadEdt = findViewById(R.id.editTextSoyad);
        yasEdt = findViewById(R.id.editTextYas);
        sehirEdt = findViewById(R.id.editTextSehir);
        bilgiler = findViewById(R.id.textViewBilgiler);


        kaydetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                        kayitEkle(adEdt.getText().toString(),soyadEdt.getText().toString(),yasEdt.getText().toString(),sehirEdt.getText().toString());
                    }

                finally {
                    v1.close();
                }


            }

        });


        gosterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor=kayitGetir();
                kayitGoster(cursor);
            }
        });

        silBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayitsil(adEdt.getText().toString());
            }
        });

        guncelleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guncelle(adEdt.getText().toString(),soyadEdt.getText().toString(),yasEdt.getText().toString(),sehirEdt.getText().toString());
            }
        });

    }






    private String [] sutunlar={"ad","soyad","yas","sehir"};
    private Cursor kayitGetir(){

        //databaseden kayıtları okuyup cursordan verileri alacağız
        SQLiteDatabase db =v1.getWritableDatabase();
        //verileri sütünlar dizisindeki değişkenlerden alacak
        Cursor okunanalar =db.query("ogrencibilgi",sutunlar,null,null,null,null,null);
        return okunanalar;
    }

    //burada cursor ile kayıdın okuyup alıp tanımlamış olduğumuz değişkenlere indekslerden çekip bu kayıtları birden fazla kayıtla okuyoryz
    @SuppressLint("Range")
    private void kayitGoster(Cursor goster)
    {
      StringBuilder builder = new StringBuilder();
      //bir sonraki indekse geç
      while(goster.moveToNext())
      {
          String add,soyadd, yasd, sehird;
          //kolonlardan bilgiyi çekiyoruz.
          add=goster.getString(goster.getColumnIndex("ad"));
          soyadd=goster.getString(goster.getColumnIndex("soyad"));
          yasd=goster.getString(goster.getColumnIndex("yas"));
          sehird=goster.getString(goster.getColumnIndex("sehir"));
          builder.append("ad: ").append(add+"\n");
          builder.append("soyad: ").append(soyadd+"\n");
          builder.append("yas: ").append(yasd+"\n");
          builder.append("sehir: ").append(sehird+"\n");
          builder.append("------------------").append("\n");

      }
        bilgiler.setText(builder);


    }

    private void kayitEkle(String adi, String soyadi,String yasi, String sehri){
         //veri tabanında okumayı açtık verilere karşılık gelen string parametreleri yolluyoruz
        SQLiteDatabase db = v1.getReadableDatabase();
        ContentValues veriler = new ContentValues();
        veriler.put("ad",adi);
        veriler.put("soyad",soyadi);
        veriler.put("yas",yasi);
        veriler.put("sehir",sehri);
        // veriler içindeki değerleri tablomuza ekliyoruz.
        db.insertOrThrow("ogrencibilgi",null,veriler);


    }

    private void kayitsil(String adi){
      //Kayıdı datatabasede okunabilirliğini açtık.
        SQLiteDatabase db=v1.getReadableDatabase();
        //öğrenci bilgi tablosundaki ad sütunundaki isme göre silme işlemi yaptırıyoruz id görede olabilir.
        db.delete("ogrencibilgi","ad"+"=?",new String[]{adi});


    }

    private void guncelle(String adi, String soyadi,String yasi, String sehri){
        //okunabiliriliği açıp değitirip güncelllemek isteiğim satırı ad değişkeninden bulup ContetValues ile
        //farklı değişkenler ile güncelliyorum.
       SQLiteDatabase db =v1.getReadableDatabase();
       ContentValues cvGuncelle= new ContentValues();
       cvGuncelle.put("ad",adi);
       cvGuncelle.put("soyad",soyadi);
       cvGuncelle.put("yas",yasi);
       cvGuncelle.put("sehir",sehri);

       db.update("ogrencibilgi",cvGuncelle,"ad"+"=?",new String[] {adi});


    }

   /* @SuppressLint("NewApi")

    public Connection connectionclass(){

        Connection con=null;
        String ip="jdbc:jtds:sqlserver://192.168.88.20/CRUDAndroidDB;integratedSecurity=true";
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con= DriverManager.getConnection(ip,"sa","FB1907.");

        }
        catch (Exception exception){

            Log.e("Error",exception.getMessage());
        }
        return con;

    }

    */
}