package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MembreMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreMetaGroupeDTO.class);
        MembreMetaGroupeDTO membreMetaGroupeDTO1 = new MembreMetaGroupeDTO();
        membreMetaGroupeDTO1.setId(1L);
        MembreMetaGroupeDTO membreMetaGroupeDTO2 = new MembreMetaGroupeDTO();
        assertThat(membreMetaGroupeDTO1).isNotEqualTo(membreMetaGroupeDTO2);
        membreMetaGroupeDTO2.setId(membreMetaGroupeDTO1.getId());
        assertThat(membreMetaGroupeDTO1).isEqualTo(membreMetaGroupeDTO2);
        membreMetaGroupeDTO2.setId(2L);
        assertThat(membreMetaGroupeDTO1).isNotEqualTo(membreMetaGroupeDTO2);
        membreMetaGroupeDTO1.setId(null);
        assertThat(membreMetaGroupeDTO1).isNotEqualTo(membreMetaGroupeDTO2);
    }
}
