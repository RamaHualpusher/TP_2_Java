package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.example.domain.Pais;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Hello world!
 *
 */
public class App 
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tp_2_java?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database!");
            String baseUrl = "https://restcountries.com/v2/callingcode/";

            for (int i = 1; i <= 300; i++) {
                Request request = new Request.Builder()
                        .url(baseUrl + i)
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                if (!response.isSuccessful()) {
                    continue;
                }

                JSONArray jsonArray = new JSONArray(responseBody);

                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                    Pais pais = new Pais();
                    pais.setCodigoPais(jsonObject.optString("alpha3Code", "---"));
                    pais.setNombrePais(jsonObject.optString("name", "unknown"));
                    pais.setCapitalPais(jsonObject.optString("capital", "unknown"));
                    pais.setRegion(jsonObject.optString("region", "unknown"));
                    pais.setPoblacion(jsonObject.optInt("population", 0));
                    pais.setLatitud(jsonObject.optJSONArray("latlng") != null
                            ? Double.parseDouble(jsonObject.optJSONArray("latlng").optString(0, "0"))
                            : 0);
                    pais.setLongitud(jsonObject.optJSONArray("latlng") != null
                            ? Double.parseDouble(jsonObject.optJSONArray("latlng").optString(1, "0"))
                            : 0);

                    insertPais(conn, pais);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void insertPais(Connection conn, Pais pais) throws SQLException {
        String query = "INSERT INTO pais (codigo_pais, nombre_pais, capital_pais, region, poblacion, latitud, longitud) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1, pais.getCodigoPais());
        statement.setString(2, pais.getNombrePais());
        statement.setString(3,pais.getCapitalPais());
        statement.setString(4, pais.getRegion());
        statement.setInt(5, pais.getPoblacion());
        statement.setDouble(6, pais.getLatitud());
        statement.setDouble(7, pais.getLongitud());

        int rowsInserted = statement.executeUpdate();

        if (rowsInserted < 0) {
            System.out.println("Error inserting pais: " + pais);
        }
    }
}
