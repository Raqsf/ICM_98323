package pt.ua.icm.implicitintentsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text_uri_message);
        Intent intent = getIntent();
        Uri uri = intent.getData();

        if (uri != null) {
            String uri_string = "URI: " + uri.toString();
            textView.setText(uri_string);
        } else {
            textView.setText("No URI Found!");
        }
    }
}