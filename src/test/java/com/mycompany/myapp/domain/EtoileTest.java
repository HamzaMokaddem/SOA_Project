package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EtoileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etoile.class);
        Etoile etoile1 = new Etoile();
        etoile1.setId(1L);
        Etoile etoile2 = new Etoile();
        etoile2.setId(etoile1.getId());
        assertThat(etoile1).isEqualTo(etoile2);
        etoile2.setId(2L);
        assertThat(etoile1).isNotEqualTo(etoile2);
        etoile1.setId(null);
        assertThat(etoile1).isNotEqualTo(etoile2);
    }
}
