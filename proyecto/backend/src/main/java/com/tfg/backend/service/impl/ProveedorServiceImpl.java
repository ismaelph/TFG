package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.model.repository.ProveedorRepository;
import com.tfg.backend.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor update(Proveedor proveedor, long id) {
        Proveedor proveedorBd = proveedorRepository.findById(id);
        if (proveedorBd != null) {
            proveedorBd.setNombre(proveedor.getNombre());
            proveedorBd.setContacto(proveedor.getContacto());
            proveedorBd.setDireccion(proveedor.getDireccion());
            proveedorBd.setTelefono(proveedor.getTelefono());
            proveedorRepository.save(proveedorBd);
        }
        return proveedorBd;
    }

    @Override
    public void delete(long id) {
        proveedorRepository.deleteById(id);
    }

    @Override
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor findById(long id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public Proveedor findByNombre(String nombre) {
        return proveedorRepository.findByNombre(nombre);
    }
}
