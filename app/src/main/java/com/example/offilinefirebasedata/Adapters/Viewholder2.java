package com.example.offilinefirebasedata.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.offilinefirebasedata.FirebaseDataDeleteHelper;
import com.example.offilinefirebasedata.Model.Model;
import com.example.offilinefirebasedata.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Viewholder2 extends BaseViewholder{
    private TextView name2_tv, address_tv, id2_tv;
    private CardView parent2;
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private Model fetchedModel;
    private DocumentReference documentReference;

    public Viewholder2(@NonNull View itemView) {
        super(itemView);
        name2_tv = itemView.findViewById(R.id.name2_tv);
        id2_tv = itemView.findViewById(R.id.id2_tv);
        address_tv = itemView.findViewById(R.id.address_tv);
        parent2 = itemView.findViewById(R.id.parent2);
    }

    @Override
    void setData(Model model) {
        name2_tv.setText(model.getName());
        address_tv.setText(model.getAddress());
        id2_tv.setText(String.valueOf(model.getTable2Id()));
    }

    @Override
    void onclick(Context context, int id2) {
        parent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit table2 details");
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.edit_table2_dialog, null);

                TextView table2Id_tv;
                EditText table2Name_et, address_et;

                table2Id_tv = dialogView.findViewById(R.id.table2Id_tv);
                table2Name_et = dialogView.findViewById(R.id.table2Name_et);
                address_et = dialogView.findViewById(R.id.address_et);

                fs.collection("table2").whereEqualTo("table2Id", id2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                fetchedModel = documentSnapshot.toObject(Model.class);
                                documentReference = documentSnapshot.getReference();
                            }
                            table2Id_tv.setText(String.valueOf(id2));
                            table2Name_et.setText(fetchedModel.getName());
                            address_et.setText(fetchedModel.getAddress());
                        }
                    }
                });

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newName = table2Name_et.getText().toString();
                        String newAddress = address_et.getText().toString();
                        Model editedModel = new Model(newName, id2, newAddress);
                        documentReference.set(editedModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setView(dialogView);
                builder.show();
            }
        });
    }

    @Override
    void onLongClick(Context context, int id2) {
        parent2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Query query = fs.collection("table2").whereEqualTo("table2Id", id2);
                FirebaseDataDeleteHelper.deleteData(context, query);
                return false;
            }
        });

    }
}
