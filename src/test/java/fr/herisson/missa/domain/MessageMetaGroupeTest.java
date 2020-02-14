package fr.herisson.missa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MessageMetaGroupeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageMetaGroupe.class);
        MessageMetaGroupe messageMetaGroupe1 = new MessageMetaGroupe();
        messageMetaGroupe1.setId(1L);
        MessageMetaGroupe messageMetaGroupe2 = new MessageMetaGroupe();
        messageMetaGroupe2.setId(messageMetaGroupe1.getId());
        assertThat(messageMetaGroupe1).isEqualTo(messageMetaGroupe2);
        messageMetaGroupe2.setId(2L);
        assertThat(messageMetaGroupe1).isNotEqualTo(messageMetaGroupe2);
        messageMetaGroupe1.setId(null);
        assertThat(messageMetaGroupe1).isNotEqualTo(messageMetaGroupe2);
    }
}
