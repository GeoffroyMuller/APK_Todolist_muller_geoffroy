package fr.iutnc.info.muller624u.todolist_muller_geoffroy_3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity;
    private ArrayList<TodoItem> items;
    private RecyclerView recycler;
    private LinearLayoutManager manager;
    private RecyclerAdapter adapter;
    private static  Boolean autoRefrech = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent creActivity = new Intent(MainActivity.this, CreerItemActivity.class);
                startActivity(creActivity);
            }
        });
        Log.i("INIT", "Fin initialisation composantes");

        // Test d'ajout d'un item
//        TodoItem item = new TodoItem(TodoItem.Tags.Important, "Réviser ses cours");
//        TodoDbHelper.addItem(item, getBaseContext());
//        item = new TodoItem(TodoItem.Tags.Normal, "Acheter du pain");
//        TodoDbHelper.addItem(item, getBaseContext());

        // On récupère les items
        items = TodoDbHelper.getItems(getBaseContext());
        Log.i("INIT", "Fin initialisation items");

        // On initialise le RecyclerView
        recycler = (RecyclerView) findViewById(R.id.recycler);
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);

        adapter = new RecyclerAdapter(items);
        recycler.setAdapter(adapter);

        setRecyclerViewItemTouchListener();
        Log.i("INIT", "Fin initialisation recycler");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(autoRefrech == true){
            autoRefrech = false;

            finish();
            startActivity(getIntent());
        }else {
            autoRefrech = true;

        }

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
            Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
            startActivity(dbmanager);
            return true;
        }
        if (id == R.id.action_vider) {
            //
            this.alertsupptable();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void alertsupptable(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Voulez vous supprimer touts les Items ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                TodoDbHelper.viderTable(getBaseContext());
                onResume();


            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public static MainActivity getContext(){
        return mainActivity;
    }
    private void setRecyclerViewItemTouchListener() {

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                // Non géré dans cet exemple (ce sont les drags) -> on retourne false
                Log.i("INIT", "mmmmmmmmmmmmmmmmmmmmmmm");
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                TodoItem item = items.get(position);

                switch(swipeDir) {
                    case ItemTouchHelper.RIGHT:
                        item.setDone(true);
                        Log.i("INIT", "gauche");
                        break;
                    case ItemTouchHelper.LEFT:
                        item.setDone(false);
                        Log.i("INIT", "droite");
                        break;
                }
                recycler.getAdapter().notifyItemChanged(position);
                Log.i("INIT", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
            }



        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycler);
    }
}
