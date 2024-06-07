package es.tfg.gestorrestaurante;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AdminCreateWorkerActivity extends AppCompatActivity {


    EditText ptxtUSuario, ptxtContrasenya;

    Button btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_worker);

        ptxtContrasenya = findViewById(R.id.ptxtCotrasenya_WorkerSignup);
        ptxtUSuario = findViewById(R.id.ptxtUsuario_WorkerSignup);

        btnRegister = findViewById(R.id.btnRegister_WorkerSignup);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ptxtUSuario.getText().toString().trim();
                String password = ptxtContrasenya.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    showToast("toast_adminCreateWorker_complete_fields");
                } else {
                    // Al registrar un nuevo trabajdor, asignar el rol WORKER (id: 2)
                    registerUser(username, password, 2);
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
                showToast("toast_adminCreateWorker_register_success");
                finish();
            }

            @Override
            public void onError(UtilREST.Response response) {
                // Manejar respuesta de error
                showToast("toast_adminCreateWorker_register_error" + response.responseCode);
            }
        });
    }

    private void showToast(String messageKey) {
        // Crea el layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Infla el layout desde xml
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Obtiene el TextView y establece el mensaje
        TextView toastText = layout.findViewById(R.id.toastMessage);
        String toastMessage = getResources().getString(getResources().getIdentifier(messageKey, "string", getPackageName()));
        toastText.setText(toastMessage);

        // Crea el nuevo toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        // Muestra el toast
        toast.show();
    }

}
