package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class TypeMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeMetaGroupe.class);
        TypeMetaGroupe typeMetaGroupe1 = new TypeMetaGroupe();
        typeMetaGroupe1.setId(1L);
        TypeMetaGroupe typeMetaGroupe2 = new TypeMetaGroupe();
        typeMetaGroupe2.setId(typeMetaGroupe1.getId());
        assertThat(typeMetaGroupe1).isEqualTo(typeMetaGroupe2);
        typeMetaGroupe2.setId(2L);
        assertThat(typeMetaGroupe1).isNotEqualTo(typeMetaGroupe2);
        typeMetaGroupe1.setId(null);
        assertThat(typeMetaGroupe1).isNotEqualTo(typeMetaGroupe2);
    }
}
