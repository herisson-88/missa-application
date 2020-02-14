package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.DateMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.repository.DateMetaGroupeRepository;
import fr.herisson.missa.repository.search.DateMetaGroupeSearchRepository;
import fr.herisson.missa.service.DateMetaGroupeService;
import fr.herisson.missa.service.dto.DateMetaGroupeDTO;
import fr.herisson.missa.service.mapper.DateMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.DateMetaGroupeCriteria;
import fr.herisson.missa.service.DateMetaGroupeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import fr.herisson.missa.domain.enumeration.Day;
/**
 * Integration tests for the {@link DateMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class DateMetaGroupeResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Day DEFAULT_EVERY = Day.DIMANCHE;
    private static final Day UPDATED_EVERY = Day.LUNDI;

    private static final Integer DEFAULT_HOUR = 1;
    private static final Integer UPDATED_HOUR = 2;
    private static final Integer SMALLER_HOUR = 1 - 1;

    private static final Integer DEFAULT_MINUTES = 1;
    private static final Integer UPDATED_MINUTES = 2;
    private static final Integer SMALLER_MINUTES = 1 - 1;

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private DateMetaGroupeRepository dateMetaGroupeRepository;

    @Autowired
    private DateMetaGroupeMapper dateMetaGroupeMapper;

    @Autowired
    private DateMetaGroupeService dateMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.DateMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private DateMetaGroupeSearchRepository mockDateMetaGroupeSearchRepository;

    @Autowired
    private DateMetaGroupeQueryService dateMetaGroupeQueryService;

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

    private MockMvc restDateMetaGroupeMockMvc;

    private DateMetaGroupe dateMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DateMetaGroupeResource dateMetaGroupeResource = new DateMetaGroupeResource(dateMetaGroupeService, dateMetaGroupeQueryService);
        this.restDateMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(dateMetaGroupeResource)
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
    public static DateMetaGroupe createEntity(EntityManager em) {
        DateMetaGroupe dateMetaGroupe = new DateMetaGroupe()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .every(DEFAULT_EVERY)
            .hour(DEFAULT_HOUR)
            .minutes(DEFAULT_MINUTES)
            .detail(DEFAULT_DETAIL);
        return dateMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DateMetaGroupe createUpdatedEntity(EntityManager em) {
        DateMetaGroupe dateMetaGroupe = new DateMetaGroupe()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .every(UPDATED_EVERY)
            .hour(UPDATED_HOUR)
            .minutes(UPDATED_MINUTES)
            .detail(UPDATED_DETAIL);
        return dateMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        dateMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createDateMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = dateMetaGroupeRepository.findAll().size();

        // Create the DateMetaGroupe
        DateMetaGroupeDTO dateMetaGroupeDTO = dateMetaGroupeMapper.toDto(dateMetaGroupe);
        restDateMetaGroupeMockMvc.perform(post("/api/date-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dateMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the DateMetaGroupe in the database
        List<DateMetaGroupe> dateMetaGroupeList = dateMetaGroupeRepository.findAll();
        assertThat(dateMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        DateMetaGroupe testDateMetaGroupe = dateMetaGroupeList.get(dateMetaGroupeList.size() - 1);
        assertThat(testDateMetaGroupe.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testDateMetaGroupe.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testDateMetaGroupe.getEvery()).isEqualTo(DEFAULT_EVERY);
        assertThat(testDateMetaGroupe.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(testDateMetaGroupe.getMinutes()).isEqualTo(DEFAULT_MINUTES);
        assertThat(testDateMetaGroupe.getDetail()).isEqualTo(DEFAULT_DETAIL);

        // Validate the DateMetaGroupe in Elasticsearch
        verify(mockDateMetaGroupeSearchRepository, times(1)).save(testDateMetaGroupe);
    }

    @Test
    @Transactional
    public void createDateMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dateMetaGroupeRepository.findAll().size();

        // Create the DateMetaGroupe with an existing ID
        dateMetaGroupe.setId(1L);
        DateMetaGroupeDTO dateMetaGroupeDTO = dateMetaGroupeMapper.toDto(dateMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDateMetaGroupeMockMvc.perform(post("/api/date-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dateMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DateMetaGroupe in the database
        List<DateMetaGroupe> dateMetaGroupeList = dateMetaGroupeRepository.findAll();
        assertThat(dateMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the DateMetaGroupe in Elasticsearch
        verify(mockDateMetaGroupeSearchRepository, times(0)).save(dateMetaGroupe);
    }


    @Test
    @Transactional
    public void getAllDateMetaGroupes() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList
        restDateMetaGroupeMockMvc.perform(get("/api/date-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dateMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))))
            .andExpect(jsonPath("$.[*].every").value(hasItem(DEFAULT_EVERY.toString())))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)))
            .andExpect(jsonPath("$.[*].minutes").value(hasItem(DEFAULT_MINUTES)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getDateMetaGroupe() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get the dateMetaGroupe
        restDateMetaGroupeMockMvc.perform(get("/api/date-meta-groupes/{id}", dateMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dateMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(sameInstant(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.dateFin").value(sameInstant(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.every").value(DEFAULT_EVERY.toString()))
            .andExpect(jsonPath("$.hour").value(DEFAULT_HOUR))
            .andExpect(jsonPath("$.minutes").value(DEFAULT_MINUTES))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()));
    }


    @Test
    @Transactional
    public void getDateMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        Long id = dateMetaGroupe.getId();

        defaultDateMetaGroupeShouldBeFound("id.equals=" + id);
        defaultDateMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultDateMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDateMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultDateMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDateMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut equals to DEFAULT_DATE_DEBUT
        defaultDateMetaGroupeShouldBeFound("dateDebut.equals=" + DEFAULT_DATE_DEBUT);

        // Get all the dateMetaGroupeList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.equals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut not equals to DEFAULT_DATE_DEBUT
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.notEquals=" + DEFAULT_DATE_DEBUT);

        // Get all the dateMetaGroupeList where dateDebut not equals to UPDATED_DATE_DEBUT
        defaultDateMetaGroupeShouldBeFound("dateDebut.notEquals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsInShouldWork() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut in DEFAULT_DATE_DEBUT or UPDATED_DATE_DEBUT
        defaultDateMetaGroupeShouldBeFound("dateDebut.in=" + DEFAULT_DATE_DEBUT + "," + UPDATED_DATE_DEBUT);

        // Get all the dateMetaGroupeList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.in=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut is not null
        defaultDateMetaGroupeShouldBeFound("dateDebut.specified=true");

        // Get all the dateMetaGroupeList where dateDebut is null
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.specified=false");
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut is greater than or equal to DEFAULT_DATE_DEBUT
        defaultDateMetaGroupeShouldBeFound("dateDebut.greaterThanOrEqual=" + DEFAULT_DATE_DEBUT);

        // Get all the dateMetaGroupeList where dateDebut is greater than or equal to UPDATED_DATE_DEBUT
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.greaterThanOrEqual=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut is less than or equal to DEFAULT_DATE_DEBUT
        defaultDateMetaGroupeShouldBeFound("dateDebut.lessThanOrEqual=" + DEFAULT_DATE_DEBUT);

        // Get all the dateMetaGroupeList where dateDebut is less than or equal to SMALLER_DATE_DEBUT
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.lessThanOrEqual=" + SMALLER_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsLessThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut is less than DEFAULT_DATE_DEBUT
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.lessThan=" + DEFAULT_DATE_DEBUT);

        // Get all the dateMetaGroupeList where dateDebut is less than UPDATED_DATE_DEBUT
        defaultDateMetaGroupeShouldBeFound("dateDebut.lessThan=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateDebutIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateDebut is greater than DEFAULT_DATE_DEBUT
        defaultDateMetaGroupeShouldNotBeFound("dateDebut.greaterThan=" + DEFAULT_DATE_DEBUT);

        // Get all the dateMetaGroupeList where dateDebut is greater than SMALLER_DATE_DEBUT
        defaultDateMetaGroupeShouldBeFound("dateDebut.greaterThan=" + SMALLER_DATE_DEBUT);
    }


    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin equals to DEFAULT_DATE_FIN
        defaultDateMetaGroupeShouldBeFound("dateFin.equals=" + DEFAULT_DATE_FIN);

        // Get all the dateMetaGroupeList where dateFin equals to UPDATED_DATE_FIN
        defaultDateMetaGroupeShouldNotBeFound("dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin not equals to DEFAULT_DATE_FIN
        defaultDateMetaGroupeShouldNotBeFound("dateFin.notEquals=" + DEFAULT_DATE_FIN);

        // Get all the dateMetaGroupeList where dateFin not equals to UPDATED_DATE_FIN
        defaultDateMetaGroupeShouldBeFound("dateFin.notEquals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin in DEFAULT_DATE_FIN or UPDATED_DATE_FIN
        defaultDateMetaGroupeShouldBeFound("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN);

        // Get all the dateMetaGroupeList where dateFin equals to UPDATED_DATE_FIN
        defaultDateMetaGroupeShouldNotBeFound("dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin is not null
        defaultDateMetaGroupeShouldBeFound("dateFin.specified=true");

        // Get all the dateMetaGroupeList where dateFin is null
        defaultDateMetaGroupeShouldNotBeFound("dateFin.specified=false");
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin is greater than or equal to DEFAULT_DATE_FIN
        defaultDateMetaGroupeShouldBeFound("dateFin.greaterThanOrEqual=" + DEFAULT_DATE_FIN);

        // Get all the dateMetaGroupeList where dateFin is greater than or equal to UPDATED_DATE_FIN
        defaultDateMetaGroupeShouldNotBeFound("dateFin.greaterThanOrEqual=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin is less than or equal to DEFAULT_DATE_FIN
        defaultDateMetaGroupeShouldBeFound("dateFin.lessThanOrEqual=" + DEFAULT_DATE_FIN);

        // Get all the dateMetaGroupeList where dateFin is less than or equal to SMALLER_DATE_FIN
        defaultDateMetaGroupeShouldNotBeFound("dateFin.lessThanOrEqual=" + SMALLER_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsLessThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin is less than DEFAULT_DATE_FIN
        defaultDateMetaGroupeShouldNotBeFound("dateFin.lessThan=" + DEFAULT_DATE_FIN);

        // Get all the dateMetaGroupeList where dateFin is less than UPDATED_DATE_FIN
        defaultDateMetaGroupeShouldBeFound("dateFin.lessThan=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByDateFinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where dateFin is greater than DEFAULT_DATE_FIN
        defaultDateMetaGroupeShouldNotBeFound("dateFin.greaterThan=" + DEFAULT_DATE_FIN);

        // Get all the dateMetaGroupeList where dateFin is greater than SMALLER_DATE_FIN
        defaultDateMetaGroupeShouldBeFound("dateFin.greaterThan=" + SMALLER_DATE_FIN);
    }


    @Test
    @Transactional
    public void getAllDateMetaGroupesByEveryIsEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where every equals to DEFAULT_EVERY
        defaultDateMetaGroupeShouldBeFound("every.equals=" + DEFAULT_EVERY);

        // Get all the dateMetaGroupeList where every equals to UPDATED_EVERY
        defaultDateMetaGroupeShouldNotBeFound("every.equals=" + UPDATED_EVERY);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByEveryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where every not equals to DEFAULT_EVERY
        defaultDateMetaGroupeShouldNotBeFound("every.notEquals=" + DEFAULT_EVERY);

        // Get all the dateMetaGroupeList where every not equals to UPDATED_EVERY
        defaultDateMetaGroupeShouldBeFound("every.notEquals=" + UPDATED_EVERY);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByEveryIsInShouldWork() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where every in DEFAULT_EVERY or UPDATED_EVERY
        defaultDateMetaGroupeShouldBeFound("every.in=" + DEFAULT_EVERY + "," + UPDATED_EVERY);

        // Get all the dateMetaGroupeList where every equals to UPDATED_EVERY
        defaultDateMetaGroupeShouldNotBeFound("every.in=" + UPDATED_EVERY);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByEveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where every is not null
        defaultDateMetaGroupeShouldBeFound("every.specified=true");

        // Get all the dateMetaGroupeList where every is null
        defaultDateMetaGroupeShouldNotBeFound("every.specified=false");
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour equals to DEFAULT_HOUR
        defaultDateMetaGroupeShouldBeFound("hour.equals=" + DEFAULT_HOUR);

        // Get all the dateMetaGroupeList where hour equals to UPDATED_HOUR
        defaultDateMetaGroupeShouldNotBeFound("hour.equals=" + UPDATED_HOUR);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour not equals to DEFAULT_HOUR
        defaultDateMetaGroupeShouldNotBeFound("hour.notEquals=" + DEFAULT_HOUR);

        // Get all the dateMetaGroupeList where hour not equals to UPDATED_HOUR
        defaultDateMetaGroupeShouldBeFound("hour.notEquals=" + UPDATED_HOUR);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsInShouldWork() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour in DEFAULT_HOUR or UPDATED_HOUR
        defaultDateMetaGroupeShouldBeFound("hour.in=" + DEFAULT_HOUR + "," + UPDATED_HOUR);

        // Get all the dateMetaGroupeList where hour equals to UPDATED_HOUR
        defaultDateMetaGroupeShouldNotBeFound("hour.in=" + UPDATED_HOUR);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour is not null
        defaultDateMetaGroupeShouldBeFound("hour.specified=true");

        // Get all the dateMetaGroupeList where hour is null
        defaultDateMetaGroupeShouldNotBeFound("hour.specified=false");
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour is greater than or equal to DEFAULT_HOUR
        defaultDateMetaGroupeShouldBeFound("hour.greaterThanOrEqual=" + DEFAULT_HOUR);

        // Get all the dateMetaGroupeList where hour is greater than or equal to UPDATED_HOUR
        defaultDateMetaGroupeShouldNotBeFound("hour.greaterThanOrEqual=" + UPDATED_HOUR);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour is less than or equal to DEFAULT_HOUR
        defaultDateMetaGroupeShouldBeFound("hour.lessThanOrEqual=" + DEFAULT_HOUR);

        // Get all the dateMetaGroupeList where hour is less than or equal to SMALLER_HOUR
        defaultDateMetaGroupeShouldNotBeFound("hour.lessThanOrEqual=" + SMALLER_HOUR);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsLessThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour is less than DEFAULT_HOUR
        defaultDateMetaGroupeShouldNotBeFound("hour.lessThan=" + DEFAULT_HOUR);

        // Get all the dateMetaGroupeList where hour is less than UPDATED_HOUR
        defaultDateMetaGroupeShouldBeFound("hour.lessThan=" + UPDATED_HOUR);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByHourIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where hour is greater than DEFAULT_HOUR
        defaultDateMetaGroupeShouldNotBeFound("hour.greaterThan=" + DEFAULT_HOUR);

        // Get all the dateMetaGroupeList where hour is greater than SMALLER_HOUR
        defaultDateMetaGroupeShouldBeFound("hour.greaterThan=" + SMALLER_HOUR);
    }


    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes equals to DEFAULT_MINUTES
        defaultDateMetaGroupeShouldBeFound("minutes.equals=" + DEFAULT_MINUTES);

        // Get all the dateMetaGroupeList where minutes equals to UPDATED_MINUTES
        defaultDateMetaGroupeShouldNotBeFound("minutes.equals=" + UPDATED_MINUTES);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes not equals to DEFAULT_MINUTES
        defaultDateMetaGroupeShouldNotBeFound("minutes.notEquals=" + DEFAULT_MINUTES);

        // Get all the dateMetaGroupeList where minutes not equals to UPDATED_MINUTES
        defaultDateMetaGroupeShouldBeFound("minutes.notEquals=" + UPDATED_MINUTES);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsInShouldWork() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes in DEFAULT_MINUTES or UPDATED_MINUTES
        defaultDateMetaGroupeShouldBeFound("minutes.in=" + DEFAULT_MINUTES + "," + UPDATED_MINUTES);

        // Get all the dateMetaGroupeList where minutes equals to UPDATED_MINUTES
        defaultDateMetaGroupeShouldNotBeFound("minutes.in=" + UPDATED_MINUTES);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsNullOrNotNull() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes is not null
        defaultDateMetaGroupeShouldBeFound("minutes.specified=true");

        // Get all the dateMetaGroupeList where minutes is null
        defaultDateMetaGroupeShouldNotBeFound("minutes.specified=false");
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes is greater than or equal to DEFAULT_MINUTES
        defaultDateMetaGroupeShouldBeFound("minutes.greaterThanOrEqual=" + DEFAULT_MINUTES);

        // Get all the dateMetaGroupeList where minutes is greater than or equal to UPDATED_MINUTES
        defaultDateMetaGroupeShouldNotBeFound("minutes.greaterThanOrEqual=" + UPDATED_MINUTES);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes is less than or equal to DEFAULT_MINUTES
        defaultDateMetaGroupeShouldBeFound("minutes.lessThanOrEqual=" + DEFAULT_MINUTES);

        // Get all the dateMetaGroupeList where minutes is less than or equal to SMALLER_MINUTES
        defaultDateMetaGroupeShouldNotBeFound("minutes.lessThanOrEqual=" + SMALLER_MINUTES);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsLessThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes is less than DEFAULT_MINUTES
        defaultDateMetaGroupeShouldNotBeFound("minutes.lessThan=" + DEFAULT_MINUTES);

        // Get all the dateMetaGroupeList where minutes is less than UPDATED_MINUTES
        defaultDateMetaGroupeShouldBeFound("minutes.lessThan=" + UPDATED_MINUTES);
    }

    @Test
    @Transactional
    public void getAllDateMetaGroupesByMinutesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        // Get all the dateMetaGroupeList where minutes is greater than DEFAULT_MINUTES
        defaultDateMetaGroupeShouldNotBeFound("minutes.greaterThan=" + DEFAULT_MINUTES);

        // Get all the dateMetaGroupeList where minutes is greater than SMALLER_MINUTES
        defaultDateMetaGroupeShouldBeFound("minutes.greaterThan=" + SMALLER_MINUTES);
    }


    @Test
    @Transactional
    public void getAllDateMetaGroupesByGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);
        MetaGroupe groupe = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupe);
        em.flush();
        dateMetaGroupe.setGroupe(groupe);
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);
        Long groupeId = groupe.getId();

        // Get all the dateMetaGroupeList where groupe equals to groupeId
        defaultDateMetaGroupeShouldBeFound("groupeId.equals=" + groupeId);

        // Get all the dateMetaGroupeList where groupe equals to groupeId + 1
        defaultDateMetaGroupeShouldNotBeFound("groupeId.equals=" + (groupeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDateMetaGroupeShouldBeFound(String filter) throws Exception {
        restDateMetaGroupeMockMvc.perform(get("/api/date-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dateMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))))
            .andExpect(jsonPath("$.[*].every").value(hasItem(DEFAULT_EVERY.toString())))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)))
            .andExpect(jsonPath("$.[*].minutes").value(hasItem(DEFAULT_MINUTES)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));

        // Check, that the count call also returns 1
        restDateMetaGroupeMockMvc.perform(get("/api/date-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDateMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restDateMetaGroupeMockMvc.perform(get("/api/date-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDateMetaGroupeMockMvc.perform(get("/api/date-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDateMetaGroupe() throws Exception {
        // Get the dateMetaGroupe
        restDateMetaGroupeMockMvc.perform(get("/api/date-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDateMetaGroupe() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        int databaseSizeBeforeUpdate = dateMetaGroupeRepository.findAll().size();

        // Update the dateMetaGroupe
        DateMetaGroupe updatedDateMetaGroupe = dateMetaGroupeRepository.findById(dateMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedDateMetaGroupe are not directly saved in db
        em.detach(updatedDateMetaGroupe);
        updatedDateMetaGroupe
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .every(UPDATED_EVERY)
            .hour(UPDATED_HOUR)
            .minutes(UPDATED_MINUTES)
            .detail(UPDATED_DETAIL);
        DateMetaGroupeDTO dateMetaGroupeDTO = dateMetaGroupeMapper.toDto(updatedDateMetaGroupe);

        restDateMetaGroupeMockMvc.perform(put("/api/date-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dateMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the DateMetaGroupe in the database
        List<DateMetaGroupe> dateMetaGroupeList = dateMetaGroupeRepository.findAll();
        assertThat(dateMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        DateMetaGroupe testDateMetaGroupe = dateMetaGroupeList.get(dateMetaGroupeList.size() - 1);
        assertThat(testDateMetaGroupe.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testDateMetaGroupe.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testDateMetaGroupe.getEvery()).isEqualTo(UPDATED_EVERY);
        assertThat(testDateMetaGroupe.getHour()).isEqualTo(UPDATED_HOUR);
        assertThat(testDateMetaGroupe.getMinutes()).isEqualTo(UPDATED_MINUTES);
        assertThat(testDateMetaGroupe.getDetail()).isEqualTo(UPDATED_DETAIL);

        // Validate the DateMetaGroupe in Elasticsearch
        verify(mockDateMetaGroupeSearchRepository, times(1)).save(testDateMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingDateMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = dateMetaGroupeRepository.findAll().size();

        // Create the DateMetaGroupe
        DateMetaGroupeDTO dateMetaGroupeDTO = dateMetaGroupeMapper.toDto(dateMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDateMetaGroupeMockMvc.perform(put("/api/date-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dateMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DateMetaGroupe in the database
        List<DateMetaGroupe> dateMetaGroupeList = dateMetaGroupeRepository.findAll();
        assertThat(dateMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DateMetaGroupe in Elasticsearch
        verify(mockDateMetaGroupeSearchRepository, times(0)).save(dateMetaGroupe);
    }

    @Test
    @Transactional
    public void deleteDateMetaGroupe() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);

        int databaseSizeBeforeDelete = dateMetaGroupeRepository.findAll().size();

        // Delete the dateMetaGroupe
        restDateMetaGroupeMockMvc.perform(delete("/api/date-meta-groupes/{id}", dateMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DateMetaGroupe> dateMetaGroupeList = dateMetaGroupeRepository.findAll();
        assertThat(dateMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DateMetaGroupe in Elasticsearch
        verify(mockDateMetaGroupeSearchRepository, times(1)).deleteById(dateMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchDateMetaGroupe() throws Exception {
        // Initialize the database
        dateMetaGroupeRepository.saveAndFlush(dateMetaGroupe);
        when(mockDateMetaGroupeSearchRepository.search(queryStringQuery("id:" + dateMetaGroupe.getId())))
            .thenReturn(Collections.singletonList(dateMetaGroupe));
        // Search the dateMetaGroupe
        restDateMetaGroupeMockMvc.perform(get("/api/_search/date-meta-groupes?query=id:" + dateMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dateMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))))
            .andExpect(jsonPath("$.[*].every").value(hasItem(DEFAULT_EVERY.toString())))
            .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR)))
            .andExpect(jsonPath("$.[*].minutes").value(hasItem(DEFAULT_MINUTES)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
}
