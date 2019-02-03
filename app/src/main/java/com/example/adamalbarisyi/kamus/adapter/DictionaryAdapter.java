package com.example.adamalbarisyi.kamus.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adamalbarisyi.kamus.DetailActivity;
import com.example.adamalbarisyi.kamus.R;
import com.example.adamalbarisyi.kamus.model.DictionaryModel;

import java.util.ArrayList;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryHolder> {

    private ArrayList<DictionaryModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public DictionaryAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public DictionaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary_row, parent, false);
        return new DictionaryHolder(view);
    }

    public void addItem(ArrayList<DictionaryModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(DictionaryHolder holder, final int position) {
        holder.imgText.setImageResource(R.drawable.ic_text);
        holder.tvWord.setText(mData.get(position).getWord());
        holder.lnRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.WORDS, mData.get(position).getWord());
                intent.putExtra(DetailActivity.TRANSLATE, mData.get(position).getTranslation());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class DictionaryHolder extends RecyclerView.ViewHolder {
        private ImageView imgText;
        private TextView tvWord;
        private LinearLayout lnRow;

        public DictionaryHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_text);
            imgText = itemView.findViewById(R.id.img_text);
            lnRow = itemView.findViewById(R.id.ln_row);
        }
    }
}
