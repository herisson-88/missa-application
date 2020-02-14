package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class PartageMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartageMetaGroupe.class);
        PartageMetaGroupe partageMetaGroupe1 = new PartageMetaGroupe();
        partageMetaGroupe1.setId(1L);
        PartageMetaGroupe partageMetaGroupe2 = new PartageMetaGroupe();
        partageMetaGroupe2.setId(partageMetaGroupe1.getId());
        assertThat(partageMetaGroupe1).isEqualTo(partageMetaGroupe2);
        partageMetaGroupe2.setId(2L);
        assertThat(partageMetaGroupe1).isNotEqualTo(partageMetaGroupe2);
        partageMetaGroupe1.setId(null);
        assertThat(partageMetaGroupe1).isNotEqualTo(partageMetaGroupe2);
    }
}
