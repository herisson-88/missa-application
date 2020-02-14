package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class LieuMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LieuMetaGroupeDTO.class);
        LieuMetaGroupeDTO lieuMetaGroupeDTO1 = new LieuMetaGroupeDTO();
        lieuMetaGroupeDTO1.setId(1L);
        LieuMetaGroupeDTO lieuMetaGroupeDTO2 = new LieuMetaGroupeDTO();
        assertThat(lieuMetaGroupeDTO1).isNotEqualTo(lieuMetaGroupeDTO2);
        lieuMetaGroupeDTO2.setId(lieuMetaGroupeDTO1.getId());
        assertThat(lieuMetaGroupeDTO1).isEqualTo(lieuMetaGroupeDTO2);
        lieuMetaGroupeDTO2.setId(2L);
        assertThat(lieuMetaGroupeDTO1).isNotEqualTo(lieuMetaGroupeDTO2);
        lieuMetaGroupeDTO1.setId(null);
        assertThat(lieuMetaGroupeDTO1).isNotEqualTo(lieuMetaGroupeDTO2);
    }
}
