package com.upc.careme.services;

import com.upc.careme.dtos.PacienteDTO;
import com.upc.careme.entidades.Paciente;
import com.upc.careme.repositorios.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PacienteDTO insertar(PacienteDTO dto) {
        Paciente entidad = modelMapper.map(dto, Paciente.class);
        entidad = pacienteRepository.save(entidad);
        return modelMapper.map(entidad, PacienteDTO.class);
    }

    public List<PacienteDTO> listar() {
        return pacienteRepository.findAll()
                .stream()
                .map(entidad -> modelMapper.map(entidad, PacienteDTO.class))
                .toList();
    }
}