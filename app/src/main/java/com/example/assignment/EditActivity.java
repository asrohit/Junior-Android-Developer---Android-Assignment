package com.example.assignment;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class EditActivity extends AppCompatActivity {


    ImageView img_CarImage;
    EditText edt_CarName, edt_Model, edt_Varent, edt_FuelType;
    Button btn_AddInfomation;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    private static final int IMAGE_PICKUP_CAMERA_CODE = 102;
    private static final int IMAGE_PICKUP_GALLARY_CODE = 103;

    private String[] camerapermission;
    private String[] storagepermission;

    private Uri imageUri;

    private String id;
    private String name;
    private String model;
    private String varent;
    private String type;
    private String addtimestamp;
    private String updatetimestamp;
    private Uri image;
    private boolean editMode = false;
    private DataBaseHelper dbHelper;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

        camerapermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        dbHelper = new DataBaseHelper(this);

        img_CarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpImage();
            }
        });

        btn_AddInfomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editMode) {

                    name = edt_CarName.getText().toString().trim();
                    model = edt_Model.getText().toString().trim();
                    varent = edt_Varent.getText().toString().trim();
                    type = edt_FuelType.getText().toString().trim();

                    dbHelper.updateInfomation(
                            "" + id,
                            "" + name,
                            "" + model,
                            "" + type,
                            "" + varent,
                            "" + imageUri,
                            "" + addtimestamp,
                            "" + updatetimestamp
                    );
                } else {
                    name = edt_CarName.getText().toString().trim();
                    model = edt_Model.getText().toString().trim();
                    varent = edt_Varent.getText().toString().trim();
                    type = edt_FuelType.getText().toString().trim();
                    addtimestamp = "" + System.currentTimeMillis();

                    dbHelper.insertInfomation(
                            "" + name,
                            "" + model,
                            "" + type,
                            "" + varent,
                            "" + imageUri,
                            "" + addtimestamp,
                            "" + addtimestamp
                    );
                }

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        intent = getIntent();
        editMode = intent.getBooleanExtra("edit Mode", editMode);
        Log.e("EDITMODE", " fced " + editMode);
        if (editMode) {
            getData();
        }

    }

    private void getData() {


        id = intent.getStringExtra("ID");
        name = intent.getStringExtra("NAME");
        model = intent.getStringExtra("MODEL");
        varent = intent.getStringExtra("VARENT");
        type = intent.getStringExtra("FUELTYPE");
        addtimestamp = intent.getStringExtra("ADD_TIMESTAMP");
        updatetimestamp = intent.getStringExtra("UPDATE_TIMESTAMP");
        image = Uri.parse(intent.getStringExtra("IMAGE"));

        edt_CarName.setText(name);
        edt_Model.setText(model);
        edt_Varent.setText(varent);
        edt_FuelType.setText(type);

        if (imageUri != null) {
            img_CarImage.setImageURI(imageUri);
        } else {
            img_CarImage.setImageResource(R.drawable.ic_camera_alt_black_24dp);

        }
    }

    private void pickUpImage() {

        String[] options = {"Camera", "Gallary"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select For Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkPermissions()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallry();
                    }
                }
            }
        });
        builder.create().show();

    }

    private void pickFromGallry() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKUP_GALLARY_CODE);
    }

    private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, IMAGE_PICKUP_CAMERA_CODE);
    }

    void initView() {
        img_CarImage = findViewById(R.id.image_View);
        edt_CarName = findViewById(R.id.txt_VehicalName);
        edt_Model = findViewById(R.id.txt_ModelName);
        edt_Varent = findViewById(R.id.txt_Varent);
        edt_FuelType = findViewById(R.id.txt_fuelType);
        btn_AddInfomation = findViewById(R.id.btn_AddInformation);
    }


    private boolean checkPermissions() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagepermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, camerapermission, CAMERA_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RESULT_OK) {

            if (requestCode == IMAGE_PICKUP_GALLARY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            } else if (requestCode == IMAGE_PICKUP_CAMERA_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {

                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    img_CarImage.setImageURI(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:

                if (grantResults.length > 0) {

                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccept && storageAccept) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Camera Permission Request Required", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

            case STORAGE_REQUEST_CODE:

                if (grantResults.length > 0) {

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted) {
                        pickFromGallry();
                    } else {
                        Toast.makeText(this, "Storage Persmission Required", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }
}
