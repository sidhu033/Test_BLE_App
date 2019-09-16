package gamsystech.user.newbleupdated.Network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest
{
    //support HTTP Method
    public enum  Method
    {
        POST,PUT,DELETE,GET

    }
    private URL url;
    private HttpURLConnection con;
    private OutputStream os;


    //After instantiation, when opening connection - IOException can occur

    public HttpRequest(URL url)  throws Exception
    {
        this.url = url;
        con = (HttpURLConnection) this.url.openConnection();
    }
    //Can be instantiated with String representation of url, force caller to check for IOException which can be thrown
    public HttpRequest(String url) throws Exception
    {
        this(new URL(url));
        Log.d("parameters", url);
    }
        /**
         * Sending connection and opening an output stream to server by pre-defined instance variable url
        *
        * @param //isPost boolean - indicates whether this request should be sent in POST method
        * @throws IOException - should be checked by caller
        * */
     private void prepareAll(Method method) throws IOException
     {
         con.setDoInput(true);
         con.setRequestMethod(method.name());
         if(method== Method.POST||method== Method.PUT)
         {
             con.setDoOutput(true);
             os = con.getOutputStream();
         }
     }
    //prepare request in GET method
    //@return HttpRequest this instance -> for chaining method @see line 22

    public HttpRequest prepare() throws IOException
    {
        prepareAll(Method.GET);
        return this;
    }
}
