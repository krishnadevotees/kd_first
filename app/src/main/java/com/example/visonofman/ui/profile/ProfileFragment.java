package com.example.visonofman.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.visonofman.R;
import com.example.visonofman.databinding.FragmentProfileBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

private FragmentProfileBinding binding;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    Context context;
    FirebaseAuth firebaseAuth;
    Dialog loadingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {


    binding = FragmentProfileBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.login_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getContext());


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference document=firestore.collection("users").document(user.getUid());

        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name= documentSnapshot.getString("name");
                    String email= documentSnapshot.getString("email");
                    binding.profileName.setText(name);
                    binding.profilrEmail.setText(email);
                    loadingDialog.dismiss();
                }
            }
        });

//        if (account != null){
//            String name =account.getDisplayName();
//            String email=account.getEmail();
//            binding.profileName.setText(name);
//            binding.profilrEmail.setText(email);
//
//        }else if (user != null){
//            String name =user.getDisplayName();
//            String email =user.getEmail();
//            binding.profileName.setText(name);
//            binding.profilrEmail.setText(email);
//        }else {
//            binding.profileName.setText("User Name Email : \n Error !!!");
//        }




        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    public void showDialog(){
//        final Dialog dialog = new Dialog(getContext());
//        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
//        dialog.setContentView(R.layout.bottamsheet_layout);
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.Dialoganimation;
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//
//    }
}