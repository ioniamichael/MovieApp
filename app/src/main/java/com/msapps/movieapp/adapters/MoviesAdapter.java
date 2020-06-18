package com.msapps.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.msapps.movieapp.R;
import com.msapps.movieapp.model.MoviesResponse;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context mContext;
    private List<MoviesResponse> moviesResponseList;

    public MoviesAdapter(Context mContext, List<MoviesResponse> moviesResponseList) {
        this.mContext = mContext;
        this.moviesResponseList = moviesResponseList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_view, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        MoviesResponse response = moviesResponseList.get(position);
        holder.mRootView.setTag(position);

        Glide.with(mContext)
                .load(response.getImage())
                .into(holder.mIVMovieImage);

        holder.mTVMovieTitle.setText(response.getTitle());
        holder.mTVReleaseYear.setText(String.valueOf(response.getReleaseYear()));

    }

    @Override
    public int getItemCount() {
        return moviesResponseList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        private CardView mRootView;
        private ImageView mIVMovieImage;
        private TextView mTVMovieTitle, mTVReleaseYear;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            mIVMovieImage = itemView.findViewById(R.id.ivMovieImage);
            mTVMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            mRootView = itemView.findViewById(R.id.rootView);
            mTVReleaseYear = itemView.findViewById(R.id.tvReleaseYear);
        }
    }

}
