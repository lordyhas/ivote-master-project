package org.unh.i_vote.data.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.unh.i_vote.R;
import org.unh.i_vote.data.model.ItemModel;
import org.unh.i_vote.data.model.ItemVoteModel;

import java.util.List;

public class VoteItemAdapter extends RecyclerView.Adapter<VoteItemAdapter.ItemViewHolder> {
    private static ClickListener clickListener;


    private final List<ItemVoteModel> itemVoteList;

    public VoteItemAdapter(List<ItemVoteModel> itemVoteList) {
        this.itemVoteList = itemVoteList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vote_recyview, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemVoteModel itemModel = itemVoteList.get(position);
        holder.itemName.setText(itemModel.getTitle());
        holder.ItemHint.setText(itemModel.getOrgName());
        if(itemModel.getLogo() != null) holder.logoImage.setImageResource(itemModel.getLogo());
        //holder.logoImage.setImageResource();


    }

    @Override
    public int getItemCount() {
        return itemVoteList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView itemName;
        TextView ItemHint;
        ImageView logoImage;

        CardView itemCard;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemName = itemView.findViewById(R.id.nomLivre);
            ItemHint = itemView.findViewById(R.id.pageLivre);
            logoImage = itemView.findViewById(R.id.voteItemImage);
            itemCard = itemView.findViewById(R.id.voteItemCard);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);

        }
        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        VoteItemAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
