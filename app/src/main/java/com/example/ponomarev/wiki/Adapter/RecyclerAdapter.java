package com.example.ponomarev.wiki.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ponomarev.wiki.JsonData.Result;
import com.example.ponomarev.wiki.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

    private static RecyclerAdapterListener clickListener;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading, noMore;
    private  List<Result> results;
    Context context;
    int layout=R.layout.recycler_layout;



    public RecyclerAdapter(Context context, List<Result> results){
        this.context=context;
        this.results = results;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ItemViewHolder pvh = new ItemViewHolder(v);
        return pvh;
    }



    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.textTitle.setText(String.valueOf(results.get(position).getName()));
        Picasso.get().load(results.get(position).getImage()).into(holder.itemImage);
        if(onLoadMoreListener != null && !isLoading && !noMore && holder.getAdapterPosition() == getItemCount() - 5) {
            isLoading = true;
            onLoadMoreListener.onLoadMore();
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    public void endLoading() {
        this.isLoading = false;
    }

    public void setNoMore(boolean noMore) {
        this.noMore = noMore;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textTitle;
        CardView cv;

        ImageView itemImage;
        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            cv.setOnClickListener(this);
            textTitle = (TextView)itemView.findViewById(R.id.titleTextView);

            itemImage = (ImageView)itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

    }
    public void setOnItemClickListener(RecyclerAdapterListener clickListener) {
        RecyclerAdapter.clickListener =  clickListener;
    }

    public interface RecyclerAdapterListener {
        void onItemClick(int position, View v);
    }

}
