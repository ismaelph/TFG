package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.entity.SolicitudPersonalizada;
import com.tfg.backend.service.CorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CorreoServiceImpl implements CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCorreo(String para, String asunto, String contenido) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(para);
            mensaje.setSubject(asunto);
            mensaje.setText(contenido);

            System.out.println("📧 Enviando correo a: " + para);
            System.out.println("📝 Asunto: " + asunto);
            System.out.println("🗒 Contenido: " + contenido);

            mailSender.send(mensaje);
            System.out.println("✅ Correo enviado correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al enviar el correo:");
            e.printStackTrace();
        }
    }

    @Override
    public void enviarCorreoNuevaSolicitudMovimiento(SolicitudMovimiento solicitud) {
        try {
            System.out.println("🧪 [DEBUG] Evaluando usuarios de la empresa:");
            solicitud.getUsuario().getEmpresa().getUsuarios()
                    .forEach(u -> {
                        System.out.println("   👤 Usuario: " + u.getUsername());
                        u.getRoles().forEach(r -> System.out.println("      ➤ Rol: " + r.getName()));
                    });

            Optional<User> adminOpt = solicitud.getUsuario().getEmpresa().getUsuarios()
                    .stream()
                    .filter(u -> u.getRoles().stream()
                            .anyMatch(r -> "ROLE_ADMIN_EMPRESA".equals(r.getName().name())))
                    .findFirst();

            if (adminOpt.isEmpty()) {
                System.err.println("🟥 No se encontró admin con ROLE_ADMIN_EMPRESA en esta empresa.");
                return;
            }

            String adminCorreo = adminOpt.get().getEmail();
            System.out.println("📤 Enviando correo de solicitud a: " + adminCorreo);

            // 💡 Usamos solo el ID del producto para evitar NullPointer si el nombre no está cargado
            String contenido = String.format(
                    "El empleado %s ha solicitado un producto.\n\nID Producto: %d\nCantidad: %d\nMotivo: %s",
                    solicitud.getUsuario().getUsername(),
                    solicitud.getProducto().getId(),
                    solicitud.getCantidadSolicitada(),
                    solicitud.getMotivo()
            );

            String asunto = "📥 Nueva solicitud de producto";
            enviarCorreo(adminCorreo, asunto, contenido);

        } catch (Exception e) {
            System.err.println("❌ Error al enviar correo de solicitud de movimiento:");
            e.printStackTrace();
        }
    }



    @Override
    public void enviarCorreoNuevaSolicitudPersonalizada(SolicitudPersonalizada solicitud) {
        try {
            System.out.println("🧪 [DEBUG] Evaluando usuarios de la empresa:");
            solicitud.getUsuario().getEmpresa().getUsuarios()
                    .forEach(u -> {
                        System.out.println("   👤 Usuario: " + u.getUsername());
                        u.getRoles().forEach(r -> System.out.println("      ➤ Rol: " + r.getName()));
                    });

            Optional<User> adminOpt = solicitud.getUsuario().getEmpresa().getUsuarios()
                    .stream()
                    .filter(u -> u.getRoles().stream()
                            .anyMatch(r -> "ROLE_ADMIN_EMPRESA".equals(r.getName().name()))) // 🔁 Aquí la corrección clave
                    .findFirst();

            if (adminOpt.isEmpty()) {
                System.err.println("⚠️ No se encontró admin con ROLE_ADMIN_EMPRESA en esta empresa.");
                return;
            }

            String adminCorreo = adminOpt.get().getEmail();
            System.out.println("📤 Enviando correo de solicitud personalizada a admin: " + adminCorreo);

            String asunto = "📥 Nueva solicitud personalizada";
            String contenido = String.format(
                    "El empleado %s ha solicitado un producto personalizado.\n\n" +
                            "Nombre propuesto: %s\nCantidad: %d\nMotivo: %s",
                    solicitud.getUsuario().getUsername(),
                    solicitud.getNombreProductoSugerido(),
                    solicitud.getCantidadDeseada(),
                    solicitud.getDescripcion()
            );

            enviarCorreo(adminCorreo, asunto, contenido);

        } catch (Exception e) {
            System.err.println("❌ Error al enviar correo de solicitud personalizada:");
            e.printStackTrace();
        }
    }

}
