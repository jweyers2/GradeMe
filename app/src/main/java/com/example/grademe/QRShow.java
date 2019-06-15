package com.example.grademe;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRShow extends AppCompatActivity {
    private ImageView qrImage;
    private TextView kursName;
    private Long qrCode;
    private int smallerDimension;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrshow);
        qrCode = getIntent().getExtras().getLong("id");

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        qrImage = (ImageView) findViewById(R.id.qrImage);
        kursName = (TextView) findViewById(R.id.kursName);

        String inputValue = Long.toString(qrCode);
        QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT,smallerDimension);

        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v("Error", e.toString());
        }

    }

}
