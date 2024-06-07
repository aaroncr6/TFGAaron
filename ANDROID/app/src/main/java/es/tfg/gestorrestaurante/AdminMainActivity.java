package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminMainActivity extends AppCompatActivity {

    Button btnAdminProductos, btnAdminPedidos, btnAdminUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

    btnAdminProductos = findViewById(R.id.btnAdminProducto);
    btnAdminPedidos = findViewById(R.id.btnAdminPedidos);
    btnAdminUsuarios = findViewById(R.id.btnAdminUsuarios);



    btnAdminProductos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AdminMainActivity.this, AdminProductosActivity.class);
            startActivity(intent);
        }
    });

    btnAdminPedidos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AdminMainActivity.this, AdminPedidosActivity.class);
            startActivity(intent);
        }
    });

    btnAdminUsuarios.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AdminMainActivity.this, AdminCreateWorkerActivity.class);
            startActivity(intent);
        }
    });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        // Aquí puedes agregar el código para cerrar la sesión del usuario

        // Crea un nuevo intent para la actividad de login
        Intent intent = new Intent(this, Login.class);

        // Establece la bandera para limpiar la pila de actividades
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Inicia la actividad de login
        startActivity(intent);

        // Finaliza la actividad actual
        finish();
    }
}