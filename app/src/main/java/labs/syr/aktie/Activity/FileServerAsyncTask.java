/*
package labs.syr.aktie.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

*/
/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: FileServerAsyncTask.java
 * Description: Written as a service to be used to transfer content(files)
 *
 *//*


public class FileServerAsyncTask extends AsyncTask {

    private Context context;
    private TextView statusText;

    public FileServerAsyncTask(Context context, View statusText) {
        this.context = context;
        this.statusText = (TextView) statusText;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {

            */
/**
             * Create a server socket and wait for client connections. This
             * call blocks until a connection is accepted from a client
             *//*

            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8888);

            } catch (IOException e) {
                Log.e("FileServerASyncTask", "Socket Createion Error" + e);
            }

            Socket client = serverSocket.accept();
            */
/**
             * If this code is reached, a client has connected and transferred data
             * Save the input stream from the client as a JPEG file
             *//*

            final File f = new File(Environment.getExternalStorageDirectory() + "/"
                    + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis()
                    + ".jpg");

            File dirs = new File(f.getParent());
            if (!dirs.exists())
                dirs.mkdirs();
            f.createNewFile();
            InputStream inputstream = client.getInputStream();
            copyFile(inputstream, new FileOutputStream(f));
            serverSocket.close();
            return f.getAbsolutePath();
        } catch (IOException e) {
            Log.e("FileServerAsyncTask", e.getMessage());
            return null;
        }
    }

    private void copyFile(InputStream inputstream, FileOutputStream fileOutputStream) {
        Context context = this.context;
        String host;
        int port;
        int len;
        Socket socket = new Socket();
        byte buf[]  = new byte[1024];
        try {
            */
/**
             * Create a client socket with the host,
             * port, and timeout information.
             *//*

            socket.bind(null);
            socket.connect((new InetSocketAddress(host, port)), 500);

            */
/**
             * Create a byte stream from a JPEG file and pipe it to the output stream
             * of the socket. This data will be retrieved by the server device.
             *//*

            OutputStream outputStream = socket.getOutputStream();
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            inputStream = cr.openInputStream(Uri.parse("path/to/picture.jpg"));
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            //catch logic
        } catch (IOException e) {
            //catch logic
        }

*/
/**
 * Clean up any open sockets when done
 * transferring or if an exception occurred.
 *//*

        finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        //catch logic
                    }
                }
            }
        }
    }

    */
/**
     * Start activity that can handle the JPEG image
     *//*

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            statusText.setText("File copied - " + result);
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + result), "image*/
/*");
            context.startActivity(intent);
        }
    }

}
*/
