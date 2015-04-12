package demosettings.franklin.com.repasopablo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;


public class ListJSON extends ActionBarActivity {

    private static final String LOG_TAG = ListJSON.class.getSimpleName() ;
    public static ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_json);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragmentJSON())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_json, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragmentJSON extends Fragment {


        public PlaceholderFragmentJSON() {
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);//nuestro fragmento va ha tener un menu.
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_refresh,menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){

                case R.id.action_refresh:
                    GetResultTask task = new GetResultTask();
                    task.execute();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
           View rootView = inflater.inflate(R.layout.fragment_list_json, container, false);
           //ArrayList<String> list = new ArrayList<>(Arrays.asList(new String[]{""}));
            GetResultTask starTask = new GetResultTask();
            starTask.execute();
            arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.text_name_view,
                    R.id.result_names,new ArrayList<String>());
            ListView viewlistName = (ListView)rootView.findViewById(R.id.list_name);
            viewlistName.setAdapter(arrayAdapter);
            return rootView;
        }

        class GetResultTask extends AsyncTask<String, Void, String[]> {

            @Override
            protected String[] doInBackground(String... params) {

               String resultString = Utility.getJsonStringFromNetwork();

                try {
                   return Utility.parseFixtureJson(resultString);

                } catch (JSONException e) {

                    Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                    e.printStackTrace();
                    return new String[]{"No DATA on server!"};
                }

            }

            @Override
            protected void onPostExecute(String[] strings) {
                arrayAdapter.clear();
                for (String result : strings){
                    arrayAdapter.add(result);
                }

            }

        }


    }

}