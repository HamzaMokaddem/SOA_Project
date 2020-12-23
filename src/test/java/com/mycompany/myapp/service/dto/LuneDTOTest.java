package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LuneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LuneDTO.class);
        LuneDTO luneDTO1 = new LuneDTO();
        luneDTO1.setId(1L);
        LuneDTO luneDTO2 = new LuneDTO();
        assertThat(luneDTO1).isNotEqualTo(luneDTO2);
        luneDTO2.setId(luneDTO1.getId());
        assertThat(luneDTO1).isEqualTo(luneDTO2);
        luneDTO2.setId(2L);
        assertThat(luneDTO1).isNotEqualTo(luneDTO2);
        luneDTO1.setId(null);
        assertThat(luneDTO1).isNotEqualTo(luneDTO2);
    }
}
