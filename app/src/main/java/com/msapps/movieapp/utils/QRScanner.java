package com.msapps.movieapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.msapps.movieapp.R;
import com.msapps.movieapp.model.MoviesResponse;

import java.io.IOException;

public class QRScanner {


    private View view;
    private Context mContext;
    private Activity mActivity;

    private QRCodeInterface iCallback;

    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    public QRScanner(View view, Context mContext, Activity mActivity, QRCodeInterface iCallback) {
        this.view = view;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.iCallback = iCallback;
        surfaceView = view.findViewById(R.id.surfaceView);
    }

    public void initialiseDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(mContext)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(mContext, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    Gson gson = new Gson();
                    MoviesResponse moviesResponse = gson.fromJson(barcodes.valueAt(0).displayValue, MoviesResponse.class);
                    iCallback.onQRScanned(moviesResponse);
                }
            }
        });
    }

    public interface QRCodeInterface {
        void onQRScanned(MoviesResponse response);
    }

    public CameraSource getCameraSource() {
        return cameraSource;
    }

}
