package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class DateMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DateMetaGroupe.class);
        DateMetaGroupe dateMetaGroupe1 = new DateMetaGroupe();
        dateMetaGroupe1.setId(1L);
        DateMetaGroupe dateMetaGroupe2 = new DateMetaGroupe();
        dateMetaGroupe2.setId(dateMetaGroupe1.getId());
        assertThat(dateMetaGroupe1).isEqualTo(dateMetaGroupe2);
        dateMetaGroupe2.setId(2L);
        assertThat(dateMetaGroupe1).isNotEqualTo(dateMetaGroupe2);
        dateMetaGroupe1.setId(null);
        assertThat(dateMetaGroupe1).isNotEqualTo(dateMetaGroupe2);
    }
}
