package com.example.grademe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.WriterException;

import org.w3c.dom.Text;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRGenerator extends AppCompatActivity {

    private ImageView qrImage;
    private EditText kursName;
    private Button btnSpeichern;
    private Button btnErstellen;
    private TextView editName;
    private TextView chosenName;
    private Bitmap bitmap;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        qrImage = (ImageView) findViewById(R.id.qrImage2);
        kursName = (EditText) findViewById(R.id.txtName);
        editName = (TextView) findViewById(R.id.editName);
        chosenName = (TextView) findViewById(R.id.chosenName);
        btnSpeichern = (Button) findViewById(R.id.btnSpeichern);
        btnSpeichern.setVisibility(View.INVISIBLE);
        chosenName.setVisibility(View.INVISIBLE);

        kursName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        btnErstellen = (Button) findViewById(R.id.btnErstellen);
        btnErstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // QR Code generieren
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;

                // TODO Erstelle in DB Kurs mit Namen wie in variable kursName und speicher Kursnummer in inputValue
                String inputValue = "0";
                QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT,smallerDimension);

                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    qrImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    Log.v("Error", e.toString());
                }
                btnSpeichern.setVisibility(View.VISIBLE);
                btnErstellen.setVisibility(View.INVISIBLE);
                kursName.setVisibility(View.INVISIBLE);
                editName.setVisibility(View.INVISIBLE);
                chosenName.setText(kursName.getText());
                chosenName.setVisibility(View.VISIBLE);
                btnSpeichern.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean save;
                        String result;
                        try {
                            save = QRGSaver.save(savePath, kursName.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                            result = save ? "Gespeichert" : "Nicht gespeichert";
                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
