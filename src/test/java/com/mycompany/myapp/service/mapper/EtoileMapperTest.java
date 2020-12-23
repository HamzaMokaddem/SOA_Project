package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EtoileMapperTest {

    private EtoileMapper etoileMapper;

    @BeforeEach
    public void setUp() {
        etoileMapper = new EtoileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(etoileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(etoileMapper.fromId(null)).isNull();
    }
}
