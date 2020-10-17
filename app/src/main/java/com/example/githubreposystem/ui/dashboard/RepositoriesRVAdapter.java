package com.example.githubreposystem.ui.dashboard;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.githubreposystem.R;
import com.example.githubreposystem.model.Repository;

import java.util.List;

public class RepositoriesRVAdapter extends RecyclerView.Adapter<RepositoriesRVAdapter.MyViewHolder> {
    private final Context mContext;
    private final RVAdapterInterface mListener;
    private List<Repository> mRepositories;

    public void addItems(final List<Repository> repositories) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = mRepositories.size();
                mRepositories.addAll(repositories);
                notifyItemInserted(count);
            }
        }, 100);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvURL, tvOwner;
        public ImageView ivAvatar;
        public EditText etComment;

        public MyViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvOwner = v.findViewById(R.id.tv_owner);
            tvURL = v.findViewById(R.id.tv_url);
            ivAvatar = v.findViewById(R.id.iv_avatar);
            etComment = v.findViewById(R.id.etComment);
        }
    }

    public RepositoriesRVAdapter(List<Repository> myDataset, Context context, RVAdapterInterface listener) {
        mRepositories = myDataset;
        mContext = context;
        mListener = listener;
    }

    @Override
    public RepositoriesRVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvName.setText(mRepositories.get(position).getName());
        holder.tvOwner.setText(mRepositories.get(position).getOwner().getLogin());
        holder.tvURL.setText(mRepositories.get(position).getUrl());
        holder.etComment.setText(mRepositories.get(position).getComment());
        Glide.with(mContext)
                .load(mRepositories.get(position).getOwner().getAvatar_url())
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivAvatar);
        if (null != mListener) {
            if (position == mRepositories.size() - 1) {
                mListener.onFinalItemReached(position);
            }
            holder.itemView.setTag(mRepositories.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClickItem((Repository) view.getTag());
                }
            });
        }
        holder.etComment.setTag(position);
        holder.etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!holder.etComment.getText().toString().equals(""))
                    mRepositories.get(position).setComment(holder.etComment.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }
}