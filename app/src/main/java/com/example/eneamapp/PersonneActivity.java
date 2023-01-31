package com.example.eneamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.example.eneamapp.*;

public class PersonneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personne);

        Intent intent = getIntent();
        String nom = intent.getStringExtra("NOM");

        TextView nomText = findViewById(R.id.nom);
        String text = nomText.getText().toString();
        nomText.setText(text + nom);

        List<String> prenoms = new ArrayList<>();
        PersonneCtrl personnes = new PersonneCtrl(this);

        ArrayList<Personne> personneArrayList = personnes.getAllPersonne();
        System.out.println(personneArrayList);

/*        ListView listView = findViewById(R.id.listView);

        final ArrayAdapter<Personne> adapter;
        adapter = new ArrayAdapter<Personne>(PersonneActivity.this, android.R.layout.simple_list_item_1, personneArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }
}