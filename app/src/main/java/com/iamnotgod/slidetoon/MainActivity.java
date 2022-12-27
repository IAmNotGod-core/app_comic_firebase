package com.iamnotgod.slidetoon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iamnotgod.slidetoon.Adapter.ComicAdapter;
import com.iamnotgod.slidetoon.Interface.IComicLoadDone;
import com.iamnotgod.slidetoon.Model.Comic;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IComicLoadDone {

    DatabaseReference comics;

    IComicLoadDone comicListener;

    TextView countComic;
    RecyclerView recComic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comics = FirebaseDatabase.getInstance().getReference("Comic");

        comicListener = this;

        loadComic();

        countComic = (TextView) findViewById(R.id.countComic);

        recComic = (RecyclerView) findViewById(R.id.recComic);
        recComic.setHasFixedSize(true);
        recComic.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadComic() {
        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Comic> comicList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot comicSnapshot:dataSnapshot.getChildren())
                {
                    Comic comic = comicSnapshot.getValue(Comic.class);
                    comicList.add(comic);
                }

                comicListener.onComicLoadDone(comicList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onComicLoadDone(List<Comic> comics) {
        countComic.setText(new StringBuilder("New Comic (").append(comics.size()).append(")"));

        recComic.setAdapter(new ComicAdapter(getBaseContext(), comics));
    }
}