package com.sample.android.gitcon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.sample.android.gitcon.R;
import com.sample.android.gitcon.models.Repository;
import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.picasso.CircleTransform;
import com.sample.android.gitcon.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepositoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // constants
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_INFO = 2;
    private static final int TYPE_REPOSITORY = 3;

    // variables
    private Context mContext;
    private Picasso mPicasso;
    private AUser mUser;
    private FollowingFollowersClickListener mCallback;
    private List<Repository> mRepositories;

    // constructor
    public RepositoriesAdapter(
            Context context,
            Picasso picasso,
            AUser user,
            FollowingFollowersClickListener callback) {
        this.mContext = context;
        this.mPicasso = picasso;
        this.mUser = user;
        this.mCallback = callback;

        initData();
    }

    // methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER: {
                return new HeaderHolder(
                        LayoutInflater
                                .from(mContext)
                                .inflate(R.layout.item_header, parent, false));
            }

            case TYPE_INFO: {
                return new InfoHolder(
                        LayoutInflater
                                .from(mContext)
                                .inflate(R.layout.item_info, parent, false));
            }

            case TYPE_REPOSITORY: {
                return new RepositoriesHolder(
                        LayoutInflater
                                .from(mContext)
                                .inflate(R.layout.item_repository, parent, false));
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_REPOSITORY: {
                RepositoriesHolder hldr = (RepositoriesHolder) holder;
                Repository repository = mRepositories.get(position);

                hldr.tvName.setText(Util.isStringNotNull(repository.getName()) ?
                        repository.getName() : "");
                hldr.tvDescription.setText(Util.isStringNotNull(repository.getDescription()) ?
                        repository.getDescription() : "");
                hldr.tvForksCount.setText(String.valueOf(repository.getForksCount()));
                hldr.tvWatchersCount.setText(String.valueOf(repository.getWatchersCount()));
                break;
            }

            case TYPE_INFO: {
                InfoHolder hldr = (InfoHolder) holder;
                Repository repository = mRepositories.get(position);

                hldr.tvInfo.setText(repository.getDescription());

                break;
            }

            case TYPE_HEADER: {
                HeaderHolder hldr = (HeaderHolder) holder;

                hldr.btnFollowers.setText(mContext.getString(R.string.tv_followers_count,
                        mUser != null ? mUser.getFollowers() : 0));
                hldr.btnFollowing.setText(mContext.getString(R.string.tv_following_count,
                        mUser != null ? mUser.getFollowing() : 0));

                if (mUser != null && Util.isStringNotNull(mUser.getLocation())) {
                    hldr.tvLocation.setVisibility(View.VISIBLE);
                    hldr.tvLocation.setText(mContext.getString(R.string.tv_location, mUser.getLocation()));
                } else {
                    hldr.tvLocation.setVisibility(View.GONE);
                }

                if (mUser != null && Util.isStringNotNull(mUser.getBio())) {
                    hldr.tvBio.setVisibility(View.VISIBLE);
                    hldr.tvBio.setText(mContext.getString(R.string.tv_bio, mUser.getBio()));
                } else {
                    hldr.tvBio.setVisibility(View.GONE);
                }

                if (mUser != null && Util.isStringNotNull(mUser.getAvatarUrl())) {
                    mPicasso
                            .load(mUser.getAvatarUrl())
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

                break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0: {
                return TYPE_HEADER;
            }

            case 1: {
                return TYPE_INFO;
            }

            default: {
                return TYPE_REPOSITORY;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mRepositories != null ? mRepositories.size() : 0;
    }

    public void addData(List<Repository> repositories, boolean notify) {
        if (mRepositories == null) {
            mRepositories = new ArrayList<>();
        }

        mRepositories.addAll(repositories);

        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void addInfo(Repository repository, boolean notify) {
        mRepositories.add(repository);

        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void updateUser(AUser user, boolean notify) {
        this.mUser = user;

        if (notify) {
            notifyItemChanged(0);
        }
    }

    private void initData() {
        mRepositories = new ArrayList<>();
        // header item
        mRepositories.add(new Repository());
    }

    // inner classes
    public class RepositoriesHolder extends RecyclerView.ViewHolder {

        // UI
        @Bind(R.id.tv_item_repository_name)
        TextView tvName;
        @Bind(R.id.tv_item_repository_description)
        TextView tvDescription;
        @Bind(R.id.tv_item_repository_forks_count)
        TextView tvForksCount;
        @Bind(R.id.tv_item_repository_watchers_count)
        TextView tvWatchersCount;

        // constructor
        public RepositoriesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class InfoHolder extends RecyclerView.ViewHolder {

        // UI
        @Bind(R.id.tv_item_info)
        TextView tvInfo;

        // constructor
        public InfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // UI
        @Bind(R.id.btn_item_header_followers)
        Button btnFollowers;
        @Bind(R.id.btn_item_header_following)
        Button btnFollowing;
        @Bind(R.id.iv_item_header_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_item_header_location)
        TextView tvLocation;
        @Bind(R.id.tv_item_header_bio)
        TextView tvBio;

        // constructor
        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initListeners();
        }

        private void initListeners() {
            btnFollowers.setOnClickListener(this);
            btnFollowing.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_item_header_followers: {
                    if (mCallback != null) {
                        mCallback.onFollowersClick();
                    }

                    break;
                }

                case R.id.btn_item_header_following: {
                    if (mCallback != null) {
                        mCallback.onFollowingClick();
                    }

                    break;
                }
            }
        }
    }

    // interfaces
    public interface FollowingFollowersClickListener {
        void onFollowersClick();
        void onFollowingClick();
    }
}
