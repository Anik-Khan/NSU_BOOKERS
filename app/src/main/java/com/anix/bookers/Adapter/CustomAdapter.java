package com.anix.bookers.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.anix.bookers.Model.Book;
import com.anix.bookers.R;

import java.util.List;

/**
 * Created by Anix on 4/7/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {


    List<Book> bList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView IVBookPic;
        public TextView TVBookName, TVAuthorName, TVAmount ;

        public MyViewHolder(View view) {
            super(view);
             IVBookPic = view.findViewById(R.id.IVbookpic);
             TVBookName = view.findViewById(R.id.TVbookname);
             TVAuthorName = view.findViewById(R.id.TVauthorname);
             TVAmount = view.findViewById(R.id.TVamount);
        }
    }

    public CustomAdapter(List<Book> List) {
        this.bList = List;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder, int position) {
        Book book = bList.get(position);
        holder.TVBookName.setText(book.getName());
        holder.IVBookPic.setBackgroundResource(book.getPicId());
        holder.TVAuthorName .setText(book.getAuthor());
        holder.TVAmount.setText(Integer.toString(book.getPrice()));
    }

    @Override
    public int getItemCount() {
        return bList.size();
    }

}
