package fr.herisson.missa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.herisson.missa.web.rest.TestUtil;

public class MessageMetaGroupeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageMetaGroupeDTO.class);
        MessageMetaGroupeDTO messageMetaGroupeDTO1 = new MessageMetaGroupeDTO();
        messageMetaGroupeDTO1.setId(1L);
        MessageMetaGroupeDTO messageMetaGroupeDTO2 = new MessageMetaGroupeDTO();
        assertThat(messageMetaGroupeDTO1).isNotEqualTo(messageMetaGroupeDTO2);
        messageMetaGroupeDTO2.setId(messageMetaGroupeDTO1.getId());
        assertThat(messageMetaGroupeDTO1).isEqualTo(messageMetaGroupeDTO2);
        messageMetaGroupeDTO2.setId(2L);
        assertThat(messageMetaGroupeDTO1).isNotEqualTo(messageMetaGroupeDTO2);
        messageMetaGroupeDTO1.setId(null);
        assertThat(messageMetaGroupeDTO1).isNotEqualTo(messageMetaGroupeDTO2);
    }
}
