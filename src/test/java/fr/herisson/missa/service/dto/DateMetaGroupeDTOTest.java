package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class DateMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DateMetaGroupeDTO.class);
        DateMetaGroupeDTO dateMetaGroupeDTO1 = new DateMetaGroupeDTO();
        dateMetaGroupeDTO1.setId(1L);
        DateMetaGroupeDTO dateMetaGroupeDTO2 = new DateMetaGroupeDTO();
        assertThat(dateMetaGroupeDTO1).isNotEqualTo(dateMetaGroupeDTO2);
        dateMetaGroupeDTO2.setId(dateMetaGroupeDTO1.getId());
        assertThat(dateMetaGroupeDTO1).isEqualTo(dateMetaGroupeDTO2);
        dateMetaGroupeDTO2.setId(2L);
        assertThat(dateMetaGroupeDTO1).isNotEqualTo(dateMetaGroupeDTO2);
        dateMetaGroupeDTO1.setId(null);
        assertThat(dateMetaGroupeDTO1).isNotEqualTo(dateMetaGroupeDTO2);
    }
}
