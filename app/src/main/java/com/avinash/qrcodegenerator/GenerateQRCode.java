package com.avinash.qrcodegenerator;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GenerateQRCode extends AppCompatActivity {

    Button generateQrButton,generateBarCodeButton;
    EditText nameInput;
    ImageView displayQRImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_generate_qrcode );
        generateQrButton = findViewById( R.id.generateQrCodeButton );
        generateBarCodeButton = findViewById(R.id.generateBarCodeButton);
        nameInput = findViewById( R.id.name );
        displayQRImage = findViewById( R.id.qrCodeImageView );

        generateQrButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = TextToImageEncode( nameInput.getText().toString(), "qrCode",500,500 );
                    displayQRImage.setImageBitmap( bitmap );
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        } );

        generateBarCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Bitmap bitmap = TextToImageEncode( nameInput.getText().toString(), "barCode",250,500 );
                    if(bitmap != null)
                        displayQRImage.setImageBitmap( bitmap );

                }catch (Exception e){}
            }
        });
    }

    private Bitmap TextToImageEncode(String Value, String codeType, int height, int width) throws WriterException {
        int color_black = getResources().getColor(R.color.black);
        int color_white = getResources().getColor(R.color.white);
        BitMatrix bitMatrix;
        try {
            if(codeType.equalsIgnoreCase("qrCode")) {
                bitMatrix = new MultiFormatWriter().encode(
                        Value,
                        BarcodeFormat.QR_CODE,
                        width, height, null
                );
            }else{
                bitMatrix = new MultiFormatWriter().encode(
                        Value,
                        BarcodeFormat.CODE_128,
                        width, height, null
                );
            }

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? color_black: color_white;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    /*private Bitmap TextToBarCodeEncode(String value){
        Bitmap bitmap = null;
        int color_black = getResources().getColor(R.color.black);
        int color_white = getResources().getColor(R.color.white);
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            Writer codeWriter;
            codeWriter = new Code128Writer();
            BitMatrix byteMatrix = codeWriter.encode(value, BarcodeFormat.CODE_128,400, 200, hintMap);
            int width = byteMatrix.getWidth();
            int height = byteMatrix.getHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, byteMatrix.get(i, j) ? color_black : color_white);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return bitmap;
    }*/
}
