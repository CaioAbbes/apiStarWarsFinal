package com.example.apiclimatempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activityResultado extends AppCompatActivity {
    private TextView nmNome;
    private TextView nmAltura;
    private TextView nmSexo;
    public void openActivityMain(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado2);
        Button button = (Button) findViewById(R.id.buttonVoltar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMain();
            }
        });
        nmNome = findViewById(R.id.tvNome);
        nmAltura = findViewById(R.id.tvAltura);
        nmSexo = findViewById(R.id.tvSexo);

        Bundle bundle = getIntent().getExtras();
        String sexo = bundle.getString("gender");
        String altura = bundle.getString("height");
        String nome = bundle.getString("name");

        nmNome.setText(nome);
        nmAltura.setText(altura);
        nmSexo.setText(sexo);

    }

    public void MandarUsu(View view){
        Uri uri = Uri.parse("https://starwars.fandom.com/pt/");
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);

    }
}
