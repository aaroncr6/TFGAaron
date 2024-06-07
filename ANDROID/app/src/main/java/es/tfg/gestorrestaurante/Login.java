package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import api.API;
import api.UtilREST;

public class Login extends AppCompatActivity {

    EditText ptxtUsuario, ptxtContrasenya;
    Button btnLogin, btnSignup;
    Long id, role;

    CheckBox cbRecordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ptxtUsuario = findViewById(R.id.ptxtUsuario_Login);
        ptxtContrasenya = findViewById(R.id.ptxtContrasenya_Login);
        cbRecordar = findViewById(R.id.checkBox_login);
        btnLogin = findViewById(R.id.btnLongIn_Login);
        btnSignup = findViewById(R.id.btnSignUp_Login);

        // Cargar el usuario y la contraseña de SharedPreferences
        SharedPreferences saveUser = getSharedPreferences("saveUser", MODE_PRIVATE);
        String username = saveUser.getString("username", "");
        String password = saveUser.getString("password", "");
        ptxtUsuario.setText(username);
        ptxtContrasenya.setText(password);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de usuario y contraseña
                String username = ptxtUsuario.getText().toString().trim();
                String password = ptxtContrasenya.getText().toString().trim();

                // Validar los campos de entrada
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Ingrese email y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    // Realizar la solicitud de inicio de sesión
                    loginUser(username, password);

                    // Guardar el usuario y la contraseña en SharedPreferences si el checkbox está marcado
                    SharedPreferences saveUser = getSharedPreferences("saveUser", MODE_PRIVATE);
                    SharedPreferences.Editor editor = saveUser.edit();
                    if (cbRecordar.isChecked()) {
                        editor.putString("username", username);
                        editor.putString("password", password);
                    } else {
                        editor.putString("username", "");
                        editor.putString("password", "");
                    }
                    editor.apply();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

            }
        });

    }

    // Método para realizar la solicitud de inicio de sesión
    private void loginUser(String email, String password) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("email", email);
            requestData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        API.loginUser(requestData, new UtilREST.OnResponseListener() {
            @Override
            public void onSuccess(UtilREST.Response response) {
                try {
                    if(response.responseCode == HttpURLConnection.HTTP_OK){
                        API.getUserIdByEmail(email, new UtilREST.OnResponseListener() {
                            @Override
                            public void onSuccess(UtilREST.Response r) {
                                try {
                                    id = Long.valueOf(r.content);

                                    // Guardar el ID del usuario en SharedPreferences
                                    SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putLong("userId", id);
                                    editor.apply();

                                    // Después de obtener el ID, obtener el rol del usuario
                                    API.getRolById(id, new UtilREST.OnResponseListener() {
                                        @Override
                                        public void onSuccess(UtilREST.Response r) {
                                            Long rol = Long.valueOf(r.content);


                                            if(rol == 1)
                                            {
                                                Intent intentAdmin = new Intent(Login.this, AdminMainActivity.class);
                                                startActivity(intentAdmin);
                                            } else if (rol == 2)
                                            {
                                                Intent intentWorker = new Intent(Login.this, WorkerMainActivity.class);
                                                startActivity(intentWorker);
                                            }
                                            else if( rol == 3){
                                                Intent intentUser = new Intent(Login.this, UserProductosActivity.class);
                                                startActivity(intentUser);
                                            }
                                        }

                                        @Override
                                        public void onError(UtilREST.Response r) {
                                            // Manejar el error al obtener el rol del usuario
                                            Toast.makeText(Login.this, "Error al obtener el rol del usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Login.this, "Error al convertir el ID del usuario", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(UtilREST.Response r) {
                                // Manejar el error al obtener el ID del usuario
                                Toast.makeText(Login.this, "Error al obtener el ID del usuario", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(Login.this, "Error respuesta servidor", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(UtilREST.Response response) {
                // Manejar la respuesta de error del servidor
                Toast.makeText(Login.this, "Error: " + response.responseCode, Toast.LENGTH_SHORT).show();
            }
        }); // Cambia la URL según la configuración de tu servidor
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about_tfg, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about_tfg:
                Intent intent = new Intent(this, AboutTfgActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}