package com.example.offilinefirebasedata.Adapters;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.offilinefirebasedata.Model.Model;

public abstract class BaseViewholder extends RecyclerView.ViewHolder {

    public BaseViewholder(@NonNull View itemView) {
        super(itemView);
    }

    abstract void setData(Model model);

    abstract void onclick(Context context, int id);

    abstract void onLongClick(Context context, int id);
}

