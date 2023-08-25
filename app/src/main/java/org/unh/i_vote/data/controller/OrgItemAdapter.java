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

import java.util.List;

public class OrgItemAdapter extends RecyclerView.Adapter<OrgItemAdapter.ItemViewHolder> {
    private static ClickListener clickListener;


    private final List<ItemModel> itemModelList;

    public OrgItemAdapter(List<ItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_org_recyview, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel itemModel = itemModelList.get(position);
        holder.itemName.setText(itemModel.getTitle());
        holder.itemHint.setText(itemModel.getSubTitle());
        holder.itemAbout.setText(itemModel.getAbout());
        if(itemModel.getLogo() != null) holder.logoImage.setImageResource(itemModel.getLogo());
        //holder.logoImage.setImageResource();
    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView itemName;
        TextView itemHint;
        TextView itemAbout;
        ImageView logoImage;
        CardView itemCard;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemName = itemView.findViewById(R.id.orgTitle);
            itemHint = itemView.findViewById(R.id.orgSubTitle);
            itemAbout = itemView.findViewById(R.id.orgAbout);
            logoImage = itemView.findViewById(R.id.orgItemImage);
            itemCard = itemView.findViewById(R.id.orgItemCard);
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
        OrgItemAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}




