package tfc.gestorRestaurante.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clase que representa la entidad Role.
 * Se utiliza para mapear los datos de la tabla rol de la base de datos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rol")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
