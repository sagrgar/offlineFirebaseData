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

public class Viewholder1 extends BaseViewholder{
    private TextView name_tv, id_tv, email_tv;
    private CardView parent;
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private Model fetchedModel;
    private DocumentReference documentReference;
    public Viewholder1(@NonNull View itemView) {
        super(itemView);
        name_tv = itemView.findViewById(R.id.name_tv);
        id_tv = itemView.findViewById(R.id.id_tv);
        email_tv = itemView.findViewById(R.id.email_tv);
        parent = itemView.findViewById(R.id.parent);
    }

    @Override
    void setData(Model model) {
        name_tv.setText(model.getName());
        email_tv.setText(model.getEmail());
        id_tv.setText(String.valueOf(model.getUserid()));
    }

    @Override
    void onclick(Context context, int id) {
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Edit user details");
                        View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.edit_user_dialog, null);

                        TextView userId_tv;
                        EditText userName_et, userEmail_et;

                        userId_tv = dialogView.findViewById(R.id.userId_tv);
                        userName_et = dialogView.findViewById(R.id.userName_et);
                        userEmail_et = dialogView.findViewById(R.id.userEmail_et);

                        fs.collection("table1").whereEqualTo("userid", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                        fetchedModel = documentSnapshot.toObject(Model.class);
                                        documentReference = documentSnapshot.getReference();
                                    }
                                    userId_tv.setText(String.valueOf(id));
                                    userName_et.setText(fetchedModel.getName());
                                    userEmail_et.setText(fetchedModel.getEmail());
                                }
                            }
                        });

                        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newName = userName_et.getText().toString();
                                String newEmail = userEmail_et.getText().toString();
                                Model editedModel = new Model(newName, newEmail, id);
                                documentReference.set(editedModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
//
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
    void onLongClick(Context context, int id) {
        parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Query query = fs.collection("table1").whereEqualTo("userid", id);
                FirebaseDataDeleteHelper.deleteData(context, query);
                return false;
            }
        });
    }
}
