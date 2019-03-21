package fr.iutnc.info.muller624u.todolist_muller_geoffroy_3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

public class CreerItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextInputEditText textlab = findViewById(R.id.editTextLabel);
        RadioButton rbfaible = findViewById(R.id.radioButtonFaible);
        RadioButton rbnormal = findViewById(R.id.radiobuttonnormal);
        RadioButton rbimportant = findViewById(R.id.radioButtonImportant);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


    }

}
