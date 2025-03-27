package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.model.repository.ProveedoresRepository;
import com.tfg.backend.service.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedoresService {
    @Autowired
    private ProveedoresRepository proveedoresRepository;


    @Override
    public Proveedor save(Proveedor proveedor) {
        return proveedoresRepository.save(proveedor);
    }

    @Override
    public Proveedor update(Proveedor proveedor, long id) {
        Proveedor pBD = proveedoresRepository.findById(id);
        if (pBD != null) {
            pBD.setNombre(proveedor.getNombre());
            pBD.setEmail(proveedor.getEmail());
            pBD.setTelefono(proveedor.getTelefono());
            pBD.setDireccion(proveedor.getDireccion());
            proveedoresRepository.save(pBD);
        }
        return pBD;
    }

    @Override
    public void delete(long id) {
        proveedoresRepository.deleteById(id);
    }

    @Override
    public List<Proveedor> findAll() {
        return proveedoresRepository.findAll();
    }

    @Override
    public Proveedor findById(long id) {
        return proveedoresRepository.findById(id);
    }

    @Override
    public Proveedor findByEmail(String email) {
        return proveedoresRepository.findByEmail(email);
    }

    @Override
    public Proveedor findByNombre(String nombre) {
        return proveedoresRepository.findByNombre(nombre);
    }
}
