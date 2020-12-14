package com.example.squadzframes.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.squadzframes.MainActivity;
import com.example.squadzframes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class profile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    Boolean choose;
    Uri filepath;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView profilePic = (ImageView) findViewById(R.id.profileImage);
        Button editButton = (Button) findViewById(R.id.edit_button);
        ImageView background = (ImageView) findViewById(R.id.backgroundProfile);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose = setChangeLocation(profilePic);
                popupMenu(profilePic);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose = setChangeLocation(background);
                popupMenu(background);
            }
        });

        ImageButton btn = (ImageButton) findViewById(R.id.back_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this, MainActivity.class );
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profilePic = (ImageView) findViewById(R.id.profileImage);
        ImageView background = (ImageView) findViewById(R.id.backgroundProfile);
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),filepath);

                if(choose == true)
                    profilePic.setImageBitmap(bitmap);
                else
                    background.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setChangeLocation(ImageView image) {
        if(image==findViewById(R.id.profileImage)){
            return true;
        }
        else if(image==findViewById(R.id.backgroundProfile)){
            return false;
        }
        return true;
    }

    private void popupMenu(ImageView image) {
        PopupMenu popupMenu = (PopupMenu) new PopupMenu(getApplicationContext(), image);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_person:
                startFileSelector();
                return true;
            case R.id.nav_background:
                startFileSelector();
                return true;
            default:
                return false;
        }
    }

    private void startFileSelector() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Choose Image"),111);

        if (filepath != null) {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Uploading");
            pd.show();

            StorageReference imageFirebaseStorage = mStorageRef.child("image/pic.jpg");
            imageFirebaseStorage.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_LONG).show();
                }

            })
            .addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Failure to get image", Toast.LENGTH_LONG).show();
                }
            })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        Double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded"+progress.toString()+"%");
                    }
                });
        }
    }
}
//        private fun deleteImage() {
//        var pd = ProgressDialog(this)
//        pd.setTitle("Deleting")
//        pd.show()
//
//        var imageFirebaseStorage = mStorageRef.child("image/pic.jpg")
//        imageFirebaseStorage.delete()
//                .addOnSuccessListener { param ->
//                pd.dismiss()
//            Toast.makeText(applicationContext,"Image Deleted",Toast.LENGTH_LONG).show()
//        }
//            .addOnFailureListener{ param ->
//                pd.dismiss()
//            Toast.makeText(applicationContext,param.message,Toast.LENGTH_LONG).show()
//        }
//            .addOnCompleteListener { param ->
//                pd.setMessage("Deleted ${imageFirebaseStorage.name}")
//            imageView.setImageResource(R.drawable.ic_launcher_background)
//        }
//    }
