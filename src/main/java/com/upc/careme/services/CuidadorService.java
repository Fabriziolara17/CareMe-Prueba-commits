package com.upc.careme.services;

import com.upc.careme.dtos.CuidadorDTO;
import com.upc.careme.entidades.Cuidador;
import com.upc.careme.repositorios.CuidadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuidadorService {

    @Autowired
    private CuidadorRepository cuidadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CuidadorDTO insertar(CuidadorDTO dto) {
        Cuidador entidad = modelMapper.map(dto, Cuidador.class);
        entidad = cuidadorRepository.save(entidad);
        return modelMapper.map(entidad, CuidadorDTO.class);
    }

    public List<CuidadorDTO> listar() {
        return cuidadorRepository.findAll()
                .stream()
                .map(entidad -> modelMapper.map(entidad, CuidadorDTO.class))
                .toList();
    }

    public List<CuidadorDTO> buscarPorEspecialidad(String especialidad) {
        return cuidadorRepository.findByEspecialidadContainingIgnoreCase(especialidad)
                .stream()
                .map(entidad -> modelMapper.map(entidad, CuidadorDTO.class))
                .toList();
    }

    public List<CuidadorDTO> buscarPorUbicacion(String ubicacion) {
        return cuidadorRepository.findByUbicacionContainingIgnoreCase(ubicacion)
                .stream()
                .map(entidad -> modelMapper.map(entidad, CuidadorDTO.class))
                .toList();
    }

    public List<CuidadorDTO> buscarPorFiltros(String ubicacion, String especialidad, String disponibilidad) {
        return cuidadorRepository.buscarPorFiltros(ubicacion, especialidad, disponibilidad)
                .stream()
                .map(entidad -> modelMapper.map(entidad, CuidadorDTO.class))
                .toList();
    }

    public List<CuidadorDTO> buscarPorCondicionMedica(String condicion) {
        if (condicion == null || condicion.isBlank()) {
            throw new IllegalArgumentException("Debe indicar una condición médica para buscar.");
        }
        return cuidadorRepository.buscarPorCondicionMedica(condicion)
                .stream()
                .map(entidad -> modelMapper.map(entidad, CuidadorDTO.class))
                .toList();
    }
}