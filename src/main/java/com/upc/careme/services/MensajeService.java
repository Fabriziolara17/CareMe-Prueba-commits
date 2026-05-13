package com.upc.careme.services;

import com.upc.careme.dtos.MensajeDTO;
import com.upc.careme.dtos.MensajeRequestDTO;
import com.upc.careme.entidades.Mensaje;
import com.upc.careme.entidades.Servicio;
import com.upc.careme.entidades.Usuario;
import com.upc.careme.repositorios.MensajeRepository;
import com.upc.careme.repositorios.ServicioRepository;
import com.upc.careme.repositorios.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;


    public MensajeDTO insertar(MensajeDTO dto) {
        Mensaje entidad = modelMapper.map(dto, Mensaje.class);
        entidad = mensajeRepository.save(entidad);
        return modelMapper.map(entidad, MensajeDTO.class);
    }

    public List<MensajeDTO> listar() {
        return mensajeRepository.findAll()
                .stream()
                .map(entidad -> modelMapper.map(entidad, MensajeDTO.class))
                .toList();
    }


    public String enviarMensaje(MensajeRequestDTO request) {

        Servicio servicio = servicioRepository.findById(request.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado. No se puede chatear fuera de un servicio."));


        Usuario remitente = usuarioRepository.findById(request.getIdRemitente())
                .orElseThrow(() -> new IllegalArgumentException("Remitente no encontrado."));


        Mensaje mensaje = new Mensaje();
        mensaje.setServicio(servicio);
        mensaje.setRemitente(remitente);
        mensaje.setContenido(request.getContenido());
        mensaje.setArchivoUrl(request.getArchivoUrl());
        mensaje.setFechaEnvio(LocalDateTime.now());


        mensajeRepository.save(mensaje);

        return "Mensaje enviado a las " + mensaje.getFechaEnvio().toLocalTime();
    }

    public List<MensajeDTO> historialPorServicio(Integer idServicio) {
        return mensajeRepository.findByServicioIdServicioOrderByFechaEnvioAsc(idServicio)
                .stream()
                .map(entidad -> modelMapper.map(entidad, MensajeDTO.class))
                .toList();
    }
}