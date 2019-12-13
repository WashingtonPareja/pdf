package com.example.pdf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class pdfclass {

    private String titulo,fecha,urlPdf;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public pdfclass(JSONObject a) throws JSONException {
        titulo = a.getString("title").toString();
        fecha = a.getString("date_published");
        urlPdf = a.getString("pdf").toString();
    }

    public static ArrayList<pdfclass> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<pdfclass> pdfclass = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            pdfclass.add(new pdfclass(datos.getJSONObject(i)));
        }
        return pdfclass;
    }

}

