package com.hansung.android.restaurant;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
//===================================카메라와 editText로 맛집 정보를 입력받는 코드==========================================
public class RestaurantRegister extends AppCompatActivity {

    EditText mId;
    EditText mName;
    EditText mAddress;
    EditText mPhone;
    private DBHelper mDbHelper;
    private DBHelper3 mDbHelper3;
    Uri imageUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantregister);

        checkDangerousPermissions();

        ImageButton buttonCamera = (ImageButton) findViewById(R.id.imageButton2);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent new_intent = new Intent(getApplicationContext(), Restaurant.class);
                startActivity(new_intent);
                insertRecord();
            }
        });

        mId = (EditText) findViewById(R.id._id);
        mName = (EditText) findViewById(R.id.edit1);
        mAddress = (EditText) findViewById(R.id.edit2);
        mPhone = (EditText) findViewById(R.id.edit3);
        mDbHelper = new DBHelper(this);
        mDbHelper3 = new DBHelper3(this);

        viewAllToTextView2();
        viewAllToTextView3();

    }

    final int REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA = 1;
    String mPhotoFileName;
    File mPhotoFile;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    //=============================외부저장소===============================
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA);

        }
    }

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
        EditText latitude = (EditText) findViewById(R.id.edit_latitude);
        EditText longitude = (EditText) findViewById(R.id.edit_longitude);


        String photo = mPhotoFile.getAbsolutePath(); //http://sexy.pe.kr/tc/763 참조

        long nOfRows = mDbHelper.insertUserByMethod(name.getText().toString(), address.getText().toString(), phone.getText().toString(),
                photo, latitude.getText().toString(), longitude.getText().toString());

        if (nOfRows > 0) {
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
        }
        //오류나면 data-data-databases 에서 db삭제하고 다시해보기

    }

    private void viewAllToTextView2() {
        EditText address = (EditText) findViewById(R.id.edit_latitude);

      Cursor cursor = mDbHelper3.getAllUsersBySQL();

       StringBuffer buffer = new StringBuffer();
       if(cursor.moveToLast()){
          buffer.append(cursor.getString(1) + " \n");
      }
        address.setText(buffer);
   }
    private void viewAllToTextView3() {
        EditText address2 = (EditText) findViewById(R.id.edit_longitude);

        Cursor cursor = mDbHelper3.getAllUsersBySQL();

        StringBuffer buffer = new StringBuffer();
        if(cursor.moveToLast()){
            buffer.append(cursor.getString(2) + " \n");
        }


        address2.setText(buffer);
    }


}
