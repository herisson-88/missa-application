package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class LienDocMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LienDocMetaGroupeDTO.class);
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO1 = new LienDocMetaGroupeDTO();
        lienDocMetaGroupeDTO1.setId(1L);
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO2 = new LienDocMetaGroupeDTO();
        assertThat(lienDocMetaGroupeDTO1).isNotEqualTo(lienDocMetaGroupeDTO2);
        lienDocMetaGroupeDTO2.setId(lienDocMetaGroupeDTO1.getId());
        assertThat(lienDocMetaGroupeDTO1).isEqualTo(lienDocMetaGroupeDTO2);
        lienDocMetaGroupeDTO2.setId(2L);
        assertThat(lienDocMetaGroupeDTO1).isNotEqualTo(lienDocMetaGroupeDTO2);
        lienDocMetaGroupeDTO1.setId(null);
        assertThat(lienDocMetaGroupeDTO1).isNotEqualTo(lienDocMetaGroupeDTO2);
    }
}
