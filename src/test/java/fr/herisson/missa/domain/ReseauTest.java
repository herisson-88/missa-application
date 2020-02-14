package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class ReseauTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reseau.class);
        Reseau reseau1 = new Reseau();
        reseau1.setId(1L);
        Reseau reseau2 = new Reseau();
        reseau2.setId(reseau1.getId());
        assertThat(reseau1).isEqualTo(reseau2);
        reseau2.setId(2L);
        assertThat(reseau1).isNotEqualTo(reseau2);
        reseau1.setId(null);
        assertThat(reseau1).isNotEqualTo(reseau2);
    }
}
