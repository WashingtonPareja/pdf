package com.example.pdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemClickListener {

    ListView lstOpciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("http://revistas.uteq.edu.ec/ws/getarticles.php?volumen=1&num=1", datos, MainActivity.this, MainActivity.this);
        ws.execute("");
        lstOpciones=(ListView)findViewById(R.id.listaticulo);
        lstOpciones.setOnItemClickListener(this);
        getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        getPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void getPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1)
        {
            Toast.makeText(this.getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject objArticulo=new JSONObject(result);
        JSONArray articulo= objArticulo.getJSONArray("articles");
        ArrayList<pdfclass>listarticulo;
        listarticulo= pdfclass.JsonObjectsBuild(articulo);
        AdaptadorArticulo adaptadorticulo = new AdaptadorArticulo(this, listarticulo);
        lstOpciones=(ListView)findViewById(R.id.listaticulo);
        lstOpciones.setAdapter(adaptadorticulo);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //  Toast.makeText(this.getApplicationContext(),((pdfclass)parent.getItemAtPosition(position)).getUrlPdf(),Toast.LENGTH_SHORT).show();
       // String url = view.getTag().toString();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(((pdfclass)parent.getItemAtPosition(position)).getUrlPdf()));
        request.setDescription("PDF Paper");
        request.setTitle("Pdf Artcilee");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "pdfPrincipal.pdf");
        DownloadManager manager = (DownloadManager)this.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

       // Toast toast = Toast.makeText(MainActivity.this, "pdf Download Complete", Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.TOP, 25, 400);
      //  toast.show();

        try {
            manager.enqueue(request);
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
