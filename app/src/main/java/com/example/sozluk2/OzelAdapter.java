package com.example.sozluk2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OzelAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<Kelime> kelimeler;
    //tasarımı görünüme aktarma servisini kullanarak layout inflater nesnesini oluştururuz.
    public OzelAdapter(Context context,ArrayList<Kelime> kelimeler){
        this.layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.kelimeler=kelimeler;
    }
    //dizideki eleman sayısını verir
    @Override
    public int getCount() {
        return kelimeler.size();
    }
    //dizideki herhangi bir elemanı elde etmek için kullanılır.
    @Override
    public Object getItem(int position) {
        return kelimeler.get(position);
    }
    //position değerini döndürür
    @Override
    public long getItemId(int position) {
        return position;
    }
    //satır görünümünü oluşturmak için kullanılır.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.satir_layout,parent,false);
        TextView txt_id=convertView.findViewById(R.id.satir_id);
        TextView txt_turkce=convertView.findViewById(R.id.satir_turkce);
        TextView txt_ingilizce=convertView.findViewById(R.id.satir_ingilizce);
        //sırasıyla tüm elemanları elde edip textview e yerleştiriyoruz
        Kelime k=kelimeler.get(position);
        //sonrasında textview lere veri girişi yapıyoruz
        txt_id.setText(String.valueOf(k.getId()));
        txt_turkce.setText(k.getTurkce());
        txt_ingilizce.setText(k.getIngilizce());
        return convertView;
    }
}
