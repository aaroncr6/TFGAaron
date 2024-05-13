package tfc.gestorRestaurante.exception.Pedido;

public class PedidoNotFoundException  extends RuntimeException
{
    public PedidoNotFoundException(String message) { super(message);}

    public PedidoNotFoundException() { super("Pedido not found");}
}
