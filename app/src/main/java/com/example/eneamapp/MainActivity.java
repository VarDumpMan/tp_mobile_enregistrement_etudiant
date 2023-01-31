package com.example.eneamapp;

import static androidx.appcompat.app.AlertDialog.*;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eneamapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;
    private ImageView imageView;
    private EditText editText;
    private  Uri selectImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);

        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mTopToolbar);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_id);

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filieres, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Vous pouvez nous écrire là !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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

    public void annuler(View view) {
        EditText edNom = findViewById(R.id.nom);
        edNom.setText("");
        EditText edPrenom = findViewById(R.id.prenom);
        edPrenom.setText("");
        EditText edDate = findViewById(R.id.date);
        edDate.setText("");
        Spinner spinner = findViewById(R.id.spinner_id);
        spinner.setSelected(true);
    }

    public void changeImage(View view) {
        selectImage(this);
    }

    private void selectImage(Context context)
    {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        Builder builder = new Builder(context);
        builder.setTitle("Choose your image");

        builder.setItems(options, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int item){
                //boolean result = Utility.checkPermission(MainActivity.this);

                if(options[item].equals("Take Photo"))
                {
                    System.out.println(options[item]);
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if(takePicture.resolveActivity(getPackageManager()) != null)
                    {

                        startActivityForResult(takePicture, 0);
                    }
                    else {
                        Toast.makeText(context, "Une erreur lors de la capture", Toast.LENGTH_LONG).show();
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                }
                else if(options[item].equals("Cancel")) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Vous avez choisi d'annuler", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    System.out.println(resultCode);
                    if (resultCode == RESULT_OK && data != null) {
                        try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            imageView.setImageBitmap(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Une erreur s'est produite",Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }

    }

    public void enregistrer(View view) {
        editText = findViewById(R.id.nom);
        String nom = editText.getText().toString();

        Intent intent = new Intent(this, PersonneActivity.class);
        intent.putExtra("NOM", nom);

        startActivity(intent);

        //startActivityForResult(intent, );
    }
}