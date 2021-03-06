package org.mozilla.firefoxlitedownloaderlibrary;

import android.content.Context;

import  org.mozilla.firefoxlitedownloaderlibrary.core.Core;
import org.mozilla.firefoxlitedownloaderlibrary.internal.ComponentHolder;
import org.mozilla.firefoxlitedownloaderlibrary.internal.DownloadRequestQueue;
import org.mozilla.firefoxlitedownloaderlibrary.request.DownloadRequestBuilder;
import org.mozilla.firefoxlitedownloaderlibrary.utils.Utils;

import androidx.lifecycle.LifecycleOwner;


public class MozzDownloader {
    private  static Context contexts;


    private MozzDownloader() {
    }


    public  void initialize(Context context) {
        contexts=context;
        initialize(context, MozzDownloaderConfig.newBuilder().build());
    }


    public static void initialize(Context context, MozzDownloaderConfig config) {
        contexts=context;
        ComponentHolder.getInstance().init(context, config);
        DownloadRequestQueue.initialize(context );
    }

    /**
     * Method to make download request
     *
     * @param url      The url on which request is to be made
     * @param dirPath  The directory path on which file is to be saved
     * @param fileName The file name with which file is to be saved
     * @return the DownloadRequestBuilder
     */
    public static DownloadRequestBuilder download(Context context,String url, String dirPath, String fileName) {
        contexts=context;
        return new DownloadRequestBuilder(contexts,url, dirPath, fileName);
    }

    /**
     * Method to pause request with the given downloadId
     *
     * @param downloadId The downloadId with which request is to be paused
     */
    public static void pause(int downloadId) {
        DownloadRequestQueue.getInstance(contexts).pause(downloadId);
    }

    /**
     * Method to resume request with the given downloadId
     *
     * @param downloadId The downloadId with which request is to be resumed
     */
    public static void resume(int downloadId) {
        DownloadRequestQueue.getInstance(contexts).resume(downloadId,(LifecycleOwner) contexts);
    }

    /**
     * Method to cancel request with the given downloadId
     *
     * @param downloadId The downloadId with which request is to be cancelled
     */
    public static void cancel(int downloadId) {
        DownloadRequestQueue.getInstance(contexts).cancel(downloadId);
    }

    /**
     * Method to cancel requests with the given tag
     *
     * @param tag The tag with which requests are to be cancelled
     */
    public static void cancel(Object tag) {
        DownloadRequestQueue.getInstance(contexts).cancel(tag);
    }

    /**
     * Method to cancel all requests
     */
    public static void cancelAll() {
        DownloadRequestQueue.getInstance(contexts).cancelAll();
    }

    /**
     * Method to check the request with the given downloadId is running or not
     *
     * @param downloadId The downloadId with which request status is to be checked
     * @return the running status
     */
    public static Status getStatus(int downloadId) {
        return DownloadRequestQueue.getInstance(contexts).getStatus(downloadId);
    }

    /**
     * Method to clean up temporary resumed files which is older than the given day
     *
     * @param days the days
     */
    public static void cleanUp(int days) {
        Utils.deleteUnwantedModelsAndTempFiles(days);
    }

    /**
     * Shuts PRDownloader down
     */
    public static void shutDown() {
        Core.shutDown();
    }


}
