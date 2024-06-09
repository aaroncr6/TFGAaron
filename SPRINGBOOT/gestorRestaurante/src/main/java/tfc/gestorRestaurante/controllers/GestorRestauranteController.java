package tfc.gestorRestaurante.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfc.gestorRestaurante.dto.DetallePedido.DetallePedidoDTO;
import tfc.gestorRestaurante.dto.Pedido.PedidoDTO;
import tfc.gestorRestaurante.dto.Producto.ProductoDTO;
import tfc.gestorRestaurante.exception.Pedido.PedidoNotFoundException;
import tfc.gestorRestaurante.mappers.Pedido.PedidoMapper;
import tfc.gestorRestaurante.mappers.Producto.ProductoMapper;
import tfc.gestorRestaurante.mappers.UserMapper;
import tfc.gestorRestaurante.mappers.detallePedido.DetallePedidoMapper;
import tfc.gestorRestaurante.models.entity.Role;
import tfc.gestorRestaurante.models.services.DetallePedido.IDetallePedidoService;
import tfc.gestorRestaurante.models.services.Pedido.IPedidoService;
import tfc.gestorRestaurante.models.services.Producto.IProductoService;
import tfc.gestorRestaurante.models.services.User.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*"})
@Tag(name ="Gestión Restaurante", description = "API para la gestión de restaurante")
public class GestorRestauranteController
{
    @Autowired
    private IPedidoService pedidoService;
    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private IDetallePedidoService detallePedidoService;
    @Autowired
    private DetallePedidoMapper detallePedidoMapper;

    @Autowired
    private IProductoService productoService;
    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(GestorRestauranteController.class);

    // Metodos para Pedido


    /**
     * Metodo que obtiene todos los pedidos
     *
     * Este método es un endpoint de la API que se encarga de obtener todos los pedidos existentes.
     * Se accede a este a través de una petición GET a la ruta "/getAllPedidos".
     *
     * @return Una respuesta HTTP que contiene una lista de todos los pedidos en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que obtiene todos los pedidos")
    @GetMapping("/getAllPedidos")
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getAllPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    /**
     * Metodo que obtiene un pedido por su ID
     *
     * Este método es un endpoint de la API que se encarga de obtener un pedido específico por su ID.
     * Se accede a este a través de una petición GET a la ruta "/getPedidoBy/{id}", donde {id} es el ID del pedido que se desea obtener.
     * Si el pedido con el ID proporcionado no se encuentra, se lanza una excepción PedidoNotFoundException.
     *
     * @param id El ID del pedido que se desea obtener.
     * @return Una respuesta HTTP que contiene el pedido solicitado en el cuerpo y un estado HTTP 200 (OK).
     *         Si no se encuentra el pedido, se lanza una excepción PedidoNotFoundException y se devuelve un estado HTTP 404 (Not Found).
     */
    @Operation(summary = "Metodo que obtiene un pedido por su ID")
    @GetMapping("/getPedidoBy/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.findPedidoById(id);
        if (pedido == null) {
            throw new PedidoNotFoundException("No se encontró un pedido con el ID: " + id);
        }
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que crea un pedido")
    @PostMapping("/createPedido")
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO newPedido = pedidoService.createPedido(pedidoDTO);
        return new ResponseEntity<>(newPedido, HttpStatus.CREATED);
    }

