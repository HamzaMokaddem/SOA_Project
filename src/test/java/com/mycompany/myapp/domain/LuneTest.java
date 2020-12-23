package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LuneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lune.class);
        Lune lune1 = new Lune();
        lune1.setId(1L);
        Lune lune2 = new Lune();
        lune2.setId(lune1.getId());
        assertThat(lune1).isEqualTo(lune2);
        lune2.setId(2L);
        assertThat(lune1).isNotEqualTo(lune2);
        lune1.setId(null);
        assertThat(lune1).isNotEqualTo(lune2);
    }
}
