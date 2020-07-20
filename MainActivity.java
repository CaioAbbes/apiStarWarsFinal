package com.example.apiclimatempo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText nmCidade;
    private TextView nmClima;
    private TextView nmTemperatura;
// 1 Pegar key e botar em outra api
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nmCidade = findViewById(R.id.txtCidade);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }
    public void openActivity2(String gen, String height, String Name){
        Intent intent= new Intent(this, activityResultado.class);
        intent.putExtra("gender", gen);
        intent.putExtra("height",height);
        intent.putExtra("name",Name);
        startActivity(intent);
    }

       public void buscaTemperatura(View view) {
        // Recupera a string de busca.
           Log.d("asd","asdsd");
        String queryString = nmCidade.getText().toString();
        // esconde o teclado qdo o botão é clicado
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Verifica o status da conexão de rede
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        /* Se a rede estiver disponivel e o campo de busca não estiver vazio
         iniciar o Loader */

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            /*nmClima.setText("");
            nmTemperatura.setText(R.string.str_empty);*/

        }
        // atualiza a textview para informar que não há conexão ou termo de busca
        else {
            if (queryString.length() == 0) {
                nmClima.setText(R.string.str_empty);
                nmTemperatura.setText(R.string.str_empty);
            } else {
                nmClima.setText(" ");
                nmTemperatura.setText(" ");
            }
        }
    }
    @NonNull
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new CarregaCodigo(this, queryString);
    }
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Converte a resposta em Json
            JSONObject jsonObject = new JSONObject(data);
            // Obtem o JSONArray
            // inicializa o contador
            int i = 0;
            String altura = null;
            String sexo = null;
            String Nome = null;
            // Procura pro resultados nos itens do array
            while (altura == null && sexo== null) {
                //  Obter autor e titulo para o item,
                // erro se o campo estiver vazio
                JSONArray resultados = jsonObject.getJSONArray("results");
                JSONObject obj= resultados.getJSONObject(i);
                altura = obj.getString("height");
                Log.d("Anakis", altura);
                Nome = obj.getString("name");
                Log.d("Pablo", Nome);
                sexo = obj.getString("gender");
                Log.d("Pablo", sexo);
                openActivity2(sexo,altura,Nome);
                // move para a proxima linha
                i++;
            }
        } catch (Exception e) {
            // Se não receber um JSOn valido, informa ao usuário
           /* nmClima.setText(R.string.str_empty);
            nmTemperatura.setText(R.string.str_empty);*/
            e.printStackTrace();
        }
    }
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // obrigatório implementar, nenhuma ação executada
    }
}