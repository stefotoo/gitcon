package com.sample.android.gitcon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.android.gitcon.R;
import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.picasso.CircleTransform;
import com.sample.android.gitcon.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserListAdapter<T extends AUser> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // variables
    private Context mContext;
    private Picasso mPicasso;
    private UserClickListener mCallback;
    private List<T> mUsers;

    // constructor
    public UserListAdapter(
            Context context,
            Picasso picasso,
            UserClickListener callback) {
        this.mContext = context;
        this.mPicasso = picasso;
        this.mCallback = callback;
    }

    // methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(
                LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserHolder hldr = (UserHolder) holder;
        AUser user = mUsers.get(position);

        hldr.tvUsername.setText(user != null && Util.isStringNotNull(user.getLogin()) ?
                user.getLogin() : "");

        if (user != null && Util.isStringNotNull(user.getAvatarUrl())) {
            mPicasso
                    .load(user.getAvatarUrl())
                    .placeholder(R.drawable.github_logo)
                    .fit()
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(hldr.ivAvatar);
        } else {
            mPicasso
                    .load(R.drawable.github_logo)
                    .fit()
                    .centerCrop()
                    .transform(new CircleTransform())
                    .into(hldr.ivAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    public void addData(List<T> users, boolean notify) {
        if (mUsers == null) {
            mUsers = new ArrayList<>();
        }

        mUsers.addAll(users);

        if (notify) {
            notifyDataSetChanged();
        }
    }

    // inner classes
    public class UserHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // UI
        @Bind(R.id.iv_item_user_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_item_user_username)
        TextView tvUsername;

        // constructor
        public UserHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mCallback != null) {
                mCallback.onUserClick(mUsers.get(getAdapterPosition()), ivAvatar);
            }
        }
    }

    // interfaces
    public interface UserClickListener {
        void onUserClick(AUser user, View view);
    }
}
