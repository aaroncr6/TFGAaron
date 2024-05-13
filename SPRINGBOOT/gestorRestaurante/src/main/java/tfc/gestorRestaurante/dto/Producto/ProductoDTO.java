package tfc.gestorRestaurante.dto.Producto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long id;
    private String nombre;
    private String ingredientes;
    private String descripcion;
    private double precio;
    private String image;
    private String nombreCategoria;


}