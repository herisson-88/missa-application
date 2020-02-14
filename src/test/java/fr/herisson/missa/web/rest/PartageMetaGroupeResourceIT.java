package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.PartageMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.repository.PartageMetaGroupeRepository;
import fr.herisson.missa.repository.search.PartageMetaGroupeSearchRepository;
import fr.herisson.missa.service.PartageMetaGroupeService;
import fr.herisson.missa.service.dto.PartageMetaGroupeDTO;
import fr.herisson.missa.service.mapper.PartageMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.PartageMetaGroupeCriteria;
import fr.herisson.missa.service.PartageMetaGroupeQueryService;

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
 * Integration tests for the {@link PartageMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class PartageMetaGroupeResourceIT {

    private static final Boolean DEFAULT_VALIDATED = false;
    private static final Boolean UPDATED_VALIDATED = true;

    private static final ZonedDateTime DEFAULT_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_VALIDATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private PartageMetaGroupeRepository partageMetaGroupeRepository;

    @Autowired
    private PartageMetaGroupeMapper partageMetaGroupeMapper;

    @Autowired
    private PartageMetaGroupeService partageMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.PartageMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private PartageMetaGroupeSearchRepository mockPartageMetaGroupeSearchRepository;

    @Autowired
    private PartageMetaGroupeQueryService partageMetaGroupeQueryService;

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

    private MockMvc restPartageMetaGroupeMockMvc;

    private PartageMetaGroupe partageMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartageMetaGroupeResource partageMetaGroupeResource = new PartageMetaGroupeResource(partageMetaGroupeService, partageMetaGroupeQueryService);
        this.restPartageMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(partageMetaGroupeResource)
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
    public static PartageMetaGroupe createEntity(EntityManager em) {
        PartageMetaGroupe partageMetaGroupe = new PartageMetaGroupe()
            .validated(DEFAULT_VALIDATED)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .detail(DEFAULT_DETAIL);
        return partageMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartageMetaGroupe createUpdatedEntity(EntityManager em) {
        PartageMetaGroupe partageMetaGroupe = new PartageMetaGroupe()
            .validated(UPDATED_VALIDATED)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .detail(UPDATED_DETAIL);
        return partageMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        partageMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartageMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = partageMetaGroupeRepository.findAll().size();

        // Create the PartageMetaGroupe
        PartageMetaGroupeDTO partageMetaGroupeDTO = partageMetaGroupeMapper.toDto(partageMetaGroupe);
        restPartageMetaGroupeMockMvc.perform(post("/api/partage-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partageMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the PartageMetaGroupe in the database
        List<PartageMetaGroupe> partageMetaGroupeList = partageMetaGroupeRepository.findAll();
        assertThat(partageMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        PartageMetaGroupe testPartageMetaGroupe = partageMetaGroupeList.get(partageMetaGroupeList.size() - 1);
        assertThat(testPartageMetaGroupe.isValidated()).isEqualTo(DEFAULT_VALIDATED);
        assertThat(testPartageMetaGroupe.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testPartageMetaGroupe.getDetail()).isEqualTo(DEFAULT_DETAIL);

        // Validate the PartageMetaGroupe in Elasticsearch
        verify(mockPartageMetaGroupeSearchRepository, times(1)).save(testPartageMetaGroupe);
    }

    @Test
    @Transactional
    public void createPartageMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partageMetaGroupeRepository.findAll().size();

        // Create the PartageMetaGroupe with an existing ID
        partageMetaGroupe.setId(1L);
        PartageMetaGroupeDTO partageMetaGroupeDTO = partageMetaGroupeMapper.toDto(partageMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartageMetaGroupeMockMvc.perform(post("/api/partage-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partageMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartageMetaGroupe in the database
        List<PartageMetaGroupe> partageMetaGroupeList = partageMetaGroupeRepository.findAll();
        assertThat(partageMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the PartageMetaGroupe in Elasticsearch
        verify(mockPartageMetaGroupeSearchRepository, times(0)).save(partageMetaGroupe);
    }


    @Test
    @Transactional
    public void getAllPartageMetaGroupes() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList
        restPartageMetaGroupeMockMvc.perform(get("/api/partage-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partageMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getPartageMetaGroupe() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get the partageMetaGroupe
        restPartageMetaGroupeMockMvc.perform(get("/api/partage-meta-groupes/{id}", partageMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partageMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.validated").value(DEFAULT_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.dateValidation").value(sameInstant(DEFAULT_DATE_VALIDATION)))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()));
    }


    @Test
    @Transactional
    public void getPartageMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        Long id = partageMetaGroupe.getId();

        defaultPartageMetaGroupeShouldBeFound("id.equals=" + id);
        defaultPartageMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultPartageMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartageMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultPartageMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartageMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartageMetaGroupesByValidatedIsEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where validated equals to DEFAULT_VALIDATED
        defaultPartageMetaGroupeShouldBeFound("validated.equals=" + DEFAULT_VALIDATED);

        // Get all the partageMetaGroupeList where validated equals to UPDATED_VALIDATED
        defaultPartageMetaGroupeShouldNotBeFound("validated.equals=" + UPDATED_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByValidatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where validated not equals to DEFAULT_VALIDATED
        defaultPartageMetaGroupeShouldNotBeFound("validated.notEquals=" + DEFAULT_VALIDATED);

        // Get all the partageMetaGroupeList where validated not equals to UPDATED_VALIDATED
        defaultPartageMetaGroupeShouldBeFound("validated.notEquals=" + UPDATED_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByValidatedIsInShouldWork() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where validated in DEFAULT_VALIDATED or UPDATED_VALIDATED
        defaultPartageMetaGroupeShouldBeFound("validated.in=" + DEFAULT_VALIDATED + "," + UPDATED_VALIDATED);

        // Get all the partageMetaGroupeList where validated equals to UPDATED_VALIDATED
        defaultPartageMetaGroupeShouldNotBeFound("validated.in=" + UPDATED_VALIDATED);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByValidatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where validated is not null
        defaultPartageMetaGroupeShouldBeFound("validated.specified=true");

        // Get all the partageMetaGroupeList where validated is null
        defaultPartageMetaGroupeShouldNotBeFound("validated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation equals to DEFAULT_DATE_VALIDATION
        defaultPartageMetaGroupeShouldBeFound("dateValidation.equals=" + DEFAULT_DATE_VALIDATION);

        // Get all the partageMetaGroupeList where dateValidation equals to UPDATED_DATE_VALIDATION
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.equals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation not equals to DEFAULT_DATE_VALIDATION
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.notEquals=" + DEFAULT_DATE_VALIDATION);

        // Get all the partageMetaGroupeList where dateValidation not equals to UPDATED_DATE_VALIDATION
        defaultPartageMetaGroupeShouldBeFound("dateValidation.notEquals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsInShouldWork() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation in DEFAULT_DATE_VALIDATION or UPDATED_DATE_VALIDATION
        defaultPartageMetaGroupeShouldBeFound("dateValidation.in=" + DEFAULT_DATE_VALIDATION + "," + UPDATED_DATE_VALIDATION);

        // Get all the partageMetaGroupeList where dateValidation equals to UPDATED_DATE_VALIDATION
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.in=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsNullOrNotNull() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation is not null
        defaultPartageMetaGroupeShouldBeFound("dateValidation.specified=true");

        // Get all the partageMetaGroupeList where dateValidation is null
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation is greater than or equal to DEFAULT_DATE_VALIDATION
        defaultPartageMetaGroupeShouldBeFound("dateValidation.greaterThanOrEqual=" + DEFAULT_DATE_VALIDATION);

        // Get all the partageMetaGroupeList where dateValidation is greater than or equal to UPDATED_DATE_VALIDATION
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.greaterThanOrEqual=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation is less than or equal to DEFAULT_DATE_VALIDATION
        defaultPartageMetaGroupeShouldBeFound("dateValidation.lessThanOrEqual=" + DEFAULT_DATE_VALIDATION);

        // Get all the partageMetaGroupeList where dateValidation is less than or equal to SMALLER_DATE_VALIDATION
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.lessThanOrEqual=" + SMALLER_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsLessThanSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation is less than DEFAULT_DATE_VALIDATION
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.lessThan=" + DEFAULT_DATE_VALIDATION);

        // Get all the partageMetaGroupeList where dateValidation is less than UPDATED_DATE_VALIDATION
        defaultPartageMetaGroupeShouldBeFound("dateValidation.lessThan=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllPartageMetaGroupesByDateValidationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        // Get all the partageMetaGroupeList where dateValidation is greater than DEFAULT_DATE_VALIDATION
        defaultPartageMetaGroupeShouldNotBeFound("dateValidation.greaterThan=" + DEFAULT_DATE_VALIDATION);

        // Get all the partageMetaGroupeList where dateValidation is greater than SMALLER_DATE_VALIDATION
        defaultPartageMetaGroupeShouldBeFound("dateValidation.greaterThan=" + SMALLER_DATE_VALIDATION);
    }


    @Test
    @Transactional
    public void getAllPartageMetaGroupesByMetaGroupePartageIsEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        MetaGroupe metaGroupePartage = MetaGroupeResourceIT.createEntity(em);
        em.persist(metaGroupePartage);
        em.flush();
        partageMetaGroupe.setMetaGroupePartage(metaGroupePartage);
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        Long metaGroupePartageId = metaGroupePartage.getId();

        // Get all the partageMetaGroupeList where metaGroupePartage equals to metaGroupePartageId
        defaultPartageMetaGroupeShouldBeFound("metaGroupePartageId.equals=" + metaGroupePartageId);

        // Get all the partageMetaGroupeList where metaGroupePartage equals to metaGroupePartageId + 1
        defaultPartageMetaGroupeShouldNotBeFound("metaGroupePartageId.equals=" + (metaGroupePartageId + 1));
    }


    @Test
    @Transactional
    public void getAllPartageMetaGroupesByMetaGroupeDestinatairesIsEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        MetaGroupe metaGroupeDestinataires = MetaGroupeResourceIT.createEntity(em);
        em.persist(metaGroupeDestinataires);
        em.flush();
        partageMetaGroupe.setMetaGroupeDestinataires(metaGroupeDestinataires);
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        Long metaGroupeDestinatairesId = metaGroupeDestinataires.getId();

        // Get all the partageMetaGroupeList where metaGroupeDestinataires equals to metaGroupeDestinatairesId
        defaultPartageMetaGroupeShouldBeFound("metaGroupeDestinatairesId.equals=" + metaGroupeDestinatairesId);

        // Get all the partageMetaGroupeList where metaGroupeDestinataires equals to metaGroupeDestinatairesId + 1
        defaultPartageMetaGroupeShouldNotBeFound("metaGroupeDestinatairesId.equals=" + (metaGroupeDestinatairesId + 1));
    }


    @Test
    @Transactional
    public void getAllPartageMetaGroupesByAuteurPartageIsEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        MissaUser auteurPartage = MissaUserResourceIT.createEntity(em);
        em.persist(auteurPartage);
        em.flush();
        partageMetaGroupe.setAuteurPartage(auteurPartage);
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        Long auteurPartageId = auteurPartage.getId();

        // Get all the partageMetaGroupeList where auteurPartage equals to auteurPartageId
        defaultPartageMetaGroupeShouldBeFound("auteurPartageId.equals=" + auteurPartageId);

        // Get all the partageMetaGroupeList where auteurPartage equals to auteurPartageId + 1
        defaultPartageMetaGroupeShouldNotBeFound("auteurPartageId.equals=" + (auteurPartageId + 1));
    }


    @Test
    @Transactional
    public void getAllPartageMetaGroupesByValidateurDuPartageIsEqualToSomething() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        MissaUser validateurDuPartage = MissaUserResourceIT.createEntity(em);
        em.persist(validateurDuPartage);
        em.flush();
        partageMetaGroupe.setValidateurDuPartage(validateurDuPartage);
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        Long validateurDuPartageId = validateurDuPartage.getId();

        // Get all the partageMetaGroupeList where validateurDuPartage equals to validateurDuPartageId
        defaultPartageMetaGroupeShouldBeFound("validateurDuPartageId.equals=" + validateurDuPartageId);

        // Get all the partageMetaGroupeList where validateurDuPartage equals to validateurDuPartageId + 1
        defaultPartageMetaGroupeShouldNotBeFound("validateurDuPartageId.equals=" + (validateurDuPartageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartageMetaGroupeShouldBeFound(String filter) throws Exception {
        restPartageMetaGroupeMockMvc.perform(get("/api/partage-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partageMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));

        // Check, that the count call also returns 1
        restPartageMetaGroupeMockMvc.perform(get("/api/partage-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartageMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restPartageMetaGroupeMockMvc.perform(get("/api/partage-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartageMetaGroupeMockMvc.perform(get("/api/partage-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPartageMetaGroupe() throws Exception {
        // Get the partageMetaGroupe
        restPartageMetaGroupeMockMvc.perform(get("/api/partage-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartageMetaGroupe() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        int databaseSizeBeforeUpdate = partageMetaGroupeRepository.findAll().size();

        // Update the partageMetaGroupe
        PartageMetaGroupe updatedPartageMetaGroupe = partageMetaGroupeRepository.findById(partageMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedPartageMetaGroupe are not directly saved in db
        em.detach(updatedPartageMetaGroupe);
        updatedPartageMetaGroupe
            .validated(UPDATED_VALIDATED)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .detail(UPDATED_DETAIL);
        PartageMetaGroupeDTO partageMetaGroupeDTO = partageMetaGroupeMapper.toDto(updatedPartageMetaGroupe);

        restPartageMetaGroupeMockMvc.perform(put("/api/partage-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partageMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the PartageMetaGroupe in the database
        List<PartageMetaGroupe> partageMetaGroupeList = partageMetaGroupeRepository.findAll();
        assertThat(partageMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        PartageMetaGroupe testPartageMetaGroupe = partageMetaGroupeList.get(partageMetaGroupeList.size() - 1);
        assertThat(testPartageMetaGroupe.isValidated()).isEqualTo(UPDATED_VALIDATED);
        assertThat(testPartageMetaGroupe.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testPartageMetaGroupe.getDetail()).isEqualTo(UPDATED_DETAIL);

        // Validate the PartageMetaGroupe in Elasticsearch
        verify(mockPartageMetaGroupeSearchRepository, times(1)).save(testPartageMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingPartageMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = partageMetaGroupeRepository.findAll().size();

        // Create the PartageMetaGroupe
        PartageMetaGroupeDTO partageMetaGroupeDTO = partageMetaGroupeMapper.toDto(partageMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartageMetaGroupeMockMvc.perform(put("/api/partage-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partageMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartageMetaGroupe in the database
        List<PartageMetaGroupe> partageMetaGroupeList = partageMetaGroupeRepository.findAll();
        assertThat(partageMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PartageMetaGroupe in Elasticsearch
        verify(mockPartageMetaGroupeSearchRepository, times(0)).save(partageMetaGroupe);
    }

    @Test
    @Transactional
    public void deletePartageMetaGroupe() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);

        int databaseSizeBeforeDelete = partageMetaGroupeRepository.findAll().size();

        // Delete the partageMetaGroupe
        restPartageMetaGroupeMockMvc.perform(delete("/api/partage-meta-groupes/{id}", partageMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartageMetaGroupe> partageMetaGroupeList = partageMetaGroupeRepository.findAll();
        assertThat(partageMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PartageMetaGroupe in Elasticsearch
        verify(mockPartageMetaGroupeSearchRepository, times(1)).deleteById(partageMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchPartageMetaGroupe() throws Exception {
        // Initialize the database
        partageMetaGroupeRepository.saveAndFlush(partageMetaGroupe);
        when(mockPartageMetaGroupeSearchRepository.search(queryStringQuery("id:" + partageMetaGroupe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(partageMetaGroupe), PageRequest.of(0, 1), 1));
        // Search the partageMetaGroupe
        restPartageMetaGroupeMockMvc.perform(get("/api/_search/partage-meta-groupes?query=id:" + partageMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partageMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].validated").value(hasItem(DEFAULT_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
}
