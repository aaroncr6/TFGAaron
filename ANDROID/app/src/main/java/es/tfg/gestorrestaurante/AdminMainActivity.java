package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.os.Bundle;
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


    }
}