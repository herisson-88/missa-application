package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.EnvoiDeMail;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.repository.EnvoiDeMailRepository;
import fr.herisson.missa.repository.search.EnvoiDeMailSearchRepository;
import fr.herisson.missa.service.EnvoiDeMailService;
import fr.herisson.missa.service.dto.EnvoiDeMailDTO;
import fr.herisson.missa.service.mapper.EnvoiDeMailMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.EnvoiDeMailCriteria;
import fr.herisson.missa.service.EnvoiDeMailQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fr.herisson.missa.web.rest.TestUtil.sameInstant;
import static fr.herisson.missa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnvoiDeMailResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class EnvoiDeMailResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_ENVOI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ENVOI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_ENVOI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOT_SPIRITUEL = "AAAAAAAAAA";
    private static final String UPDATED_MOT_SPIRITUEL = "BBBBBBBBBB";

    private static final String DEFAULT_CONSEIL_TECHNIQUE = "AAAAAAAAAA";
    private static final String UPDATED_CONSEIL_TECHNIQUE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_DESTINATAIRE = 1;
    private static final Integer UPDATED_NB_DESTINATAIRE = 2;
    private static final Integer SMALLER_NB_DESTINATAIRE = 1 - 1;

    private static final Boolean DEFAULT_SENDED = false;
    private static final Boolean UPDATED_SENDED = true;

    @Autowired
    private EnvoiDeMailRepository envoiDeMailRepository;

    @Mock
    private EnvoiDeMailRepository envoiDeMailRepositoryMock;

    @Autowired
    private EnvoiDeMailMapper envoiDeMailMapper;

    @Mock
    private EnvoiDeMailService envoiDeMailServiceMock;

    @Autowired
    private EnvoiDeMailService envoiDeMailService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.EnvoiDeMailSearchRepositoryMockConfiguration
     */
    @Autowired
    private EnvoiDeMailSearchRepository mockEnvoiDeMailSearchRepository;

    @Autowired
    private EnvoiDeMailQueryService envoiDeMailQueryService;

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

    private MockMvc restEnvoiDeMailMockMvc;

    private EnvoiDeMail envoiDeMail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnvoiDeMailResource envoiDeMailResource = new EnvoiDeMailResource(envoiDeMailService, envoiDeMailQueryService);
        this.restEnvoiDeMailMockMvc = MockMvcBuilders.standaloneSetup(envoiDeMailResource)
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
    public static EnvoiDeMail createEntity(EntityManager em) {
        EnvoiDeMail envoiDeMail = new EnvoiDeMail()
            .dateEnvoi(DEFAULT_DATE_ENVOI)
            .titre(DEFAULT_TITRE)
            .adresseMail(DEFAULT_ADRESSE_MAIL)
            .motSpirituel(DEFAULT_MOT_SPIRITUEL)
            .conseilTechnique(DEFAULT_CONSEIL_TECHNIQUE)
            .nbDestinataire(DEFAULT_NB_DESTINATAIRE)
            .sended(DEFAULT_SENDED);
        return envoiDeMail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnvoiDeMail createUpdatedEntity(EntityManager em) {
        EnvoiDeMail envoiDeMail = new EnvoiDeMail()
            .dateEnvoi(UPDATED_DATE_ENVOI)
            .titre(UPDATED_TITRE)
            .adresseMail(UPDATED_ADRESSE_MAIL)
            .motSpirituel(UPDATED_MOT_SPIRITUEL)
            .conseilTechnique(UPDATED_CONSEIL_TECHNIQUE)
            .nbDestinataire(UPDATED_NB_DESTINATAIRE)
            .sended(UPDATED_SENDED);
        return envoiDeMail;
    }

    @BeforeEach
    public void initTest() {
        envoiDeMail = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvoiDeMail() throws Exception {
        int databaseSizeBeforeCreate = envoiDeMailRepository.findAll().size();

        // Create the EnvoiDeMail
        EnvoiDeMailDTO envoiDeMailDTO = envoiDeMailMapper.toDto(envoiDeMail);
        restEnvoiDeMailMockMvc.perform(post("/api/envoi-de-mails")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envoiDeMailDTO)))
            .andExpect(status().isCreated());

        // Validate the EnvoiDeMail in the database
        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeCreate + 1);
        EnvoiDeMail testEnvoiDeMail = envoiDeMailList.get(envoiDeMailList.size() - 1);
        assertThat(testEnvoiDeMail.getDateEnvoi()).isEqualTo(DEFAULT_DATE_ENVOI);
        assertThat(testEnvoiDeMail.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testEnvoiDeMail.getAdresseMail()).isEqualTo(DEFAULT_ADRESSE_MAIL);
        assertThat(testEnvoiDeMail.getMotSpirituel()).isEqualTo(DEFAULT_MOT_SPIRITUEL);
        assertThat(testEnvoiDeMail.getConseilTechnique()).isEqualTo(DEFAULT_CONSEIL_TECHNIQUE);
        assertThat(testEnvoiDeMail.getNbDestinataire()).isEqualTo(DEFAULT_NB_DESTINATAIRE);
        assertThat(testEnvoiDeMail.isSended()).isEqualTo(DEFAULT_SENDED);

        // Validate the EnvoiDeMail in Elasticsearch
        verify(mockEnvoiDeMailSearchRepository, times(1)).save(testEnvoiDeMail);
    }

    @Test
    @Transactional
    public void createEnvoiDeMailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = envoiDeMailRepository.findAll().size();

        // Create the EnvoiDeMail with an existing ID
        envoiDeMail.setId(1L);
        EnvoiDeMailDTO envoiDeMailDTO = envoiDeMailMapper.toDto(envoiDeMail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvoiDeMailMockMvc.perform(post("/api/envoi-de-mails")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envoiDeMailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnvoiDeMail in the database
        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeCreate);

        // Validate the EnvoiDeMail in Elasticsearch
        verify(mockEnvoiDeMailSearchRepository, times(0)).save(envoiDeMail);
    }


    @Test
    @Transactional
    public void checkDateEnvoiIsRequired() throws Exception {
        int databaseSizeBeforeTest = envoiDeMailRepository.findAll().size();
        // set the field null
        envoiDeMail.setDateEnvoi(null);

        // Create the EnvoiDeMail, which fails.
        EnvoiDeMailDTO envoiDeMailDTO = envoiDeMailMapper.toDto(envoiDeMail);

        restEnvoiDeMailMockMvc.perform(post("/api/envoi-de-mails")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envoiDeMailDTO)))
            .andExpect(status().isBadRequest());

        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitreIsRequired() throws Exception {
        int databaseSizeBeforeTest = envoiDeMailRepository.findAll().size();
        // set the field null
        envoiDeMail.setTitre(null);

        // Create the EnvoiDeMail, which fails.
        EnvoiDeMailDTO envoiDeMailDTO = envoiDeMailMapper.toDto(envoiDeMail);

        restEnvoiDeMailMockMvc.perform(post("/api/envoi-de-mails")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envoiDeMailDTO)))
            .andExpect(status().isBadRequest());

        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbDestinataireIsRequired() throws Exception {
        int databaseSizeBeforeTest = envoiDeMailRepository.findAll().size();
        // set the field null
        envoiDeMail.setNbDestinataire(null);

        // Create the EnvoiDeMail, which fails.
        EnvoiDeMailDTO envoiDeMailDTO = envoiDeMailMapper.toDto(envoiDeMail);

        restEnvoiDeMailMockMvc.perform(post("/api/envoi-de-mails")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envoiDeMailDTO)))
            .andExpect(status().isBadRequest());

        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMails() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList
        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envoiDeMail.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnvoi").value(hasItem(sameInstant(DEFAULT_DATE_ENVOI))))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].adresseMail").value(hasItem(DEFAULT_ADRESSE_MAIL.toString())))
            .andExpect(jsonPath("$.[*].motSpirituel").value(hasItem(DEFAULT_MOT_SPIRITUEL.toString())))
            .andExpect(jsonPath("$.[*].conseilTechnique").value(hasItem(DEFAULT_CONSEIL_TECHNIQUE.toString())))
            .andExpect(jsonPath("$.[*].nbDestinataire").value(hasItem(DEFAULT_NB_DESTINATAIRE)))
            .andExpect(jsonPath("$.[*].sended").value(hasItem(DEFAULT_SENDED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEnvoiDeMailsWithEagerRelationshipsIsEnabled() throws Exception {
        EnvoiDeMailResource envoiDeMailResource = new EnvoiDeMailResource(envoiDeMailServiceMock, envoiDeMailQueryService);
        when(envoiDeMailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEnvoiDeMailMockMvc = MockMvcBuilders.standaloneSetup(envoiDeMailResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails?eagerload=true"))
        .andExpect(status().isOk());

        verify(envoiDeMailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEnvoiDeMailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EnvoiDeMailResource envoiDeMailResource = new EnvoiDeMailResource(envoiDeMailServiceMock, envoiDeMailQueryService);
            when(envoiDeMailServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEnvoiDeMailMockMvc = MockMvcBuilders.standaloneSetup(envoiDeMailResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails?eagerload=true"))
        .andExpect(status().isOk());

            verify(envoiDeMailServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEnvoiDeMail() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get the envoiDeMail
        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails/{id}", envoiDeMail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(envoiDeMail.getId().intValue()))
            .andExpect(jsonPath("$.dateEnvoi").value(sameInstant(DEFAULT_DATE_ENVOI)))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.adresseMail").value(DEFAULT_ADRESSE_MAIL.toString()))
            .andExpect(jsonPath("$.motSpirituel").value(DEFAULT_MOT_SPIRITUEL.toString()))
            .andExpect(jsonPath("$.conseilTechnique").value(DEFAULT_CONSEIL_TECHNIQUE.toString()))
            .andExpect(jsonPath("$.nbDestinataire").value(DEFAULT_NB_DESTINATAIRE))
            .andExpect(jsonPath("$.sended").value(DEFAULT_SENDED.booleanValue()));
    }


    @Test
    @Transactional
    public void getEnvoiDeMailsByIdFiltering() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        Long id = envoiDeMail.getId();

        defaultEnvoiDeMailShouldBeFound("id.equals=" + id);
        defaultEnvoiDeMailShouldNotBeFound("id.notEquals=" + id);

        defaultEnvoiDeMailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnvoiDeMailShouldNotBeFound("id.greaterThan=" + id);

        defaultEnvoiDeMailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnvoiDeMailShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi equals to DEFAULT_DATE_ENVOI
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.equals=" + DEFAULT_DATE_ENVOI);

        // Get all the envoiDeMailList where dateEnvoi equals to UPDATED_DATE_ENVOI
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.equals=" + UPDATED_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi not equals to DEFAULT_DATE_ENVOI
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.notEquals=" + DEFAULT_DATE_ENVOI);

        // Get all the envoiDeMailList where dateEnvoi not equals to UPDATED_DATE_ENVOI
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.notEquals=" + UPDATED_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsInShouldWork() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi in DEFAULT_DATE_ENVOI or UPDATED_DATE_ENVOI
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.in=" + DEFAULT_DATE_ENVOI + "," + UPDATED_DATE_ENVOI);

        // Get all the envoiDeMailList where dateEnvoi equals to UPDATED_DATE_ENVOI
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.in=" + UPDATED_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi is not null
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.specified=true");

        // Get all the envoiDeMailList where dateEnvoi is null
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi is greater than or equal to DEFAULT_DATE_ENVOI
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.greaterThanOrEqual=" + DEFAULT_DATE_ENVOI);

        // Get all the envoiDeMailList where dateEnvoi is greater than or equal to UPDATED_DATE_ENVOI
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.greaterThanOrEqual=" + UPDATED_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi is less than or equal to DEFAULT_DATE_ENVOI
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.lessThanOrEqual=" + DEFAULT_DATE_ENVOI);

        // Get all the envoiDeMailList where dateEnvoi is less than or equal to SMALLER_DATE_ENVOI
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.lessThanOrEqual=" + SMALLER_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsLessThanSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi is less than DEFAULT_DATE_ENVOI
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.lessThan=" + DEFAULT_DATE_ENVOI);

        // Get all the envoiDeMailList where dateEnvoi is less than UPDATED_DATE_ENVOI
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.lessThan=" + UPDATED_DATE_ENVOI);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByDateEnvoiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where dateEnvoi is greater than DEFAULT_DATE_ENVOI
        defaultEnvoiDeMailShouldNotBeFound("dateEnvoi.greaterThan=" + DEFAULT_DATE_ENVOI);

        // Get all the envoiDeMailList where dateEnvoi is greater than SMALLER_DATE_ENVOI
        defaultEnvoiDeMailShouldBeFound("dateEnvoi.greaterThan=" + SMALLER_DATE_ENVOI);
    }


    @Test
    @Transactional
    public void getAllEnvoiDeMailsByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where titre equals to DEFAULT_TITRE
        defaultEnvoiDeMailShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the envoiDeMailList where titre equals to UPDATED_TITRE
        defaultEnvoiDeMailShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByTitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where titre not equals to DEFAULT_TITRE
        defaultEnvoiDeMailShouldNotBeFound("titre.notEquals=" + DEFAULT_TITRE);

        // Get all the envoiDeMailList where titre not equals to UPDATED_TITRE
        defaultEnvoiDeMailShouldBeFound("titre.notEquals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultEnvoiDeMailShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the envoiDeMailList where titre equals to UPDATED_TITRE
        defaultEnvoiDeMailShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where titre is not null
        defaultEnvoiDeMailShouldBeFound("titre.specified=true");

        // Get all the envoiDeMailList where titre is null
        defaultEnvoiDeMailShouldNotBeFound("titre.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnvoiDeMailsByTitreContainsSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where titre contains DEFAULT_TITRE
        defaultEnvoiDeMailShouldBeFound("titre.contains=" + DEFAULT_TITRE);

        // Get all the envoiDeMailList where titre contains UPDATED_TITRE
        defaultEnvoiDeMailShouldNotBeFound("titre.contains=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByTitreNotContainsSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where titre does not contain DEFAULT_TITRE
        defaultEnvoiDeMailShouldNotBeFound("titre.doesNotContain=" + DEFAULT_TITRE);

        // Get all the envoiDeMailList where titre does not contain UPDATED_TITRE
        defaultEnvoiDeMailShouldBeFound("titre.doesNotContain=" + UPDATED_TITRE);
    }


    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire equals to DEFAULT_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.equals=" + DEFAULT_NB_DESTINATAIRE);

        // Get all the envoiDeMailList where nbDestinataire equals to UPDATED_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.equals=" + UPDATED_NB_DESTINATAIRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire not equals to DEFAULT_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.notEquals=" + DEFAULT_NB_DESTINATAIRE);

        // Get all the envoiDeMailList where nbDestinataire not equals to UPDATED_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.notEquals=" + UPDATED_NB_DESTINATAIRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsInShouldWork() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire in DEFAULT_NB_DESTINATAIRE or UPDATED_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.in=" + DEFAULT_NB_DESTINATAIRE + "," + UPDATED_NB_DESTINATAIRE);

        // Get all the envoiDeMailList where nbDestinataire equals to UPDATED_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.in=" + UPDATED_NB_DESTINATAIRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsNullOrNotNull() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire is not null
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.specified=true");

        // Get all the envoiDeMailList where nbDestinataire is null
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire is greater than or equal to DEFAULT_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.greaterThanOrEqual=" + DEFAULT_NB_DESTINATAIRE);

        // Get all the envoiDeMailList where nbDestinataire is greater than or equal to UPDATED_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.greaterThanOrEqual=" + UPDATED_NB_DESTINATAIRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire is less than or equal to DEFAULT_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.lessThanOrEqual=" + DEFAULT_NB_DESTINATAIRE);

        // Get all the envoiDeMailList where nbDestinataire is less than or equal to SMALLER_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.lessThanOrEqual=" + SMALLER_NB_DESTINATAIRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsLessThanSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire is less than DEFAULT_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.lessThan=" + DEFAULT_NB_DESTINATAIRE);

        // Get all the envoiDeMailList where nbDestinataire is less than UPDATED_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.lessThan=" + UPDATED_NB_DESTINATAIRE);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByNbDestinataireIsGreaterThanSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where nbDestinataire is greater than DEFAULT_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldNotBeFound("nbDestinataire.greaterThan=" + DEFAULT_NB_DESTINATAIRE);

        // Get all the envoiDeMailList where nbDestinataire is greater than SMALLER_NB_DESTINATAIRE
        defaultEnvoiDeMailShouldBeFound("nbDestinataire.greaterThan=" + SMALLER_NB_DESTINATAIRE);
    }


    @Test
    @Transactional
    public void getAllEnvoiDeMailsBySendedIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where sended equals to DEFAULT_SENDED
        defaultEnvoiDeMailShouldBeFound("sended.equals=" + DEFAULT_SENDED);

        // Get all the envoiDeMailList where sended equals to UPDATED_SENDED
        defaultEnvoiDeMailShouldNotBeFound("sended.equals=" + UPDATED_SENDED);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsBySendedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where sended not equals to DEFAULT_SENDED
        defaultEnvoiDeMailShouldNotBeFound("sended.notEquals=" + DEFAULT_SENDED);

        // Get all the envoiDeMailList where sended not equals to UPDATED_SENDED
        defaultEnvoiDeMailShouldBeFound("sended.notEquals=" + UPDATED_SENDED);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsBySendedIsInShouldWork() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where sended in DEFAULT_SENDED or UPDATED_SENDED
        defaultEnvoiDeMailShouldBeFound("sended.in=" + DEFAULT_SENDED + "," + UPDATED_SENDED);

        // Get all the envoiDeMailList where sended equals to UPDATED_SENDED
        defaultEnvoiDeMailShouldNotBeFound("sended.in=" + UPDATED_SENDED);
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsBySendedIsNullOrNotNull() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        // Get all the envoiDeMailList where sended is not null
        defaultEnvoiDeMailShouldBeFound("sended.specified=true");

        // Get all the envoiDeMailList where sended is null
        defaultEnvoiDeMailShouldNotBeFound("sended.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnvoiDeMailsByGroupePartageParMailIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);
        MetaGroupe groupePartageParMail = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupePartageParMail);
        em.flush();
        envoiDeMail.addGroupePartageParMail(groupePartageParMail);
        envoiDeMailRepository.saveAndFlush(envoiDeMail);
        Long groupePartageParMailId = groupePartageParMail.getId();

        // Get all the envoiDeMailList where groupePartageParMail equals to groupePartageParMailId
        defaultEnvoiDeMailShouldBeFound("groupePartageParMailId.equals=" + groupePartageParMailId);

        // Get all the envoiDeMailList where groupePartageParMail equals to groupePartageParMailId + 1
        defaultEnvoiDeMailShouldNotBeFound("groupePartageParMailId.equals=" + (groupePartageParMailId + 1));
    }


    @Test
    @Transactional
    public void getAllEnvoiDeMailsByAdminIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);
        MissaUser admin = MissaUserResourceIT.createEntity(em);
        em.persist(admin);
        em.flush();
        envoiDeMail.setAdmin(admin);
        envoiDeMailRepository.saveAndFlush(envoiDeMail);
        Long adminId = admin.getId();

        // Get all the envoiDeMailList where admin equals to adminId
        defaultEnvoiDeMailShouldBeFound("adminId.equals=" + adminId);

        // Get all the envoiDeMailList where admin equals to adminId + 1
        defaultEnvoiDeMailShouldNotBeFound("adminId.equals=" + (adminId + 1));
    }


    @Test
    @Transactional
    public void getAllEnvoiDeMailsByGroupeDuMailIsEqualToSomething() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);
        MetaGroupe groupeDuMail = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupeDuMail);
        em.flush();
        envoiDeMail.setGroupeDuMail(groupeDuMail);
        envoiDeMailRepository.saveAndFlush(envoiDeMail);
        Long groupeDuMailId = groupeDuMail.getId();

        // Get all the envoiDeMailList where groupeDuMail equals to groupeDuMailId
        defaultEnvoiDeMailShouldBeFound("groupeDuMailId.equals=" + groupeDuMailId);

        // Get all the envoiDeMailList where groupeDuMail equals to groupeDuMailId + 1
        defaultEnvoiDeMailShouldNotBeFound("groupeDuMailId.equals=" + (groupeDuMailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnvoiDeMailShouldBeFound(String filter) throws Exception {
        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envoiDeMail.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnvoi").value(hasItem(sameInstant(DEFAULT_DATE_ENVOI))))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].adresseMail").value(hasItem(DEFAULT_ADRESSE_MAIL.toString())))
            .andExpect(jsonPath("$.[*].motSpirituel").value(hasItem(DEFAULT_MOT_SPIRITUEL.toString())))
            .andExpect(jsonPath("$.[*].conseilTechnique").value(hasItem(DEFAULT_CONSEIL_TECHNIQUE.toString())))
            .andExpect(jsonPath("$.[*].nbDestinataire").value(hasItem(DEFAULT_NB_DESTINATAIRE)))
            .andExpect(jsonPath("$.[*].sended").value(hasItem(DEFAULT_SENDED.booleanValue())));

        // Check, that the count call also returns 1
        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnvoiDeMailShouldNotBeFound(String filter) throws Exception {
        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEnvoiDeMail() throws Exception {
        // Get the envoiDeMail
        restEnvoiDeMailMockMvc.perform(get("/api/envoi-de-mails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvoiDeMail() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        int databaseSizeBeforeUpdate = envoiDeMailRepository.findAll().size();

        // Update the envoiDeMail
        EnvoiDeMail updatedEnvoiDeMail = envoiDeMailRepository.findById(envoiDeMail.getId()).get();
        // Disconnect from session so that the updates on updatedEnvoiDeMail are not directly saved in db
        em.detach(updatedEnvoiDeMail);
        updatedEnvoiDeMail
            .dateEnvoi(UPDATED_DATE_ENVOI)
            .titre(UPDATED_TITRE)
            .adresseMail(UPDATED_ADRESSE_MAIL)
            .motSpirituel(UPDATED_MOT_SPIRITUEL)
            .conseilTechnique(UPDATED_CONSEIL_TECHNIQUE)
            .nbDestinataire(UPDATED_NB_DESTINATAIRE)
            .sended(UPDATED_SENDED);
        EnvoiDeMailDTO envoiDeMailDTO = envoiDeMailMapper.toDto(updatedEnvoiDeMail);

        restEnvoiDeMailMockMvc.perform(put("/api/envoi-de-mails")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envoiDeMailDTO)))
            .andExpect(status().isOk());

        // Validate the EnvoiDeMail in the database
        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeUpdate);
        EnvoiDeMail testEnvoiDeMail = envoiDeMailList.get(envoiDeMailList.size() - 1);
        assertThat(testEnvoiDeMail.getDateEnvoi()).isEqualTo(UPDATED_DATE_ENVOI);
        assertThat(testEnvoiDeMail.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testEnvoiDeMail.getAdresseMail()).isEqualTo(UPDATED_ADRESSE_MAIL);
        assertThat(testEnvoiDeMail.getMotSpirituel()).isEqualTo(UPDATED_MOT_SPIRITUEL);
        assertThat(testEnvoiDeMail.getConseilTechnique()).isEqualTo(UPDATED_CONSEIL_TECHNIQUE);
        assertThat(testEnvoiDeMail.getNbDestinataire()).isEqualTo(UPDATED_NB_DESTINATAIRE);
        assertThat(testEnvoiDeMail.isSended()).isEqualTo(UPDATED_SENDED);

        // Validate the EnvoiDeMail in Elasticsearch
        verify(mockEnvoiDeMailSearchRepository, times(1)).save(testEnvoiDeMail);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvoiDeMail() throws Exception {
        int databaseSizeBeforeUpdate = envoiDeMailRepository.findAll().size();

        // Create the EnvoiDeMail
        EnvoiDeMailDTO envoiDeMailDTO = envoiDeMailMapper.toDto(envoiDeMail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvoiDeMailMockMvc.perform(put("/api/envoi-de-mails")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envoiDeMailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnvoiDeMail in the database
        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EnvoiDeMail in Elasticsearch
        verify(mockEnvoiDeMailSearchRepository, times(0)).save(envoiDeMail);
    }

    @Test
    @Transactional
    public void deleteEnvoiDeMail() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);

        int databaseSizeBeforeDelete = envoiDeMailRepository.findAll().size();

        // Delete the envoiDeMail
        restEnvoiDeMailMockMvc.perform(delete("/api/envoi-de-mails/{id}", envoiDeMail.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnvoiDeMail> envoiDeMailList = envoiDeMailRepository.findAll();
        assertThat(envoiDeMailList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EnvoiDeMail in Elasticsearch
        verify(mockEnvoiDeMailSearchRepository, times(1)).deleteById(envoiDeMail.getId());
    }

    @Test
    @Transactional
    public void searchEnvoiDeMail() throws Exception {
        // Initialize the database
        envoiDeMailRepository.saveAndFlush(envoiDeMail);
        when(mockEnvoiDeMailSearchRepository.search(queryStringQuery("id:" + envoiDeMail.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(envoiDeMail), PageRequest.of(0, 1), 1));
        // Search the envoiDeMail
        restEnvoiDeMailMockMvc.perform(get("/api/_search/envoi-de-mails?query=id:" + envoiDeMail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envoiDeMail.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnvoi").value(hasItem(sameInstant(DEFAULT_DATE_ENVOI))))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].adresseMail").value(hasItem(DEFAULT_ADRESSE_MAIL.toString())))
            .andExpect(jsonPath("$.[*].motSpirituel").value(hasItem(DEFAULT_MOT_SPIRITUEL.toString())))
            .andExpect(jsonPath("$.[*].conseilTechnique").value(hasItem(DEFAULT_CONSEIL_TECHNIQUE.toString())))
            .andExpect(jsonPath("$.[*].nbDestinataire").value(hasItem(DEFAULT_NB_DESTINATAIRE)))
            .andExpect(jsonPath("$.[*].sended").value(hasItem(DEFAULT_SENDED.booleanValue())));
    }
}
