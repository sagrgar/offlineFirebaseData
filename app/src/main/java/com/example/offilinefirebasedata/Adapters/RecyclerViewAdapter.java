package com.example.offilinefirebasedata.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offilinefirebasedata.Model.Model;
import com.example.offilinefirebasedata.R;
import com.example.offilinefirebasedata.Utils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewholder> {

    private Context context;
    private ArrayList<Model> modelArrayList = new ArrayList<>();
//    private ArrayList<Table1> table1ArrayList = new ArrayList<>();
//    private ArrayList<Table2> table2ArrayList = new ArrayList<>();
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private Viewholder1 viewholder1;
    private Viewholder2 viewholder2;


    public RecyclerViewAdapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public BaseViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case Constants.TABLE1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.table1_row, parent,false);
                viewholder1 = new Viewholder1(view1);
                return viewholder1;
            case Constants.TABLE2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.table2_row, parent,false);
                viewholder2 = new Viewholder2(view2);
                return viewholder2;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewholder holder, int position) {

        Model model1 = modelArrayList.get(position);
        Model model2 = modelArrayList.get(position);
        switch (modelArrayList.get(position).getViewType()){
            case Constants.TABLE1:
                holder.setData(modelArrayList.get(position));
                int id = model1.getUserid();
                holder.onclick(context, id);
                holder.onLongClick(context, id);
                break;

            case Constants.TABLE2:
                holder.setData(modelArrayList.get(position));
                int id2 = model2.getTable2Id();
                holder.onclick(context, id2);
                holder.onLongClick(context, id2);
                break;


        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public void setModel(ArrayList<Model> modelArrayList){
        this.modelArrayList = modelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        switch (modelArrayList.get(position).getViewType()){
            case 1:
                return Constants.TABLE1;
            case 2:
                return Constants.TABLE2;
            default:
                throw new IllegalArgumentException();
        }
    }
}
