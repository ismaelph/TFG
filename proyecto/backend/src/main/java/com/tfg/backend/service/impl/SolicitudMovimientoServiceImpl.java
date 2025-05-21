package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.model.repository.SolicitudMovimientoRepository;
import com.tfg.backend.service.SolicitudMovimientoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SolicitudMovimientoServiceImpl implements SolicitudMovimientoService {

    @Autowired
    private SolicitudMovimientoRepository solicitudRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public SolicitudMovimiento save(SolicitudMovimiento solicitud) {
        System.out.println("🟢 SERVICE.save(): Recibido -> Producto = " +
                (solicitud.getProducto() != null ? solicitud.getProducto().getId() : "null") +
                ", Usuario = " +
                (solicitud.getUsuario() != null ? solicitud.getUsuario().getId() : "null"));

        // Recuperar entidades reales
        if (solicitud.getProducto() != null && solicitud.getProducto().getId() != null) {
            Producto producto = productoRepository.findById(solicitud.getProducto().getId()).orElse(null);
            solicitud.setProducto(producto);
        }

        if (solicitud.getUsuario() != null && solicitud.getUsuario().getId() != null) {
            User user = userRepository.findById(solicitud.getUsuario().getId()).orElse(null);
            solicitud.setUsuario(user);
        }

        return solicitudRepository.save(solicitud);
    }




    @Override
    public SolicitudMovimiento findById(Long id) {
        return solicitudRepository.findById(id).orElse(null);
    }

    @Override
    public List<SolicitudMovimiento> findAll() {
        return solicitudRepository.findAll();
    }

    @Override
    public List<SolicitudMovimiento> findByUsuarioId(Long usuarioId) {
        User usuario = userRepository.findById(usuarioId).orElse(null);
        if (usuario != null) {
            return solicitudRepository.findByUsuario(usuario);
        }
        return List.of();
    }

    @Override
    public SolicitudMovimiento updateEstado(SolicitudMovimiento solicitud, Long id) {
        SolicitudMovimiento actual = findById(id);
        if (actual != null) {
            actual.setEstado(solicitud.getEstado());
            actual.setRespuestaAdmin(solicitud.getRespuestaAdmin());
            actual.setFechaResolucion(solicitud.getFechaResolucion());
            return solicitudRepository.save(actual);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        solicitudRepository.deleteById(id);
    }

}
