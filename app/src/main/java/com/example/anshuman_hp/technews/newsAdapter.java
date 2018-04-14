package com.example.anshuman_hp.technews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

/**
 * Created by Anshuman-HP on 27-03-2017.
 */

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {
Context context;
    List<articles> data;
    Random rnd = new Random();

    int color ;
    newsAdapter(Context context,List<articles> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        color=Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Log.v("hhh", color+"");
        holder.backg.setColorFilter(-12567980);
        holder.author.setText(holder.author.getText()+data.get(position).getAuthor());
        holder.author.setShadowLayer(2,1,1,Color.BLACK);
        holder.published.setText(holder.published.getText()+data.get(position).getPublishedAt());
        holder.published.setShadowLayer(2,1,1,Color.BLACK);
        holder.title.setText(data.get(position).getTitle());
        holder.title.setShadowLayer(3,2,2,Color.BLACK);
        holder.decript.setText(data.get(position).getDescription());
        holder.decript.setShadowLayer(2,1,1,Color.BLACK);
        Glide.with(context).load(data.get(position).getUrlToImage()).centerCrop().into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photo,backg;
        TextView decript,title,published,author;
        public ViewHolder(View itemView) {
            super(itemView);
            decript=(TextView)itemView.findViewById(R.id.description);
            title=(TextView)itemView.findViewById(R.id.title);
            published=(TextView)itemView.findViewById(R.id.published);
            author=(TextView)itemView.findViewById(R.id.author);
            photo=(ImageView)itemView.findViewById(R.id.urltoimage);
            backg=(ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}
