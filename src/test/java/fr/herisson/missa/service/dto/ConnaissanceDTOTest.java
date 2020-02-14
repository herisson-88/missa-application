package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class ConnaissanceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnaissanceDTO.class);
        ConnaissanceDTO connaissanceDTO1 = new ConnaissanceDTO();
        connaissanceDTO1.setId(1L);
        ConnaissanceDTO connaissanceDTO2 = new ConnaissanceDTO();
        assertThat(connaissanceDTO1).isNotEqualTo(connaissanceDTO2);
        connaissanceDTO2.setId(connaissanceDTO1.getId());
        assertThat(connaissanceDTO1).isEqualTo(connaissanceDTO2);
        connaissanceDTO2.setId(2L);
        assertThat(connaissanceDTO1).isNotEqualTo(connaissanceDTO2);
        connaissanceDTO1.setId(null);
        assertThat(connaissanceDTO1).isNotEqualTo(connaissanceDTO2);
    }
}
