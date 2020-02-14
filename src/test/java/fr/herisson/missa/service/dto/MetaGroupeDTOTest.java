package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaGroupeDTO.class);
        MetaGroupeDTO metaGroupeDTO1 = new MetaGroupeDTO();
        metaGroupeDTO1.setId(1L);
        MetaGroupeDTO metaGroupeDTO2 = new MetaGroupeDTO();
        assertThat(metaGroupeDTO1).isNotEqualTo(metaGroupeDTO2);
        metaGroupeDTO2.setId(metaGroupeDTO1.getId());
        assertThat(metaGroupeDTO1).isEqualTo(metaGroupeDTO2);
        metaGroupeDTO2.setId(2L);
        assertThat(metaGroupeDTO1).isNotEqualTo(metaGroupeDTO2);
        metaGroupeDTO1.setId(null);
        assertThat(metaGroupeDTO1).isNotEqualTo(metaGroupeDTO2);
    }
}
