package com.example.visonofman.Adepters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visonofman.R;

import java.util.List;

public class CardView_Adepter extends BaseAdapter {

    private Context context;
    private List<String> cardDataList;

    public CardView_Adepter(Context context, List<String> cardDataList) {
        this.context = context;
        this.cardDataList = cardDataList;
    }


    @Override
    public int getCount() {
        return cardDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return cardDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getItemViewType(int position) {
//        if (position < 4) {
//            return 0;
//        } else if (position >=4 && position <8) {
//            return 1;
//        }else if (position >=8 && position <12) {
//            return 2;
//        }else {
//            return 3;
//        }
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview, viewGroup, false);
        }

        TextView textView1 = view.findViewById(R.id.tv);
        textView1.setText(cardDataList.get(i));


//
//        if (getItemViewType(i) == 0) {
//            // First 4 items
//            if (view == null) {
//                view = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.cardview, viewGroup, false);
//            }
//            TextView textView1 = view.findViewById(R.id.tv);
//            textView1.setText(cardDataList.get(i));
//        } else if (getItemViewType(i) == 1){
//            // Next 4 items
//            if (view == null) {
//                view = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.cardview, viewGroup, false);
//            }
//            TextView textView = view.findViewById(R.id.tv);
//            textView.setText(cardDataList.get(i));
//        } else  if (getItemViewType(i) == 2){
//            // Next 4 items
//            if (view == null) {
//                view = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.cardview, viewGroup, false);
//            }
//            TextView textView = view.findViewById(R.id.tv);
//            textView.setText(cardDataList.get(i));
//        }else  if (getItemViewType(i) == 3){
//            // Next 4 items
//            if (view == null) {
//                view = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.cardview, viewGroup, false);
//            }
//            TextView textView = view.findViewById(R.id.tv);
//            textView.setText(cardDataList.get(i));
//        }


        return view;
    }


}
