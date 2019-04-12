 package com.example.weather_watch;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.inputmethod.InputMethod;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.weather_watch.R;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.util.concurrent.ExecutionException;

public class weatherActivity extends AppCompatActivity {
    EditText editText;
    TextView resultTextView;


    public void getWeather(View view){
        DownloadTask task=new DownloadTask();
        try {
            String encodeCityName= URLEncoder.encode(editText.getText().toString(),"UTF-8");
            task.execute("https://openweathermap.org/data/2.5/weather?q="+encodeCityName+"&appid=b6907d289e10d714a6e88b30761fae22").get();
        } catch (Exception e) {
            Toast.makeText(weatherActivity.this, "Could not get weather information :(", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);

    }

    public class DownloadTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... urls) {
            URL url=null;
            HttpURLConnection urlConnection=null;
            String result="";
            try {
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(inputStream);
                int data=reader.read();
                while (data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();

                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(weatherActivity.this, "Could not get weather information :(", Toast.LENGTH_SHORT).show();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("JSON!",s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                String weatherInfo=jsonObject.getString("weather");
                String tempInfo=jsonObject.getString("main");//for temp
                Log.i("WeatherContent",weatherInfo);
                Log.i("tempContent",tempInfo);
                JSONArray weatherArray=new JSONArray(weatherInfo);
                JSONObject tempObject=new JSONObject(tempInfo);
                String message="";
                System.out.println(weatherArray.length());//only one object in array i.e. at 0th position--{"id":721,"main":"Haze","description":"haze","icon":"50n"}
                for(int i=0;i<weatherArray.length();i++){
                    JSONObject jsonPart=weatherArray.getJSONObject(i);//only one object in array i.e. at 0th position--{"id":721,"main":"Haze","description":"haze","icon":"50n"}
                    String main=jsonPart.getString("main");
                    String description=jsonPart.getString("description");
                    Log.i("main",jsonPart.getString("main"));
                    Log.i("description",jsonPart.getString("description"));
                    if(!main.equals("")&&!description.equals("")){
                        message+= main+": "+description+"\n";
                    }
                }


                    String temp=tempObject.getString("temp");
                    String pressure=tempObject.getString("pressure");
                    String humidity=tempObject.getString("humidity");
                    Log.i("temp",tempObject.getString("temp"));
                    Log.i("pressure",tempObject.getString("pressure"));
                    Log.i("humidity",tempObject.getString("humidity"));

                    if(!temp.equals("")&&!pressure.equals("")&&!humidity.equals("")){
                        message+= "Temperature: " +temp+"\n"+"Presssure: "+pressure+ "\n"+"Humidity: "+humidity +"\n";
                    }

                if(!message.equals("")){
                    resultTextView.setText(message);
                }else{
                    Toast.makeText(weatherActivity.this, "Could not get weather information :(", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(weatherActivity.this, "Could not get weather information :(", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        editText=findViewById(R.id.editText);
        resultTextView=findViewById(R.id.resultTextView);

        getSupportActionBar().setTitle("Weather");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for showing back button


        // Log.i("Url is",result);

    }
}
