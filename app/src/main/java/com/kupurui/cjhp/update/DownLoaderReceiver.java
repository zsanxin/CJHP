package com.kupurui.cjhp.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.kupurui.cjhp.config.UserManager;


/**
 * Created by admin on 2016/4/26.
 */
public class DownLoaderReceiver extends BroadcastReceiver {

    long id;

    @Override
    public void onReceive(Context context, Intent intent) {
        id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (UserManager.getAppid() == id) {
        String serviceString = Context.DOWNLOAD_SERVICE;
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(serviceString);

        DownloadManager.Query myDownloadQuery = new DownloadManager.Query();

        myDownloadQuery.setFilterById(id);
        Cursor myDownload = downloadManager.query(myDownloadQuery);
        if (myDownload.moveToFirst()) {
            int fileNameIdx =
                    myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
            int fileUriIdx =
                    myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

            String fileName = myDownload.getString(fileNameIdx);
            String fileUri = myDownload.getString(fileUriIdx);

            // TODO Do something with the file.
            Log.i("result", fileName + " : " + fileUri);

            //安装代码
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setDataAndType(Uri.parse("file://" + fileName), "application/vnd.android.package-archive");
            context.startActivity(install);
        }
        myDownload.close();
        }
    }

}
