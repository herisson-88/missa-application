package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class PartageMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartageMetaGroupeDTO.class);
        PartageMetaGroupeDTO partageMetaGroupeDTO1 = new PartageMetaGroupeDTO();
        partageMetaGroupeDTO1.setId(1L);
        PartageMetaGroupeDTO partageMetaGroupeDTO2 = new PartageMetaGroupeDTO();
        assertThat(partageMetaGroupeDTO1).isNotEqualTo(partageMetaGroupeDTO2);
        partageMetaGroupeDTO2.setId(partageMetaGroupeDTO1.getId());
        assertThat(partageMetaGroupeDTO1).isEqualTo(partageMetaGroupeDTO2);
        partageMetaGroupeDTO2.setId(2L);
        assertThat(partageMetaGroupeDTO1).isNotEqualTo(partageMetaGroupeDTO2);
        partageMetaGroupeDTO1.setId(null);
        assertThat(partageMetaGroupeDTO1).isNotEqualTo(partageMetaGroupeDTO2);
    }
}
