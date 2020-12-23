package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.LuneService;
import com.mycompany.myapp.domain.Lune;
import com.mycompany.myapp.repository.LuneRepository;
import com.mycompany.myapp.service.dto.LuneDTO;
import com.mycompany.myapp.service.mapper.LuneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Lune}.
 */
@Service
@Transactional
public class LuneServiceImpl implements LuneService {

    private final Logger log = LoggerFactory.getLogger(LuneServiceImpl.class);

    private final LuneRepository luneRepository;

    private final LuneMapper luneMapper;

    public LuneServiceImpl(LuneRepository luneRepository, LuneMapper luneMapper) {
        this.luneRepository = luneRepository;
        this.luneMapper = luneMapper;
    }

    @Override
    public LuneDTO save(LuneDTO luneDTO) {
        log.debug("Request to save Lune : {}", luneDTO);
        Lune lune = luneMapper.toEntity(luneDTO);
        lune = luneRepository.save(lune);
        return luneMapper.toDto(lune);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LuneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lunes");
        return luneRepository.findAll(pageable)
            .map(luneMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LuneDTO> findOne(Long id) {
        log.debug("Request to get Lune : {}", id);
        return luneRepository.findById(id)
            .map(luneMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lune : {}", id);
        luneRepository.deleteById(id);
    }
}
