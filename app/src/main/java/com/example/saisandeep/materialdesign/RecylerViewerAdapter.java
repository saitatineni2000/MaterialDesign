package com.example.saisandeep.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by saisandeep on 4/23/2015.
 */
public class RecylerViewerAdapter extends RecyclerView.Adapter<RecylerViewerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    MyViewHolder holder;
    Context context;

    ClickListener clickListener;

    RecylerViewerAdapter(Context context, List<Information> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;

    }

    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);

        Log.d("Sandeep", "onCreateViewHolder called");

        holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        Information current = data.get(i);

        Log.d("Sandeep", "onBindViewHolder called" + i);
        holder.textView.setText(current.title);
        holder.imageView.setImageResource(current.iconID);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text1);
            imageView = (ImageView) itemView.findViewById(R.id.image1);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context,"Item was clicked "+getPosition(),Toast.LENGTH_SHORT).show();
            //delete(getPosition());

            //onclick using adapter
            /*Intent i=new Intent(context,Activity2.class);
            context.startActivity(i);*/

            //onclick utilization using fragment for that we need to create an interface and the drawerfragment implent that interface.

            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
