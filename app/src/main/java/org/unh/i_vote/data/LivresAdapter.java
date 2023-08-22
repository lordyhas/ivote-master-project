package org.unh.i_vote.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.unh.i_vote.R;

import java.util.List;

public class LivresAdapter extends RecyclerView.Adapter<LivresAdapter.LivresVH> {
    private static ClickListener clickListener;


    private List<LivresModel> listRecyLivre;

    public LivresAdapter(List<LivresModel> listRecyLivre) {
        this.listRecyLivre = listRecyLivre;
    }

    @NonNull
    @Override
    public LivresVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_livres_recyview, parent, false);
        LivresVH livresVH = new LivresVH(view);
        return livresVH;
    }

    @Override
    public void onBindViewHolder(@NonNull LivresVH holder, int position) {
        LivresModel livresModel = listRecyLivre.get(position);
        holder.itemName.setText(livresModel.getTitre());
        holder.ItemHint.setText(livresModel.getPage());
        holder.logoImage.setImageResource(livresModel.getLogo());
        //holder.logoImage.setImageResource();


    }

    @Override
    public int getItemCount() {
        return listRecyLivre.size();
    }

    public class LivresVH extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView itemName;
        TextView ItemHint;
        ImageView logoImage;

        CardView itemCard;
        public LivresVH(@NonNull View itemView) {
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
        LivresAdapter.clickListener = clickListener;
    }

    protected interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
