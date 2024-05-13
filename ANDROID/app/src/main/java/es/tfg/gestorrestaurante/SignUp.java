package es.tfg.gestorrestaurante;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import api.API;
import api.UtilREST;

public class SignUp extends AppCompatActivity {

    EditText ptxtUSuario, ptxtContrasenya;

    Button btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ptxtContrasenya = findViewById(R.id.ptxtCotrasenya_Signup);
        ptxtUSuario = findViewById(R.id.ptxtUsuario_Signup);

        btnRegister = findViewById(R.id.btnRegister_Signup);
        btnCancel = findViewById(R.id.btnCancel_Signup);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ptxtUSuario.getText().toString().trim();
                String password = ptxtContrasenya.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Al registrar un nuevo usuario, asignar el rol CLIENT (id: 3)
                    registerUser(username, password, 3);
                }
            }
        });

    }

    private void registerUser(String username, String password, int roleId) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("email", username);
            requestData.put("password", password);
            JSONArray roleIdsArray = new JSONArray(); // Crear un nuevo JSONArray para los IDs de rol
            roleIdsArray.put(roleId); // Agregar el ID de rol al array
            requestData.put("rolIds", roleIdsArray); // Agregar el array al objeto JSON
        } catch (JSONException e) {
            e.printStackTrace();
        }
        API.registerUser(requestData, new UtilREST.OnResponseListener() {
            @Override
            public void onSuccess(UtilREST.Response response) {
                // Manejar respuesta exitosa (si es necesario)
                Toast.makeText(SignUp.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(UtilREST.Response response) {
                // Manejar respuesta de error
                Toast.makeText(SignUp.this, "Error al registrar usuario: " + response.responseCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}