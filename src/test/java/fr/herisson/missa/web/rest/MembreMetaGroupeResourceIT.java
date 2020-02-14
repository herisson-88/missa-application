package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.MembreMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.repository.MembreMetaGroupeRepository;
import fr.herisson.missa.repository.search.MembreMetaGroupeSearchRepository;
import fr.herisson.missa.service.MembreMetaGroupeService;
import fr.herisson.missa.service.dto.MembreMetaGroupeDTO;
import fr.herisson.missa.service.mapper.MembreMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.MembreMetaGroupeCriteria;
import fr.herisson.missa.service.MembreMetaGroupeQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
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
 * Integration tests for the {@link MembreMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class MembreMetaGroupeResourceIT {

    private static final Boolean DEFAULT_VALIDATED = false;
    private static final Boolean UPDATED_VALIDATED = true;

    private static final ZonedDateTime DEFAULT_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_VALIDATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_ADMIN = false;
    private static final Boolean UPDATED_ADMIN = true;

    private static final String DEFAULT_PRESENTATION = "AAAAAAAAAA";
    private static final String UPDATED_PRESENTATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONNAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_CONNAISSANCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MAILING_LIST = false;
    private static final Boolean UPDATED_MAILING_LIST = true;

    @Autowired
    private MembreMetaGroupeRepository membreMetaGroupeRepository;

    @Autowired
    private MembreMetaGroupeMapper membreMetaGroupeMapper;

    @Autowired
    private MembreMetaGroupeService membreMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.MembreMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private MembreMetaGroupeSearchRepository mockMembreMetaGroupeSearchRepository;

    @Autowired
    private MembreMetaGroupeQueryService membreMetaGroupeQueryService;

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

    private MockMvc restMembreMetaGroupeMockMvc;

    private MembreMetaGroupe membreMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembreMetaGroupeResource membreMetaGroupeResource = new MembreMetaGroupeResource(membreMetaGroupeService, membreMetaGroupeQueryService);
        this.restMembreMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(membreMetaGroupeResource)
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
    public static MembreMetaGroupe createEntity(EntityManager em) {
        MembreMetaGroupe membreMetaGroupe = new MembreMetaGroupe()
            .validated(DEFAULT_VALIDATED)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .admin(DEFAULT_ADMIN)
            .presentation(DEFAULT_PRESENTATION)
            .connaissance(DEFAULT_CONNAISSANCE)
            .mailingList(DEFAULT_MAILING_LIST);
        return membreMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreMetaGroupe createUpdatedEntity(EntityManager em) {
        MembreMetaGroupe membreMetaGroupe = new MembreMetaGroupe()
            .validated(UPDATED_VALIDATED)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .admin(UPDATED_ADMIN)
            .presentation(UPDATED_PRESENTATION)
            .connaissance(UPDATED_CONNAISSANCE)
            .mailingList(UPDATED_MAILING_LIST);
        return membreMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        membreMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembreMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = membreMetaGroupeRepository.findAll().size();

        // Create the MembreMetaGroupe
        MembreMetaGroupeDTO membreMetaGroupeDTO = membreMetaGroupeMapper.toDto(membreMetaGroupe);
        restMembreMetaGroupeMockMvc.perform(post("/api/membre-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the MembreMetaGroupe in the database
        List<MembreMetaGroupe> membreMetaGroupeList = membreMetaGroupeRepository.findAll();
        assertThat(membreMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        MembreMetaGroupe testMembreMetaGroupe = membreMetaGroupeList.get(membreMetaGroupeList.size() - 1);
        assertThat(testMembreMetaGroupe.isValidated()).isEqualTo(DEFAULT_VALIDATED);
        assertThat(testMembreMetaGroupe.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testMembreMetaGroupe.isAdmin()).isEqualTo(DEFAULT_ADMIN);
        assertThat(testMembreMetaGroupe.getPresentation()).isEqualTo(DEFAULT_PRESENTATION);
        assertThat(testMembreMetaGroupe.getConnaissance()).isEqualTo(DEFAULT_CONNAISSANCE);
        assertThat(testMembreMetaGroupe.isMailingList()).isEqualTo(DEFAULT_MAILING_LIST);

        // Validate the MembreMetaGroupe in Elasticsearch
        verify(mockMembreMetaGroupeSearchRepository, times(1)).save(testMembreMetaGroupe);
    }

    @Test
    @Transactional
    public void createMembreMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreMetaGroupeRepository.findAll().size();

        // Create the MembreMetaGroupe with an existing ID
        membreMetaGroupe.setId(1L);
        MembreMetaGroupeDTO membreMetaGroupeDTO = membreMetaGroupeMapper.toDto(membreMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreMetaGroupeMockMvc.perform(post("/api/membre-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MembreMetaGroupe in the database
        List<MembreMetaGroupe> membreMetaGroupeList = membreMetaGroupeRepository.findAll();
        assertThat(membreMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the MembreMetaGroupe in Elasticsearch
        verify(mockMembreMetaGroupeSearchRepository, times(0)).save(membreMetaGroupe);
    }


    @Test
    @Transactional
    public void checkMailingListIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreMetaGroupeRepository.findAll().size();
        // set the field null
        membreMetaGroupe.setMailingList(null);

        // Create the MembreMetaGroupe, which fails.
        MembreMetaGroupeDTO membreMetaGroupeDTO = membreMetaGroupeMapper.toDto(membreMetaGroupe);

        restMembreMetaGroupeMockMvc.perform(post("/api/membre-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        List<MembreMetaGroupe> membreMetaGroupeList = membreMetaGroupeRepository.findAll();
        assertThat(membreMetaGroupeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupes() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList
        restMembreMetaGroupeMockMvc.perform(get("/api/membre-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].admin").value(hasItem(DEFAULT_ADMIN.booleanValue())))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(DEFAULT_PRESENTATION.toString())))
            .andExpect(jsonPath("$.[*].connaissance").value(hasItem(DEFAULT_CONNAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].mailingList").value(hasItem(DEFAULT_MAILING_LIST.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMembreMetaGroupe() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get the membreMetaGroupe
        restMembreMetaGroupeMockMvc.perform(get("/api/membre-meta-groupes/{id}", membreMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membreMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.validated").value(DEFAULT_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.dateValidation").value(sameInstant(DEFAULT_DATE_VALIDATION)))
            .andExpect(jsonPath("$.admin").value(DEFAULT_ADMIN.booleanValue()))
            .andExpect(jsonPath("$.presentation").value(DEFAULT_PRESENTATION.toString()))
            .andExpect(jsonPath("$.connaissance").value(DEFAULT_CONNAISSANCE.toString()))
            .andExpect(jsonPath("$.mailingList").value(DEFAULT_MAILING_LIST.booleanValue()));
    }


    @Test
    @Transactional
    public void getMembreMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        Long id = membreMetaGroupe.getId();

        defaultMembreMetaGroupeShouldBeFound("id.equals=" + id);
        defaultMembreMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultMembreMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMembreMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultMembreMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMembreMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMembreMetaGroupesByValidatedIsEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where validated equals to DEFAULT_VALIDATED
        defaultMembreMetaGroupeShouldBeFound("validated.equals=" + DEFAULT_VALIDATED);

        // Get all the membreMetaGroupeList where validated equals to UPDATED_VALIDATED
        defaultMembreMetaGroupeShouldNotBeFound("validated.equals=" + UPDATED_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByValidatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where validated not equals to DEFAULT_VALIDATED
        defaultMembreMetaGroupeShouldNotBeFound("validated.notEquals=" + DEFAULT_VALIDATED);

        // Get all the membreMetaGroupeList where validated not equals to UPDATED_VALIDATED
        defaultMembreMetaGroupeShouldBeFound("validated.notEquals=" + UPDATED_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByValidatedIsInShouldWork() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where validated in DEFAULT_VALIDATED or UPDATED_VALIDATED
        defaultMembreMetaGroupeShouldBeFound("validated.in=" + DEFAULT_VALIDATED + "," + UPDATED_VALIDATED);

        // Get all the membreMetaGroupeList where validated equals to UPDATED_VALIDATED
        defaultMembreMetaGroupeShouldNotBeFound("validated.in=" + UPDATED_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByValidatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where validated is not null
        defaultMembreMetaGroupeShouldBeFound("validated.specified=true");

        // Get all the membreMetaGroupeList where validated is null
        defaultMembreMetaGroupeShouldNotBeFound("validated.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation equals to DEFAULT_DATE_VALIDATION
        defaultMembreMetaGroupeShouldBeFound("dateValidation.equals=" + DEFAULT_DATE_VALIDATION);

        // Get all the membreMetaGroupeList where dateValidation equals to UPDATED_DATE_VALIDATION
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.equals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation not equals to DEFAULT_DATE_VALIDATION
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.notEquals=" + DEFAULT_DATE_VALIDATION);

        // Get all the membreMetaGroupeList where dateValidation not equals to UPDATED_DATE_VALIDATION
        defaultMembreMetaGroupeShouldBeFound("dateValidation.notEquals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsInShouldWork() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation in DEFAULT_DATE_VALIDATION or UPDATED_DATE_VALIDATION
        defaultMembreMetaGroupeShouldBeFound("dateValidation.in=" + DEFAULT_DATE_VALIDATION + "," + UPDATED_DATE_VALIDATION);

        // Get all the membreMetaGroupeList where dateValidation equals to UPDATED_DATE_VALIDATION
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.in=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation is not null
        defaultMembreMetaGroupeShouldBeFound("dateValidation.specified=true");

        // Get all the membreMetaGroupeList where dateValidation is null
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation is greater than or equal to DEFAULT_DATE_VALIDATION
        defaultMembreMetaGroupeShouldBeFound("dateValidation.greaterThanOrEqual=" + DEFAULT_DATE_VALIDATION);

        // Get all the membreMetaGroupeList where dateValidation is greater than or equal to UPDATED_DATE_VALIDATION
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.greaterThanOrEqual=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation is less than or equal to DEFAULT_DATE_VALIDATION
        defaultMembreMetaGroupeShouldBeFound("dateValidation.lessThanOrEqual=" + DEFAULT_DATE_VALIDATION);

        // Get all the membreMetaGroupeList where dateValidation is less than or equal to SMALLER_DATE_VALIDATION
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.lessThanOrEqual=" + SMALLER_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsLessThanSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation is less than DEFAULT_DATE_VALIDATION
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.lessThan=" + DEFAULT_DATE_VALIDATION);

        // Get all the membreMetaGroupeList where dateValidation is less than UPDATED_DATE_VALIDATION
        defaultMembreMetaGroupeShouldBeFound("dateValidation.lessThan=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByDateValidationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where dateValidation is greater than DEFAULT_DATE_VALIDATION
        defaultMembreMetaGroupeShouldNotBeFound("dateValidation.greaterThan=" + DEFAULT_DATE_VALIDATION);

        // Get all the membreMetaGroupeList where dateValidation is greater than SMALLER_DATE_VALIDATION
        defaultMembreMetaGroupeShouldBeFound("dateValidation.greaterThan=" + SMALLER_DATE_VALIDATION);
    }


    @Test
    @Transactional
    public void getAllMembreMetaGroupesByAdminIsEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where admin equals to DEFAULT_ADMIN
        defaultMembreMetaGroupeShouldBeFound("admin.equals=" + DEFAULT_ADMIN);

        // Get all the membreMetaGroupeList where admin equals to UPDATED_ADMIN
        defaultMembreMetaGroupeShouldNotBeFound("admin.equals=" + UPDATED_ADMIN);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByAdminIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where admin not equals to DEFAULT_ADMIN
        defaultMembreMetaGroupeShouldNotBeFound("admin.notEquals=" + DEFAULT_ADMIN);

        // Get all the membreMetaGroupeList where admin not equals to UPDATED_ADMIN
        defaultMembreMetaGroupeShouldBeFound("admin.notEquals=" + UPDATED_ADMIN);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByAdminIsInShouldWork() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where admin in DEFAULT_ADMIN or UPDATED_ADMIN
        defaultMembreMetaGroupeShouldBeFound("admin.in=" + DEFAULT_ADMIN + "," + UPDATED_ADMIN);

        // Get all the membreMetaGroupeList where admin equals to UPDATED_ADMIN
        defaultMembreMetaGroupeShouldNotBeFound("admin.in=" + UPDATED_ADMIN);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByAdminIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where admin is not null
        defaultMembreMetaGroupeShouldBeFound("admin.specified=true");

        // Get all the membreMetaGroupeList where admin is null
        defaultMembreMetaGroupeShouldNotBeFound("admin.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByMailingListIsEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where mailingList equals to DEFAULT_MAILING_LIST
        defaultMembreMetaGroupeShouldBeFound("mailingList.equals=" + DEFAULT_MAILING_LIST);

        // Get all the membreMetaGroupeList where mailingList equals to UPDATED_MAILING_LIST
        defaultMembreMetaGroupeShouldNotBeFound("mailingList.equals=" + UPDATED_MAILING_LIST);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByMailingListIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where mailingList not equals to DEFAULT_MAILING_LIST
        defaultMembreMetaGroupeShouldNotBeFound("mailingList.notEquals=" + DEFAULT_MAILING_LIST);

        // Get all the membreMetaGroupeList where mailingList not equals to UPDATED_MAILING_LIST
        defaultMembreMetaGroupeShouldBeFound("mailingList.notEquals=" + UPDATED_MAILING_LIST);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByMailingListIsInShouldWork() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where mailingList in DEFAULT_MAILING_LIST or UPDATED_MAILING_LIST
        defaultMembreMetaGroupeShouldBeFound("mailingList.in=" + DEFAULT_MAILING_LIST + "," + UPDATED_MAILING_LIST);

        // Get all the membreMetaGroupeList where mailingList equals to UPDATED_MAILING_LIST
        defaultMembreMetaGroupeShouldNotBeFound("mailingList.in=" + UPDATED_MAILING_LIST);
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByMailingListIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        // Get all the membreMetaGroupeList where mailingList is not null
        defaultMembreMetaGroupeShouldBeFound("mailingList.specified=true");

        // Get all the membreMetaGroupeList where mailingList is null
        defaultMembreMetaGroupeShouldNotBeFound("mailingList.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreMetaGroupesByGroupeMembreIsEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);
        MetaGroupe groupeMembre = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupeMembre);
        em.flush();
        membreMetaGroupe.setGroupeMembre(groupeMembre);
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);
        Long groupeMembreId = groupeMembre.getId();

        // Get all the membreMetaGroupeList where groupeMembre equals to groupeMembreId
        defaultMembreMetaGroupeShouldBeFound("groupeMembreId.equals=" + groupeMembreId);

        // Get all the membreMetaGroupeList where groupeMembre equals to groupeMembreId + 1
        defaultMembreMetaGroupeShouldNotBeFound("groupeMembreId.equals=" + (groupeMembreId + 1));
    }


    @Test
    @Transactional
    public void getAllMembreMetaGroupesByValideParIsEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);
        MissaUser validePar = MissaUserResourceIT.createEntity(em);
        em.persist(validePar);
        em.flush();
        membreMetaGroupe.setValidePar(validePar);
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);
        Long valideParId = validePar.getId();

        // Get all the membreMetaGroupeList where validePar equals to valideParId
        defaultMembreMetaGroupeShouldBeFound("valideParId.equals=" + valideParId);

        // Get all the membreMetaGroupeList where validePar equals to valideParId + 1
        defaultMembreMetaGroupeShouldNotBeFound("valideParId.equals=" + (valideParId + 1));
    }


    @Test
    @Transactional
    public void getAllMembreMetaGroupesByMissaUserIsEqualToSomething() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);
        MissaUser missaUser = MissaUserResourceIT.createEntity(em);
        em.persist(missaUser);
        em.flush();
        membreMetaGroupe.setMissaUser(missaUser);
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);
        Long missaUserId = missaUser.getId();

        // Get all the membreMetaGroupeList where missaUser equals to missaUserId
        defaultMembreMetaGroupeShouldBeFound("missaUserId.equals=" + missaUserId);

        // Get all the membreMetaGroupeList where missaUser equals to missaUserId + 1
        defaultMembreMetaGroupeShouldNotBeFound("missaUserId.equals=" + (missaUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMembreMetaGroupeShouldBeFound(String filter) throws Exception {
        restMembreMetaGroupeMockMvc.perform(get("/api/membre-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].admin").value(hasItem(DEFAULT_ADMIN.booleanValue())))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(DEFAULT_PRESENTATION.toString())))
            .andExpect(jsonPath("$.[*].connaissance").value(hasItem(DEFAULT_CONNAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].mailingList").value(hasItem(DEFAULT_MAILING_LIST.booleanValue())));

        // Check, that the count call also returns 1
        restMembreMetaGroupeMockMvc.perform(get("/api/membre-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMembreMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restMembreMetaGroupeMockMvc.perform(get("/api/membre-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMembreMetaGroupeMockMvc.perform(get("/api/membre-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMembreMetaGroupe() throws Exception {
        // Get the membreMetaGroupe
        restMembreMetaGroupeMockMvc.perform(get("/api/membre-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembreMetaGroupe() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        int databaseSizeBeforeUpdate = membreMetaGroupeRepository.findAll().size();

        // Update the membreMetaGroupe
        MembreMetaGroupe updatedMembreMetaGroupe = membreMetaGroupeRepository.findById(membreMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedMembreMetaGroupe are not directly saved in db
        em.detach(updatedMembreMetaGroupe);
        updatedMembreMetaGroupe
            .validated(UPDATED_VALIDATED)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .admin(UPDATED_ADMIN)
            .presentation(UPDATED_PRESENTATION)
            .connaissance(UPDATED_CONNAISSANCE)
            .mailingList(UPDATED_MAILING_LIST);
        MembreMetaGroupeDTO membreMetaGroupeDTO = membreMetaGroupeMapper.toDto(updatedMembreMetaGroupe);

        restMembreMetaGroupeMockMvc.perform(put("/api/membre-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the MembreMetaGroupe in the database
        List<MembreMetaGroupe> membreMetaGroupeList = membreMetaGroupeRepository.findAll();
        assertThat(membreMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        MembreMetaGroupe testMembreMetaGroupe = membreMetaGroupeList.get(membreMetaGroupeList.size() - 1);
        assertThat(testMembreMetaGroupe.isValidated()).isEqualTo(UPDATED_VALIDATED);
        assertThat(testMembreMetaGroupe.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testMembreMetaGroupe.isAdmin()).isEqualTo(UPDATED_ADMIN);
        assertThat(testMembreMetaGroupe.getPresentation()).isEqualTo(UPDATED_PRESENTATION);
        assertThat(testMembreMetaGroupe.getConnaissance()).isEqualTo(UPDATED_CONNAISSANCE);
        assertThat(testMembreMetaGroupe.isMailingList()).isEqualTo(UPDATED_MAILING_LIST);

        // Validate the MembreMetaGroupe in Elasticsearch
        verify(mockMembreMetaGroupeSearchRepository, times(1)).save(testMembreMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingMembreMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = membreMetaGroupeRepository.findAll().size();

        // Create the MembreMetaGroupe
        MembreMetaGroupeDTO membreMetaGroupeDTO = membreMetaGroupeMapper.toDto(membreMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreMetaGroupeMockMvc.perform(put("/api/membre-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MembreMetaGroupe in the database
        List<MembreMetaGroupe> membreMetaGroupeList = membreMetaGroupeRepository.findAll();
        assertThat(membreMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MembreMetaGroupe in Elasticsearch
        verify(mockMembreMetaGroupeSearchRepository, times(0)).save(membreMetaGroupe);
    }

    @Test
    @Transactional
    public void deleteMembreMetaGroupe() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);

        int databaseSizeBeforeDelete = membreMetaGroupeRepository.findAll().size();

        // Delete the membreMetaGroupe
        restMembreMetaGroupeMockMvc.perform(delete("/api/membre-meta-groupes/{id}", membreMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembreMetaGroupe> membreMetaGroupeList = membreMetaGroupeRepository.findAll();
        assertThat(membreMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MembreMetaGroupe in Elasticsearch
        verify(mockMembreMetaGroupeSearchRepository, times(1)).deleteById(membreMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchMembreMetaGroupe() throws Exception {
        // Initialize the database
        membreMetaGroupeRepository.saveAndFlush(membreMetaGroupe);
        when(mockMembreMetaGroupeSearchRepository.search(queryStringQuery("id:" + membreMetaGroupe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(membreMetaGroupe), PageRequest.of(0, 1), 1));
        // Search the membreMetaGroupe
        restMembreMetaGroupeMockMvc.perform(get("/api/_search/membre-meta-groupes?query=id:" + membreMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].admin").value(hasItem(DEFAULT_ADMIN.booleanValue())))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(DEFAULT_PRESENTATION.toString())))
            .andExpect(jsonPath("$.[*].connaissance").value(hasItem(DEFAULT_CONNAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].mailingList").value(hasItem(DEFAULT_MAILING_LIST.booleanValue())));
    }
}
