package com.example.offilinefirebasedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Model> userArrayList = new ArrayList<>();
    private FirebaseFirestore fs;

    private Button table1_btn, table2_btn, table3_btn, table4_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table1_btn = findViewById(R.id.table1_btn);
        table2_btn = findViewById(R.id.table2_btn);
        table3_btn = findViewById(R.id.table3_btn);
        table4_btn = findViewById(R.id.table4_btn);

        table1_btn.setOnClickListener(this);
        table2_btn.setOnClickListener(this);
        table3_btn.setOnClickListener(this);
        table4_btn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, userArrayList);

        fs = FirebaseFirestore.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.table1_btn:
                userArrayList.clear();
                firebaseData("table1", "userid");
                break;
            case R.id.table2_btn:
                userArrayList.clear();
                firebaseData("table2", "table2Id");
                break;
            case R.id.table3_btn:
                Toast.makeText(this, "table 3 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.table4_btn:
                Toast.makeText(this, "table 4 clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void firebaseData(String tableName, String idName) {
        recyclerView.setAdapter(null);
        fs.collection(tableName).orderBy(idName, Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        switch (documentChange.getType()) {
                            case ADDED:
//                                userArrayList.clear();
                                Model model = documentChange.getDocument().toObject(Model.class);
                                userArrayList.add(model);
                                recyclerViewAdapter.setModel(userArrayList);
                                break;

                            case MODIFIED:
                                Model model2 = documentChange.getDocument().toObject(Model.class);
                                for (int i = 0; i < userArrayList.size(); i++) {
                                    if (tableName == "table1") {
                                        if (model2.getUserid() == userArrayList.get(i).getUserid()) {
                                            userArrayList.set(i, model2);
                                        }
                                    } else if (tableName == "table2") {
                                        if (model2.getTable2Id() == userArrayList.get(i).getTable2Id()) {
                                            userArrayList.set(i, model2);
                                        }
                                    }

                                }
                                recyclerViewAdapter.setModel(userArrayList);
                                break;

                            case REMOVED:
                                Model model1 = documentChange.getDocument().toObject(Model.class);
                                for (int i = 0; i < userArrayList.size(); i++) {
                                    if (tableName == "table1") {
                                        if (model1.getUserid() == userArrayList.get(i).getUserid()) {
                                            userArrayList.remove(i);
                                        }
                                    } else if (tableName == "table2") {
                                        if (model1.getTable2Id() == userArrayList.get(i).getTable2Id()) {
                                            userArrayList.remove(i);
                                        }
                                    }
                                }
                                recyclerViewAdapter.setModel(userArrayList);
                                break;
                        }
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                }
            }
        });
    }
}