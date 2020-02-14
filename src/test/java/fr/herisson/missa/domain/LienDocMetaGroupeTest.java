package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class LienDocMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LienDocMetaGroupe.class);
        LienDocMetaGroupe lienDocMetaGroupe1 = new LienDocMetaGroupe();
        lienDocMetaGroupe1.setId(1L);
        LienDocMetaGroupe lienDocMetaGroupe2 = new LienDocMetaGroupe();
        lienDocMetaGroupe2.setId(lienDocMetaGroupe1.getId());
        assertThat(lienDocMetaGroupe1).isEqualTo(lienDocMetaGroupe2);
        lienDocMetaGroupe2.setId(2L);
        assertThat(lienDocMetaGroupe1).isNotEqualTo(lienDocMetaGroupe2);
        lienDocMetaGroupe1.setId(null);
        assertThat(lienDocMetaGroupe1).isNotEqualTo(lienDocMetaGroupe2);
    }
}
