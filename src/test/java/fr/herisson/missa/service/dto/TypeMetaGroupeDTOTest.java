package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class TypeMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeMetaGroupeDTO.class);
        TypeMetaGroupeDTO typeMetaGroupeDTO1 = new TypeMetaGroupeDTO();
        typeMetaGroupeDTO1.setId(1L);
        TypeMetaGroupeDTO typeMetaGroupeDTO2 = new TypeMetaGroupeDTO();
        assertThat(typeMetaGroupeDTO1).isNotEqualTo(typeMetaGroupeDTO2);
        typeMetaGroupeDTO2.setId(typeMetaGroupeDTO1.getId());
        assertThat(typeMetaGroupeDTO1).isEqualTo(typeMetaGroupeDTO2);
        typeMetaGroupeDTO2.setId(2L);
        assertThat(typeMetaGroupeDTO1).isNotEqualTo(typeMetaGroupeDTO2);
        typeMetaGroupeDTO1.setId(null);
        assertThat(typeMetaGroupeDTO1).isNotEqualTo(typeMetaGroupeDTO2);
    }
}
