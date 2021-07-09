package com.derslet.derslet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

public class OgrenciQrKod extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    PreviewView kamera_alani;
    ListenableFuture<ProcessCameraProvider> kamera_saglayici;

    ImageButton geri_buton;

    Veritabani veritabani = new Veritabani();
    Statement stmt = null;

    Boolean qr_okundu_mu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci_qr_kod);

        kamera_alani = findViewById(R.id.kamera_alani);

        kamera_saglayici = ProcessCameraProvider.getInstance(this);
        kamerayi_cagir();


        //Butonlar
        geri_buton = (ImageButton)findViewById(R.id.geri_buton);
        geri_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                OgrenciQrKod.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void kamerayi_cagir() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            kamerayiCalistir();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                kamerayiCalistir();
            } else {
                Toast.makeText(this, "Kamera izni yok!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void kamerayiCalistir() {
        kamera_saglayici.addListener(() -> {
            try {
                kamera_goruntusu_al(kamera_saglayici.get());
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Kamerayı çalıştırırken bir hata oluştu!", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void kamera_goruntusu_al(@NonNull ProcessCameraProvider cameraProvider) {
        kamera_alani.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview onizleme = new Preview.Builder()
                .build();

        CameraSelector kamera_secici = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        onizleme.setSurfaceProvider(kamera_alani.createSurfaceProvider());

        ImageAnalysis gorsel_analizleyici =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        gorsel_analizleyici.setAnalyzer(ContextCompat.getMainExecutor(this), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            //QR kod bulununca çalışacak fonksiyon;
            @Override
            public void onQRCodeFound(String qrkod) {
                qrkodKontrolEt(qrkod);
            }

            //QR kod bulunmayınca çalışacak fonksiyon;
            @Override
            public void qrCodeNotFound() {

            }
        }));

        Camera kamera = cameraProvider.bindToLifecycle((LifecycleOwner) this, kamera_secici, gorsel_analizleyici, onizleme);
    }

    // Veri tabanı üzerinden qrkod kontrol edilip derse katılım sağlanacak
    void qrkodKontrolEt(String qrCode){
        if(!qr_okundu_mu){
            qr_okundu_mu = true;
            //Yerel verilerde eğer giriş anahtarı kayıtlı ise veriyi çekme işlemi
            SharedPreferences yerel_veriler = getApplicationContext().getSharedPreferences("Yerel Veri", Context.MODE_PRIVATE);
            String id = yerel_veriler.getString("Token","");

            // Veritabanı Hata Giderici ('java.sql.Statement java.sql.Connection.createStatement()' on a null object reference)
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // Veritabanı Sorgu İşlemleri
            try {
                stmt = (veritabani.getExtraConnection()).createStatement();
                String sql = "INSERT INTO devamsizlik(ogrenciid, derskontrolid) VALUES('"+id+"','"+qrCode+"')";
                stmt.executeUpdate(sql);

                //Toast menü
                Toast toast = Toast.makeText(getApplicationContext(), "Başarılı şekilde yoklamaya katıldınız!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();

            } catch (Exception e){

                //Toast menü
                Toast toast = Toast.makeText(getApplicationContext(), "Bir hata meydana geldi!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();

                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            finish();
            OgrenciQrKod.this.overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        }
    }
}
