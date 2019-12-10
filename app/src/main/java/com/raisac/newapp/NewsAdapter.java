package com.raisac.newapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<News> infoArrayAdapter;
    Context context;
    String dateObject;

    public NewsAdapter(ArrayList<News> infoArrayAdapter, Context context) {
        this.infoArrayAdapter = infoArrayAdapter;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, final int position) {

        holder.title.setText(infoArrayAdapter.get(position).getTitle());
        holder.section.setText(infoArrayAdapter.get(position).getSection());

        String dateObject = infoArrayAdapter.get(position).getDate();

        String formattedDate = String.valueOf(formatDate(dateObject));

        holder.time.setText(formattedDate);
        //holder.date.setText(formattedDate);
        holder.time.setText(formattedDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = infoArrayAdapter.get(position).getUrl();
                Intent intentUrl = new Intent(Intent.ACTION_VIEW);
                intentUrl.setData(Uri.parse(url));
                context.startActivity(intentUrl);
            }
        });
    }


    @Override
    public int getItemCount() {
        return infoArrayAdapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.heading)
        TextView title;

        @BindView(R.id.category)
        TextView section;

        @BindView(R.id.time)
        TextView time;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String formatDate(String dateObject) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        try {
            return String.valueOf(myFormat.parse(dateObject));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObject;
    }

    }



