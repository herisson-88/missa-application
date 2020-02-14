package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MissaUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MissaUserDTO.class);
        MissaUserDTO missaUserDTO1 = new MissaUserDTO();
        missaUserDTO1.setId(1L);
        MissaUserDTO missaUserDTO2 = new MissaUserDTO();
        assertThat(missaUserDTO1).isNotEqualTo(missaUserDTO2);
        missaUserDTO2.setId(missaUserDTO1.getId());
        assertThat(missaUserDTO1).isEqualTo(missaUserDTO2);
        missaUserDTO2.setId(2L);
        assertThat(missaUserDTO1).isNotEqualTo(missaUserDTO2);
        missaUserDTO1.setId(null);
        assertThat(missaUserDTO1).isNotEqualTo(missaUserDTO2);
    }
}
