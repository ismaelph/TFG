package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.repository.EmpresasRepository;
import com.tfg.backend.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {
    @Autowired
    private EmpresasRepository empresasRepository;


    @Override
    public Empresa save(Empresa empresa) {
        return empresasRepository.save(empresa);
    }

    @Override
    public Empresa update(Empresa empresa, long id) {
        Empresa empresaBD = empresasRepository.findById(id);
        if (empresaBD != null) {
            empresaBD.setNombre(empresa.getNombre());
            empresaBD.setCif(empresa.getCif());
            empresaBD.setDireccion(empresa.getDireccion());
            empresaBD.setCiudad(empresa.getCiudad());
            empresaBD.setPais(empresa.getPais());
            empresaBD.setTelefono(empresa.getTelefono());
            empresaBD.setEmail(empresa.getEmail());
            empresaBD.setWeb(empresa.getWeb());
            empresaBD.setUsers(empresa.getUsers());
            empresasRepository.save(empresaBD);
        }
        return empresaBD;
    }

    @Override
    public void delete(long id) {
        empresasRepository.deleteById(id);
    }

    @Override
    public Empresa findById(long id) {
        return empresasRepository.findById(id);
    }

    @Override
    public List<Empresa> findAll() {
        return empresasRepository.findAll();
    }

    @Override
    public Empresa findByNombre(String nombre) {
        return empresasRepository.findByNombre(nombre);
    }
}
