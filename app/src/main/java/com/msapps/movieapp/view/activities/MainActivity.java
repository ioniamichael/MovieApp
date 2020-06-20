package com.msapps.movieapp.view.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.msapps.movieapp.R;
import com.msapps.movieapp.view.fragments.MoviesListFragment;
import com.msapps.movieapp.view.fragments.QRCodeScannerFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, QRCodeScannerFragment.OnQACodeFragmentStateChangedListener {

    private ImageButton mIBScanQRCode;
    private FrameLayout qr_screen_container,fragment_container;

    private static final int REQUEST_CAMERA_PERMISSION = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        fragment_container = findViewById(R.id.fragment_container);
        mIBScanQRCode = findViewById(R.id.ibScanQRCode);
        qr_screen_container = findViewById(R.id.qr_screen_container);
        mIBScanQRCode.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openMoviesListFragment();
    }

    public void openMoviesListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.fragment_container, new MoviesListFragment())
                .commit();
    }

    @Override
    public void onQACodeFragmentResumed() {
        mIBScanQRCode.setVisibility(View.GONE);
        qr_screen_container.setActivated(true);
    }

    @Override
    public void onQACodeFragmentDestroyed() {
        qr_screen_container.setActivated(false);
        fragment_container.animate().alpha(1f).setDuration(700);
        mIBScanQRCode.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            openQRScannerFragment();
        }
    }

    private void openQRScannerFragment() {
        fragment_container.animate().alpha(0.2f).setDuration(500);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .addToBackStack(null)
                .add(R.id.qr_screen_container, new QRCodeScannerFragment())
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openQRScannerFragment();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Camera permission")
                        .setMessage("We need your permission to use camera.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }
}