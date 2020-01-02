package com.avinash.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    Button generateQr,readQr;
    private final int PERMISSION_REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        generateQr = findViewById( R.id.generate_qr );
        readQr = findViewById( R.id.read_qr );

        generateQr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this,GenerateQRCode.class );
                startActivity( i );
            }
        } );

        readQr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    Intent i = new Intent( MainActivity.this,ScannerQRCode.class );
                    startActivity( i );
                }
            }
        } );
    }

    public boolean checkPermission() {
        //int externalStoragePermission = ContextCompat.checkSelfPermission( Objects.requireNonNull(this), WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(this, CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }
}
