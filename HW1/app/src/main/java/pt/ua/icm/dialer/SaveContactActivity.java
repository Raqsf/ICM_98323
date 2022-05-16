package pt.ua.icm.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SaveContactActivity extends AppCompatActivity {

    private EditText name;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contact);

        name = findViewById(R.id.editContactName);
        number = findViewById(R.id.editContactNumber);
    }

    public void save(View view) {
        Intent intent = getIntent();
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("number", number.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}