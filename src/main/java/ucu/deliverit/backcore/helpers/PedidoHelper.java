package ucu.deliverit.backcore.helpers;

import java.util.ArrayList;
import java.util.List;
import ucu.deliverit.backcore.entidades.Cliente;
import ucu.deliverit.backcore.entidades.Direccion;
import ucu.deliverit.backcore.entidades.Pedido;
import ucu.deliverit.backcore.entidades.Restaurant;
import ucu.deliverit.backcore.entidades.Sucursal;
import ucu.deliverit.backcore.entidades.Usuario;
import ucu.deliverit.backcore.entidades.Viaje;

public class PedidoHelper {
    
    public List<Pedido> limpiarPedidosMobile (List<Pedido> pedidos) {
        List<Pedido> resultado = new ArrayList<>();
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pAux = new Pedido();
            Viaje vAux = new Viaje();
            vAux.setId(pedidos.get(i).getViaje().getId());   
            vAux.setPrecio(pedidos.get(i).getViaje().getPrecio());
            vAux.setEstado(pedidos.get(i).getViaje().getEstado());
            vAux.setFecha(pedidos.get(i).getViaje().getFecha());

            Usuario uAux = new Usuario();
            uAux.setFoto(pedidos.get(i).getViaje().getSucursal().getRestaurant().getUsuario().getFoto());

            Restaurant rAux = new Restaurant();
            rAux.setId(pedidos.get(i).getViaje().getSucursal().getRestaurant().getId());
            rAux.setRazonSocial(pedidos.get(i).getViaje().getSucursal().getRestaurant().getRazonSocial());
            rAux.setUsuario(uAux);

            Sucursal sAux = new Sucursal();
            sAux.setId(pedidos.get(i).getViaje().getSucursal().getId());
            sAux.setDireccion(pedidos.get(i).getViaje().getSucursal().getDireccion());
            sAux.setRestaurant(rAux);

            vAux.setSucursal(sAux);   
                
            pAux.setViaje(vAux);
            Direccion dAux = pedidos.get(i).getCliente().getDireccion();
            Cliente cAux = new Cliente();
            cAux.setId(pedidos.get(i).getCliente().getId());
            cAux.setNombre(pedidos.get(i).getCliente().getNombre());
            cAux.setTelefono(pedidos.get(i).getCliente().getTelefono());
            cAux.setDireccion(dAux);
            pAux.setDetalle(pedidos.get(i).getDetalle());
            pAux.setCliente(cAux);
            pAux.setId(pedidos.get(i).getId());
            resultado.add(pAux);
        }
        return resultado;
    }
    
    public List<Pedido> limpiarPedidosToday (List<Pedido> pedidos) {
        List<Pedido> resultado = new ArrayList<>();
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pAux = new Pedido();
            pAux.setId(pedidos.get(i).getId());            
            Cliente cAux = pedidos.get(i).getCliente();            
            Viaje vAux = pedidos.get(i).getViaje();
            Usuario uAux = new Usuario();
            uAux.setId(vAux.getSucursal().getRestaurant().getUsuario().getId());
            uAux.setTelefono(vAux.getSucursal().getRestaurant().getUsuario().getTelefono());
            vAux.getSucursal().getRestaurant().setUsuario(uAux);
            pAux.setCliente(cAux);
            pAux.setViaje(vAux);
            resultado.add(pAux);
        }
        return resultado;
    }
}
