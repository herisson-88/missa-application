package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MembreMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreMetaGroupe.class);
        MembreMetaGroupe membreMetaGroupe1 = new MembreMetaGroupe();
        membreMetaGroupe1.setId(1L);
        MembreMetaGroupe membreMetaGroupe2 = new MembreMetaGroupe();
        membreMetaGroupe2.setId(membreMetaGroupe1.getId());
        assertThat(membreMetaGroupe1).isEqualTo(membreMetaGroupe2);
        membreMetaGroupe2.setId(2L);
        assertThat(membreMetaGroupe1).isNotEqualTo(membreMetaGroupe2);
        membreMetaGroupe1.setId(null);
        assertThat(membreMetaGroupe1).isNotEqualTo(membreMetaGroupe2);
    }
}
