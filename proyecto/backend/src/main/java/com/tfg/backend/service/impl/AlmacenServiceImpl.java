package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Almacen;
import com.tfg.backend.model.repository.AlmacenRepository;
import com.tfg.backend.service.AlmacenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AlmacenServiceImpl implements AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Override
    public Almacen save(Almacen almacen) {
        return almacenRepository.save(almacen);
    }

    @Override
    public Almacen update(Almacen almacen, long id) {
        Almacen actual = almacenRepository.findById(id).orElse(null);
        if (actual != null) {
            actual.setNombre(almacen.getNombre());
            actual.setDireccion(almacen.getDireccion());
            return almacenRepository.save(actual);
        }
        return null;
    }

    @Override
    public Almacen findById(long id) {
        return almacenRepository.findById(id).orElse(null);
    }

    @Override
    public List<Almacen> findAll() {
        return almacenRepository.findAll();
    }

    @Override
    public void delete(long id) {
        almacenRepository.deleteById(id);
    }

    @Override
    public List<Almacen> findByEmpresaId(long empresaId) {
        return almacenRepository.findByEmpresaId(empresaId);
    }
}
