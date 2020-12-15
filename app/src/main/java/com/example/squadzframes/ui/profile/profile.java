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

import com.example.squadzframes.ui.home.MainActivity;
import com.example.squadzframes.R;
import com.example.squadzframes.ui.intro.Login_Page;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.Random;

public class profile extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    Boolean choose;
    Uri filepath;
    private ImageView profilePic;
    private ImageView background;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference userDatabase;
    String currentUserID;
    String downloadURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        currentUserID = currentUser.getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        profilePic = (ImageView) findViewById(R.id.profileImage);
        Button editButton = (Button) findViewById(R.id.edit_button);
        background = (ImageView) findViewById(R.id.backgroundProfile);

        //profileURL = "profile:" + currentUserID + ".jpg";

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.child("image").getValue().toString();
                String back_image = snapshot.child("background_image").getValue().toString();

                Picasso.get().load(image).into(profilePic);
                //Picasso.get().load(back_image).into(background);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu();
            }
        });

        Button logoutbtn = (Button) findViewById(R.id.button_logout);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                //editor.putString("remember", "false");
                //editor.apply();
                Intent intent = new Intent(profile.this, Login_Page.class);
                startActivity(intent);
                finish();
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
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null && choose == true) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(this);
        }else if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),filepath);

                    background.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                ProgressDialog pd = new ProgressDialog(profile.this);
                pd.setTitle("Uploading Profile Image..");
                pd.setMessage("Please wait while we upload and process the image");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                StorageReference filepathRef = mStorageRef.child("profile_images").child("profile:" + currentUserID + ".jpg");
                filepathRef.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       if(task.isSuccessful()){

                           filepathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   // Got the download URL for 'profile: + currentUserID +.jpg'
                                   downloadURL = uri.toString();
                                   userDatabase.child("image").setValue(downloadURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               pd.dismiss();
                                               Toast.makeText(profile.this,"Successful Upload.",Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   });
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception exception) {
                                   // Handle any errors
                                   Toast.makeText(profile.this,"Error in Uploading.",Toast.LENGTH_LONG).show();
                               }
                           });

                       }else{
                           pd.hide();
                           Toast.makeText(profile.this,"Error in uploading",Toast.LENGTH_LONG).show();
                       }
                   }
               });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10)+10;
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
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

    private void popupMenu() {
        PopupMenu popupMenu = (PopupMenu) new PopupMenu(getApplicationContext(), background);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_person:
                choose = setChangeLocation(profilePic);
                startFileSelector(profilePic);
                return true;
            case R.id.nav_background:
                choose = setChangeLocation(background);
                startFileSelector(background);
                return true;
            default:
                return false;
        }
    }

    private void startFileSelector(ImageView image) {
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

