package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class OrganisateurMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisateurMetaGroupe.class);
        OrganisateurMetaGroupe organisateurMetaGroupe1 = new OrganisateurMetaGroupe();
        organisateurMetaGroupe1.setId(1L);
        OrganisateurMetaGroupe organisateurMetaGroupe2 = new OrganisateurMetaGroupe();
        organisateurMetaGroupe2.setId(organisateurMetaGroupe1.getId());
        assertThat(organisateurMetaGroupe1).isEqualTo(organisateurMetaGroupe2);
        organisateurMetaGroupe2.setId(2L);
        assertThat(organisateurMetaGroupe1).isNotEqualTo(organisateurMetaGroupe2);
        organisateurMetaGroupe1.setId(null);
        assertThat(organisateurMetaGroupe1).isNotEqualTo(organisateurMetaGroupe2);
    }
}
