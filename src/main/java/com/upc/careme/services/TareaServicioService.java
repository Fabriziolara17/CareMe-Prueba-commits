package com.upc.careme.services;

import com.upc.careme.dtos.TareaServicioDTO;
import com.upc.careme.entidades.TareaServicio;
import com.upc.careme.repositorios.TareaServicioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaServicioService {

    @Autowired
    private TareaServicioRepository tareaServicioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TareaServicioDTO insertar(TareaServicioDTO dto) {
        TareaServicio entidad = modelMapper.map(dto, TareaServicio.class);
        entidad = tareaServicioRepository.save(entidad);
        return modelMapper.map(entidad, TareaServicioDTO.class);
    }

    public List<TareaServicioDTO> listar() {
        return tareaServicioRepository.findAll()
                .stream()
                .map(entidad -> modelMapper.map(entidad, TareaServicioDTO.class))
                .toList();
    }
}