package fr.iutnc.info.muller624u.todolist_muller_geoffroy_3;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

import fr.iutnc.info.muller624u.todolist_muller_geoffroy_3.TodoItem;

/**
 * Created by phil on 07/02/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TodoHolder> {

    private ArrayList<TodoItem> items;

    public RecyclerAdapter(ArrayList<TodoItem> items) {
        this.items = items;
    }

    @Override
    public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new TodoHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TodoHolder holder, int position) {
        TodoItem it = items.get(position);
        holder.bindTodo(it);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class TodoHolder extends RecyclerView.ViewHolder {
        private Resources resources;
        private ImageView image;
        private Switch sw;
        private TextView label;
        private ImageButton buttonSup;
        private ImageButton buttonModif;
        private LinearLayout viewItem;
        private TodoItem item;

        public TodoHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.imageView);
            sw = (Switch) itemView.findViewById(R.id.switch1);
            viewItem = (LinearLayout) itemView.findViewById(R.id.lin_lay_font_item);
            label = (TextView) itemView.findViewById(R.id.textView);
            resources = itemView.getResources();
            buttonSup = (ImageButton) itemView.findViewById(R.id.imageButton_supp);
            buttonModif = (ImageButton) itemView.findViewById(R.id.imageButton_modif);


        }

        public void bindTodo(final TodoItem todo) {
            if(todo.isDone()){
                activerCouleur();
            }else{
                desactiverCouleur();
            }
            label.setText(todo.getLabel());
            sw.setChecked(todo.isDone());
            sw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todo.setDone(!todo.isDone());
                    TodoDbHelper.updateItem(todo, MainActivity.getContext());
                    if(todo.isDone()){
                        activerCouleur();
                    }else{
                        desactiverCouleur();
                    }
                }
            });

            buttonSup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("INIT", "===Supp : "+todo.getId());

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getContext());
                    builder.setMessage("Voulez vous supprimer l'Item ?");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            TodoDbHelper.supItem(todo, MainActivity.getContext());
                            MainActivity.getContext().setVisible(false);
                            MainActivity.getContext().recreate();
                            MainActivity.getContext().setVisible(true);

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
            });
            buttonModif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("INIT", "===Modif : "+todo.getId());

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getContext());
                    //builder.setView();
                    builder.setMessage("Modifier l'Item");
                    builder.setView(R.layout.modifier);
                    builder.setPositiveButton("modifier", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            EditText editlab = (EditText)((AlertDialog)dialog).findViewById(R.id.editTextLabel);
                            RadioButton rbfaible = (RadioButton) ((AlertDialog)dialog).findViewById(R.id.radioButtonFaible);
                            RadioButton rbnormal = (RadioButton) ((AlertDialog)dialog).findViewById(R.id.radiobuttonnormal);
                            RadioButton rbimportant = (RadioButton) ((AlertDialog)dialog).findViewById(R.id.radioButtonImportant);
                            String label = editlab.getText().toString();
                            TodoItem.Tags tags = TodoItem.getTagFor(todo.getTag().toString());


                            if(rbfaible.isChecked()){
                                tags = TodoItem.getTagFor("Faible");
                            }else if(rbnormal.isChecked()){
                                tags = TodoItem.getTagFor("Normal");
                            }else if(rbimportant.isChecked()){
                                tags = TodoItem.getTagFor("Important");
                            }
                            todo.setTag(tags);
                            Log.i("INIT", "======== :"+todo.getLabel()+"|");
                            if(label.equals("")){

                            }else {
                                todo.setLabel(label);
                            }
                            TodoDbHelper.updateItem(todo, MainActivity.getContext());
                            dialog.cancel();
                            MainActivity.getContext().recreate();
                        }
                    });
                    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            switch(todo.getTag()) {
                case Faible:
                    image.setBackgroundColor(resources.getColor(R.color.faible));
                    break;
                case Normal:
                    image.setBackgroundColor(resources.getColor(R.color.normal));
                    break;
                case Important:
                    image.setBackgroundColor(resources.getColor(R.color.important));
                    break;

            }


        }
        public void activerCouleur(){
            buttonSup.setBackgroundColor(Color.argb(40,40, 73, 92));
            buttonModif.setBackgroundColor(Color.argb(40,40, 73, 92));
            viewItem.setBackgroundColor(Color.argb(40,40, 73, 92));
        }
        public void desactiverCouleur(){
            buttonSup.setBackgroundColor(Color.argb(0,40, 73, 92));
            buttonModif.setBackgroundColor(Color.argb(0,40, 73, 92));
            viewItem.setBackgroundColor(Color.argb(0,40, 73, 92));
        }

    }
}
