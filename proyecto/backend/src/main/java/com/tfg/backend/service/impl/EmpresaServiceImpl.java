package com.tfg.backend.service.impl;


import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.repository.EmpresaRepository;
import com.tfg.backend.service.EmpresaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;


    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa update(Empresa empresa, long id) {
        Empresa actual = empresaRepository.findById(id);
        if (actual != null) {
            actual.setNombre(empresa.getNombre());
            actual.setClaveAcceso(empresa.getClaveAcceso());
            return empresaRepository.save(actual);
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
    public void delete(long id) {
        empresaRepository.deleteById(id);
    }
}
