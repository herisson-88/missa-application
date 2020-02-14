package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class EnvoiDeMailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvoiDeMail.class);
        EnvoiDeMail envoiDeMail1 = new EnvoiDeMail();
        envoiDeMail1.setId(1L);
        EnvoiDeMail envoiDeMail2 = new EnvoiDeMail();
        envoiDeMail2.setId(envoiDeMail1.getId());
        assertThat(envoiDeMail1).isEqualTo(envoiDeMail2);
        envoiDeMail2.setId(2L);
        assertThat(envoiDeMail1).isNotEqualTo(envoiDeMail2);
        envoiDeMail1.setId(null);
        assertThat(envoiDeMail1).isNotEqualTo(envoiDeMail2);
    }
}
