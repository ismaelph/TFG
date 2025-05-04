package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.repository.EmpresaRepository;
import com.tfg.backend.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa update(Empresa empresa, long id) {
        Empresa empresaBD = empresaRepository.findById(id);
        if (empresaBD != null) {
            empresa.setId(empresa.getId());
            empresa.setNombre(empresa.getNombre());
            empresa.setPassword(empresa.getPassword());
            empresa.setAdmins(empresa.getAdmins());
            return empresaRepository.save(empresa);
        }
        return empresaBD;
    }

    @Override
    public void delete(long id) {
        empresaRepository.deleteById(id);
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
    public Empresa findByNombre(String nombre) {
        return empresaRepository.findByNombre(nombre);
    }

    @Override
    public void agregarAdminAEmpresa(Empresa empresa, User admin) {
        empresa.getAdmins().add(admin);
        empresaRepository.save(empresa);
    }

    @Override
    public void eliminarAdminDeEmpresa(Empresa empresa, User admin) {
        empresa.getAdmins().remove(admin);
        empresaRepository.save(empresa);
    }
}
