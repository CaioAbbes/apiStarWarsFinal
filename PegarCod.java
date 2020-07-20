package com.example.apiclimatempo;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PegarCod {
    private static final String LOG_TAG = PegarCod.class.getSimpleName();
    // Constantes utilizadas pela API
    // URL para a API de metereologia.
    //                                      http://dataservice.accuweather.com/locations/v1/cities/search?apikey=uS4MACAkFEZHV7pqqM1HDPocakID0704&q=S%C3%A3o%20Paulo

    private static final String PESSOA = "https://swapi.dev/api/people/?";
    // Parametros da api key, necessário para consultar
    private static final String QUERY_PARAM= "q"; //maxResults
    // Parametro do idioma
    private static final String LANGUAGE = "language";
    private static final String PESQUISA = "search";
    // Parametro se deve retornar detalhes completos
    private static final String DETAILS = "details";
    // Parametro da qtde de objetos para retornar
    private static final String ID = "";

    static String buscaPessoa(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String CodigoJSONString = null;
        String Codigo = null;
        Uri builtURI;
        // Construção da URI de Busca
        try {

            builtURI = Uri.parse(PESSOA).buildUpon()
                    .appendQueryParameter(PESQUISA, queryString)
                    .build();
            Log.d("asdas", "asd" + builtURI);
            // Converte a URI para a URL.
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            Log.d("pac", "mike+" + urlConnection);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Busca o InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Cria o buffer para o input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Usa o StringBuilder para receber a resposta.
            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Adiciona a linha a string.
                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {
                // se o stream estiver vazio não faz nada
                return null;
            }
            CodigoJSONString = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // fecha a conexão e o buffer.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
        }
        // escreve o Json no log
//        Log.d(LOG_TAG, CodigoJSONString);
        Log.d("Azul", CodigoJSONString);
        return CodigoJSONString;
        // http://dataservice.accuweather.com/locations/v1/cities/search?apikey=uS4MACAkFEZHV7pqqM1HDPocakID0704&q=s%C3%A3o%20paulo&language=pt-br

    }
}

