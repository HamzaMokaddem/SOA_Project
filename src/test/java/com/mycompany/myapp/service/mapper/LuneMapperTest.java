package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LuneMapperTest {

    private LuneMapper luneMapper;

    @BeforeEach
    public void setUp() {
        luneMapper = new LuneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(luneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(luneMapper.fromId(null)).isNull();
    }
}
