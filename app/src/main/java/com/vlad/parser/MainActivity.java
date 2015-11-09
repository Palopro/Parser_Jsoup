package com.vlad.parser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private TextView city;
    private TextView temp;
    private TextView wind;
    private TextView nav;
    private TextView hum;
    private TextView water;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // инициализация графических компонентов
        Button button = (Button) findViewById(R.id.button);
        title = (TextView)findViewById(R.id.titleView);
        city = (TextView)findViewById(R.id.cityView);
        temp = (TextView)findViewById(R.id.tempView);
        wind = (TextView)findViewById(R.id.windView);
        nav = (TextView)findViewById(R.id.barView);
        hum = (TextView)findViewById(R.id.humView);
        water = (TextView)findViewById(R.id.waterView);
        date = (TextView)findViewById(R.id.dateView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void OnClick(View v) {
        Toast toast = Toast.makeText(getApplicationContext(),"Loading",Toast.LENGTH_SHORT);
        toast.show();
        MyTask mt = new MyTask();
        mt.execute();
    }

  class MyTask extends AsyncTask<Void, Void, Void> {

      String html = "https://www.gismeteo.ua/weather-kyiv-4944/#wdaily1";
      String Title;
      String City;
      String Temp;
      String Wind;
      String Bar;
      String Hum;
      String Water;
      String Date;
      String Country;

      @Override
      protected Void doInBackground(Void... params) {

          Document doc = null;//здесь хранится HTML документ


          try {
              doc = Jsoup.connect(html).get();//считываем страницу
          } catch (IOException e) {
              e.printStackTrace(); // если не удалось считать
          }

          if (doc != null) {
              Title = doc.title(); // если считалось то вытаскиваем  с дока заголовок
              City = doc.select("[class=typeC]").text(); //Город
              Country = doc.select("[href=/catalog/ukraine/]").first().text(); //Страна
              Temp = doc.select("[class=value m_temp c]").first().text(); //Температура
              Wind = doc.select("[class=value m_wind ms]").first().text(); //Ветер
              Bar = doc.select("[class=value m_press torr]").first().text(); //Атмосферное давление
              Hum = doc.select("[class=wicon hum]").first().text(); //Влажность
              Water = doc.select("[class=value m_temp c]").first().text(); //Температура воды
              Date = doc.select("[class=icon date]").text(); // Дата и время обновления данных

          } else {
              Title = "No information available";
              City = "No information available";
              Temp= "No information available";
              Wind = "No information available";
              Bar = "No information available";
              Hum = "No information available";
              Water = "No information available";
          }
          return null;
      }

      @Override
      protected void onPostExecute(Void result){
          super.onPostExecute(result);

          title.setText(Title);
          city.setText(City + ", " + Country);
          temp.setText(Temp);
          wind.setText(Wind);
          nav.setText(Bar);
          hum.setText(Hum);
          water.setText(Water);
          date.setText(Date);
      }
  }
}
