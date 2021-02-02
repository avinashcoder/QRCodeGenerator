package com.avinash.qrcodegenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScannerQRCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    TextView scannerTextView;
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_scanner_qrcode );
        scannerTextView = findViewById( R.id.scannedText );
        scannerView = findViewById( R.id.qrCodeScanner );
}

    @Override
    public void handleResult(Result rawResult) {
        scannerTextView.setText( rawResult.toString() );
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
