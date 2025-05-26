package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.repository.CategoriaRepository;
import com.tfg.backend.model.repository.EmpresaRepository;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.model.repository.ProveedorRepository;
import com.tfg.backend.service.EmpresaService;
import com.tfg.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Empresa save(Empresa empresa) {
        try {
            if (empresa.getClaveAcceso() != null && !empresa.getClaveAcceso().startsWith("$2a$")) {
                System.out.println("🔒 Encriptando clave de acceso para nueva empresa...");
                empresa.setClaveAcceso(passwordEncoder.encode(empresa.getClaveAcceso()));
            }
            Empresa guardada = empresaRepository.save(empresa);
            System.out.println("✅ Empresa guardada correctamente: " + guardada.getNombre());
            return guardada;
        } catch (Exception e) {
            System.out.println("❌ Error al guardar la empresa: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Empresa update(Empresa empresa, long id) {
        try {
            Empresa actual = empresaRepository.findById(id);
            if (actual != null) {
                actual.setNombre(empresa.getNombre());

                if (empresa.getClaveAcceso() != null && !empresa.getClaveAcceso().startsWith("$2a$")) {
                    System.out.println("🔄 Encriptando nueva clave de acceso para empresa actualizada...");
                    actual.setClaveAcceso(passwordEncoder.encode(empresa.getClaveAcceso()));
                } else {
                    actual.setClaveAcceso(empresa.getClaveAcceso()); // puede venir nula o ya codificada
                }

                Empresa actualizada = empresaRepository.save(actual);
                System.out.println("✅ Empresa actualizada: " + actualizada.getNombre());
                return actualizada;
            } else {
                System.out.println("⚠️ Empresa no encontrada para actualizar con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar empresa: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Empresa findById(long id) {
        return empresaRepository.findById(id);
    }

    @Override
    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(long id) {
        try {
            Empresa empresa = empresaRepository.findById(id);
            if (empresa != null) {
                System.out.println("🗑️ Eliminando empresa: " + empresa.getNombre());
                userService.procesarUsuariosAntesDeEliminarEmpresa(empresa);
                empresaRepository.deleteById(id);
                System.out.println("✅ Empresa eliminada correctamente.");
            } else {
                System.out.println("⚠️ Empresa no encontrada para eliminar con ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar empresa: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void eliminarEmpresaYDependencias(long id) {
        Empresa empresa = empresaRepository.findById(id);
        if (empresa == null) {
            throw new RuntimeException("Empresa no encontrada con ID: " + id);
        }

        System.out.println("[DELETE] Empresa encontrada: " + empresa.getNombre());

        // 1. Expulsar y degradar usuarios
        System.out.println("[DELETE] Expulsando y degradando usuarios...");
        userService.expulsarYDegradarUsuariosDeEmpresa(empresa);

        // 2. Eliminar proveedores
        System.out.println("[DELETE] Eliminando proveedores asociados...");
        proveedorRepository.deleteAllByEmpresa(empresa);

        // 3. Eliminar categorías
        System.out.println("[DELETE] Eliminando categorías asociadas...");
        categoriaRepository.deleteAllByEmpresa(empresa);

        // 4. Eliminar productos
        System.out.println("[DELETE] Eliminando proveedores asociados...");
        productoRepository.deleteAllByEmpresa(empresa);

        // 5. Eliminar empresa
        System.out.println("[DELETE] Eliminando empresa...");
        empresaRepository.deleteById(id);

        System.out.println("[DELETE] Empresa eliminada correctamente.");
    }

}
