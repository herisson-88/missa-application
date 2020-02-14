package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class EnvoiDeMailDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvoiDeMailDTO.class);
        EnvoiDeMailDTO envoiDeMailDTO1 = new EnvoiDeMailDTO();
        envoiDeMailDTO1.setId(1L);
        EnvoiDeMailDTO envoiDeMailDTO2 = new EnvoiDeMailDTO();
        assertThat(envoiDeMailDTO1).isNotEqualTo(envoiDeMailDTO2);
        envoiDeMailDTO2.setId(envoiDeMailDTO1.getId());
        assertThat(envoiDeMailDTO1).isEqualTo(envoiDeMailDTO2);
        envoiDeMailDTO2.setId(2L);
        assertThat(envoiDeMailDTO1).isNotEqualTo(envoiDeMailDTO2);
        envoiDeMailDTO1.setId(null);
        assertThat(envoiDeMailDTO1).isNotEqualTo(envoiDeMailDTO2);
    }
}
