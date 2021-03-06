package ucu.deliverit.backcore.helpers;

import java.sql.Timestamp;
import ucu.deliverit.backcore.entidades.Configuracion;
import ucu.deliverit.backcore.entidades.Transaccion;
import ucu.deliverit.backcore.entidades.Viaje;
import ucu.deliverit.backcore.entidades.servicios.ConfiguracionFacadeREST;
import ucu.deliverit.backcore.entidades.servicios.PedidoFacadeREST;
import ucu.deliverit.backcore.entidades.servicios.TransaccionFacadeREST;

public class TransaccionHelper {
    private TransaccionFacadeREST transaccionFacade;
    private ConfiguracionFacadeREST configFacade;
    private PedidoFacadeREST pedidoFacade;
    
    public TransaccionHelper(TransaccionFacadeREST transaccionFacade, ConfiguracionFacadeREST configFacade, PedidoFacadeREST pedidoFacade) {
        this.transaccionFacade = transaccionFacade;
        this.configFacade = configFacade;
        this.pedidoFacade = pedidoFacade;
    }
    
    public void pagar(Viaje viaje) {
        
        Transaccion t = new Transaccion();
        t.setViaje(viaje);
        
        int porcentaje = Integer.parseInt(configFacade.findByDesc(Configuracion.PORCENTAJE).getValor());
        double monto = ((100 - porcentaje) * viaje.getPrecio()) / 100;
        t.setMonto(monto);
        t.setFechaHora(new Timestamp(System.currentTimeMillis()));
        
        transaccionFacade.create(t);
    }
}