package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class ReseauDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReseauDTO.class);
        ReseauDTO reseauDTO1 = new ReseauDTO();
        reseauDTO1.setId(1L);
        ReseauDTO reseauDTO2 = new ReseauDTO();
        assertThat(reseauDTO1).isNotEqualTo(reseauDTO2);
        reseauDTO2.setId(reseauDTO1.getId());
        assertThat(reseauDTO1).isEqualTo(reseauDTO2);
        reseauDTO2.setId(2L);
        assertThat(reseauDTO1).isNotEqualTo(reseauDTO2);
        reseauDTO1.setId(null);
        assertThat(reseauDTO1).isNotEqualTo(reseauDTO2);
    }
}
