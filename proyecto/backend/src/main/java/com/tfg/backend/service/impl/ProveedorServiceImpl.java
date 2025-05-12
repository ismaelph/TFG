package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.model.repository.ProveedorRepository;
import com.tfg.backend.service.ProveedorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public Proveedor save(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
        return proveedorRepository.findById(proveedor.getId()).orElse(null);
    }

    @Override
    public Proveedor update(Proveedor proveedorEditado, Long id) {
        Proveedor proveedorBD = proveedorRepository.findById(id).orElse(null);
        if (proveedorBD != null) {
            proveedorBD.setNombre(proveedorEditado.getNombre());
            proveedorBD.setTelefono(proveedorEditado.getTelefono());
            proveedorBD.setEmail(proveedorEditado.getEmail());
            return proveedorRepository.save(proveedorBD);
        }
        return null;
    }

    @Override
    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Override
    public List<Proveedor> findByEmpresa(Empresa empresa) {
        return proveedorRepository.findByEmpresa(empresa);
    }

    @Override
    public void delete(Long id) {
        proveedorRepository.deleteById(id);
    }
}
