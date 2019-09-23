package com.notynote.diveatlas;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userProfile extends Fragment {

    //UI variable

    LinearLayout userDataLayout;
    TextView userEmail;
    TextView userName;
    TextView userPhone;
    TextView userCertLevel;
    TextView userCertNo;
    TextView userCertAgent;
    Button userLogOut;
    Button editProfile;
    ProgressBar loadingBar;
    TextView userGreeting;
    RelativeLayout userDetailLayout;
    ImageButton profileImage;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dRef;
    String userID;

    StorageReference mStorage;

    Calendar cal;
    int timeOfDay;
    String greet;

    private static final int Image_Capture_Code = 1;
    private static final int RESULT_LOAD_IMAGE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        loadingBar = (ProgressBar) view.findViewById(R.id.LoadingBar);

        loadingBar.setVisibility(View.VISIBLE);

        userDataLayout = (LinearLayout)view.findViewById(R.id.userDataLayout);
//        userEmail = (TextView)view.findViewById(R.id.tvUserEmail);
        userName = (TextView)view.findViewById(R.id.tvUserName);
//        userPhone = (TextView)view.findViewById(R.id.tvUserPhone);
        userCertLevel = (TextView)view.findViewById(R.id.tvUserDiverLevel);
        userCertNo = (TextView)view.findViewById(R.id.tvUserDiveNumber);
        userCertAgent = (TextView)view.findViewById(R.id.tvUserDiveAgent);
        userGreeting = view.findViewById(R.id.userGreeting);
        userDetailLayout = view.findViewById(R.id.userDetailLayout);

        //image profile
        profileImage = view.findViewById(R.id.ProfileImage);
        profileImage.setOnClickListener(view1 -> selectImage());

        //greeting by time
        cal = Calendar.getInstance();
        timeOfDay = cal.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greet = "Good Morning";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greet = "Good Afternoon";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            greet = "Good Evening";
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            greet = "Good Night";
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        dRef = firebaseDatabase.getReference("Userdata");

        userID = firebaseUser.getUid();
        DatabaseReference uidRef = dRef.child("Userdata").child(userID);

        mStorage = FirebaseStorage.getInstance().getReference();

        //get information from database
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map)dataSnapshot.child(userID).getValue();
                String email = String.valueOf(map.get("email"));
                String firstname = String.valueOf(map.get("firstName"));
                String lastname = String.valueOf(map.get("lastName"));
                String phone = String.valueOf(map.get("phone"));
                String certlevel = String.valueOf(map.get("certLevel"));
                String certnumber = String.valueOf(map.get("certNo"));
                String certagent = String.valueOf(map.get("certAgent"));
                String profileImg = String.valueOf(map.get("imageUrl"));

                userGreeting.setText(greet);
                userName.setText(firstname + " " + lastname);
                if (certlevel.equals("")){
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(userCertLevel,TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                    userCertLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    userCertLevel.setText("N/A");
                } else {
                    TextViewCompat.setAutoSizeTextTypeWithDefaults(userCertLevel,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                    userCertLevel.setText(certlevel);
                }
                if (certnumber.equals("")){
                    userCertNo.setText("N/A");
                } else {
                    userCertNo.setText(certnumber);
                }
                userCertAgent.setText(certagent);
//                userPhone.setText("Phone: " + phone);
                if (profileImg.equals("")){
                    profileImg = "https://ui-avatars.com/api/?size=300&rounded=true&name=" + firstname + "+" + lastname;
                    Picasso.get().load(profileImg).into(profileImage);
                } else {
                    Picasso.get().load(profileImg).into(profileImage);
                }

                loadingBar.setVisibility(View.GONE);
                userDetailLayout.setVisibility(View.VISIBLE);
                userGreeting.setVisibility(View.VISIBLE);
                userName.setVisibility(View.VISIBLE);
//                userEmail.setVisibility(View.VISIBLE);
                userCertAgent.setVisibility(View.VISIBLE);
                userCertLevel.setVisibility(View.VISIBLE);
                userCertNo.setVisibility(View.VISIBLE);
//                userPhone.setVisibility(View.VISIBLE);
                userLogOut.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set Infomation
//        userEmail.setText(firebaseUser.getEmail());

        editProfile = view.findViewById(R.id.btnEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect back to log in page
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                editProfile frag = new editProfile();
                ft.replace(R.id.fragmentView, frag);
                ft.commit();
            }
        });


        userLogOut = (Button)view.findViewById(R.id.btnLogOut);
        userLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sign out
                FirebaseAuth.getInstance().signOut();

                //redirect back to log in page
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProfilePage frag = new ProfilePage();
                ft.replace(R.id.fragmentView, frag);
                ft.commit();
            }
        });

        return view;
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Userdata uData = new Userdata();
            uData.setEmail(ds.child(userID).getValue(Userdata.class).getEmail());
            uData.setFirstName(ds.child(userID).getValue(Userdata.class).getFirstName());
            uData.setLastName(ds.child(userID).getValue(Userdata.class).getLastName());
            uData.setPhone(ds.child(userID).getValue(Userdata.class).getPhone());
            uData.setCertLevel(ds.child(userID).getValue(Userdata.class).getCertLevel());
            uData.setCertNo(ds.child(userID).getValue(Userdata.class).getCertNo());
            uData.setCertAgent(ds.child(userID).getValue(Userdata.class).getCertAgent());
        }
    }

    private void selectImage(){

        final CharSequence[] items = {"Take Photo", "Gallery", "Cancel"};

        //Aleart Dialog is a popup
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (items[i].equals("Take Photo")){
                    //Toast.makeText(getActivity(),"Clicked on Take Photo",Toast.LENGTH_SHORT).show();

                    Dexter.withActivity(getActivity())
                            .withPermissions(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            )
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {

                                    if (report.isAnyPermissionPermanentlyDenied()){
                                        showSettingsDialog();
                                    }

                                    if (report.areAllPermissionsGranted()) {

                                        launchCameraIntent();

//                                        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                        startActivityForResult(cInt, Image_Capture_Code);

                                    } else {
                                        showSettingsDialog();
                                    }

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                    token.continuePermissionRequest();

                                }
                            })
                            .check();


                } else if (items[i].equals("Gallery")){
                    //Toast.makeText(getActivity(),"Clicked On Gallery",Toast.LENGTH_SHORT).show();

                    Dexter.withActivity(getActivity())
                            .withPermissions(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                            )
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {

                                    if (report.isAnyPermissionPermanentlyDenied()){
                                        showSettingsDialog();
                                    }

                                    if (report.areAllPermissionsGranted()) {

                                        launchGalleryIntent();

                                    } else {
                                        showSettingsDialog();
                                    }

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                    token.continuePermissionRequest();

                                }
                            })
                            .check();

                } else if (items[i].equals("Cancel")){
                    //Toast.makeText(getActivity(),"Clicked On Cancel",Toast.LENGTH_SHORT).show();
                    //dialogInterface.dismiss();
                }

            }
        });

        builder.show();

    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 300);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 300);

        startActivityForResult(intent, Image_Capture_Code);
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 300);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 300);

        startActivityForResult(intent, Image_Capture_Code);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Image_Capture_Code:
                if (resultCode == Activity.RESULT_OK) {

                    // Database
                    Uri uri = data.getParcelableExtra("path");
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                        Uri uploadUri = getImageUri(getActivity().getApplicationContext(), bitmap);

                        final StorageReference filepath = mStorage.child(userID).child(uploadUri.getLastPathSegment());

                        filepath.putFile(uploadUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                //get url of just uploaded file
                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_SHORT).show();

                                        //update database
                                        userID = firebaseUser.getUid();
                                        DatabaseReference uRef = FirebaseDatabase.getInstance().getReference().child("Userdata").child(userID);

                                        Map<String, Object> updates = new HashMap<String, Object>();

                                        updates.put("imageUrl", uri.toString());

                                        uRef.updateChildren(updates);
                                    }
                                });
                            }
                        });


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "Picture not taken!", Toast.LENGTH_SHORT).show();
                }
                break;
            case RESULT_LOAD_IMAGE:
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    if (selectedImage != null) {
                        Uri uri = getImageUri(getActivity().getApplicationContext(), selectedImage);

                        final StorageReference filepath = mStorage.child(userID).child(uri.getLastPathSegment());

                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                //get url of just uploaded file
                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //update database
                                        userID = firebaseUser.getUid();
                                        DatabaseReference uRef = FirebaseDatabase.getInstance().getReference().child("Userdata").child(userID);

                                        Map<String, Object> updates = new HashMap<String, Object>();

                                        updates.put("imageUrl", uri.toString());

                                        uRef.updateChildren(updates);
                                    }
                                });
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Picture not taken!", Toast.LENGTH_SHORT).show();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                break;

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}