    /**
     * Metodo que crea un pedido
     *
     * Este método es un endpoint de la API que se encarga de crear un nuevo pedido.
     * Se accede a este a través de una petición POST a la ruta "/createPedido".
     * El cuerpo de la petición debe contener un objeto PedidoDTO que representa el pedido que se desea crear.
     *
     * @param pedidoDTO Un objeto PedidoDTO que representa el pedido que se desea crear.
     * @return Una respuesta HTTP que contiene el pedido creado en el cuerpo y un estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Metodo que actualiza un pedido por su ID")
    @PutMapping("/updatePedidoBy/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO updatedPedido = pedidoService.updatePedido(id, pedidoDTO);
        return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
    }

    /**
     * Metodo que borra un pedido por su ID
     *
     * Este método es un endpoint de la API que se encarga de borrar un pedido específico por su ID.
     * Se accede a este a través de una petición DELETE a la ruta "/deletePedidoBy/{id}", donde {id} es el ID del pedido que se desea borrar.
     * Si el pedido con el ID proporcionado no se encuentra, se lanza una excepción PedidoNotFoundException.
     *
     * @param id El ID del pedido que se desea borrar.
     * @return Una respuesta HTTP con un estado HTTP 204 (NO CONTENT).
     */
    @Operation(summary = "Metodo que borra un pedido por su ID")
    @DeleteMapping("/deletePedidoBy/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Metodo que obtiene todos los pedidos de un usuario
     *
     * Este método es un endpoint de la API que se encarga de obtener todos los pedidos de un usuario específico por su ID de usuario.
     * Se accede a este a través de una petición GET a la ruta "/getPedidosByUserId/{userId}", donde {userId} es el ID del usuario cuyos pedidos se desean obtener.
     *
     * @param userId El ID del usuario cuyos pedidos se desean obtener.
     * @return Una respuesta HTTP que contiene una lista de todos los pedidos del usuario en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que obtiene todos los pedidos de un usuario")
    @GetMapping("/getPedidosByUserId/{userId}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByUserId(@PathVariable Long userId) {
        List<PedidoDTO> pedidos = pedidoService.findPedidosByUserId(userId);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Metodos para detallePedido

    /**
     * Metodo que obtiene un detallePedido por su ID
     *
     * Este método es un endpoint de la API que se encarga de obtener un detalle de pedido específico por su ID.
     * Se accede a este a través de una petición GET a la ruta "/getDetallePedidoBy/{id}", donde {id} es el ID del detalle de pedido que se desea obtener.
     *
     * @param id El ID del detalle de pedido que se desea obtener.
     * @return Una respuesta HTTP que contiene el detalle de pedido solicitado en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que obtiene un detallePedido por su ID")
    @GetMapping("/getDetallePedidoBy/{id}")
    public ResponseEntity<DetallePedidoDTO> getDetallePedidoById(@PathVariable Long id) {
        DetallePedidoDTO detallePedidoDTO = detallePedidoService.findDetallePedidoById(id);
        return new ResponseEntity<>(detallePedidoDTO, HttpStatus.OK);
    }

    /**
     * Metodo que crea un detallePedido
     *
     * Este método es un endpoint de la API que se encarga de crear un nuevo detalle de pedido.
     * Se accede a este a través de una petición POST a la ruta "/createDetallePedido".
     * El cuerpo de la petición debe contener un objeto DetallePedidoDTO que representa el detalle de pedido que se desea crear.
     *
     * @param detallePedidoDTO Un objeto DetallePedidoDTO que representa el detalle de pedido que se desea crear.
     * @return Una respuesta HTTP que contiene el detalle de pedido creado en el cuerpo y un estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Metodo que crea un detallePedido")
    @PostMapping("/createDetallePedido")
    public ResponseEntity<DetallePedidoDTO> createDetallePedido(@RequestBody DetallePedidoDTO detallePedidoDTO) {
        DetallePedidoDTO newDetallePedido = detallePedidoService.createDetallePedido(detallePedidoDTO);
        return new ResponseEntity<>(newDetallePedido, HttpStatus.CREATED);
    }

    /**
     * Metodo que actualiza un detallePedido por su ID
     *
     * Este método es un endpoint de la API que se encarga de actualizar un detalle de pedido específico por su ID.
     * Se accede a este a través de una petición PUT a la ruta "/updateDetallePedidoBy/{id}", donde {id} es el ID del detalle de pedido que se desea actualizar.
     * El cuerpo de la petición debe contener un objeto DetallePedidoDTO que representa el detalle de pedido con los datos actualizados.
     *
     * @param id El ID del detalle de pedido que se desea actualizar.
     * @param detallePedidoDTO Un objeto DetallePedidoDTO que representa el detalle de pedido con los datos actualizados.
     * @return Una respuesta HTTP que contiene el detalle de pedido actualizado en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que actualiza un detallePedido por su ID")
    @PutMapping("/updateDetallePedidoBy/{id}")
    public ResponseEntity<DetallePedidoDTO> updateDetallePedido(@PathVariable Long id, @RequestBody DetallePedidoDTO detallePedidoDTO) {
        DetallePedidoDTO updateDetallePedidoDTOoDTO  = detallePedidoService.updateDetallePedido(id, detallePedidoDTO);
        return new ResponseEntity<>(updateDetallePedidoDTOoDTO, HttpStatus.OK);
    }

    /**
     * Metodo que borra un detallePedido por su ID
     *
     * Este método es un endpoint de la API que se encarga de borrar un detalle de pedido específico por su ID.
     * Se accede a este a través de una petición DELETE a la ruta "/deleteDetallePedidoBy/{id}", donde {id} es el ID del detalle de pedido que se desea borrar.
     * Si el detalle de pedido con el ID proporcionado no se encuentra, se lanza una excepción DetallePedidoNotFoundException.
     *
     * @param id El ID del detalle de pedido que se desea borrar.
     * @return Una respuesta HTTP con un estado HTTP 204 (NO CONTENT).
     */
    @Operation(summary = "Metodo que borra un detallePedido por su ID")
    @DeleteMapping("/deleteDetallePedidoBy/{id}")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable Long id) {
        detallePedidoService.deleteDetallePedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Metodos para Producto

    /**
     * Metodo que obtiene un producto por su ID
     *
     * Este método es un endpoint de la API que se encarga de obtener un producto específico por su ID.
     * Se accede a este a través de una petición GET a la ruta "/getProductoBy/{id}", donde {id} es el ID del producto que se desea obtener.
     *
     * @param id El ID del producto que se desea obtener.
     * @return Una respuesta HTTP que contiene el producto solicitado en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que obtiene un producto por su ID")
    @GetMapping("/getProductoBy/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        ProductoDTO producto = productoService.findProductoById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    /**
     * Metodo que obtiene todos los productos
     *
     * Este método es un endpoint de la API que se encarga de obtener todos los productos existentes.
     * Se accede a este a través de una petición GET a la ruta "/getAllProductos".
     *
     * @return Una respuesta HTTP que contiene una lista de todos los productos en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que obtiene todos los productos")
    @GetMapping("/getAllProductos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.getAllProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    /**
     * Metodo que crea un producto
     *
     * Este método es un endpoint de la API que se encarga de crear un nuevo producto.
     * Se accede a este a través de una petición POST a la ruta "/createProducto".
     * El cuerpo de la petición debe contener un objeto ProductoDTO que representa el producto que se desea crear.
     *
     * @param productoDTO Un objeto ProductoDTO que representa el producto que se desea crear.
     * @return Una respuesta HTTP que contiene el producto creado en el cuerpo y un estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Metodo que crea un producto")
    @PostMapping("/createProducto")
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO newProducto = productoService.createProducto(productoDTO);
        return new ResponseEntity<>(newProducto, HttpStatus.CREATED);
    }

    /**
     * Metodo que actualiza un producto por su ID
     *
     * Este método es un endpoint de la API que se encarga de actualizar un producto específico por su ID.
     * Se accede a este a través de una petición PUT a la ruta "/updateProductoBy/{id}", donde {id} es el ID del producto que se desea actualizar.
     * El cuerpo de la petición debe contener un objeto ProductoDTO que representa el producto con los datos actualizados.
     *
     * @param id El ID del producto que se desea actualizar.
     * @param productoDTO Un objeto ProductoDTO que representa el producto con los datos actualizados.
     * @return Una respuesta HTTP que contiene el producto actualizado en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que actualiza un producto por su ID")
    @PutMapping("/updateProductoBy/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO updatedProducto = productoService.updateProducto(id, productoDTO);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    /**
     * Metodo que borra un producto por su ID
     *
     * Este método es un endpoint de la API que se encarga de borrar un producto específico por su ID.
     * Se accede a este a través de una petición DELETE a la ruta "/deleteProductoBy/{id}", donde {id} es el ID del producto que se desea borrar.
     * Si el producto con el ID proporcionado no se encuentra, se lanza una excepción ProductoNotFoundException.
     *
     * @param id El ID del producto que se desea borrar.
     * @return Una respuesta HTTP con un estado HTTP 204 (NO CONTENT).
     */
    @Operation(summary = "Metodo que borra un producto por su ID")
    @DeleteMapping("/deleteProductoBy/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Metodos para usuario

    /**
     * Metodo que obtiene el rol de un usuario por su ID
     *
     * Este método es un endpoint de la API que se encarga de obtener el rol de un usuario específico por su ID.
     * Se accede a este a través de una petición GET a la ruta "/getRolBy/{id}", donde {id} es el ID del usuario cuyo rol se desea obtener.
     *
     * @param id El ID del usuario cuyo rol se desea obtener.
     * @return Una respuesta HTTP que contiene el ID del rol del usuario en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que obtiene el rol de un usuarionpor su ID")
    @GetMapping("/getRolBy/{id}")
    public ResponseEntity<Long> getRolById(@PathVariable Long id){
        Long roleId = userService.getRolById(id);
        return ResponseEntity.ok(roleId);

    }

    /**
     * Metodo que obtiene el ID de un usuario por su email
     *
     * Este método es un endpoint de la API que se encarga de obtener el ID de un usuario específico por su email.
     * Se accede a este a través de una petición GET a la ruta "/getUserIdByEmail", y se debe proporcionar el email del usuario como un parámetro de consulta.
     *
     * @param email El email del usuario cuyo ID se desea obtener.
     * @return Una respuesta HTTP que contiene el ID del usuario en el cuerpo y un estado HTTP 200 (OK).
     */
    @Operation(summary = "Metodo que obtiene el id de un usuarionpor su email")
    @GetMapping("/getUserIdByEmail")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam("email") String email) {
        Long roleId = userService.findUserIdByEmail(email);
        return ResponseEntity.ok(roleId);
    }

}
