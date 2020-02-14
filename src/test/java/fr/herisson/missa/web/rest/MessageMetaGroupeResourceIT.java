package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.MessageMetaGroupe;
import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.repository.MessageMetaGroupeRepository;
import fr.herisson.missa.repository.search.MessageMetaGroupeSearchRepository;
import fr.herisson.missa.service.MessageMetaGroupeService;
import fr.herisson.missa.service.dto.MessageMetaGroupeDTO;
import fr.herisson.missa.service.mapper.MessageMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.MessageMetaGroupeCriteria;
import fr.herisson.missa.service.MessageMetaGroupeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static fr.herisson.missa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MessageMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class MessageMetaGroupeResourceIT {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLIQUE = false;
    private static final Boolean UPDATED_PUBLIQUE = true;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private MessageMetaGroupeRepository messageMetaGroupeRepository;

    @Autowired
    private MessageMetaGroupeMapper messageMetaGroupeMapper;

    @Autowired
    private MessageMetaGroupeService messageMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.MessageMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private MessageMetaGroupeSearchRepository mockMessageMetaGroupeSearchRepository;

    @Autowired
    private MessageMetaGroupeQueryService messageMetaGroupeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMessageMetaGroupeMockMvc;

    private MessageMetaGroupe messageMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageMetaGroupeResource messageMetaGroupeResource = new MessageMetaGroupeResource(messageMetaGroupeService, messageMetaGroupeQueryService);
        this.restMessageMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(messageMetaGroupeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageMetaGroupe createEntity(EntityManager em) {
        MessageMetaGroupe messageMetaGroupe = new MessageMetaGroupe()
            .titre(DEFAULT_TITRE)
            .publique(DEFAULT_PUBLIQUE)
            .message(DEFAULT_MESSAGE);
        return messageMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageMetaGroupe createUpdatedEntity(EntityManager em) {
        MessageMetaGroupe messageMetaGroupe = new MessageMetaGroupe()
            .titre(UPDATED_TITRE)
            .publique(UPDATED_PUBLIQUE)
            .message(UPDATED_MESSAGE);
        return messageMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        messageMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = messageMetaGroupeRepository.findAll().size();

        // Create the MessageMetaGroupe
        MessageMetaGroupeDTO messageMetaGroupeDTO = messageMetaGroupeMapper.toDto(messageMetaGroupe);
        restMessageMetaGroupeMockMvc.perform(post("/api/message-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the MessageMetaGroupe in the database
        List<MessageMetaGroupe> messageMetaGroupeList = messageMetaGroupeRepository.findAll();
        assertThat(messageMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        MessageMetaGroupe testMessageMetaGroupe = messageMetaGroupeList.get(messageMetaGroupeList.size() - 1);
        assertThat(testMessageMetaGroupe.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testMessageMetaGroupe.isPublique()).isEqualTo(DEFAULT_PUBLIQUE);
        assertThat(testMessageMetaGroupe.getMessage()).isEqualTo(DEFAULT_MESSAGE);

        // Validate the MessageMetaGroupe in Elasticsearch
        verify(mockMessageMetaGroupeSearchRepository, times(1)).save(testMessageMetaGroupe);
    }

    @Test
    @Transactional
    public void createMessageMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageMetaGroupeRepository.findAll().size();

        // Create the MessageMetaGroupe with an existing ID
        messageMetaGroupe.setId(1L);
        MessageMetaGroupeDTO messageMetaGroupeDTO = messageMetaGroupeMapper.toDto(messageMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMetaGroupeMockMvc.perform(post("/api/message-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageMetaGroupe in the database
        List<MessageMetaGroupe> messageMetaGroupeList = messageMetaGroupeRepository.findAll();
        assertThat(messageMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the MessageMetaGroupe in Elasticsearch
        verify(mockMessageMetaGroupeSearchRepository, times(0)).save(messageMetaGroupe);
    }


    @Test
    @Transactional
    public void getAllMessageMetaGroupes() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList
        restMessageMetaGroupeMockMvc.perform(get("/api/message-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].publique").value(hasItem(DEFAULT_PUBLIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getMessageMetaGroupe() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get the messageMetaGroupe
        restMessageMetaGroupeMockMvc.perform(get("/api/message-meta-groupes/{id}", messageMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.publique").value(DEFAULT_PUBLIQUE.booleanValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }


    @Test
    @Transactional
    public void getMessageMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        Long id = messageMetaGroupe.getId();

        defaultMessageMetaGroupeShouldBeFound("id.equals=" + id);
        defaultMessageMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultMessageMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMessageMetaGroupesByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where titre equals to DEFAULT_TITRE
        defaultMessageMetaGroupeShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the messageMetaGroupeList where titre equals to UPDATED_TITRE
        defaultMessageMetaGroupeShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByTitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where titre not equals to DEFAULT_TITRE
        defaultMessageMetaGroupeShouldNotBeFound("titre.notEquals=" + DEFAULT_TITRE);

        // Get all the messageMetaGroupeList where titre not equals to UPDATED_TITRE
        defaultMessageMetaGroupeShouldBeFound("titre.notEquals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultMessageMetaGroupeShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the messageMetaGroupeList where titre equals to UPDATED_TITRE
        defaultMessageMetaGroupeShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where titre is not null
        defaultMessageMetaGroupeShouldBeFound("titre.specified=true");

        // Get all the messageMetaGroupeList where titre is null
        defaultMessageMetaGroupeShouldNotBeFound("titre.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessageMetaGroupesByTitreContainsSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where titre contains DEFAULT_TITRE
        defaultMessageMetaGroupeShouldBeFound("titre.contains=" + DEFAULT_TITRE);

        // Get all the messageMetaGroupeList where titre contains UPDATED_TITRE
        defaultMessageMetaGroupeShouldNotBeFound("titre.contains=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByTitreNotContainsSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where titre does not contain DEFAULT_TITRE
        defaultMessageMetaGroupeShouldNotBeFound("titre.doesNotContain=" + DEFAULT_TITRE);

        // Get all the messageMetaGroupeList where titre does not contain UPDATED_TITRE
        defaultMessageMetaGroupeShouldBeFound("titre.doesNotContain=" + UPDATED_TITRE);
    }


    @Test
    @Transactional
    public void getAllMessageMetaGroupesByPubliqueIsEqualToSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where publique equals to DEFAULT_PUBLIQUE
        defaultMessageMetaGroupeShouldBeFound("publique.equals=" + DEFAULT_PUBLIQUE);

        // Get all the messageMetaGroupeList where publique equals to UPDATED_PUBLIQUE
        defaultMessageMetaGroupeShouldNotBeFound("publique.equals=" + UPDATED_PUBLIQUE);
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByPubliqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where publique not equals to DEFAULT_PUBLIQUE
        defaultMessageMetaGroupeShouldNotBeFound("publique.notEquals=" + DEFAULT_PUBLIQUE);

        // Get all the messageMetaGroupeList where publique not equals to UPDATED_PUBLIQUE
        defaultMessageMetaGroupeShouldBeFound("publique.notEquals=" + UPDATED_PUBLIQUE);
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByPubliqueIsInShouldWork() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where publique in DEFAULT_PUBLIQUE or UPDATED_PUBLIQUE
        defaultMessageMetaGroupeShouldBeFound("publique.in=" + DEFAULT_PUBLIQUE + "," + UPDATED_PUBLIQUE);

        // Get all the messageMetaGroupeList where publique equals to UPDATED_PUBLIQUE
        defaultMessageMetaGroupeShouldNotBeFound("publique.in=" + UPDATED_PUBLIQUE);
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByPubliqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        // Get all the messageMetaGroupeList where publique is not null
        defaultMessageMetaGroupeShouldBeFound("publique.specified=true");

        // Get all the messageMetaGroupeList where publique is null
        defaultMessageMetaGroupeShouldNotBeFound("publique.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageMetaGroupesByAuteurIsEqualToSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);
        MissaUser auteur = MissaUserResourceIT.createEntity(em);
        em.persist(auteur);
        em.flush();
        messageMetaGroupe.setAuteur(auteur);
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);
        Long auteurId = auteur.getId();

        // Get all the messageMetaGroupeList where auteur equals to auteurId
        defaultMessageMetaGroupeShouldBeFound("auteurId.equals=" + auteurId);

        // Get all the messageMetaGroupeList where auteur equals to auteurId + 1
        defaultMessageMetaGroupeShouldNotBeFound("auteurId.equals=" + (auteurId + 1));
    }


    @Test
    @Transactional
    public void getAllMessageMetaGroupesByGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);
        MetaGroupe groupe = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupe);
        em.flush();
        messageMetaGroupe.setGroupe(groupe);
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);
        Long groupeId = groupe.getId();

        // Get all the messageMetaGroupeList where groupe equals to groupeId
        defaultMessageMetaGroupeShouldBeFound("groupeId.equals=" + groupeId);

        // Get all the messageMetaGroupeList where groupe equals to groupeId + 1
        defaultMessageMetaGroupeShouldNotBeFound("groupeId.equals=" + (groupeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageMetaGroupeShouldBeFound(String filter) throws Exception {
        restMessageMetaGroupeMockMvc.perform(get("/api/message-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].publique").value(hasItem(DEFAULT_PUBLIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));

        // Check, that the count call also returns 1
        restMessageMetaGroupeMockMvc.perform(get("/api/message-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restMessageMetaGroupeMockMvc.perform(get("/api/message-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageMetaGroupeMockMvc.perform(get("/api/message-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMessageMetaGroupe() throws Exception {
        // Get the messageMetaGroupe
        restMessageMetaGroupeMockMvc.perform(get("/api/message-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageMetaGroupe() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        int databaseSizeBeforeUpdate = messageMetaGroupeRepository.findAll().size();

        // Update the messageMetaGroupe
        MessageMetaGroupe updatedMessageMetaGroupe = messageMetaGroupeRepository.findById(messageMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedMessageMetaGroupe are not directly saved in db
        em.detach(updatedMessageMetaGroupe);
        updatedMessageMetaGroupe
            .titre(UPDATED_TITRE)
            .publique(UPDATED_PUBLIQUE)
            .message(UPDATED_MESSAGE);
        MessageMetaGroupeDTO messageMetaGroupeDTO = messageMetaGroupeMapper.toDto(updatedMessageMetaGroupe);

        restMessageMetaGroupeMockMvc.perform(put("/api/message-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the MessageMetaGroupe in the database
        List<MessageMetaGroupe> messageMetaGroupeList = messageMetaGroupeRepository.findAll();
        assertThat(messageMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        MessageMetaGroupe testMessageMetaGroupe = messageMetaGroupeList.get(messageMetaGroupeList.size() - 1);
        assertThat(testMessageMetaGroupe.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testMessageMetaGroupe.isPublique()).isEqualTo(UPDATED_PUBLIQUE);
        assertThat(testMessageMetaGroupe.getMessage()).isEqualTo(UPDATED_MESSAGE);

        // Validate the MessageMetaGroupe in Elasticsearch
        verify(mockMessageMetaGroupeSearchRepository, times(1)).save(testMessageMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = messageMetaGroupeRepository.findAll().size();

        // Create the MessageMetaGroupe
        MessageMetaGroupeDTO messageMetaGroupeDTO = messageMetaGroupeMapper.toDto(messageMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMetaGroupeMockMvc.perform(put("/api/message-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageMetaGroupe in the database
        List<MessageMetaGroupe> messageMetaGroupeList = messageMetaGroupeRepository.findAll();
        assertThat(messageMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MessageMetaGroupe in Elasticsearch
        verify(mockMessageMetaGroupeSearchRepository, times(0)).save(messageMetaGroupe);
    }

    @Test
    @Transactional
    public void deleteMessageMetaGroupe() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);

        int databaseSizeBeforeDelete = messageMetaGroupeRepository.findAll().size();

        // Delete the messageMetaGroupe
        restMessageMetaGroupeMockMvc.perform(delete("/api/message-meta-groupes/{id}", messageMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageMetaGroupe> messageMetaGroupeList = messageMetaGroupeRepository.findAll();
        assertThat(messageMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MessageMetaGroupe in Elasticsearch
        verify(mockMessageMetaGroupeSearchRepository, times(1)).deleteById(messageMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchMessageMetaGroupe() throws Exception {
        // Initialize the database
        messageMetaGroupeRepository.saveAndFlush(messageMetaGroupe);
        when(mockMessageMetaGroupeSearchRepository.search(queryStringQuery("id:" + messageMetaGroupe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(messageMetaGroupe), PageRequest.of(0, 1), 1));
        // Search the messageMetaGroupe
        restMessageMetaGroupeMockMvc.perform(get("/api/_search/message-meta-groupes?query=id:" + messageMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].publique").value(hasItem(DEFAULT_PUBLIQUE.booleanValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }
}
