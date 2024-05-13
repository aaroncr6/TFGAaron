package api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import api.UtilREST.*;

// Proporciona métodos para interactuar con el API RESTful.
public class API {

    private static final String URL = "http://192.168.1.134:8080/";


    // Solicitud GET a la URL de la API para obtener todos los posts.
    // Usa un UtilREST.OnResponseListener como parámetro para manejar la respuesta de la solicitud.
    public static void getPosts(UtilREST.OnResponseListener listener, String URL) {
        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "posts", listener);
    }

    // Similar al método anterior, usa también el id para obtener un post en concreto.
    public static void getPost(int id, UtilREST.OnResponseListener listener, String URL) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de post no válido");
        }
        UtilREST.runQuery(QueryType.GET, URL + "posts/" + id, listener);
    }

    // Solicitud POST a la URL de la API para crear un nuevo post.
    // Usa un objeto JSONObject que representa el post que se creará, así como un UtilREST.OnResponseListener para manejar la respuesta.
    public static void postPost(JSONObject post, UtilREST.OnResponseListener listener, String URL) {
        UtilREST.runQuery(QueryType.POST, URL + "posts", post.toString(), listener);
    }

    // Solicitud PUT a la URL de la API para actualizar un post existente.
    // Usa un ID de post (id), un objeto JSONObject que contiene los datos actualizados del post, y un UtilREST.OnResponseListener para manejar la respuesta.
    public static void putPost(int id, JSONObject post, UtilREST.OnResponseListener listener, String URL) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de post no válido");
        }
        UtilREST.runQuery(QueryType.PUT, URL + "posts/" + id, post.toString(), listener);
    }

    // Solicitud DELETE a la URL de la API para eliminar un post existente.
    // Usa el id del post a eliminar y un UtilREST.OnResponseListener para manejar la respuesta.
    public static void deletePost(int id, UtilREST.OnResponseListener listener, String URL) {
        if (id < 0) {
            throw new IllegalArgumentException("ID de post no válido");
        }
        UtilREST.runQuery(QueryType.DELETE, URL + "posts/" + id, listener);
    }

    // METODO PARA LOGIN
    public static void loginUser(JSONObject credentials, UtilREST.OnResponseListener listener) {

        UtilREST.runQuery(UtilREST.QueryType.POST, URL + "auth/login", credentials.toString(), listener);
    }

    //METODO PARA SIGNUP
    public static void registerUser(JSONObject userData, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.POST, URL + "auth/signup", userData.toString(), listener);
    }

    //  obtener el rol por ID
    public static void getRolById(long id, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "api/getRolBy/" + id, listener);
    }

    //  encontrar el rol por email
    public static void getUserIdByEmail(String email, UtilREST.OnResponseListener listener) {

        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "api/getUserIdByEmail?email=" + email, listener);
    }

    // obtener todos los productos
    public static void getAllProducts(UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "api/getAllProductos", listener);
    }

    // obtener un producto por ID
    public static void getProductById(long id, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "api/getProductoBy/" + id, listener);
    }

    // eliminar un producto por ID
    public static void deleteProductById(long id, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.DELETE, URL + "api/deleteProductoBy/" + id, listener);
    }

    // update un producto por ID
    public static void updateProductById(long id, JSONObject product, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.PUT, URL + "api/updateProductoBy/" + id, product.toString(), listener);
    }

    //crear un producto
    public static void createProduct(JSONObject product, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.POST, URL + "api/createProducto", product.toString(), listener);
    }


    // Actualizar un pedido por ID
    public static void updatePedidoById(long id, JSONObject pedido, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.PUT, URL + "api/updatePedidoBy/" + id, pedido.toString(), listener);
    }

    // Actualizar un detallePedido por ID
    public static void updateDetallePedidoById(long id, JSONObject detallePedido, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.PUT, URL + "api/updateDetallePedidoBy/" + id, detallePedido.toString(), listener);
    }

    // Crear un pedido
    public static void createPedido(JSONObject pedido, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.POST, URL + "api/createPedido", pedido.toString(), listener);
    }

    // Crear un detallePedido
    public static void createDetallePedido(JSONObject detallePedido, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.POST, URL + "api/createDetallePedido", detallePedido.toString(), listener);
    }

    // Obtener un pedido por ID
    public static void getPedidoById(long id, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "api/getPedidoBy/" + id, listener);
    }

    // Obtener un detallePedido por ID
    public static void getDetallePedidoById(long id, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "api/getDetallePedidoBy/" + id, listener);
    }

    // Borrar un pedido por ID
    public static void deletePedidoById(long id, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.DELETE, URL + "api/deletePedidoBy/" + id, listener);
    }

    // Borrar un detallePedido por ID
    public static void deleteDetallePedidoById(long id, UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.DELETE, URL + "api/deleteDetallePedidoBy/" + id, listener);
    }

    // obtener todos los pedidos
    public static void getAllPedidos(UtilREST.OnResponseListener listener) {
        UtilREST.runQuery(UtilREST.QueryType.GET, URL + "api/getAllPedidos", listener);
    }


}

