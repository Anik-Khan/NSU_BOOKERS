package com.anix.bookers.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.anix.bookers.BookListActivity;
import com.anix.bookers.BuyBookActivity;
import com.anix.bookers.Model.Book;
import com.anix.bookers.R;

import java.util.List;

/**
 * Created by Anix on 4/7/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    List<Book> bList;
    int index;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView IVBookPic;
        public TextView TVBookName, TVAuthorName, TVAmount ;
        public final Context context;
        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            context = itemView.getContext();
             IVBookPic = view.findViewById(R.id.IVbookpic);
             TVBookName = view.findViewById(R.id.TVbookname);
             TVAuthorName = view.findViewById(R.id.TVauthorname);
             TVAmount = view.findViewById(R.id.TVamount);
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            for(int i = 0; i<bList.size(); i++){
                if(i==getLayoutPosition()){
                    Book b = bList.get(i);
                    intent = new Intent(context,BuyBookActivity.class);
                    intent.putExtra("Name",b.getName());
                    intent.putExtra("Author",b.getAuthor());
                    intent.putExtra("Price",b.getPrice());
                    intent.putExtra("i",Integer.toString(i));
                    break;
                }
            }
            context.startActivity(intent);

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
        holder.TVAuthorName .setText(book.getAuthor());
        holder.TVAmount.setText(book.getPrice());
    }

    @Override
    public int getItemCount() {
        return bList.size();
    }

}
