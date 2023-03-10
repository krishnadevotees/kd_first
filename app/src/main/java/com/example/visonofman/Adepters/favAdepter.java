package com.example.visonofman.Adepters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visonofman.ModelClass.fav_integers;
import com.example.visonofman.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class favAdepter extends RecyclerView.Adapter<favAdepter.ViewHolder> {

    private List<fav_integers> list ;
    private ItemClickLisener mitemClickLisener;
    Context context;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String sloka,tran,desc;


    public favAdepter(List<fav_integers> list,Context context,ItemClickLisener itemClickLisener) {
        this.list = list;
        this.context=context;
        this.mitemClickLisener=itemClickLisener;
    }

    @NonNull
    @Override
    public favAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_list_item, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favAdepter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mitemClickLisener.onItemClick(list.get(position));
            }
        });

        int c,v;
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        c=list.get(position).getChapter();
        v=list.get(position).getVerse();

        firestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               String  lang = documentSnapshot.getString("selectedLanguage");
                Log.d("devin", "onSuccess: selectedLanguage in display fragment ``favAdepter``  " + lang);

                firestore.collection("display").document(lang+"").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                         sloka = documentSnapshot.getString("verse");
//                        Toast.makeText(context, "success"+sloka, Toast.LENGTH_SHORT).show();

                        holder.ch.setText("Chapter "+(c+1));
                        holder.verse.setText(sloka+" "+(v+1));
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface    ItemClickLisener{
        void onItemClick(fav_integers fav_integers );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ch,verse;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ch = itemView.findViewById(R.id.list_tv1);
            verse = itemView.findViewById(R.id.list_tv2);
            linearLayout =itemView.findViewById(R.id.fav);
//            itemView.setOnClickListener(this);
//            new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position =getAdapterPosition();
////                    int[] integers = {list.get(position).getLanguage(),list.get(position).getChapter(),list.get(position).getVerse()};
//                    Intent intent = new Intent(context, FavoriteActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("l",list.get(position).getLanguage());
//                    bundle.putInt("v",list.get(position).getChapter());
//                    bundle.putInt("c",list.get(position).getVerse());
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });
        }

    }
}
