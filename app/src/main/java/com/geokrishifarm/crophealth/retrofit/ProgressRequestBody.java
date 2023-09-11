package com.geokrishifarm.crophealth.retrofit;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;
    private String content_type;

    public ProgressRequestBody(final File file, String content_type, final UploadCallbacks listener) {
        this.content_type = content_type;
//        Timber.v(content_type);
        mFile = file;
        mListener = listener;
    }

    @Override
    // public MediaType contentType() {
//        return MediaType.parse(content_type + "/*");
//    }
    public MediaType contentType() {
        return MediaType.parse(content_type);
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded, fileLength));
            }
        } finally {
            in.close();
        }
    }

    public enum ProgressStatus {
        FINISHED, IN_PROGRESS, ERROR
    }

    public interface UploadCallbacks {
        // void onProgressUpdate(int percentage);
//        void onError();
//
//        void onFinish();

        void onProgressUpdate(double individualUploadPercent, Double individualFileUploadedInMb, Double individualFileTotalSizeInMb, ProgressStatus status);
    }

    private class ProgressUpdater implements Runnable {
        private double mUploaded;
        private double mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {

            if (mListener == null) {
                return;
            }

            if (mUploaded == mTotal) {
                mListener.onProgressUpdate((double) (100 * mUploaded / mTotal), (mUploaded / (1024 * 1024)), (mTotal / (1024 * 1024)), ProgressStatus.FINISHED);
            } else {
                Log.v("test", "uploadedSizeUntillNow/totalsize " + (mUploaded / (1024 * 1024)) + " MB /" + (mTotal / (1024 * 1024)) + " mb");
                mListener.onProgressUpdate((double) (100 * mUploaded / mTotal), (mUploaded / (1024 * 1024)), (mTotal / (1024 * 1024)), ProgressStatus.IN_PROGRESS);
            }
        }

    }


}
