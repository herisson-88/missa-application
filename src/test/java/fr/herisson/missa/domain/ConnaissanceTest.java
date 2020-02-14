package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class ConnaissanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Connaissance.class);
        Connaissance connaissance1 = new Connaissance();
        connaissance1.setId(1L);
        Connaissance connaissance2 = new Connaissance();
        connaissance2.setId(connaissance1.getId());
        assertThat(connaissance1).isEqualTo(connaissance2);
        connaissance2.setId(2L);
        assertThat(connaissance1).isNotEqualTo(connaissance2);
        connaissance1.setId(null);
        assertThat(connaissance1).isNotEqualTo(connaissance2);
    }
}
