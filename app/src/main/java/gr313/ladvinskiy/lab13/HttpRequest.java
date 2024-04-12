package gr313.ladvinskiy.lab13;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kotlin.ranges.UIntRange;
public class HttpRequest {
    Activity ctx;

    class Worker implements Runnable
    {
        String target;

        public void run()
        {

            try {
                URL url = new URL(target);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedInputStream inp = new BufferedInputStream(con.getInputStream());
                byte[] buf = new byte[100];
                String res = "";

                while (true)
                {
                    int n = inp.read(buf);
                    if(n <= 0) break;
                    res += new String(buf,0,n);
                }
                con.disconnect();
                final String res2 = res;
                ctx.runOnUiThread(new Runnable() {public void run() {on_request_complete(res2);}});
            }
            catch(Exception ex) {Log.e("error",ex.toString());}
        }
    }

    public void on_request_complete(String response)
    {
    }

    public void make_request(String endpoint)
    {
        Worker w = new Worker();
        w.target = endpoint;
        Thread t = new Thread(w);
        t.start();
    }

    public  HttpRequest(Activity ctx)
    {
        this.ctx = ctx;
    }

}
