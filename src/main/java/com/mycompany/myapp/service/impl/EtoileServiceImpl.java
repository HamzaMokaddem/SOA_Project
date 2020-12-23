package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.EtoileService;
import com.mycompany.myapp.domain.Etoile;
import com.mycompany.myapp.repository.EtoileRepository;
import com.mycompany.myapp.service.dto.EtoileDTO;
import com.mycompany.myapp.service.mapper.EtoileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Etoile}.
 */
@Service
@Transactional
public class EtoileServiceImpl implements EtoileService {

    private final Logger log = LoggerFactory.getLogger(EtoileServiceImpl.class);

    private final EtoileRepository etoileRepository;

    private final EtoileMapper etoileMapper;

    public EtoileServiceImpl(EtoileRepository etoileRepository, EtoileMapper etoileMapper) {
        this.etoileRepository = etoileRepository;
        this.etoileMapper = etoileMapper;
    }

    @Override
    public EtoileDTO save(EtoileDTO etoileDTO) {
        log.debug("Request to save Etoile : {}", etoileDTO);
        Etoile etoile = etoileMapper.toEntity(etoileDTO);
        etoile = etoileRepository.save(etoile);
        return etoileMapper.toDto(etoile);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EtoileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Etoiles");
        return etoileRepository.findAll(pageable)
            .map(etoileMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EtoileDTO> findOne(Long id) {
        log.debug("Request to get Etoile : {}", id);
        return etoileRepository.findById(id)
            .map(etoileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etoile : {}", id);
        etoileRepository.deleteById(id);
    }
}
