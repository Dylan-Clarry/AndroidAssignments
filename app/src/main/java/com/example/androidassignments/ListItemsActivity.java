package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 4249;
    public ImageButton imgBtn;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    public Switch s;
    public CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME,"in onCreate");

        // launch camera button listener
        imgBtn = findViewById(R.id.cameraBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    invokeCamera();
                }
                else{
                    String[] request = {Manifest.permission.CAMERA};
                    requestPermissions(request, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        });

        // Switch toggle code
        s = (Switch) findViewById(R.id.listSwitch);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CharSequence text = "Switch is On";// "Switch is Off"
                    int duration = Toast.LENGTH_SHORT; // = Toast.LENGTH_LONG if Off

                    Toast toast = Toast.makeText(getApplicationContext() , text, duration);
                    toast.show();
                }
                else{
                    CharSequence text = "Switch is Off";// "Switch is Off"
                    int duration = Toast.LENGTH_LONG; // = Toast.LENGTH_LONG if Off

                    Toast toast = Toast.makeText(getApplicationContext() , text, duration);
                    toast.show();
                }

            }
        });

        // CheckBox toggling
        checkBox = (CheckBox) findViewById(R.id.listCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

                    builder.setMessage(R.string.dialogMessage);
                    builder.setTitle(R.string.dialogTitle);
                    builder.setPositiveButton(R.string.dialogOk, new DialogInterface.OnClickListener() {

                        // User exits
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response","Exited list items");
                            setResult(Activity.RESULT_OK,resultIntent);
                            finish();
                        }
                    });
                    builder.setNegativeButton(R.string.dialogNo, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.show();
                }
            }
        });
    }

    @Override // camera permission request function
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){//if permission granted
                invokeCamera(); //try to launch camera again
            }
        }
    }

    private void invokeCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            Bitmap image = (Bitmap)data.getExtras().get("data");
            imgBtn.setImageBitmap(image);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
