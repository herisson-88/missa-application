package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MissaUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MissaUser.class);
        MissaUser missaUser1 = new MissaUser();
        missaUser1.setId(1L);
        MissaUser missaUser2 = new MissaUser();
        missaUser2.setId(missaUser1.getId());
        assertThat(missaUser1).isEqualTo(missaUser2);
        missaUser2.setId(2L);
        assertThat(missaUser1).isNotEqualTo(missaUser2);
        missaUser1.setId(null);
        assertThat(missaUser1).isNotEqualTo(missaUser2);
    }
}
