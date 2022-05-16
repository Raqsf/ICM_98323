package pt.ua.icm.dialer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView numberDialed;
    private Button contact1Button;
    private Button contact2Button;
    private Button contact3Button;
    private Button delete;

    private Contact cont1;
    private Contact cont2;
    private Contact cont3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberDialed = findViewById(R.id.textView);

        contact1Button = findViewById(R.id.contact1);
        contact1Button.setText(cont1 == null ? "---" : cont1.getName());

        contact2Button = findViewById(R.id.contact2);
        contact2Button.setText(cont2 == null ? "---" : cont2.getName());

        contact3Button = findViewById(R.id.contact3);
        contact3Button.setText(cont3 == null ? "---" : cont3.getName());

        delete = findViewById(R.id.delete);

        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("Delete", "LONG CLICK");
                numberDialed.setText("");
                return true;
            }
        });

        contact1Button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveContact(v, "1");
                return true;
            }
        });

        contact2Button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveContact(v, "2");
                return true;
            }
        });

        contact3Button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveContact(v, "3");
                return true;
            }
        });
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        saveIntentData(data);
                    }
                }
            });

    private void saveContact(View view, String id) {
        Intent intent = new Intent(MainActivity.this, SaveContactActivity.class);
        intent.putExtra("id", id);
        someActivityResultLauncher.launch(intent);
    }

    private void saveIntentData(Intent data) {
        switch(data.getStringExtra("id")) {
            case "1":
                cont1 = new Contact(data.getStringExtra("name"), data.getStringExtra("number"));
                contact1Button.setText(cont1.getName());
                break;
            case "2":
                cont2 = new Contact(data.getStringExtra("name"), data.getStringExtra("number"));
                contact2Button.setText(cont2.getName());
                break;
            case "3":
                cont3 = new Contact(data.getStringExtra("name"), data.getStringExtra("number"));
                contact3Button.setText(cont3.getName());
                break;
        }

    }

    public void addDial(View view) {
        Button b = (Button) view;
        switch(b.getId()) {
            case R.id.contact1:
                if(cont1 != null) {
                    numberDialed.setText(cont1.getNumber());
                }
                break;
            case R.id.contact2:
                if(cont2 != null) {
                    numberDialed.setText(cont2.getNumber());
                }
                break;
            case R.id.contact3:
                if(cont3 != null) {
                    numberDialed.setText(cont3.getNumber());
                }
                break;

        }
    }

    public void addNumber(View view) {
        String number = view.getTag().toString();
        String alreadyDialed = numberDialed.getText().toString();
        numberDialed.setText(String.format("%s%s", alreadyDialed, number));
    }

    public void delete(View view) {
        String alreadyDialed = numberDialed.getText().toString();
        if(alreadyDialed.length() > 0) {
            String newValue = alreadyDialed.substring(0,alreadyDialed.length() - 1);
            numberDialed.setText(String.format("%s", newValue));
        }
    }

    public void openPhoneDialer(View view) {
        String number = numberDialed.getText().toString();
        Uri numberUri = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_DIAL, numberUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }
}