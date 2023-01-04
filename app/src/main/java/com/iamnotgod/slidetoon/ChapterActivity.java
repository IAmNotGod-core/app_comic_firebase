package com.iamnotgod.slidetoon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iamnotgod.slidetoon.Adapter.ChapterAdapter;
import com.iamnotgod.slidetoon.Common.Common;
import com.iamnotgod.slidetoon.Interface.IChapterLoadDone;
import com.iamnotgod.slidetoon.Model.Chapter;
import com.iamnotgod.slidetoon.Model.Comic;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity implements IChapterLoadDone {

    DatabaseReference chapters;

    IChapterLoadDone chapterListener;

    RecyclerView recChapter;
    TextView countChapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        chapters = FirebaseDatabase.getInstance().getReference("Comic").child("Chapters");

        chapterListener = this;

        countChapter = (TextView) findViewById(R.id.countChapter);
        recChapter = (RecyclerView) findViewById(R.id.recChapter);
        recChapter.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recChapter.setLayoutManager(layoutManager);
        recChapter.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_navigate_before_24);
        toolbar.setNavigationOnClickListener((view -> { finish(); }));

        fetchChapter(Common.comicSelected);

    }

    private void fetchChapter(Comic comicSelected) {
        chapters.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Chapter> chapterList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chapterSnapshot:dataSnapshot.getChildren())
                {
                    Chapter chapter = chapterSnapshot.getValue(Chapter.class);
                    chapterList.add(chapter);
                }

                chapterListener.onChapterLoad(chapterList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onChapterLoad(List<Chapter> chapterList) {
        countChapter.setText(new StringBuilder("CHAPTER (").append(getIntent().getIntExtra("count", 0)).append(")"));

        recChapter.setAdapter(new ChapterAdapter(getBaseContext(), chapterList));
    }
}