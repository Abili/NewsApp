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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        holder.author.setText("By " + infoArrayAdapter.get(position).getAuthor());

        /*
        get the date from the News class
         */
        String dateObject = infoArrayAdapter.get(position).getDate();

        String formattedDate = null;
        try {

            //the String casted from the News class is passed intot he formatDate() method so tht it can get converted
            formattedDate = formatDate(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //set the converted time into the TextView
        holder.time.setText(formattedDate);

        /*
        on clicking any item on the list, an intent is started that sends us to the webpage
        containing that news body
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve the url using the News class from the JSON
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

        //obtain the resouce IDs for the Views using butter Knife.
        @BindView(R.id.heading)
        TextView title;

        @BindView(R.id.category)
        TextView section;

        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.author)
        TextView author;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //convert the String date from the JSON to the actual date
    private String formatDate(String dateObject) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        // since its a String use parse to convert it into a date
        Date dateTime = dateFormat.parse(dateObject);

        //check to see ig the dates are actually beng converted, this mainly is to help with the debugging process
        System.out.println("Current Date Time : " + dateTime);
        return String.valueOf(dateTime);
    }

}



