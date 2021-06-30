package com.example.ex7pc2021;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
public class MainActivity extends AppCompatActivity {
    Sandwish sandwish = null;
    private SharedPreferences sp = null;
    private void updateSandwish() {
        TextView id = findViewById(R.id.idtext);
        TextView comment = findViewById(R.id.commentTxt);
        TextView status = findViewById(R.id.statusTxt);
        CheckBox hummus = findViewById(R.id.hummusChx);
        CheckBox tihini = findViewById(R.id.tihinichx);
        TextView pickles = findViewById(R.id.editNum);
        TextView title = findViewById(R.id.title);
        ImageView trash = findViewById(R.id.trash);
        Button order = findViewById(R.id.addOrder);
       EditText name = findViewById(R.id.gustNmae);
        order.setText(sandwish.order);
        id.setText(sandwish.getId());
        id.setEnabled(false);
        comment.setText(sandwish.getComment());
        status.setText(sandwish.getStatus());
        hummus.setChecked(!sandwish.hummus.equals(""));
        tihini.setChecked(!sandwish.tihini.equals(""));
        pickles.setText(Integer.toString(sandwish.getPickles()));
        if(sandwish.getCustomer_name().equals(""));{}
        name.setText(sandwish.getCustomer_name());
        title.setText(sandwish.tiltle);
        if(!status.getText().toString().equals("waiting..")){
            trash.setVisibility(View.GONE);
            order.setVisibility(View.GONE);
        }else
            {
                trash.setVisibility(View.VISIBLE);
                if (name.getText().toString().equals(""))
                {order.setText("add new order");}else
                    {
                        order.setText("edit your order") ;
                    }
            }


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.sp = this.getSharedPreferences( "data_b", Context.MODE_PRIVATE);
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        String id = sp.getString("id", null);
        if (id != null) {
            fireStore.collection("order").document(id).get().addOnSuccessListener(result ->
            {
                this.sandwish = result.toObject(Sandwish.class);


                updateSandwish();
            });
        }

        if (sandwish == null) {
            sandwish = new Sandwish();
        }
        updateSandwish();
        TextView idd = findViewById(R.id.idtext);
        idd.setText(sandwish.id);
        idd.setEnabled(false);
        Button plus = findViewById(R.id.plus);
        Button minus = findViewById(R.id.minus);
        CheckBox hummus = findViewById(R.id.hummusChx);
        CheckBox tihini = findViewById(R.id.tihinichx);
        TextView textView = findViewById(R.id.editNum);
        TextView textView1 = findViewById(R.id.statusTxt);
        plus.setOnClickListener(v ->
        {
            if (Integer.parseInt(textView.getText().toString()) < 10) {
                textView.setText(Integer.toString(Integer.parseInt(textView.getText().toString()) + 1));
                sandwish.addPickles();
            }

        });


        minus.setOnClickListener(v ->
        {
            if (Integer.parseInt(textView.getText().toString()) > 0) {
                textView.setText(Integer.toString(Integer.parseInt(textView.getText().toString()) - 1));
                sandwish.removeHummus();
            }
        });

        hummus.setOnClickListener(v -> {
            if (hummus.isChecked()) {
                sandwish.addHummus();
                return;
            }
            sandwish.removeHummus();

        });
        tihini.setOnClickListener(v -> {
            if (tihini.isChecked()) {
                sandwish.addTihini();
                return;
            }
            sandwish.removeTihini();

        });
        textView1.setOnClickListener(v -> {


        });
        Button addOrder = findViewById(R.id.addOrder);
        TextView name = findViewById(R.id.gustNmae);
        TextView comment = findViewById(R.id.commentTxt);
        TextView title = findViewById(R.id.title);
        ImageView trash = findViewById(R.id.trash);

        addOrder.setOnClickListener(v ->
        {
            sandwish.setName(name.getText().toString());
            sandwish.addComment(comment.getText().toString());
            FirebaseApp.initializeApp(this.getBaseContext());
            addOrder.setText(R.string.edit);
            title.setText(name.getText()  + getString(R.string.res));
            sandwish.setName(name.getText().toString() );
            sandwish.tiltle = name.getText() + " " + "reservation";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Path path = new Path();
                path.arcTo(0f, 0f, 85f, 60f, 270f, -180f, true);
                ObjectAnimator animator = ObjectAnimator.ofFloat(title, View.X, View.Y, path);
                animator.setDuration(1000);
                animator.start();
            }

            name.setVisibility(View.GONE);
            trash.setVisibility(View.VISIBLE);
            sandwish.trash = true;
            FirebaseFirestore dp = FirebaseFirestore.getInstance();
            dp.collection("order").document(sandwish.getId()).set(this.sandwish);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("id", sandwish.getId());
            editor.apply();
        });
        trash.setOnClickListener(v -> {
            FirebaseFirestore fireStoree = FirebaseFirestore.getInstance();
            fireStoree.collection("order").document(sandwish.getId()).delete();
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("id");
            editor.apply();
            sandwish = new Sandwish();
            updateSandwish();
        });
    }

    void listenToChanges() {
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        ListenerRegistration snapshotListener = fireStore.collection("order").
                document(this.sandwish.getId()).
                addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()) {
                            sandwish = value.toObject(Sandwish.class);
                            updateSandwish();
                        }
                    }
                });

    }


}