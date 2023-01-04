package com.iamnotgod.slidetoon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iamnotgod.slidetoon.ChapterActivity;
import com.iamnotgod.slidetoon.Interface.IComicItemListener;
import com.iamnotgod.slidetoon.Model.Comic;
import com.iamnotgod.slidetoon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    public static Comic comicSelected;

    Context context;
    List<Comic> comics;
    LayoutInflater inflater;

    public ComicAdapter(Context context, List<Comic> comics) {
        this.context = context;
        this.comics = comics;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.comic_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Picasso.get().load(comics.get(i).Image).into(holder.comicThumb);
        holder.comicTitle.setText(comics.get(i).Name);

        holder.setComicItemListener(new IComicItemListener() {
            @Override
            public void onClick(View view, int position) {
                comicSelected = comics.get(position);
                Intent intent = (new Intent(context, ChapterActivity.class));
                intent.putExtra("name", comics.get(position).Name);
                intent.putExtra("count", comics.get(position).Chapters.size());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView comicThumb;
        TextView comicTitle;

        IComicItemListener comicItemListener;

        public void setComicItemListener(IComicItemListener comicItemListener) {
            this.comicItemListener = comicItemListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            comicThumb = (ImageView) itemView.findViewById(R.id.comicThumb);
            comicTitle = (TextView) itemView.findViewById(R.id.comicTitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            comicItemListener.onClick(view, getAdapterPosition());

        }
    }
}
