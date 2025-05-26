package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.model.dto.SolicitudMovimientoDto;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.enums.EstadoSolicitud;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.model.repository.SolicitudMovimientoRepository;
import com.tfg.backend.service.CorreoService;
import com.tfg.backend.service.SolicitudMovimientoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Autowired
    private SolicitudMovimientoRepository solicitudMovimientoRepository;

    @Autowired
    private CorreoService correoService;



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

    @Override
    public void crearSolicitud(SolicitudMovimientoDto dto, User usuarioActual) {
        try {
            System.out.println("📥 Creando nueva solicitud de movimiento...");

            // Convertimos el DTO en entidad
            SolicitudMovimiento solicitud = dto.to();

            // Buscar producto REAL desde la base de datos
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("❌ Producto no encontrado con ID: " + dto.getProductoId()));

            // Validar empresa (por si acaso)
            if (!producto.getEmpresa().getId().equals(usuarioActual.getEmpresa().getId())) {
                throw new RuntimeException("❌ No puedes solicitar productos de otra empresa.");
            }

            // Asignar producto real
            solicitud.setProducto(producto);
            System.out.println("📦 Producto asignado: " + producto.getNombre() + " (ID: " + producto.getId() + ")");

            // Seteo de estado y fecha
            solicitud.setEstado(EstadoSolicitud.EN_ESPERA_STOCK);
            solicitud.setFechaSolicitud(Instant.now());
            solicitud.setUsuario(usuarioActual);
            System.out.println("👤 Usuario solicitante: " + usuarioActual.getUsername());

            // Guardar en base de datos
            solicitudMovimientoRepository.save(solicitud);
            System.out.println("✅ Solicitud guardada con éxito en la base de datos.");

            // Enviar correo
            correoService.enviarCorreoNuevaSolicitudMovimiento(solicitud);
            System.out.println("📧 Correo enviado al administrador de empresa.");

        } catch (Exception e) {
            System.err.println("❌ Error al crear la solicitud de movimiento:");
            e.printStackTrace();
            throw new RuntimeException("Error interno al crear la solicitud");
        }
    }


}
