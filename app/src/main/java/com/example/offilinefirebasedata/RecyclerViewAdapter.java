package com.example.offilinefirebasedata;

import static com.example.offilinefirebasedata.Model.TABLE1;
import static com.example.offilinefirebasedata.Model.TABLE2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Model> modelArrayList = new ArrayList<>();
    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private Model fetchedModel;
    private DocumentReference documentReference;
    private Model model;


    public RecyclerViewAdapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TABLE1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.table1_row, parent,false);
                return new ViewHolder1(view1);
            case TABLE2:
            default:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.table2_row, parent,false);
                return new ViewHolder2(view2);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        fs = FirebaseFirestore.getInstance();
        model = modelArrayList.get(position);
        switch (modelArrayList.get(position).getViewType()){

            case TABLE1:
                String name = model.getName();
                String email = model.getEmail();
                int id = model.getUserid();

                ((ViewHolder1)holder).setData(name, email, id);
                ((ViewHolder1)holder).parent.setOnClickListener(new View.OnClickListener() {
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
                                Model editedModel = new Model(1, newName, newEmail, id);
                                documentReference.set(editedModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Intent intent = new Intent(context, MainActivity.class);
//                                        context.startActivity(intent);
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

                ((ViewHolder1)holder).parent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Query query = fs.collection("table1").whereEqualTo("userid", id);
                        Log.d("sagrgarUserID", id + "");
                        FirebaseDataDeleteHelper.deleteData(context, query);
                        return false;
                    }
                });
                break;

            case TABLE2:
                String name2 = model.getName();
                String address = model.getAddress();
                int id2 = model.getTable2Id();
                ((ViewHolder2)holder).setData(name2, address, id2);
                ((ViewHolder2)holder).parent2.setOnClickListener(new View.OnClickListener() {
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
                                Model editedModel = new Model(2, newName, id2, newAddress);
                                documentReference.set(editedModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Intent intent = new Intent(context, MainActivity.class);
//                                        context.startActivity(intent);
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
                ((ViewHolder2)holder).parent2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Query query = fs.collection("table2").whereEqualTo("table2Id", id2);
                        Log.d("sagrgarID", id2+ "");
                        FirebaseDataDeleteHelper.deleteData(context, query);
                        return false;
                    }
                });

                break;

            default:
                return;

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
                return TABLE1;
            case 2:
                return TABLE2;
            default:
                return -1;
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{
        private TextView name_tv, id_tv, email_tv;
        private CardView parent;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
            id_tv = itemView.findViewById(R.id.id_tv);
            email_tv = itemView.findViewById(R.id.email_tv);
            parent = itemView.findViewById(R.id.parent);
        }

        private void setData(String name, String email, int id) {
            name_tv.setText(name);
            email_tv.setText(email);
            id_tv.setText(String.valueOf(id));
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{
        private TextView name2_tv, address_tv, id2_tv;
        private CardView parent2;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            name2_tv = itemView.findViewById(R.id.name2_tv);
            id2_tv = itemView.findViewById(R.id.id2_tv);
            address_tv = itemView.findViewById(R.id.address_tv);
            parent2 = itemView.findViewById(R.id.parent2);

        }
        private void setData(String name2, String address, int id2) {
            name2_tv.setText(name2);
            address_tv.setText(address);
            id2_tv.setText(String.valueOf(id2));
        }

    }

//    public void deleteData(Query query) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Delete Item");
//        builder.setMessage("Are you sure?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                                documentSnapshot.getReference().delete();
//                            }
//                        }
//                    }
//                });
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.create().show();
//    }
}
