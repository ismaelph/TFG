package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.*;
import com.tfg.backend.model.repository.AlmacenRepository;
import com.tfg.backend.model.repository.EstanteriaRepository;
import com.tfg.backend.model.repository.PlantaRepository;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.service.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EstanteriaRepository estanteriaRepository;

    @Autowired
    private PlantaRepository plantaRepository;

    @Autowired
    private AlmacenRepository almacenRepository;


    @Override
    public Producto save(Producto producto) {
        // Guardar producto inicialmente
        Producto guardado = productoRepository.save(producto);

        // 🔁 Recargar relaciones completas
        if (guardado.getEstanteria() != null && guardado.getEstanteria().getId() != null) {
            Estanteria est = estanteriaRepository.findById(guardado.getEstanteria().getId()).orElse(null);
            if (est != null && est.getPlanta() != null && est.getPlanta().getId() != null) {
                Planta pla = plantaRepository.findById(est.getPlanta().getId()).orElse(null);
                if (pla != null && pla.getAlmacen() != null && pla.getAlmacen().getId() != null) {
                    Almacen alm = almacenRepository.findById(pla.getAlmacen().getId()).orElse(null);
                    pla.setAlmacen(alm);
                }
                est.setPlanta(pla);
            }
            guardado.setEstanteria(est);
        }

        // ✅ Verificación de stock usando 'guardado'
        if (guardado.getCantidad() != null && guardado.getStockMinimo() != null &&
                guardado.getCantidad() <= guardado.getStockMinimo()) {
            System.out.println("⚠️ Producto con stock bajo: " + guardado.getNombre() +
                    " (Stock actual: " + guardado.getCantidad() +
                    ", mínimo recomendado: " + guardado.getStockMinimo() + ")");
        }

        return guardado;
    }



    @Override
    public Producto update(Producto productoEditado, Long id) {
        Producto productoBD = findById(id);

        if (productoEditado.getNombre() != null)
            productoBD.setNombre(productoEditado.getNombre());

        if (productoEditado.getPrecio() != null)
            productoBD.setPrecio(productoEditado.getPrecio());

        if (productoEditado.getCantidad() != null)
            productoBD.setCantidad(productoEditado.getCantidad());

        productoBD.setUsoInterno(productoEditado.isUsoInterno());

        if (productoEditado.getCategoria() != null)
            productoBD.setCategoria(productoEditado.getCategoria());

        if (productoEditado.getProveedor() != null)
            productoBD.setProveedor(productoEditado.getProveedor());

        if (productoEditado.getEstanteria() != null)
            productoBD.setEstanteria(productoEditado.getEstanteria());

        if (productoEditado.getStockMinimo() != null)
            productoBD.setStockMinimo(productoEditado.getStockMinimo());

        if (productoEditado.getImagenUrl() != null)
            productoBD.setImagenUrl(productoEditado.getImagenUrl());

        // ✅ Verificación correcta
        if (productoBD.getCantidad() != null && productoBD.getStockMinimo() != null &&
                productoBD.getCantidad() <= productoBD.getStockMinimo()) {
            System.out.println("⚠️ Producto con stock bajo: " + productoBD.getNombre() +
                    " (Stock actual: " + productoBD.getCantidad() +
                    ", mínimo recomendado: " + productoBD.getStockMinimo() + ")");
        }

        return productoRepository.save(productoBD);
    }



    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> findByEmpresa(Empresa empresa) {
        List<Producto> productos = productoRepository.findByEmpresa(empresa);

        for (Producto p : productos) {
            if (p.getEstanteria() != null && p.getEstanteria().getId() != null) {
                Estanteria est = estanteriaRepository.findById(p.getEstanteria().getId()).orElse(null);
                if (est != null && est.getPlanta() != null && est.getPlanta().getId() != null) {
                    Planta pla = plantaRepository.findById(est.getPlanta().getId()).orElse(null);
                    if (pla != null && pla.getAlmacen() != null && pla.getAlmacen().getId() != null) {
                        Almacen alm = almacenRepository.findById(pla.getAlmacen().getId()).orElse(null);
                        pla.setAlmacen(alm);
                    }
                    est.setPlanta(pla);
                }
                p.setEstanteria(est);
            }
        }

        return productos;
    }

}
