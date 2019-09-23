package com.hansung.android.restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hhan0 on 2017-11-17.
 */
//=======================================카메라와 메뉴 정보를 입력하고 저장하게 해주는 코드===============================
public class MenuAdd extends AppCompatActivity {

    private DBHelper2 mDbHelper2;
    Uri imageUri;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuadd);

        mDbHelper2 = new DBHelper2(this);

        ImageButton buttonCamera = (ImageButton) findViewById(R.id.imageButton2);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                insertRecord();
            }
        });

    }

    String mPhotoFileName;
    File mPhotoFile;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //==================카메라 파일의 이름이 현재 시간으로 적히는 코드======================
    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }


    //=======================카메라 사진 찍는 코드================================
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG" + currentDateFormat() + ".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

            if (mPhotoFile != null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                imageUri = FileProvider.getUriForFile(this, "com.hansung.android.restaurant", mPhotoFile);
                //authority에 패키지이름 고쳐주기

                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }
    }


    //=====================카메라 결과값 저장해주고 보여주는 코드=========================
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageUri = FileProvider.getUriForFile(this, "com.hansung.android.restaurant", mPhotoFile);

            if (mPhotoFileName != null) {
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);

                ImageView ImageView = (android.widget.ImageView) findViewById(R.id.imageButton2);
                ImageView.setImageURI(Uri.fromFile(mPhotoFile));
            }
        }

    }


    //========================데이터 저장을 해주는 코드=================================
    private void insertRecord() {
        EditText name = (EditText) findViewById(R.id.edit1);
        EditText address = (EditText) findViewById(R.id.edit2);
        EditText phone = (EditText) findViewById(R.id.edit3);
        String photo = mPhotoFile.getAbsolutePath();


        long nOfRows = mDbHelper2.insertUserByMethod(name.getText().toString(), address.getText().toString(), phone.getText().toString(), photo);
        if (nOfRows > 0) {
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
            Intent new_intent = new Intent(getApplicationContext(), Restaurant.class);
            new_intent.setData(imageUri);
            startActivity(new_intent);
        } else {
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
        }
    }
}