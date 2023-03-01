package com.example.visonofman.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.visonofman.ModelClass.UpdateProfileModel;
import com.example.visonofman.R;
import com.example.visonofman.databinding.FragmentProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    Context context;
    FirebaseAuth firebaseAuth;
    Dialog loadingDialog;
    String name, email, bio;
    Uri uri, uri2;
    Bitmap bitmap;
    ImageView img;
    TextInputEditText n, e, d;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        uri= Uri.parse("");

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.textInputLayout.setHintAnimationEnabled(true);


        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.login_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference document = firestore.collection("users").document(user.getUid());

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    name = documentSnapshot.getString("name");
                    email = documentSnapshot.getString("email");
                    bio = documentSnapshot.getString("desc");

                    if (documentSnapshot.getString("image") != "") {
                        Picasso.get().load(documentSnapshot.getString("image")).into(binding.profileImage);
                        uri2 = Uri.parse(documentSnapshot.getString("image"));
                    }
                    binding.profileBio.setText(bio);
                    binding.profileName.setText(name);
                    binding.profilrEmail.setText(email);
                    loadingDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "error !! in profile " + e, Toast.LENGTH_SHORT).show();
            }
        });

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                dialog.setContentView(R.layout.update_profile);

                Button update = dialog.findViewById(R.id.ok);
                Button cancle = dialog.findViewById(R.id.cancle);
                Button browse = dialog.findViewById(R.id.browse);
                TextInputLayout textInputLayout = dialog.findViewById(R.id.text_input_layout);
                TextInputLayout textInputLayout2 = dialog.findViewById(R.id.text_input_layout2);
                TextInputLayout textInputLayout3 = dialog.findViewById(R.id.text_input_layout3);
                textInputLayout.setHintAnimationEnabled(true);
                textInputLayout2.setHintAnimationEnabled(true);
                textInputLayout3.setHintAnimationEnabled(true);

                img = dialog.findViewById(R.id.image_updateProfile);
                n = dialog.findViewById(R.id.name);
                e = dialog.findViewById(R.id.email);
                d = dialog.findViewById(R.id.desc);

                n.setText(name);
                e.setText(email);
                d.setText(bio);
                if (uri2 != null) {
                    Picasso.get().load(uri2).into(img);
                }


                browse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Dexter.withActivity(getActivity())
                                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // Permission granted, browse for image
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.setType("image/*");
                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // Permission denied
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        // Show permission rationale dialog
                                        token.continuePermissionRequest();
                                    }
                                }).check();
                    }
                });


                dialog.setTitle("Update Profile");
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.Dialoganimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        updateUser();
                        dialog.dismiss();
                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });


        return root;
    }

    private void updateUser() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updateing...");
        progressDialog.show();

//        String uname, uemail, udesc ;
//        uname = n.getText().toString();
//        uemail = e.getText().toString();
//        udesc = d.getText().toString();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference reference = firebaseStorage.getReference("images/" + firebaseAuth.getCurrentUser().getEmail());


        if (uri != null) {

        } else {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("profileimage",0);
            String uriString = sharedPreferences.getString("imageUri", null);
            if (uriString!=null){
                uri = Uri.parse(uriString);
            }

        }

        reference.putFile(uri,null,uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri3) {
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        DocumentReference documentReference = firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                        UpdateProfileModel model = new UpdateProfileModel(n.getText().toString(), e.getText().toString(), d.getText().toString(), uri3.toString());

                        Map<String, Object> data = new HashMap<>();
                        data.put("name", model.getName());
                        data.put("email", model.getEmail());
                        data.put("desc", model.getDisc());
                        data.put("image", model.getImage());

                        documentReference.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                getActivity().recreate();
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Profile Update Fail", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot snapshot) {
                float per = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Updating... " + (int) per + " %");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("profileimage",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("imageUri", uri.toString());
            editor.apply();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);


            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}