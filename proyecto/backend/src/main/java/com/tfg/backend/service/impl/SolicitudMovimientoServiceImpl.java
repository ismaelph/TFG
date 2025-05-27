package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.model.dto.SolicitudMovimientoDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.MovimientoProducto;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.enums.EstadoSolicitud;
import com.tfg.backend.model.enums.TipoMovimiento;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.model.repository.SolicitudMovimientoRepository;
import com.tfg.backend.service.CorreoService;
import com.tfg.backend.service.MovimientoProductoService;
import com.tfg.backend.service.ProductoService;
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
    private CorreoService correoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MovimientoProductoService movimientoProductoService;

    @Override
    public SolicitudMovimiento save(SolicitudMovimiento solicitud) {
        System.out.println("🟢 SERVICE.save(): Recibido -> Producto = " +
                (solicitud.getProducto() != null ? solicitud.getProducto().getId() : "null") +
                ", Usuario = " +
                (solicitud.getUsuario() != null ? solicitud.getUsuario().getId() : "null"));

        // Recuperar entidades reales desde BDD
        if (solicitud.getProducto() != null && solicitud.getProducto().getId() != null) {
            Producto producto = productoRepository.findById(solicitud.getProducto().getId()).orElse(null);
            if (producto == null) {
                System.err.println("❌ Producto no encontrado con ID: " + solicitud.getProducto().getId());
            } else {
                solicitud.setProducto(producto);
            }
        }

        if (solicitud.getUsuario() != null && solicitud.getUsuario().getId() != null) {
            User user = userRepository.findById(solicitud.getUsuario().getId()).orElse(null);
            if (user == null) {
                System.err.println("❌ Usuario no encontrado con ID: " + solicitud.getUsuario().getId());
            } else {
                solicitud.setUsuario(user);
            }
        }

        System.out.println("💾 Guardando solicitud en base de datos...");
        return solicitudRepository.save(solicitud);
    }

    @Override
    public SolicitudMovimiento findById(Long id) {
        System.out.println("🔍 Buscando solicitud con ID: " + id);
        return solicitudRepository.findById(id).orElse(null);
    }

    @Override
    public List<SolicitudMovimiento> findAll() {
        System.out.println("📂 Listando todas las solicitudes");
        return solicitudRepository.findAll();
    }

    @Override
    public List<SolicitudMovimiento> findByUsuarioId(Long usuarioId) {
        System.out.println("🔍 Buscando solicitudes para usuario ID: " + usuarioId);
        User usuario = userRepository.findById(usuarioId).orElse(null);
        if (usuario != null) {
            return solicitudRepository.findByUsuario(usuario);
        }
        System.err.println("❌ Usuario no encontrado con ID: " + usuarioId);
        return List.of();
    }

    @Override
    public SolicitudMovimiento updateEstado(SolicitudMovimiento solicitud, Long id) {
        System.out.println("🔄 Actualizando estado de solicitud ID: " + id);
        SolicitudMovimiento actual = findById(id);
        if (actual != null) {
            actual.setEstado(solicitud.getEstado());
            actual.setRespuestaAdmin(solicitud.getRespuestaAdmin());
            actual.setFechaResolucion(solicitud.getFechaResolucion());
            return solicitudRepository.save(actual);
        }
        System.err.println("❌ Solicitud no encontrada para actualizar");
        return null;
    }

    @Override
    public void delete(Long id) {
        System.out.println("🗑 Eliminando solicitud con ID: " + id);
        solicitudRepository.deleteById(id);
    }

    @Override
    public void crearSolicitud(SolicitudMovimientoDto dto, User usuarioActual) {
        try {
            System.out.println("📥 Creando nueva solicitud de movimiento...");
            System.out.println("🔎 DTO recibido: productoId = " + dto.getProductoId() + ", cantidad = " + dto.getCantidadSolicitada());

            SolicitudMovimiento solicitud = dto.to();
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("❌ Producto no encontrado con ID: " + dto.getProductoId()));

            if (producto.getEmpresa() == null || usuarioActual.getEmpresa() == null ||
                    !producto.getEmpresa().getId().equals(usuarioActual.getEmpresa().getId())) {
                System.err.println("🚫 Empresa del producto y del usuario no coinciden.");
                throw new RuntimeException("❌ No puedes solicitar productos de otra empresa.");
            }

            solicitud.setProducto(producto);
            solicitud.setEstado(EstadoSolicitud.PENDIENTE);
            solicitud.setFechaSolicitud(Instant.now());
            solicitud.setUsuario(usuarioActual);

            solicitudRepository.save(solicitud);
            System.out.println("✅ Solicitud guardada correctamente");

            correoService.enviarCorreoNuevaSolicitudMovimiento(solicitud);
            System.out.println("📧 Correo enviado al administrador");

        } catch (Exception e) {
            System.err.println("❌ Error al crear la solicitud:");
            e.printStackTrace();
            throw new RuntimeException("Error interno al crear la solicitud");
        }
    }

    @Override
    public void aprobarSolicitud(Long id, String respuestaAdmin) {
        try {
            System.out.println("✅ Aprobando solicitud ID: " + id);
            SolicitudMovimiento solicitud = findById(id);
            if (solicitud == null) {
                System.err.println("❌ Solicitud no encontrada");
                throw new RuntimeException("Solicitud no encontrada");
            }

            Producto producto = solicitud.getProducto();
            int stockActual = producto.getCantidad();
            int cantidadSolicitada = solicitud.getCantidadSolicitada();

            System.out.println("📦 Stock actual: " + stockActual + " | Solicitado: " + cantidadSolicitada);

            if (stockActual >= cantidadSolicitada) {
                MovimientoProducto movimiento = MovimientoProducto.builder()
                        .producto(producto)
                        .usuario(solicitud.getUsuario())
                        .empresa(producto.getEmpresa())
                        .cantidad(cantidadSolicitada)
                        .tipo(TipoMovimiento.SALIDA)
                        .build();

                movimientoProductoService.save(movimiento);
                System.out.println("📤 Movimiento registrado correctamente");

                producto.setCantidad(stockActual - cantidadSolicitada);
                productoService.save(producto);
                System.out.println("🛠 Producto actualizado. Nuevo stock: " + producto.getCantidad());

                solicitud.setEstado(EstadoSolicitud.STOCK_RECIBIDO);
            } else {
                solicitud.setEstado(EstadoSolicitud.EN_ESPERA_STOCK);
                System.out.println("⚠️ Stock insuficiente");
            }

            solicitud.setLeida(true);
            solicitud.setFechaResolucion(Instant.now());
            solicitud.setRespuestaAdmin(respuestaAdmin);
            save(solicitud);
            System.out.println("💾 Solicitud finalizada y guardada");

        } catch (Exception e) {
            System.err.println("❌ Error en aprobarSolicitud:");
            e.printStackTrace();
            throw new RuntimeException("Error al aprobar solicitud de movimiento");
        }
    }

    @Override
    public List<SolicitudMovimiento> findByEmpresa(Empresa empresa) {
        System.out.println("🔍 Buscando solicitudes por empresa: " + empresa.getNombre());
        return solicitudRepository.findByUsuario_Empresa(empresa);
    }
}
