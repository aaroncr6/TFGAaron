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


    @Operation(summary = "Metodo que obtiene todos los pedidos")
    @GetMapping("/getAllPedidos")
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getAllPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que obtiene un pedido por su ID")
    @GetMapping("/getPedidoBy/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.findPedidoById(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que crea un pedido")
    @PostMapping("/createPedido")
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO newPedido = pedidoService.createPedido(pedidoDTO);
        return new ResponseEntity<>(newPedido, HttpStatus.CREATED);
    }

    @Operation(summary = "Metodo que actualiza un pedido por su ID")
    @PutMapping("/updatePedidoBy/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO updatedPedido = pedidoService.updatePedido(id, pedidoDTO);
        return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que borra un pedido por su ID")
    @DeleteMapping("/deletePedidoBy/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Metodo que obtiene todos los pedidos de un usuario")
    @GetMapping("/getPedidosByUserId/{userId}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByUserId(@PathVariable Long userId) {
        List<PedidoDTO> pedidos = pedidoService.findPedidosByUserId(userId);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Metodos para detallePedido

    @Operation(summary = "Metodo que obtiene un detallePedido por su ID")
    @GetMapping("/getDetallePedidoBy/{id}")
    public ResponseEntity<DetallePedidoDTO> getDetallePedidoById(@PathVariable Long id) {
        DetallePedidoDTO detallePedidoDTO = detallePedidoService.findDetallePedidoById(id);
        return new ResponseEntity<>(detallePedidoDTO, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que crea un detallePedido")
    @PostMapping("/createDetallePedido")
    public ResponseEntity<DetallePedidoDTO> createDetallePedido(@RequestBody DetallePedidoDTO detallePedidoDTO) {
        DetallePedidoDTO newDetallePedido = detallePedidoService.createDetallePedido(detallePedidoDTO);
        return new ResponseEntity<>(newDetallePedido, HttpStatus.CREATED);
    }

    @Operation(summary = "Metodo que actualiza un detallePedido por su ID")
    @PutMapping("/updateDetallePedidoBy/{id}")
    public ResponseEntity<DetallePedidoDTO> updateDetallePedido(@PathVariable Long id, @RequestBody DetallePedidoDTO detallePedidoDTO) {
        DetallePedidoDTO updateDetallePedidoDTOoDTO  = detallePedidoService.updateDetallePedido(id, detallePedidoDTO);
        return new ResponseEntity<>(updateDetallePedidoDTOoDTO, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que borra un detallePedido por su ID")
    @DeleteMapping("/deleteDetallePedidoBy/{id}")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable Long id) {
        detallePedidoService.deleteDetallePedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Metodos para Producto

    @Operation(summary = "Metodo que obtiene un producto por su ID")
    @GetMapping("/getProductoBy/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        ProductoDTO producto = productoService.findProductoById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que obtiene todos los productos")
    @GetMapping("/getAllProductos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.getAllProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que crea un producto")
    @PostMapping("/createProducto")
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO newProducto = productoService.createProducto(productoDTO);
        return new ResponseEntity<>(newProducto, HttpStatus.CREATED);
    }

    @Operation(summary = "Metodo que actualiza un producto por su ID")
    @PutMapping("/updateProductoBy/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO updatedProducto = productoService.updateProducto(id, productoDTO);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    @Operation(summary = "Metodo que borra un producto por su ID")
    @DeleteMapping("/deleteProductoBy/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Metodos para usuario
    @Operation(summary = "Metodo que obtiene el rol de un usuarionpor su ID")
    @GetMapping("/getRolBy/{id}")
    public ResponseEntity<Long> getRolById(@PathVariable Long id){
        Long roleId = userService.getRolById(id);
        return ResponseEntity.ok(roleId);

    }

    @Operation(summary = "Metodo que obtiene el id de un usuarionpor su email")
    @GetMapping("/getUserIdByEmail")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam("email") String email) {
        Long roleId = userService.findUserIdByEmail(email);
        return ResponseEntity.ok(roleId);
    }

}
