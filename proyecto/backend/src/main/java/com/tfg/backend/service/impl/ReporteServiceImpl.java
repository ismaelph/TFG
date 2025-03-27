package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Reporte;
import com.tfg.backend.model.repository.ReporteRepository;
import com.tfg.backend.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;


    @Override
    public Reporte save(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    @Override
    public Reporte update(Reporte reporte, long id) {
        Reporte rBD = reporteRepository.findById(id);
        if (rBD != null) {
            rBD.setUsuario(reporte.getUsuario());
            rBD.setNombreReporte(reporte.getNombreReporte());
            rBD.setFechaGeneracion(reporte.getFechaGeneracion());
            rBD.setArchivoPdf(reporte.getArchivoPdf());
            reporteRepository.save(rBD);
        }
        return rBD;
    }

    @Override
    public void delete(long id) {
        reporteRepository.deleteById(id);
    }

    @Override
    public Reporte findById(long id) {
        return reporteRepository.findById(id);
    }

    @Override
    public List<Reporte> findAll() {
        return reporteRepository.findAll();
    }

    @Override
    public Reporte findReportesUltimoMes() {
        return reporteRepository.findReportesUltimoMes();
    }
}
