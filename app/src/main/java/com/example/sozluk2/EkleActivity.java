package com.example.sozluk2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EkleActivity extends AppCompatActivity {
    EditText eTurkce,eIngilizce;
    TextView tId;
    Button bEkle,bDuzelt,bSil;
    Veritabani vt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        eTurkce=findViewById(R.id.edit_turkce);
        eIngilizce=findViewById(R.id.edit_ingilizce);
        tId=findViewById(R.id.txt_id);

        bEkle=findViewById(R.id.button);
        bDuzelt=findViewById(R.id.button2);
        bSil=findViewById(R.id.button3);

        int id=getIntent().getIntExtra("id",0);
        String turkce=getIntent().getStringExtra("turkce");
        String ingilizce=getIntent().getStringExtra("ingilizce");

        tId.setText(String.valueOf(id));
        eTurkce.setText(turkce);
        eIngilizce.setText(ingilizce);
        }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_cikis) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnEkle(View v){
        String turkce=eTurkce.getText().toString();
        String ingilizce=eIngilizce.getText().toString();

        Kelime yeniKelime=new Kelime(0,turkce,ingilizce);
        if (!turkce.equals("") && !ingilizce.equals("")){
            vt =new Veritabani(this);
            long sonuc = vt.kelimeEkle(yeniKelime);
            vt.close();
            if(sonuc>0){
                Toast.makeText(this, "Kayıt ekleme işlemi tamamlandı.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "kayıt ekleme başarısız.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Eksik bilgi girdiniz!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void btnDuzelt(View v){
        int id =Integer.parseInt(tId.getText().toString());
        String turkce =eTurkce.getText().toString();
        String ingilizce =eIngilizce.getText().toString();

        Kelime yeniKelime=new Kelime(id,turkce,ingilizce);

        if (id>0 && !turkce.equals("") && !ingilizce.equals("")){
            vt=new Veritabani(this);
            long sonuc = vt.kelimeDuzelt(yeniKelime);
            vt.close();
            if (sonuc>0){
                Toast.makeText(this, "Düzeltme işlemi tamamlandı..", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Düzeltme işlemi başarısız..", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Eksik bilgi girdiniz!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void btnSil (View v){
        final int id =Integer.parseInt(tId.getText().toString());
        String turkce =eTurkce.getText().toString();
        String ingilizce =eIngilizce.getText().toString();

        if (id>0){
            AlertDialog.Builder mesaj=new AlertDialog.Builder(this);
            mesaj.setTitle("Kayıt Sil");
            mesaj.setMessage("Silmek istiyor musun ?");
            mesaj.setNegativeButton("Hayır",null);
            mesaj.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    vt=new Veritabani(EkleActivity.this);
                    long cevap=vt.kelimeSil(id);
                    vt.close();
                    Toast.makeText(EkleActivity.this, "Kayıt Silindi.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            mesaj.create().show();
        }else{
            Toast.makeText(this, "Eksik bilgi girdiniz!", Toast.LENGTH_SHORT).show();
        }
    }
}