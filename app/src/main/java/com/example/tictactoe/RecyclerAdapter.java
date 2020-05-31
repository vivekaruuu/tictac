package com.example.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolders>
{
    private ArrayList<item> mItems=new ArrayList<>();
    public RecyclerAdapter(ArrayList<item> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        holder.name.setText(mItems.get(position).getName());
        holder.win.setText(String.valueOf(mItems.get(position).getWin()));
        holder.draw.setText(String.valueOf(mItems.get(position).getDraw()));
        holder.lose.setText(String.valueOf(mItems.get(position).getLoss()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder  {

        private TextView name,win,draw,lose;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.PlayerName);
            win=itemView.findViewById(R.id.wins);
            draw=itemView.findViewById(R.id.draw);
            lose=itemView.findViewById(R.id.losses);

        }

    }


}
