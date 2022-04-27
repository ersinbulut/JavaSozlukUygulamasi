package com.example.sozluk2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Kelime> kelimeler;
    OzelAdapter ozelAdapter;
    EditText eAra;
    Veritabani vt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        eAra=findViewById(R.id.editText);
        eAra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                vt = new Veritabani(MainActivity.this);

                if (!s.toString().equals("")){
                    kelimeler= vt.kelimeAra(s.toString());
                }else{
                    kelimeler=vt.kelimeListele();
                }
                vt.close();
                ozelAdapter=new OzelAdapter(MainActivity.this,kelimeler);
                listView.setAdapter(ozelAdapter);
            }
        });
        /*
        kelimeler=new ArrayList<>();
        kelimeler.add(new Kelime(0,"araba","car"));
         */
        vt =new Veritabani(this);
        kelimeler = vt.kelimeListele();
        vt.close();

        ozelAdapter = new OzelAdapter(this,kelimeler);
        listView.setAdapter(ozelAdapter);

        //listview a tıkladığımızda diğer aktivitiye geçmesi için
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Kelime secilenKelime = kelimeler.get(position);

                Intent e = new Intent(MainActivity.this, EkleActivity.class);
                e.putExtra("islem","duzelt");
                e.putExtra("id",secilenKelime.getId());
                e.putExtra("turkce",secilenKelime.getTurkce());
                e.putExtra("ingilizce",secilenKelime.getIngilizce());
                startActivityForResult(e,100);
            }
        });
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //+ ya basıldığında
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //ekleme ekranı açılacak
        if (item.getItemId() == R.id.item_ekle) {
            Intent i = new Intent(this, EkleActivity.class);
            startActivityForResult(i,100);
        }
        return super.onOptionsItemSelected(item);
    }
    //ekleme işleminden sonra listview ı güncelleyen method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        vt =new Veritabani(this);
        kelimeler = vt.kelimeListele();
        vt.close();

        ozelAdapter = new OzelAdapter(this,kelimeler);
        listView.setAdapter(ozelAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}