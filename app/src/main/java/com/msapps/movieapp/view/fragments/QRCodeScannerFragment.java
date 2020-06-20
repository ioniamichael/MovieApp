package com.msapps.movieapp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.msapps.movieapp.R;
import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.utils.QRScanner;
import com.msapps.movieapp.viewmodel.MoviesViewModel;

import java.util.ArrayList;

public class QRCodeScannerFragment extends Fragment implements QRScanner.QRCodeInterface {

    private View view;
    private OnQACodeFragmentStateChangedListener iCallback;
    private MoviesViewModel mMoviesViewModel;
    private TextView mTVMovieTitle;
    private QRScanner qrScanner;
    private Snackbar snackbar;
    private ArrayList<String> errorMovies;


    public interface OnQACodeFragmentStateChangedListener {
        void onQACodeFragmentDestroyed();

        void onQACodeFragmentResumed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_qr_code_scanner, container, false);
        initViewModel();
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
        initArrayErrorMovies();
        qrScanner.initialiseDetectorsAndSources();
        iCallback.onQACodeFragmentResumed();
    }

    private void initArrayErrorMovies() {
        errorMovies = new ArrayList<>();
    }

    private void initViewModel() {
        mMoviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MoviesViewModel.class);
        qrScanner = new QRScanner(view, getContext(), getActivity(), this::onQRScanned);
    }


    private void initViews() {
        mTVMovieTitle = view.findViewById(R.id.tvMovieTitle);
    }

    @Override
    public void onQRScanned(MoviesResponse response) {
        if (mMoviesViewModel.getMovieByTitle(response.getTitle()) == null) {
            mMoviesViewModel.addMovie(response);
            snackbar = Snackbar.make(view, getResources().getString(R.string.movie_added, response.getTitle()), Snackbar.LENGTH_LONG);
            if (!snackbar.isShown()) {
                snackbar.show();
            }
        } else {
            snackbar = Snackbar.make(view, getResources().getString(R.string.movie_exist, response.getTitle()), Snackbar.LENGTH_LONG);
            if (!snackbar.isShown()) {
                snackbar.show();
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iCallback = (OnQACodeFragmentStateChangedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentStateChangedListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        qrScanner.getCameraSource().release();

    }

    @Override
    public void onDetach() {
        iCallback = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        iCallback.onQACodeFragmentDestroyed();
    }


}