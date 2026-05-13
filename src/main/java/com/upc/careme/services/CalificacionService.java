package com.upc.careme.services;

import com.upc.careme.dtos.CalificacionDTO;
import com.upc.careme.dtos.CalificacionRequestDTO;
import com.upc.careme.entidades.Calificacion;
import com.upc.careme.entidades.Cuidador;
import com.upc.careme.entidades.Familiar;
import com.upc.careme.entidades.Servicio;
import com.upc.careme.repositorios.CalificacionRepository;
import com.upc.careme.repositorios.CuidadorRepository;
import com.upc.careme.repositorios.FamiliarRepository;
import com.upc.careme.repositorios.ServicioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private FamiliarRepository familiarRepository;

    @Autowired
    private CuidadorRepository cuidadorRepository;

    @Autowired
    private ModelMapper modelMapper;


    public CalificacionDTO insertar(CalificacionDTO dto) {
        Calificacion entidad = modelMapper.map(dto, Calificacion.class);
        entidad = calificacionRepository.save(entidad);
        return modelMapper.map(entidad, CalificacionDTO.class);
    }

    public List<CalificacionDTO> listar() {
        return calificacionRepository.findAll()
                .stream()
                .map(entidad -> modelMapper.map(entidad, CalificacionDTO.class))
                .toList();
    }


    public String registrarResena(CalificacionRequestDTO request) {

        Servicio servicio = servicioRepository.findById(request.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("El servicio especificado no existe."));

        if (!"finalizado".equals(servicio.getEstado())) {
            throw new IllegalArgumentException("Solo puedes calificar servicios que ya han sido pagados y finalizados.");
        }


        Familiar familiar = familiarRepository.findById(request.getIdFamiliar())
                .orElseThrow(() -> new IllegalArgumentException("Familiar no encontrado."));

        Cuidador cuidador = cuidadorRepository.findById(request.getIdCuidador())
                .orElseThrow(() -> new IllegalArgumentException("Cuidador no encontrado."));


        if (request.getPuntuacion() < 1 || request.getPuntuacion() > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5 estrellas.");
        }


        Calificacion calificacion = new Calificacion();
        calificacion.setServicio(servicio);
        calificacion.setFamiliar(familiar);
        calificacion.setCuidador(cuidador);
        calificacion.setPuntuacion(request.getPuntuacion());
        calificacion.setComentario(request.getComentario());
        calificacion.setFechaRegistro(LocalDateTime.now());

        calificacionRepository.save(calificacion);

        return "¡Gracias por tu reseña! Le has dado " + request.getPuntuacion() + " estrellas al cuidador y ayudado a la comunidad.";
    }
}