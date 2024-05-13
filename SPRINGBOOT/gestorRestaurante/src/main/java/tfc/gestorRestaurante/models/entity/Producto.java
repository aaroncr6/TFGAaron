package tfc.gestorRestaurante.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column
    private String ingredientes;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private double precio;

    @Column(length = 102400)
    private String image;

    @Column(name = "nombre_Categoria")
    private String nombreCategoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<DetallePedido> detallePedidos;
}