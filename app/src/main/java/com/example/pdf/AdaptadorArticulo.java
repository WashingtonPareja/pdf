package com.example.pdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

public class AdaptadorArticulo extends ArrayAdapter<pdfclass> {
    public AdaptadorArticulo(Context context, ArrayList<pdfclass> datos) {
        super(context, R.layout.ly_item, datos);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.ly_item, null);
        TextView lblTitulo = (TextView)item.findViewById(R.id.txtitulo);
        lblTitulo.setText(getItem(position).getTitulo());
        TextView lblfecha = (TextView)item.findViewById(R.id.txtsubtitulo);
        lblfecha.setText(getItem(position).getFecha());
        ImageView imageView = (ImageView)item.findViewById(R.id.imgpdf);
        //Glide.with(this.getContext()).load(getItem(position).getUrlPdf()).into(imageView);
        imageView.setTag(getItem(position).getUrlPdf());
        return(item);
    }
}
