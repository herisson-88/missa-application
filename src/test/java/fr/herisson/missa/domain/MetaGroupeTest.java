package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaGroupe.class);
        MetaGroupe metaGroupe1 = new MetaGroupe();
        metaGroupe1.setId(1L);
        MetaGroupe metaGroupe2 = new MetaGroupe();
        metaGroupe2.setId(metaGroupe1.getId());
        assertThat(metaGroupe1).isEqualTo(metaGroupe2);
        metaGroupe2.setId(2L);
        assertThat(metaGroupe1).isNotEqualTo(metaGroupe2);
        metaGroupe1.setId(null);
        assertThat(metaGroupe1).isNotEqualTo(metaGroupe2);
    }
}
