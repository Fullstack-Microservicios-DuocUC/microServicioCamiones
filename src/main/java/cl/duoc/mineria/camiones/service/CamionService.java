package cl.duoc.mineria.camiones.service;


import cl.duoc.mineria.camiones.exception.CamionNotFoundException;
import cl.duoc.mineria.camiones.exception.PatenteDuplicadaException;
import cl.duoc.mineria.camiones.mapper.CamionMapper;
import cl.duoc.mineria.camiones.model.ActualizarEstadoCamionDTO;
import cl.duoc.mineria.camiones.model.Camion;
import cl.duoc.mineria.camiones.model.RegistrarCamionDTO;
import cl.duoc.mineria.camiones.repository.CamionRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio encargado de gestionar el ciclo de vida y telemetría de los camiones autónomos.
 */
@Service
@RequiredArgsConstructor
public class CamionService {

    private static final Logger logger = LoggerFactory.getLogger(CamionService.class);
    private final CamionRepository camionRepository;
    private final CamionMapper camionMapper;

    @Transactional
    public Camion registrarCamion(RegistrarCamionDTO dto) {
        if (camionRepository.findByPatente(dto.getPatente()).isPresent()) {
            throw new PatenteDuplicadaException("La patente " + dto.getPatente() + " ya se encuentra registrada en la flota");
        }
        Camion nuevoCamion = camionMapper.toEntity(dto);
        return camionRepository.save(nuevoCamion);
    }

    @Transactional(readOnly = true)
    public List<Camion> listarFlota() {
        return camionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Camion obtenerPorId(Long id) {
        return camionRepository.findById(id)
                .orElseThrow(() -> new CamionNotFoundException("El camión con ID " + id + " no existe en los registros operativos."));
    }

    @Transactional
    public Camion actualizarEstado(Long id, ActualizarEstadoCamionDTO dto) {
        logger.info("Recibida solicitud para actualizar estado del camión ID {}: {}", id, dto);
        Camion camion = obtenerPorId(id);
        camion.setEstadoCamion(dto.getEstadoCamion());
        return camionRepository.save(camion);
    }

    // El método clave que usarán los otros microservicios mediante WebClient
    @Transactional(readOnly = true)
    public boolean existeCamion(Long id) {
        return camionRepository.existsById(id);
    }
}