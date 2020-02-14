package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class LieuMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LieuMetaGroupe.class);
        LieuMetaGroupe lieuMetaGroupe1 = new LieuMetaGroupe();
        lieuMetaGroupe1.setId(1L);
        LieuMetaGroupe lieuMetaGroupe2 = new LieuMetaGroupe();
        lieuMetaGroupe2.setId(lieuMetaGroupe1.getId());
        assertThat(lieuMetaGroupe1).isEqualTo(lieuMetaGroupe2);
        lieuMetaGroupe2.setId(2L);
        assertThat(lieuMetaGroupe1).isNotEqualTo(lieuMetaGroupe2);
        lieuMetaGroupe1.setId(null);
        assertThat(lieuMetaGroupe1).isNotEqualTo(lieuMetaGroupe2);
    }
}
