package es.tfg.gestorrestaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserMainActivity extends AppCompatActivity {

    Button btnUserProductos,btnUserCarrito, btnUserHistorialPedidos;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        userId = getIntent().getLongExtra("USER_ID", 0);
        ((MyApplication) getApplication()).getCarrito().setUserId(userId);

        btnUserProductos = findViewById(R.id.btnProductosUser);
        btnUserCarrito = findViewById(R.id.btnCarritoUser);
        btnUserHistorialPedidos = findViewById(R.id.btnHistorialPedidos_user);


        btnUserProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserProductosActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

    }
}