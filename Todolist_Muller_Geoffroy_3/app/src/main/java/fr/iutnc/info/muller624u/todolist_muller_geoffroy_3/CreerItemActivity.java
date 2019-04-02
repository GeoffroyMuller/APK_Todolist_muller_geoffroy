package fr.iutnc.info.muller624u.todolist_muller_geoffroy_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreerItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editlab = findViewById(R.id.editTextLabel);
                RadioButton rbfaible = findViewById(R.id.radioButtonFaible);
                RadioButton rbnormal = findViewById(R.id.radiobuttonnormal);
                RadioButton rbimportant = findViewById(R.id.radioButtonImportant);
                String label = editlab.getText().toString();
                TodoItem.Tags tags = TodoItem.getTagFor("Faible");
                String test3 = "rien";

                if(rbfaible.isChecked()){
                    tags = TodoItem.getTagFor("Faible");
                }else if(rbnormal.isChecked()){
                    tags = TodoItem.getTagFor("Normal");
                }else if(rbimportant.isChecked()){
                    tags = TodoItem.getTagFor("Important");
                }
                TodoItem res = new TodoItem(tags, label);
                TodoDbHelper.addItem(res, getApplicationContext());
                Snackbar.make(view,  label+" "+test3, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                finish();
            }

        });


    }

}
