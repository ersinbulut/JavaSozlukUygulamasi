package com.example.sozluk2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Veritabani extends SQLiteOpenHelper {
    //YAPICI METOT (Constructor)
    public Veritabani(@Nullable Context context) {
        super(context, "sozluk.db",null,1);
    }
    //bu method uygulamayı ilk kurduğumuzda yalnızca bir kere çalışır.
    @Override
    public void onCreate(SQLiteDatabase db) {//UYGULAMA İLK DEFA KURULDUĞUNDA ÇALIŞIR
        db.execSQL("CREATE TABLE IF NOT EXISTS kelime(id INTEGER PRIMARY KEY AUTOINCREMENT,turkce TEXT,ingilizce TEXT)");
    }
    //UYGULAMADA GÜNCELLEME OLDUĞU ZAMAN ÇALIŞIR
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS kelime");//uygulamayı kurduğunda tabloyu siler
        onCreate(db);//ve yeniden oluşturması için oncreate tekrar çalıştırır.
    }

    public long kelimeEkle(Kelime k){
        ContentValues icerik=new ContentValues();
        //tablodaki alan adına göre nesne içerisindeki alan ı icerik olarak ekleme
        icerik.put("turkce",k.getTurkce());
        icerik.put("ingilizce",k.getIngilizce());

        SQLiteDatabase db=  getWritableDatabase();
        //YENİ EKLENEN KAYDIN ID SİNİ CEVAP OLARAK VERİR
        long cevap = db.insert("kelime",null,icerik);
        //HATA OLURSA -1 SONUCU VERİR
        return cevap;
    }

    public ArrayList<Kelime> kelimeListele(){
        ArrayList<Kelime> kelimeArrayList=new ArrayList<>();
        //veri tabanından okuma işlemleri
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, turkce, ingilizce FROM kelime",null);
        //rawquery bilgileri cursor tipinde döndürür
        //cursor nesnesi: kayıtları üzerinde gezinebileceğimiz şekile getirir örneğin 10 kayıt dönüyorsa sırayla cursor u haraket
        //ettirip bu kaydın içeriğine erişebiliriz

        //ilk kayıt var ise true döner
        if (cursor.moveToFirst()){
            do {
                int i=cursor.getInt(0);
                String tur=cursor.getString(1);
                String ing=cursor.getString(2);
                //bu değerleri diziye atma
                Kelime k=new Kelime(i,tur,ing);
                kelimeArrayList.add(k);
            }while (cursor.moveToNext());//her kaydı okuduktan sonra bir sonrakine geçer
        }
        cursor.close();
        return kelimeArrayList;
    }

    public Long kelimeDuzelt(Kelime k){
        ContentValues icerik=new ContentValues();
        icerik.put("turkce",k.getTurkce());
        icerik.put("ingilizce",k.getIngilizce());

        SQLiteDatabase db=  getWritableDatabase();
        //GÜNCELLENEN KAYDIN ID SİNİ CEVAP OLARAK VERİR
        long cevap = db.update("kelime",icerik,"id="+k.getId(),null);
        //HATA OLURSA -1 SONUCU VERİR
        return cevap;
    }

    public long kelimeSil(int silinecek_id){
        SQLiteDatabase db=  getWritableDatabase();
        long cevap = db.delete("kelime","id="+silinecek_id,null);
        return cevap;
    }

    public ArrayList<Kelime> kelimeAra(String aranan){
        String[] args={aranan+"%",aranan+"%"};

        ArrayList<Kelime> kelimeArrayList=new ArrayList<>();
        //veri tabanından okuma işlemleri
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,turkce,ingilice FROM kelime WHERE turkce LIKE ? or ingilizce LIKE ?",args);
        //rawquery bilgileri cursor tipinde döndürür
        //cursor nesnesi: kayıtları üzerinde gezinebileceğimiz şekile getirir örneğin 10 kayıt dönüyorsa sırayla cursor u haraket
        //ettirip bu kaydın içeriğine erişebiliriz

        if (cursor.moveToFirst()){
            //ilk kayıt var ise true döner
            do {
                int i=cursor.getInt(0);
                String tur=cursor.getString(1);
                String ing=cursor.getString(2);
                //bu değerleri diziye atma
                Kelime k=new Kelime(i,tur,ing);
                kelimeArrayList.add(k);
            }while (cursor.moveToNext());//her kaydı okuduktan sonra bir sonrakine geçer
        }
        cursor.close();
        return kelimeArrayList;
    }


}
