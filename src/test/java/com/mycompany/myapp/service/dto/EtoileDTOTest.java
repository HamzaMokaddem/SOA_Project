package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EtoileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtoileDTO.class);
        EtoileDTO etoileDTO1 = new EtoileDTO();
        etoileDTO1.setId(1L);
        EtoileDTO etoileDTO2 = new EtoileDTO();
        assertThat(etoileDTO1).isNotEqualTo(etoileDTO2);
        etoileDTO2.setId(etoileDTO1.getId());
        assertThat(etoileDTO1).isEqualTo(etoileDTO2);
        etoileDTO2.setId(2L);
        assertThat(etoileDTO1).isNotEqualTo(etoileDTO2);
        etoileDTO1.setId(null);
        assertThat(etoileDTO1).isNotEqualTo(etoileDTO2);
    }
}
