package tfc.gestorRestaurante.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido")
@DynamicUpdate
public class Pedido
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "total_pedido")
    private double totalPedido;

    @Column
    private LocalDateTime fechaInicio;

    @Column
    private LocalDateTime fechaFin;

    @Column(nullable = false)
    private String estado;

    @JsonBackReference("pedidos")
    @ManyToOne
    @JoinColumn( name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
    private List<DetallePedido> detallesPedido;

}
