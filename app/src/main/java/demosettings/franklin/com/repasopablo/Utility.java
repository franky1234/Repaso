package demosettings.franklin.com.repasopablo;

/**
 * Created by Franklin on 3/17/2015.
 */
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;

/**
 * Created by Pablo on 3/10/2015.
 */
public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getJsonStringFromNetwork() {
        Log.d(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        try {
            final String IP_LOCAL = "http://10.0.2.2:3000/hello.json";//"http://10.0.2.2:3000/hello.json";

            Uri builtUri = Uri.parse(IP_LOCAL).buildUpon().build();
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            return buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public static String[] parseFixtureJson(String fixtureJson) throws JSONException {
        //Log.v(LOG_TAG,"<<<<<"+fixtureJson+">>>>>");
        JSONArray jsonArray = new JSONArray(fixtureJson); //agarramos en un array el array de JSons
        ArrayList<String> result = new ArrayList<>();

        final String ID = "id";
        final String NAME = "name";
        final String CREATED = "created_at";
        final String UPDATED = "updated_at";

        for (int i = 0; i < jsonArray.length(); i++) {
            String id;
            String name;
            String created;
            String updated;
           JSONObject matchObject = jsonArray.getJSONObject(i);//aca tengo un Json
           //String nombre = matchObject.getString(NAME); ya obtiene el nombre
            id = matchObject.getString(ID);
            name = matchObject.getString(NAME);
            //created = matchObject.getString(CREATED);
            //updated = matchObject.getString(UPDATED);

            String resultString = new Formatter().format("%s: %s", id, name).toString();
           //me devuelve el array de valores.
            result.add(resultString);
        }
        return result.toArray(new String[result.size()]);
    }
}