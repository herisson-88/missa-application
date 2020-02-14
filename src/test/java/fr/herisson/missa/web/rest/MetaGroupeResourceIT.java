package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.MembreMetaGroupe;
import fr.herisson.missa.domain.DateMetaGroupe;
import fr.herisson.missa.domain.LieuMetaGroupe;
import fr.herisson.missa.domain.LienDocMetaGroupe;
import fr.herisson.missa.domain.OrganisateurMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.MessageMetaGroupe;
import fr.herisson.missa.domain.EnvoiDeMail;
import fr.herisson.missa.domain.PartageMetaGroupe;
import fr.herisson.missa.domain.TypeMetaGroupe;
import fr.herisson.missa.domain.Reseau;
import fr.herisson.missa.repository.MetaGroupeRepository;
import fr.herisson.missa.repository.search.MetaGroupeSearchRepository;
import fr.herisson.missa.service.MetaGroupeService;
import fr.herisson.missa.service.dto.MetaGroupeDTO;
import fr.herisson.missa.service.mapper.MetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.MetaGroupeCriteria;
import fr.herisson.missa.service.MetaGroupeQueryService;

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

import fr.herisson.missa.domain.enumeration.MembreDiffusion;
import fr.herisson.missa.domain.enumeration.Visibilite;
/**
 * Integration tests for the {@link MetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class MetaGroupeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTORITE_VALIDATION = false;
    private static final Boolean UPDATED_AUTORITE_VALIDATION = true;

    private static final MembreDiffusion DEFAULT_MEMBRE_PARENT = MembreDiffusion.DIFFUSION_UP;
    private static final MembreDiffusion UPDATED_MEMBRE_PARENT = MembreDiffusion.DIFFUSION_ALL;

    private static final Visibilite DEFAULT_VISIBILITE = Visibilite.EXISTENCE_PUBLIC;
    private static final Visibilite UPDATED_VISIBILITE = Visibilite.VISIBILITE_PUBLIC;

    private static final Boolean DEFAULT_DROIT_ENVOI_DE_MAIL = false;
    private static final Boolean UPDATED_DROIT_ENVOI_DE_MAIL = true;

    private static final Boolean DEFAULT_DEMANDE_DIFFUSION_VERTICALE = false;
    private static final Boolean UPDATED_DEMANDE_DIFFUSION_VERTICALE = true;

    private static final Boolean DEFAULT_MESSAGERIE_MODEREE = false;
    private static final Boolean UPDATED_MESSAGERIE_MODEREE = true;

    private static final Boolean DEFAULT_GROUPE_FINAL = false;
    private static final Boolean UPDATED_GROUPE_FINAL = true;

    private static final ZonedDateTime DEFAULT_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_VALIDATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_VALIDATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private MetaGroupeRepository metaGroupeRepository;

    @Autowired
    private MetaGroupeMapper metaGroupeMapper;

    @Autowired
    private MetaGroupeService metaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.MetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private MetaGroupeSearchRepository mockMetaGroupeSearchRepository;

    @Autowired
    private MetaGroupeQueryService metaGroupeQueryService;

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

    private MockMvc restMetaGroupeMockMvc;

    private MetaGroupe metaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MetaGroupeResource metaGroupeResource = new MetaGroupeResource(metaGroupeService, metaGroupeQueryService);
        this.restMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(metaGroupeResource)
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
    public static MetaGroupe createEntity(EntityManager em) {
        MetaGroupe metaGroupe = new MetaGroupe()
            .nom(DEFAULT_NOM)
            .autoriteValidation(DEFAULT_AUTORITE_VALIDATION)
            .membreParent(DEFAULT_MEMBRE_PARENT)
            .visibilite(DEFAULT_VISIBILITE)
            .droitEnvoiDeMail(DEFAULT_DROIT_ENVOI_DE_MAIL)
            .demandeDiffusionVerticale(DEFAULT_DEMANDE_DIFFUSION_VERTICALE)
            .messagerieModeree(DEFAULT_MESSAGERIE_MODEREE)
            .groupeFinal(DEFAULT_GROUPE_FINAL)
            .dateValidation(DEFAULT_DATE_VALIDATION)
            .detail(DEFAULT_DETAIL);
        return metaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MetaGroupe createUpdatedEntity(EntityManager em) {
        MetaGroupe metaGroupe = new MetaGroupe()
            .nom(UPDATED_NOM)
            .autoriteValidation(UPDATED_AUTORITE_VALIDATION)
            .membreParent(UPDATED_MEMBRE_PARENT)
            .visibilite(UPDATED_VISIBILITE)
            .droitEnvoiDeMail(UPDATED_DROIT_ENVOI_DE_MAIL)
            .demandeDiffusionVerticale(UPDATED_DEMANDE_DIFFUSION_VERTICALE)
            .messagerieModeree(UPDATED_MESSAGERIE_MODEREE)
            .groupeFinal(UPDATED_GROUPE_FINAL)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .detail(UPDATED_DETAIL);
        return metaGroupe;
    }

    @BeforeEach
    public void initTest() {
        metaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = metaGroupeRepository.findAll().size();

        // Create the MetaGroupe
        MetaGroupeDTO metaGroupeDTO = metaGroupeMapper.toDto(metaGroupe);
        restMetaGroupeMockMvc.perform(post("/api/meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(metaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the MetaGroupe in the database
        List<MetaGroupe> metaGroupeList = metaGroupeRepository.findAll();
        assertThat(metaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        MetaGroupe testMetaGroupe = metaGroupeList.get(metaGroupeList.size() - 1);
        assertThat(testMetaGroupe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMetaGroupe.isAutoriteValidation()).isEqualTo(DEFAULT_AUTORITE_VALIDATION);
        assertThat(testMetaGroupe.getMembreParent()).isEqualTo(DEFAULT_MEMBRE_PARENT);
        assertThat(testMetaGroupe.getVisibilite()).isEqualTo(DEFAULT_VISIBILITE);
        assertThat(testMetaGroupe.isDroitEnvoiDeMail()).isEqualTo(DEFAULT_DROIT_ENVOI_DE_MAIL);
        assertThat(testMetaGroupe.isDemandeDiffusionVerticale()).isEqualTo(DEFAULT_DEMANDE_DIFFUSION_VERTICALE);
        assertThat(testMetaGroupe.isMessagerieModeree()).isEqualTo(DEFAULT_MESSAGERIE_MODEREE);
        assertThat(testMetaGroupe.isGroupeFinal()).isEqualTo(DEFAULT_GROUPE_FINAL);
        assertThat(testMetaGroupe.getDateValidation()).isEqualTo(DEFAULT_DATE_VALIDATION);
        assertThat(testMetaGroupe.getDetail()).isEqualTo(DEFAULT_DETAIL);

        // Validate the MetaGroupe in Elasticsearch
        verify(mockMetaGroupeSearchRepository, times(1)).save(testMetaGroupe);
    }

    @Test
    @Transactional
    public void createMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = metaGroupeRepository.findAll().size();

        // Create the MetaGroupe with an existing ID
        metaGroupe.setId(1L);
        MetaGroupeDTO metaGroupeDTO = metaGroupeMapper.toDto(metaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaGroupeMockMvc.perform(post("/api/meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(metaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaGroupe in the database
        List<MetaGroupe> metaGroupeList = metaGroupeRepository.findAll();
        assertThat(metaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the MetaGroupe in Elasticsearch
        verify(mockMetaGroupeSearchRepository, times(0)).save(metaGroupe);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = metaGroupeRepository.findAll().size();
        // set the field null
        metaGroupe.setNom(null);

        // Create the MetaGroupe, which fails.
        MetaGroupeDTO metaGroupeDTO = metaGroupeMapper.toDto(metaGroupe);

        restMetaGroupeMockMvc.perform(post("/api/meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(metaGroupeDTO)))
            .andExpect(status().isBadRequest());

        List<MetaGroupe> metaGroupeList = metaGroupeRepository.findAll();
        assertThat(metaGroupeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMetaGroupes() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList
        restMetaGroupeMockMvc.perform(get("/api/meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].autoriteValidation").value(hasItem(DEFAULT_AUTORITE_VALIDATION.booleanValue())))
            .andExpect(jsonPath("$.[*].membreParent").value(hasItem(DEFAULT_MEMBRE_PARENT.toString())))
            .andExpect(jsonPath("$.[*].visibilite").value(hasItem(DEFAULT_VISIBILITE.toString())))
            .andExpect(jsonPath("$.[*].droitEnvoiDeMail").value(hasItem(DEFAULT_DROIT_ENVOI_DE_MAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].demandeDiffusionVerticale").value(hasItem(DEFAULT_DEMANDE_DIFFUSION_VERTICALE.booleanValue())))
            .andExpect(jsonPath("$.[*].messagerieModeree").value(hasItem(DEFAULT_MESSAGERIE_MODEREE.booleanValue())))
            .andExpect(jsonPath("$.[*].groupeFinal").value(hasItem(DEFAULT_GROUPE_FINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getMetaGroupe() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get the metaGroupe
        restMetaGroupeMockMvc.perform(get("/api/meta-groupes/{id}", metaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(metaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.autoriteValidation").value(DEFAULT_AUTORITE_VALIDATION.booleanValue()))
            .andExpect(jsonPath("$.membreParent").value(DEFAULT_MEMBRE_PARENT.toString()))
            .andExpect(jsonPath("$.visibilite").value(DEFAULT_VISIBILITE.toString()))
            .andExpect(jsonPath("$.droitEnvoiDeMail").value(DEFAULT_DROIT_ENVOI_DE_MAIL.booleanValue()))
            .andExpect(jsonPath("$.demandeDiffusionVerticale").value(DEFAULT_DEMANDE_DIFFUSION_VERTICALE.booleanValue()))
            .andExpect(jsonPath("$.messagerieModeree").value(DEFAULT_MESSAGERIE_MODEREE.booleanValue()))
            .andExpect(jsonPath("$.groupeFinal").value(DEFAULT_GROUPE_FINAL.booleanValue()))
            .andExpect(jsonPath("$.dateValidation").value(sameInstant(DEFAULT_DATE_VALIDATION)))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()));
    }


    @Test
    @Transactional
    public void getMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        Long id = metaGroupe.getId();

        defaultMetaGroupeShouldBeFound("id.equals=" + id);
        defaultMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where nom equals to DEFAULT_NOM
        defaultMetaGroupeShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the metaGroupeList where nom equals to UPDATED_NOM
        defaultMetaGroupeShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where nom not equals to DEFAULT_NOM
        defaultMetaGroupeShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the metaGroupeList where nom not equals to UPDATED_NOM
        defaultMetaGroupeShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultMetaGroupeShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the metaGroupeList where nom equals to UPDATED_NOM
        defaultMetaGroupeShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where nom is not null
        defaultMetaGroupeShouldBeFound("nom.specified=true");

        // Get all the metaGroupeList where nom is null
        defaultMetaGroupeShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllMetaGroupesByNomContainsSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where nom contains DEFAULT_NOM
        defaultMetaGroupeShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the metaGroupeList where nom contains UPDATED_NOM
        defaultMetaGroupeShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where nom does not contain DEFAULT_NOM
        defaultMetaGroupeShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the metaGroupeList where nom does not contain UPDATED_NOM
        defaultMetaGroupeShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByAutoriteValidationIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where autoriteValidation equals to DEFAULT_AUTORITE_VALIDATION
        defaultMetaGroupeShouldBeFound("autoriteValidation.equals=" + DEFAULT_AUTORITE_VALIDATION);

        // Get all the metaGroupeList where autoriteValidation equals to UPDATED_AUTORITE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("autoriteValidation.equals=" + UPDATED_AUTORITE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByAutoriteValidationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where autoriteValidation not equals to DEFAULT_AUTORITE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("autoriteValidation.notEquals=" + DEFAULT_AUTORITE_VALIDATION);

        // Get all the metaGroupeList where autoriteValidation not equals to UPDATED_AUTORITE_VALIDATION
        defaultMetaGroupeShouldBeFound("autoriteValidation.notEquals=" + UPDATED_AUTORITE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByAutoriteValidationIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where autoriteValidation in DEFAULT_AUTORITE_VALIDATION or UPDATED_AUTORITE_VALIDATION
        defaultMetaGroupeShouldBeFound("autoriteValidation.in=" + DEFAULT_AUTORITE_VALIDATION + "," + UPDATED_AUTORITE_VALIDATION);

        // Get all the metaGroupeList where autoriteValidation equals to UPDATED_AUTORITE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("autoriteValidation.in=" + UPDATED_AUTORITE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByAutoriteValidationIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where autoriteValidation is not null
        defaultMetaGroupeShouldBeFound("autoriteValidation.specified=true");

        // Get all the metaGroupeList where autoriteValidation is null
        defaultMetaGroupeShouldNotBeFound("autoriteValidation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMembreParentIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where membreParent equals to DEFAULT_MEMBRE_PARENT
        defaultMetaGroupeShouldBeFound("membreParent.equals=" + DEFAULT_MEMBRE_PARENT);

        // Get all the metaGroupeList where membreParent equals to UPDATED_MEMBRE_PARENT
        defaultMetaGroupeShouldNotBeFound("membreParent.equals=" + UPDATED_MEMBRE_PARENT);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMembreParentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where membreParent not equals to DEFAULT_MEMBRE_PARENT
        defaultMetaGroupeShouldNotBeFound("membreParent.notEquals=" + DEFAULT_MEMBRE_PARENT);

        // Get all the metaGroupeList where membreParent not equals to UPDATED_MEMBRE_PARENT
        defaultMetaGroupeShouldBeFound("membreParent.notEquals=" + UPDATED_MEMBRE_PARENT);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMembreParentIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where membreParent in DEFAULT_MEMBRE_PARENT or UPDATED_MEMBRE_PARENT
        defaultMetaGroupeShouldBeFound("membreParent.in=" + DEFAULT_MEMBRE_PARENT + "," + UPDATED_MEMBRE_PARENT);

        // Get all the metaGroupeList where membreParent equals to UPDATED_MEMBRE_PARENT
        defaultMetaGroupeShouldNotBeFound("membreParent.in=" + UPDATED_MEMBRE_PARENT);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMembreParentIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where membreParent is not null
        defaultMetaGroupeShouldBeFound("membreParent.specified=true");

        // Get all the metaGroupeList where membreParent is null
        defaultMetaGroupeShouldNotBeFound("membreParent.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByVisibiliteIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where visibilite equals to DEFAULT_VISIBILITE
        defaultMetaGroupeShouldBeFound("visibilite.equals=" + DEFAULT_VISIBILITE);

        // Get all the metaGroupeList where visibilite equals to UPDATED_VISIBILITE
        defaultMetaGroupeShouldNotBeFound("visibilite.equals=" + UPDATED_VISIBILITE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByVisibiliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where visibilite not equals to DEFAULT_VISIBILITE
        defaultMetaGroupeShouldNotBeFound("visibilite.notEquals=" + DEFAULT_VISIBILITE);

        // Get all the metaGroupeList where visibilite not equals to UPDATED_VISIBILITE
        defaultMetaGroupeShouldBeFound("visibilite.notEquals=" + UPDATED_VISIBILITE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByVisibiliteIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where visibilite in DEFAULT_VISIBILITE or UPDATED_VISIBILITE
        defaultMetaGroupeShouldBeFound("visibilite.in=" + DEFAULT_VISIBILITE + "," + UPDATED_VISIBILITE);

        // Get all the metaGroupeList where visibilite equals to UPDATED_VISIBILITE
        defaultMetaGroupeShouldNotBeFound("visibilite.in=" + UPDATED_VISIBILITE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByVisibiliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where visibilite is not null
        defaultMetaGroupeShouldBeFound("visibilite.specified=true");

        // Get all the metaGroupeList where visibilite is null
        defaultMetaGroupeShouldNotBeFound("visibilite.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDroitEnvoiDeMailIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where droitEnvoiDeMail equals to DEFAULT_DROIT_ENVOI_DE_MAIL
        defaultMetaGroupeShouldBeFound("droitEnvoiDeMail.equals=" + DEFAULT_DROIT_ENVOI_DE_MAIL);

        // Get all the metaGroupeList where droitEnvoiDeMail equals to UPDATED_DROIT_ENVOI_DE_MAIL
        defaultMetaGroupeShouldNotBeFound("droitEnvoiDeMail.equals=" + UPDATED_DROIT_ENVOI_DE_MAIL);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDroitEnvoiDeMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where droitEnvoiDeMail not equals to DEFAULT_DROIT_ENVOI_DE_MAIL
        defaultMetaGroupeShouldNotBeFound("droitEnvoiDeMail.notEquals=" + DEFAULT_DROIT_ENVOI_DE_MAIL);

        // Get all the metaGroupeList where droitEnvoiDeMail not equals to UPDATED_DROIT_ENVOI_DE_MAIL
        defaultMetaGroupeShouldBeFound("droitEnvoiDeMail.notEquals=" + UPDATED_DROIT_ENVOI_DE_MAIL);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDroitEnvoiDeMailIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where droitEnvoiDeMail in DEFAULT_DROIT_ENVOI_DE_MAIL or UPDATED_DROIT_ENVOI_DE_MAIL
        defaultMetaGroupeShouldBeFound("droitEnvoiDeMail.in=" + DEFAULT_DROIT_ENVOI_DE_MAIL + "," + UPDATED_DROIT_ENVOI_DE_MAIL);

        // Get all the metaGroupeList where droitEnvoiDeMail equals to UPDATED_DROIT_ENVOI_DE_MAIL
        defaultMetaGroupeShouldNotBeFound("droitEnvoiDeMail.in=" + UPDATED_DROIT_ENVOI_DE_MAIL);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDroitEnvoiDeMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where droitEnvoiDeMail is not null
        defaultMetaGroupeShouldBeFound("droitEnvoiDeMail.specified=true");

        // Get all the metaGroupeList where droitEnvoiDeMail is null
        defaultMetaGroupeShouldNotBeFound("droitEnvoiDeMail.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDemandeDiffusionVerticaleIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where demandeDiffusionVerticale equals to DEFAULT_DEMANDE_DIFFUSION_VERTICALE
        defaultMetaGroupeShouldBeFound("demandeDiffusionVerticale.equals=" + DEFAULT_DEMANDE_DIFFUSION_VERTICALE);

        // Get all the metaGroupeList where demandeDiffusionVerticale equals to UPDATED_DEMANDE_DIFFUSION_VERTICALE
        defaultMetaGroupeShouldNotBeFound("demandeDiffusionVerticale.equals=" + UPDATED_DEMANDE_DIFFUSION_VERTICALE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDemandeDiffusionVerticaleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where demandeDiffusionVerticale not equals to DEFAULT_DEMANDE_DIFFUSION_VERTICALE
        defaultMetaGroupeShouldNotBeFound("demandeDiffusionVerticale.notEquals=" + DEFAULT_DEMANDE_DIFFUSION_VERTICALE);

        // Get all the metaGroupeList where demandeDiffusionVerticale not equals to UPDATED_DEMANDE_DIFFUSION_VERTICALE
        defaultMetaGroupeShouldBeFound("demandeDiffusionVerticale.notEquals=" + UPDATED_DEMANDE_DIFFUSION_VERTICALE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDemandeDiffusionVerticaleIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where demandeDiffusionVerticale in DEFAULT_DEMANDE_DIFFUSION_VERTICALE or UPDATED_DEMANDE_DIFFUSION_VERTICALE
        defaultMetaGroupeShouldBeFound("demandeDiffusionVerticale.in=" + DEFAULT_DEMANDE_DIFFUSION_VERTICALE + "," + UPDATED_DEMANDE_DIFFUSION_VERTICALE);

        // Get all the metaGroupeList where demandeDiffusionVerticale equals to UPDATED_DEMANDE_DIFFUSION_VERTICALE
        defaultMetaGroupeShouldNotBeFound("demandeDiffusionVerticale.in=" + UPDATED_DEMANDE_DIFFUSION_VERTICALE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDemandeDiffusionVerticaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where demandeDiffusionVerticale is not null
        defaultMetaGroupeShouldBeFound("demandeDiffusionVerticale.specified=true");

        // Get all the metaGroupeList where demandeDiffusionVerticale is null
        defaultMetaGroupeShouldNotBeFound("demandeDiffusionVerticale.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMessagerieModereeIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where messagerieModeree equals to DEFAULT_MESSAGERIE_MODEREE
        defaultMetaGroupeShouldBeFound("messagerieModeree.equals=" + DEFAULT_MESSAGERIE_MODEREE);

        // Get all the metaGroupeList where messagerieModeree equals to UPDATED_MESSAGERIE_MODEREE
        defaultMetaGroupeShouldNotBeFound("messagerieModeree.equals=" + UPDATED_MESSAGERIE_MODEREE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMessagerieModereeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where messagerieModeree not equals to DEFAULT_MESSAGERIE_MODEREE
        defaultMetaGroupeShouldNotBeFound("messagerieModeree.notEquals=" + DEFAULT_MESSAGERIE_MODEREE);

        // Get all the metaGroupeList where messagerieModeree not equals to UPDATED_MESSAGERIE_MODEREE
        defaultMetaGroupeShouldBeFound("messagerieModeree.notEquals=" + UPDATED_MESSAGERIE_MODEREE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMessagerieModereeIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where messagerieModeree in DEFAULT_MESSAGERIE_MODEREE or UPDATED_MESSAGERIE_MODEREE
        defaultMetaGroupeShouldBeFound("messagerieModeree.in=" + DEFAULT_MESSAGERIE_MODEREE + "," + UPDATED_MESSAGERIE_MODEREE);

        // Get all the metaGroupeList where messagerieModeree equals to UPDATED_MESSAGERIE_MODEREE
        defaultMetaGroupeShouldNotBeFound("messagerieModeree.in=" + UPDATED_MESSAGERIE_MODEREE);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByMessagerieModereeIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where messagerieModeree is not null
        defaultMetaGroupeShouldBeFound("messagerieModeree.specified=true");

        // Get all the metaGroupeList where messagerieModeree is null
        defaultMetaGroupeShouldNotBeFound("messagerieModeree.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByGroupeFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where groupeFinal equals to DEFAULT_GROUPE_FINAL
        defaultMetaGroupeShouldBeFound("groupeFinal.equals=" + DEFAULT_GROUPE_FINAL);

        // Get all the metaGroupeList where groupeFinal equals to UPDATED_GROUPE_FINAL
        defaultMetaGroupeShouldNotBeFound("groupeFinal.equals=" + UPDATED_GROUPE_FINAL);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByGroupeFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where groupeFinal not equals to DEFAULT_GROUPE_FINAL
        defaultMetaGroupeShouldNotBeFound("groupeFinal.notEquals=" + DEFAULT_GROUPE_FINAL);

        // Get all the metaGroupeList where groupeFinal not equals to UPDATED_GROUPE_FINAL
        defaultMetaGroupeShouldBeFound("groupeFinal.notEquals=" + UPDATED_GROUPE_FINAL);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByGroupeFinalIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where groupeFinal in DEFAULT_GROUPE_FINAL or UPDATED_GROUPE_FINAL
        defaultMetaGroupeShouldBeFound("groupeFinal.in=" + DEFAULT_GROUPE_FINAL + "," + UPDATED_GROUPE_FINAL);

        // Get all the metaGroupeList where groupeFinal equals to UPDATED_GROUPE_FINAL
        defaultMetaGroupeShouldNotBeFound("groupeFinal.in=" + UPDATED_GROUPE_FINAL);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByGroupeFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where groupeFinal is not null
        defaultMetaGroupeShouldBeFound("groupeFinal.specified=true");

        // Get all the metaGroupeList where groupeFinal is null
        defaultMetaGroupeShouldNotBeFound("groupeFinal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation equals to DEFAULT_DATE_VALIDATION
        defaultMetaGroupeShouldBeFound("dateValidation.equals=" + DEFAULT_DATE_VALIDATION);

        // Get all the metaGroupeList where dateValidation equals to UPDATED_DATE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("dateValidation.equals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation not equals to DEFAULT_DATE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("dateValidation.notEquals=" + DEFAULT_DATE_VALIDATION);

        // Get all the metaGroupeList where dateValidation not equals to UPDATED_DATE_VALIDATION
        defaultMetaGroupeShouldBeFound("dateValidation.notEquals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsInShouldWork() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation in DEFAULT_DATE_VALIDATION or UPDATED_DATE_VALIDATION
        defaultMetaGroupeShouldBeFound("dateValidation.in=" + DEFAULT_DATE_VALIDATION + "," + UPDATED_DATE_VALIDATION);

        // Get all the metaGroupeList where dateValidation equals to UPDATED_DATE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("dateValidation.in=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation is not null
        defaultMetaGroupeShouldBeFound("dateValidation.specified=true");

        // Get all the metaGroupeList where dateValidation is null
        defaultMetaGroupeShouldNotBeFound("dateValidation.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation is greater than or equal to DEFAULT_DATE_VALIDATION
        defaultMetaGroupeShouldBeFound("dateValidation.greaterThanOrEqual=" + DEFAULT_DATE_VALIDATION);

        // Get all the metaGroupeList where dateValidation is greater than or equal to UPDATED_DATE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("dateValidation.greaterThanOrEqual=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation is less than or equal to DEFAULT_DATE_VALIDATION
        defaultMetaGroupeShouldBeFound("dateValidation.lessThanOrEqual=" + DEFAULT_DATE_VALIDATION);

        // Get all the metaGroupeList where dateValidation is less than or equal to SMALLER_DATE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("dateValidation.lessThanOrEqual=" + SMALLER_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsLessThanSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation is less than DEFAULT_DATE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("dateValidation.lessThan=" + DEFAULT_DATE_VALIDATION);

        // Get all the metaGroupeList where dateValidation is less than UPDATED_DATE_VALIDATION
        defaultMetaGroupeShouldBeFound("dateValidation.lessThan=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllMetaGroupesByDateValidationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        // Get all the metaGroupeList where dateValidation is greater than DEFAULT_DATE_VALIDATION
        defaultMetaGroupeShouldNotBeFound("dateValidation.greaterThan=" + DEFAULT_DATE_VALIDATION);

        // Get all the metaGroupeList where dateValidation is greater than SMALLER_DATE_VALIDATION
        defaultMetaGroupeShouldBeFound("dateValidation.greaterThan=" + SMALLER_DATE_VALIDATION);
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByMembresIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        MembreMetaGroupe membres = MembreMetaGroupeResourceIT.createEntity(em);
        em.persist(membres);
        em.flush();
        metaGroupe.addMembres(membres);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long membresId = membres.getId();

        // Get all the metaGroupeList where membres equals to membresId
        defaultMetaGroupeShouldBeFound("membresId.equals=" + membresId);

        // Get all the metaGroupeList where membres equals to membresId + 1
        defaultMetaGroupeShouldNotBeFound("membresId.equals=" + (membresId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByDatesIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        DateMetaGroupe dates = DateMetaGroupeResourceIT.createEntity(em);
        em.persist(dates);
        em.flush();
        metaGroupe.addDates(dates);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long datesId = dates.getId();

        // Get all the metaGroupeList where dates equals to datesId
        defaultMetaGroupeShouldBeFound("datesId.equals=" + datesId);

        // Get all the metaGroupeList where dates equals to datesId + 1
        defaultMetaGroupeShouldNotBeFound("datesId.equals=" + (datesId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByLieuIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        LieuMetaGroupe lieu = LieuMetaGroupeResourceIT.createEntity(em);
        em.persist(lieu);
        em.flush();
        metaGroupe.addLieu(lieu);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long lieuId = lieu.getId();

        // Get all the metaGroupeList where lieu equals to lieuId
        defaultMetaGroupeShouldBeFound("lieuId.equals=" + lieuId);

        // Get all the metaGroupeList where lieu equals to lieuId + 1
        defaultMetaGroupeShouldNotBeFound("lieuId.equals=" + (lieuId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByDocsIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        LienDocMetaGroupe docs = LienDocMetaGroupeResourceIT.createEntity(em);
        em.persist(docs);
        em.flush();
        metaGroupe.addDocs(docs);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long docsId = docs.getId();

        // Get all the metaGroupeList where docs equals to docsId
        defaultMetaGroupeShouldBeFound("docsId.equals=" + docsId);

        // Get all the metaGroupeList where docs equals to docsId + 1
        defaultMetaGroupeShouldNotBeFound("docsId.equals=" + (docsId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByCoordonneesOrganisateursIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        OrganisateurMetaGroupe coordonneesOrganisateurs = OrganisateurMetaGroupeResourceIT.createEntity(em);
        em.persist(coordonneesOrganisateurs);
        em.flush();
        metaGroupe.addCoordonneesOrganisateurs(coordonneesOrganisateurs);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long coordonneesOrganisateursId = coordonneesOrganisateurs.getId();

        // Get all the metaGroupeList where coordonneesOrganisateurs equals to coordonneesOrganisateursId
        defaultMetaGroupeShouldBeFound("coordonneesOrganisateursId.equals=" + coordonneesOrganisateursId);

        // Get all the metaGroupeList where coordonneesOrganisateurs equals to coordonneesOrganisateursId + 1
        defaultMetaGroupeShouldNotBeFound("coordonneesOrganisateursId.equals=" + (coordonneesOrganisateursId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesBySousGroupesIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        MetaGroupe sousGroupes = MetaGroupeResourceIT.createEntity(em);
        em.persist(sousGroupes);
        em.flush();
        metaGroupe.addSousGroupes(sousGroupes);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long sousGroupesId = sousGroupes.getId();

        // Get all the metaGroupeList where sousGroupes equals to sousGroupesId
        defaultMetaGroupeShouldBeFound("sousGroupesId.equals=" + sousGroupesId);

        // Get all the metaGroupeList where sousGroupes equals to sousGroupesId + 1
        defaultMetaGroupeShouldNotBeFound("sousGroupesId.equals=" + (sousGroupesId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByMessagesDuGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        MessageMetaGroupe messagesDuGroupe = MessageMetaGroupeResourceIT.createEntity(em);
        em.persist(messagesDuGroupe);
        em.flush();
        metaGroupe.addMessagesDuGroupe(messagesDuGroupe);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long messagesDuGroupeId = messagesDuGroupe.getId();

        // Get all the metaGroupeList where messagesDuGroupe equals to messagesDuGroupeId
        defaultMetaGroupeShouldBeFound("messagesDuGroupeId.equals=" + messagesDuGroupeId);

        // Get all the metaGroupeList where messagesDuGroupe equals to messagesDuGroupeId + 1
        defaultMetaGroupeShouldNotBeFound("messagesDuGroupeId.equals=" + (messagesDuGroupeId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByMailsIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        EnvoiDeMail mails = EnvoiDeMailResourceIT.createEntity(em);
        em.persist(mails);
        em.flush();
        metaGroupe.addMails(mails);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long mailsId = mails.getId();

        // Get all the metaGroupeList where mails equals to mailsId
        defaultMetaGroupeShouldBeFound("mailsId.equals=" + mailsId);

        // Get all the metaGroupeList where mails equals to mailsId + 1
        defaultMetaGroupeShouldNotBeFound("mailsId.equals=" + (mailsId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByPartagesVersIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        PartageMetaGroupe partagesVers = PartageMetaGroupeResourceIT.createEntity(em);
        em.persist(partagesVers);
        em.flush();
        metaGroupe.addPartagesVers(partagesVers);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long partagesVersId = partagesVers.getId();

        // Get all the metaGroupeList where partagesVers equals to partagesVersId
        defaultMetaGroupeShouldBeFound("partagesVersId.equals=" + partagesVersId);

        // Get all the metaGroupeList where partagesVers equals to partagesVersId + 1
        defaultMetaGroupeShouldNotBeFound("partagesVersId.equals=" + (partagesVersId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByPartagesRecusIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        PartageMetaGroupe partagesRecus = PartageMetaGroupeResourceIT.createEntity(em);
        em.persist(partagesRecus);
        em.flush();
        metaGroupe.addPartagesRecus(partagesRecus);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long partagesRecusId = partagesRecus.getId();

        // Get all the metaGroupeList where partagesRecus equals to partagesRecusId
        defaultMetaGroupeShouldBeFound("partagesRecusId.equals=" + partagesRecusId);

        // Get all the metaGroupeList where partagesRecus equals to partagesRecusId + 1
        defaultMetaGroupeShouldNotBeFound("partagesRecusId.equals=" + (partagesRecusId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        MetaGroupe parent = MetaGroupeResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        metaGroupe.setParent(parent);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long parentId = parent.getId();

        // Get all the metaGroupeList where parent equals to parentId
        defaultMetaGroupeShouldBeFound("parentId.equals=" + parentId);

        // Get all the metaGroupeList where parent equals to parentId + 1
        defaultMetaGroupeShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        TypeMetaGroupe type = TypeMetaGroupeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        metaGroupe.setType(type);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long typeId = type.getId();

        // Get all the metaGroupeList where type equals to typeId
        defaultMetaGroupeShouldBeFound("typeId.equals=" + typeId);

        // Get all the metaGroupeList where type equals to typeId + 1
        defaultMetaGroupeShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByReseauIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Reseau reseau = ReseauResourceIT.createEntity(em);
        em.persist(reseau);
        em.flush();
        metaGroupe.setReseau(reseau);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long reseauId = reseau.getId();

        // Get all the metaGroupeList where reseau equals to reseauId
        defaultMetaGroupeShouldBeFound("reseauId.equals=" + reseauId);

        // Get all the metaGroupeList where reseau equals to reseauId + 1
        defaultMetaGroupeShouldNotBeFound("reseauId.equals=" + (reseauId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaGroupesByMessageMailReferentIsEqualToSomething() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        EnvoiDeMail messageMailReferent = EnvoiDeMailResourceIT.createEntity(em);
        em.persist(messageMailReferent);
        em.flush();
        metaGroupe.addMessageMailReferent(messageMailReferent);
        metaGroupeRepository.saveAndFlush(metaGroupe);
        Long messageMailReferentId = messageMailReferent.getId();

        // Get all the metaGroupeList where messageMailReferent equals to messageMailReferentId
        defaultMetaGroupeShouldBeFound("messageMailReferentId.equals=" + messageMailReferentId);

        // Get all the metaGroupeList where messageMailReferent equals to messageMailReferentId + 1
        defaultMetaGroupeShouldNotBeFound("messageMailReferentId.equals=" + (messageMailReferentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMetaGroupeShouldBeFound(String filter) throws Exception {
        restMetaGroupeMockMvc.perform(get("/api/meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].autoriteValidation").value(hasItem(DEFAULT_AUTORITE_VALIDATION.booleanValue())))
            .andExpect(jsonPath("$.[*].membreParent").value(hasItem(DEFAULT_MEMBRE_PARENT.toString())))
            .andExpect(jsonPath("$.[*].visibilite").value(hasItem(DEFAULT_VISIBILITE.toString())))
            .andExpect(jsonPath("$.[*].droitEnvoiDeMail").value(hasItem(DEFAULT_DROIT_ENVOI_DE_MAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].demandeDiffusionVerticale").value(hasItem(DEFAULT_DEMANDE_DIFFUSION_VERTICALE.booleanValue())))
            .andExpect(jsonPath("$.[*].messagerieModeree").value(hasItem(DEFAULT_MESSAGERIE_MODEREE.booleanValue())))
            .andExpect(jsonPath("$.[*].groupeFinal").value(hasItem(DEFAULT_GROUPE_FINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));

        // Check, that the count call also returns 1
        restMetaGroupeMockMvc.perform(get("/api/meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restMetaGroupeMockMvc.perform(get("/api/meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMetaGroupeMockMvc.perform(get("/api/meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMetaGroupe() throws Exception {
        // Get the metaGroupe
        restMetaGroupeMockMvc.perform(get("/api/meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMetaGroupe() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        int databaseSizeBeforeUpdate = metaGroupeRepository.findAll().size();

        // Update the metaGroupe
        MetaGroupe updatedMetaGroupe = metaGroupeRepository.findById(metaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedMetaGroupe are not directly saved in db
        em.detach(updatedMetaGroupe);
        updatedMetaGroupe
            .nom(UPDATED_NOM)
            .autoriteValidation(UPDATED_AUTORITE_VALIDATION)
            .membreParent(UPDATED_MEMBRE_PARENT)
            .visibilite(UPDATED_VISIBILITE)
            .droitEnvoiDeMail(UPDATED_DROIT_ENVOI_DE_MAIL)
            .demandeDiffusionVerticale(UPDATED_DEMANDE_DIFFUSION_VERTICALE)
            .messagerieModeree(UPDATED_MESSAGERIE_MODEREE)
            .groupeFinal(UPDATED_GROUPE_FINAL)
            .dateValidation(UPDATED_DATE_VALIDATION)
            .detail(UPDATED_DETAIL);
        MetaGroupeDTO metaGroupeDTO = metaGroupeMapper.toDto(updatedMetaGroupe);

        restMetaGroupeMockMvc.perform(put("/api/meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(metaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the MetaGroupe in the database
        List<MetaGroupe> metaGroupeList = metaGroupeRepository.findAll();
        assertThat(metaGroupeList).hasSize(databaseSizeBeforeUpdate);
        MetaGroupe testMetaGroupe = metaGroupeList.get(metaGroupeList.size() - 1);
        assertThat(testMetaGroupe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMetaGroupe.isAutoriteValidation()).isEqualTo(UPDATED_AUTORITE_VALIDATION);
        assertThat(testMetaGroupe.getMembreParent()).isEqualTo(UPDATED_MEMBRE_PARENT);
        assertThat(testMetaGroupe.getVisibilite()).isEqualTo(UPDATED_VISIBILITE);
        assertThat(testMetaGroupe.isDroitEnvoiDeMail()).isEqualTo(UPDATED_DROIT_ENVOI_DE_MAIL);
        assertThat(testMetaGroupe.isDemandeDiffusionVerticale()).isEqualTo(UPDATED_DEMANDE_DIFFUSION_VERTICALE);
        assertThat(testMetaGroupe.isMessagerieModeree()).isEqualTo(UPDATED_MESSAGERIE_MODEREE);
        assertThat(testMetaGroupe.isGroupeFinal()).isEqualTo(UPDATED_GROUPE_FINAL);
        assertThat(testMetaGroupe.getDateValidation()).isEqualTo(UPDATED_DATE_VALIDATION);
        assertThat(testMetaGroupe.getDetail()).isEqualTo(UPDATED_DETAIL);

        // Validate the MetaGroupe in Elasticsearch
        verify(mockMetaGroupeSearchRepository, times(1)).save(testMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = metaGroupeRepository.findAll().size();

        // Create the MetaGroupe
        MetaGroupeDTO metaGroupeDTO = metaGroupeMapper.toDto(metaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaGroupeMockMvc.perform(put("/api/meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(metaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaGroupe in the database
        List<MetaGroupe> metaGroupeList = metaGroupeRepository.findAll();
        assertThat(metaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MetaGroupe in Elasticsearch
        verify(mockMetaGroupeSearchRepository, times(0)).save(metaGroupe);
    }

    @Test
    @Transactional
    public void deleteMetaGroupe() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);

        int databaseSizeBeforeDelete = metaGroupeRepository.findAll().size();

        // Delete the metaGroupe
        restMetaGroupeMockMvc.perform(delete("/api/meta-groupes/{id}", metaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MetaGroupe> metaGroupeList = metaGroupeRepository.findAll();
        assertThat(metaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MetaGroupe in Elasticsearch
        verify(mockMetaGroupeSearchRepository, times(1)).deleteById(metaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchMetaGroupe() throws Exception {
        // Initialize the database
        metaGroupeRepository.saveAndFlush(metaGroupe);
        when(mockMetaGroupeSearchRepository.search(queryStringQuery("id:" + metaGroupe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(metaGroupe), PageRequest.of(0, 1), 1));
        // Search the metaGroupe
        restMetaGroupeMockMvc.perform(get("/api/_search/meta-groupes?query=id:" + metaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].autoriteValidation").value(hasItem(DEFAULT_AUTORITE_VALIDATION.booleanValue())))
            .andExpect(jsonPath("$.[*].membreParent").value(hasItem(DEFAULT_MEMBRE_PARENT.toString())))
            .andExpect(jsonPath("$.[*].visibilite").value(hasItem(DEFAULT_VISIBILITE.toString())))
            .andExpect(jsonPath("$.[*].droitEnvoiDeMail").value(hasItem(DEFAULT_DROIT_ENVOI_DE_MAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].demandeDiffusionVerticale").value(hasItem(DEFAULT_DEMANDE_DIFFUSION_VERTICALE.booleanValue())))
            .andExpect(jsonPath("$.[*].messagerieModeree").value(hasItem(DEFAULT_MESSAGERIE_MODEREE.booleanValue())))
            .andExpect(jsonPath("$.[*].groupeFinal").value(hasItem(DEFAULT_GROUPE_FINAL.booleanValue())))
            .andExpect(jsonPath("$.[*].dateValidation").value(hasItem(sameInstant(DEFAULT_DATE_VALIDATION))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
}
