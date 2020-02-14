package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.LieuMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.repository.LieuMetaGroupeRepository;
import fr.herisson.missa.repository.search.LieuMetaGroupeSearchRepository;
import fr.herisson.missa.service.LieuMetaGroupeService;
import fr.herisson.missa.service.dto.LieuMetaGroupeDTO;
import fr.herisson.missa.service.mapper.LieuMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.LieuMetaGroupeCriteria;
import fr.herisson.missa.service.LieuMetaGroupeQueryService;

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
 * Integration tests for the {@link LieuMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class LieuMetaGroupeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;
    private static final Double SMALLER_LAT = 1D - 1D;

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;
    private static final Double SMALLER_LON = 1D - 1D;

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTEMENT_GROUPE = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT_GROUPE = "BBBBBBBBBB";

    @Autowired
    private LieuMetaGroupeRepository lieuMetaGroupeRepository;

    @Autowired
    private LieuMetaGroupeMapper lieuMetaGroupeMapper;

    @Autowired
    private LieuMetaGroupeService lieuMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.LieuMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private LieuMetaGroupeSearchRepository mockLieuMetaGroupeSearchRepository;

    @Autowired
    private LieuMetaGroupeQueryService lieuMetaGroupeQueryService;

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

    private MockMvc restLieuMetaGroupeMockMvc;

    private LieuMetaGroupe lieuMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LieuMetaGroupeResource lieuMetaGroupeResource = new LieuMetaGroupeResource(lieuMetaGroupeService, lieuMetaGroupeQueryService);
        this.restLieuMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(lieuMetaGroupeResource)
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
    public static LieuMetaGroupe createEntity(EntityManager em) {
        LieuMetaGroupe lieuMetaGroupe = new LieuMetaGroupe()
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .detail(DEFAULT_DETAIL)
            .departementGroupe(DEFAULT_DEPARTEMENT_GROUPE);
        return lieuMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LieuMetaGroupe createUpdatedEntity(EntityManager em) {
        LieuMetaGroupe lieuMetaGroupe = new LieuMetaGroupe()
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .detail(UPDATED_DETAIL)
            .departementGroupe(UPDATED_DEPARTEMENT_GROUPE);
        return lieuMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        lieuMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createLieuMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = lieuMetaGroupeRepository.findAll().size();

        // Create the LieuMetaGroupe
        LieuMetaGroupeDTO lieuMetaGroupeDTO = lieuMetaGroupeMapper.toDto(lieuMetaGroupe);
        restLieuMetaGroupeMockMvc.perform(post("/api/lieu-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lieuMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the LieuMetaGroupe in the database
        List<LieuMetaGroupe> lieuMetaGroupeList = lieuMetaGroupeRepository.findAll();
        assertThat(lieuMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        LieuMetaGroupe testLieuMetaGroupe = lieuMetaGroupeList.get(lieuMetaGroupeList.size() - 1);
        assertThat(testLieuMetaGroupe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLieuMetaGroupe.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testLieuMetaGroupe.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testLieuMetaGroupe.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testLieuMetaGroupe.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testLieuMetaGroupe.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testLieuMetaGroupe.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testLieuMetaGroupe.getDepartementGroupe()).isEqualTo(DEFAULT_DEPARTEMENT_GROUPE);

        // Validate the LieuMetaGroupe in Elasticsearch
        verify(mockLieuMetaGroupeSearchRepository, times(1)).save(testLieuMetaGroupe);
    }

    @Test
    @Transactional
    public void createLieuMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lieuMetaGroupeRepository.findAll().size();

        // Create the LieuMetaGroupe with an existing ID
        lieuMetaGroupe.setId(1L);
        LieuMetaGroupeDTO lieuMetaGroupeDTO = lieuMetaGroupeMapper.toDto(lieuMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLieuMetaGroupeMockMvc.perform(post("/api/lieu-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lieuMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LieuMetaGroupe in the database
        List<LieuMetaGroupe> lieuMetaGroupeList = lieuMetaGroupeRepository.findAll();
        assertThat(lieuMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the LieuMetaGroupe in Elasticsearch
        verify(mockLieuMetaGroupeSearchRepository, times(0)).save(lieuMetaGroupe);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = lieuMetaGroupeRepository.findAll().size();
        // set the field null
        lieuMetaGroupe.setNom(null);

        // Create the LieuMetaGroupe, which fails.
        LieuMetaGroupeDTO lieuMetaGroupeDTO = lieuMetaGroupeMapper.toDto(lieuMetaGroupe);

        restLieuMetaGroupeMockMvc.perform(post("/api/lieu-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lieuMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        List<LieuMetaGroupe> lieuMetaGroupeList = lieuMetaGroupeRepository.findAll();
        assertThat(lieuMetaGroupeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupes() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList
        restLieuMetaGroupeMockMvc.perform(get("/api/lieu-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lieuMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].departementGroupe").value(hasItem(DEFAULT_DEPARTEMENT_GROUPE)));
    }
    
    @Test
    @Transactional
    public void getLieuMetaGroupe() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get the lieuMetaGroupe
        restLieuMetaGroupeMockMvc.perform(get("/api/lieu-meta-groupes/{id}", lieuMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lieuMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.departementGroupe").value(DEFAULT_DEPARTEMENT_GROUPE));
    }


    @Test
    @Transactional
    public void getLieuMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        Long id = lieuMetaGroupe.getId();

        defaultLieuMetaGroupeShouldBeFound("id.equals=" + id);
        defaultLieuMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultLieuMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLieuMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultLieuMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLieuMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where nom equals to DEFAULT_NOM
        defaultLieuMetaGroupeShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the lieuMetaGroupeList where nom equals to UPDATED_NOM
        defaultLieuMetaGroupeShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where nom not equals to DEFAULT_NOM
        defaultLieuMetaGroupeShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the lieuMetaGroupeList where nom not equals to UPDATED_NOM
        defaultLieuMetaGroupeShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultLieuMetaGroupeShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the lieuMetaGroupeList where nom equals to UPDATED_NOM
        defaultLieuMetaGroupeShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where nom is not null
        defaultLieuMetaGroupeShouldBeFound("nom.specified=true");

        // Get all the lieuMetaGroupeList where nom is null
        defaultLieuMetaGroupeShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllLieuMetaGroupesByNomContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where nom contains DEFAULT_NOM
        defaultLieuMetaGroupeShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the lieuMetaGroupeList where nom contains UPDATED_NOM
        defaultLieuMetaGroupeShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where nom does not contain DEFAULT_NOM
        defaultLieuMetaGroupeShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the lieuMetaGroupeList where nom does not contain UPDATED_NOM
        defaultLieuMetaGroupeShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where adresse equals to DEFAULT_ADRESSE
        defaultLieuMetaGroupeShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the lieuMetaGroupeList where adresse equals to UPDATED_ADRESSE
        defaultLieuMetaGroupeShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where adresse not equals to DEFAULT_ADRESSE
        defaultLieuMetaGroupeShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the lieuMetaGroupeList where adresse not equals to UPDATED_ADRESSE
        defaultLieuMetaGroupeShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultLieuMetaGroupeShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the lieuMetaGroupeList where adresse equals to UPDATED_ADRESSE
        defaultLieuMetaGroupeShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where adresse is not null
        defaultLieuMetaGroupeShouldBeFound("adresse.specified=true");

        // Get all the lieuMetaGroupeList where adresse is null
        defaultLieuMetaGroupeShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllLieuMetaGroupesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where adresse contains DEFAULT_ADRESSE
        defaultLieuMetaGroupeShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the lieuMetaGroupeList where adresse contains UPDATED_ADRESSE
        defaultLieuMetaGroupeShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where adresse does not contain DEFAULT_ADRESSE
        defaultLieuMetaGroupeShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the lieuMetaGroupeList where adresse does not contain UPDATED_ADRESSE
        defaultLieuMetaGroupeShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where codePostal equals to DEFAULT_CODE_POSTAL
        defaultLieuMetaGroupeShouldBeFound("codePostal.equals=" + DEFAULT_CODE_POSTAL);

        // Get all the lieuMetaGroupeList where codePostal equals to UPDATED_CODE_POSTAL
        defaultLieuMetaGroupeShouldNotBeFound("codePostal.equals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByCodePostalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where codePostal not equals to DEFAULT_CODE_POSTAL
        defaultLieuMetaGroupeShouldNotBeFound("codePostal.notEquals=" + DEFAULT_CODE_POSTAL);

        // Get all the lieuMetaGroupeList where codePostal not equals to UPDATED_CODE_POSTAL
        defaultLieuMetaGroupeShouldBeFound("codePostal.notEquals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByCodePostalIsInShouldWork() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where codePostal in DEFAULT_CODE_POSTAL or UPDATED_CODE_POSTAL
        defaultLieuMetaGroupeShouldBeFound("codePostal.in=" + DEFAULT_CODE_POSTAL + "," + UPDATED_CODE_POSTAL);

        // Get all the lieuMetaGroupeList where codePostal equals to UPDATED_CODE_POSTAL
        defaultLieuMetaGroupeShouldNotBeFound("codePostal.in=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByCodePostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where codePostal is not null
        defaultLieuMetaGroupeShouldBeFound("codePostal.specified=true");

        // Get all the lieuMetaGroupeList where codePostal is null
        defaultLieuMetaGroupeShouldNotBeFound("codePostal.specified=false");
    }
                @Test
    @Transactional
    public void getAllLieuMetaGroupesByCodePostalContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where codePostal contains DEFAULT_CODE_POSTAL
        defaultLieuMetaGroupeShouldBeFound("codePostal.contains=" + DEFAULT_CODE_POSTAL);

        // Get all the lieuMetaGroupeList where codePostal contains UPDATED_CODE_POSTAL
        defaultLieuMetaGroupeShouldNotBeFound("codePostal.contains=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByCodePostalNotContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where codePostal does not contain DEFAULT_CODE_POSTAL
        defaultLieuMetaGroupeShouldNotBeFound("codePostal.doesNotContain=" + DEFAULT_CODE_POSTAL);

        // Get all the lieuMetaGroupeList where codePostal does not contain UPDATED_CODE_POSTAL
        defaultLieuMetaGroupeShouldBeFound("codePostal.doesNotContain=" + UPDATED_CODE_POSTAL);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where ville equals to DEFAULT_VILLE
        defaultLieuMetaGroupeShouldBeFound("ville.equals=" + DEFAULT_VILLE);

        // Get all the lieuMetaGroupeList where ville equals to UPDATED_VILLE
        defaultLieuMetaGroupeShouldNotBeFound("ville.equals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where ville not equals to DEFAULT_VILLE
        defaultLieuMetaGroupeShouldNotBeFound("ville.notEquals=" + DEFAULT_VILLE);

        // Get all the lieuMetaGroupeList where ville not equals to UPDATED_VILLE
        defaultLieuMetaGroupeShouldBeFound("ville.notEquals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByVilleIsInShouldWork() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where ville in DEFAULT_VILLE or UPDATED_VILLE
        defaultLieuMetaGroupeShouldBeFound("ville.in=" + DEFAULT_VILLE + "," + UPDATED_VILLE);

        // Get all the lieuMetaGroupeList where ville equals to UPDATED_VILLE
        defaultLieuMetaGroupeShouldNotBeFound("ville.in=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where ville is not null
        defaultLieuMetaGroupeShouldBeFound("ville.specified=true");

        // Get all the lieuMetaGroupeList where ville is null
        defaultLieuMetaGroupeShouldNotBeFound("ville.specified=false");
    }
                @Test
    @Transactional
    public void getAllLieuMetaGroupesByVilleContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where ville contains DEFAULT_VILLE
        defaultLieuMetaGroupeShouldBeFound("ville.contains=" + DEFAULT_VILLE);

        // Get all the lieuMetaGroupeList where ville contains UPDATED_VILLE
        defaultLieuMetaGroupeShouldNotBeFound("ville.contains=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByVilleNotContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where ville does not contain DEFAULT_VILLE
        defaultLieuMetaGroupeShouldNotBeFound("ville.doesNotContain=" + DEFAULT_VILLE);

        // Get all the lieuMetaGroupeList where ville does not contain UPDATED_VILLE
        defaultLieuMetaGroupeShouldBeFound("ville.doesNotContain=" + UPDATED_VILLE);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat equals to DEFAULT_LAT
        defaultLieuMetaGroupeShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the lieuMetaGroupeList where lat equals to UPDATED_LAT
        defaultLieuMetaGroupeShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat not equals to DEFAULT_LAT
        defaultLieuMetaGroupeShouldNotBeFound("lat.notEquals=" + DEFAULT_LAT);

        // Get all the lieuMetaGroupeList where lat not equals to UPDATED_LAT
        defaultLieuMetaGroupeShouldBeFound("lat.notEquals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsInShouldWork() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultLieuMetaGroupeShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the lieuMetaGroupeList where lat equals to UPDATED_LAT
        defaultLieuMetaGroupeShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat is not null
        defaultLieuMetaGroupeShouldBeFound("lat.specified=true");

        // Get all the lieuMetaGroupeList where lat is null
        defaultLieuMetaGroupeShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat is greater than or equal to DEFAULT_LAT
        defaultLieuMetaGroupeShouldBeFound("lat.greaterThanOrEqual=" + DEFAULT_LAT);

        // Get all the lieuMetaGroupeList where lat is greater than or equal to UPDATED_LAT
        defaultLieuMetaGroupeShouldNotBeFound("lat.greaterThanOrEqual=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat is less than or equal to DEFAULT_LAT
        defaultLieuMetaGroupeShouldBeFound("lat.lessThanOrEqual=" + DEFAULT_LAT);

        // Get all the lieuMetaGroupeList where lat is less than or equal to SMALLER_LAT
        defaultLieuMetaGroupeShouldNotBeFound("lat.lessThanOrEqual=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat is less than DEFAULT_LAT
        defaultLieuMetaGroupeShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the lieuMetaGroupeList where lat is less than UPDATED_LAT
        defaultLieuMetaGroupeShouldBeFound("lat.lessThan=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lat is greater than DEFAULT_LAT
        defaultLieuMetaGroupeShouldNotBeFound("lat.greaterThan=" + DEFAULT_LAT);

        // Get all the lieuMetaGroupeList where lat is greater than SMALLER_LAT
        defaultLieuMetaGroupeShouldBeFound("lat.greaterThan=" + SMALLER_LAT);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon equals to DEFAULT_LON
        defaultLieuMetaGroupeShouldBeFound("lon.equals=" + DEFAULT_LON);

        // Get all the lieuMetaGroupeList where lon equals to UPDATED_LON
        defaultLieuMetaGroupeShouldNotBeFound("lon.equals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon not equals to DEFAULT_LON
        defaultLieuMetaGroupeShouldNotBeFound("lon.notEquals=" + DEFAULT_LON);

        // Get all the lieuMetaGroupeList where lon not equals to UPDATED_LON
        defaultLieuMetaGroupeShouldBeFound("lon.notEquals=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsInShouldWork() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon in DEFAULT_LON or UPDATED_LON
        defaultLieuMetaGroupeShouldBeFound("lon.in=" + DEFAULT_LON + "," + UPDATED_LON);

        // Get all the lieuMetaGroupeList where lon equals to UPDATED_LON
        defaultLieuMetaGroupeShouldNotBeFound("lon.in=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsNullOrNotNull() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon is not null
        defaultLieuMetaGroupeShouldBeFound("lon.specified=true");

        // Get all the lieuMetaGroupeList where lon is null
        defaultLieuMetaGroupeShouldNotBeFound("lon.specified=false");
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon is greater than or equal to DEFAULT_LON
        defaultLieuMetaGroupeShouldBeFound("lon.greaterThanOrEqual=" + DEFAULT_LON);

        // Get all the lieuMetaGroupeList where lon is greater than or equal to UPDATED_LON
        defaultLieuMetaGroupeShouldNotBeFound("lon.greaterThanOrEqual=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon is less than or equal to DEFAULT_LON
        defaultLieuMetaGroupeShouldBeFound("lon.lessThanOrEqual=" + DEFAULT_LON);

        // Get all the lieuMetaGroupeList where lon is less than or equal to SMALLER_LON
        defaultLieuMetaGroupeShouldNotBeFound("lon.lessThanOrEqual=" + SMALLER_LON);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsLessThanSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon is less than DEFAULT_LON
        defaultLieuMetaGroupeShouldNotBeFound("lon.lessThan=" + DEFAULT_LON);

        // Get all the lieuMetaGroupeList where lon is less than UPDATED_LON
        defaultLieuMetaGroupeShouldBeFound("lon.lessThan=" + UPDATED_LON);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByLonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where lon is greater than DEFAULT_LON
        defaultLieuMetaGroupeShouldNotBeFound("lon.greaterThan=" + DEFAULT_LON);

        // Get all the lieuMetaGroupeList where lon is greater than SMALLER_LON
        defaultLieuMetaGroupeShouldBeFound("lon.greaterThan=" + SMALLER_LON);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByDepartementGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where departementGroupe equals to DEFAULT_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldBeFound("departementGroupe.equals=" + DEFAULT_DEPARTEMENT_GROUPE);

        // Get all the lieuMetaGroupeList where departementGroupe equals to UPDATED_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldNotBeFound("departementGroupe.equals=" + UPDATED_DEPARTEMENT_GROUPE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByDepartementGroupeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where departementGroupe not equals to DEFAULT_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldNotBeFound("departementGroupe.notEquals=" + DEFAULT_DEPARTEMENT_GROUPE);

        // Get all the lieuMetaGroupeList where departementGroupe not equals to UPDATED_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldBeFound("departementGroupe.notEquals=" + UPDATED_DEPARTEMENT_GROUPE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByDepartementGroupeIsInShouldWork() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where departementGroupe in DEFAULT_DEPARTEMENT_GROUPE or UPDATED_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldBeFound("departementGroupe.in=" + DEFAULT_DEPARTEMENT_GROUPE + "," + UPDATED_DEPARTEMENT_GROUPE);

        // Get all the lieuMetaGroupeList where departementGroupe equals to UPDATED_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldNotBeFound("departementGroupe.in=" + UPDATED_DEPARTEMENT_GROUPE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByDepartementGroupeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where departementGroupe is not null
        defaultLieuMetaGroupeShouldBeFound("departementGroupe.specified=true");

        // Get all the lieuMetaGroupeList where departementGroupe is null
        defaultLieuMetaGroupeShouldNotBeFound("departementGroupe.specified=false");
    }
                @Test
    @Transactional
    public void getAllLieuMetaGroupesByDepartementGroupeContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where departementGroupe contains DEFAULT_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldBeFound("departementGroupe.contains=" + DEFAULT_DEPARTEMENT_GROUPE);

        // Get all the lieuMetaGroupeList where departementGroupe contains UPDATED_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldNotBeFound("departementGroupe.contains=" + UPDATED_DEPARTEMENT_GROUPE);
    }

    @Test
    @Transactional
    public void getAllLieuMetaGroupesByDepartementGroupeNotContainsSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        // Get all the lieuMetaGroupeList where departementGroupe does not contain DEFAULT_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldNotBeFound("departementGroupe.doesNotContain=" + DEFAULT_DEPARTEMENT_GROUPE);

        // Get all the lieuMetaGroupeList where departementGroupe does not contain UPDATED_DEPARTEMENT_GROUPE
        defaultLieuMetaGroupeShouldBeFound("departementGroupe.doesNotContain=" + UPDATED_DEPARTEMENT_GROUPE);
    }


    @Test
    @Transactional
    public void getAllLieuMetaGroupesByGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);
        MetaGroupe groupe = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupe);
        em.flush();
        lieuMetaGroupe.setGroupe(groupe);
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);
        Long groupeId = groupe.getId();

        // Get all the lieuMetaGroupeList where groupe equals to groupeId
        defaultLieuMetaGroupeShouldBeFound("groupeId.equals=" + groupeId);

        // Get all the lieuMetaGroupeList where groupe equals to groupeId + 1
        defaultLieuMetaGroupeShouldNotBeFound("groupeId.equals=" + (groupeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLieuMetaGroupeShouldBeFound(String filter) throws Exception {
        restLieuMetaGroupeMockMvc.perform(get("/api/lieu-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lieuMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].departementGroupe").value(hasItem(DEFAULT_DEPARTEMENT_GROUPE)));

        // Check, that the count call also returns 1
        restLieuMetaGroupeMockMvc.perform(get("/api/lieu-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLieuMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restLieuMetaGroupeMockMvc.perform(get("/api/lieu-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLieuMetaGroupeMockMvc.perform(get("/api/lieu-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLieuMetaGroupe() throws Exception {
        // Get the lieuMetaGroupe
        restLieuMetaGroupeMockMvc.perform(get("/api/lieu-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLieuMetaGroupe() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        int databaseSizeBeforeUpdate = lieuMetaGroupeRepository.findAll().size();

        // Update the lieuMetaGroupe
        LieuMetaGroupe updatedLieuMetaGroupe = lieuMetaGroupeRepository.findById(lieuMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedLieuMetaGroupe are not directly saved in db
        em.detach(updatedLieuMetaGroupe);
        updatedLieuMetaGroupe
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .detail(UPDATED_DETAIL)
            .departementGroupe(UPDATED_DEPARTEMENT_GROUPE);
        LieuMetaGroupeDTO lieuMetaGroupeDTO = lieuMetaGroupeMapper.toDto(updatedLieuMetaGroupe);

        restLieuMetaGroupeMockMvc.perform(put("/api/lieu-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lieuMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the LieuMetaGroupe in the database
        List<LieuMetaGroupe> lieuMetaGroupeList = lieuMetaGroupeRepository.findAll();
        assertThat(lieuMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        LieuMetaGroupe testLieuMetaGroupe = lieuMetaGroupeList.get(lieuMetaGroupeList.size() - 1);
        assertThat(testLieuMetaGroupe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLieuMetaGroupe.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testLieuMetaGroupe.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testLieuMetaGroupe.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testLieuMetaGroupe.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testLieuMetaGroupe.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testLieuMetaGroupe.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testLieuMetaGroupe.getDepartementGroupe()).isEqualTo(UPDATED_DEPARTEMENT_GROUPE);

        // Validate the LieuMetaGroupe in Elasticsearch
        verify(mockLieuMetaGroupeSearchRepository, times(1)).save(testLieuMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingLieuMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = lieuMetaGroupeRepository.findAll().size();

        // Create the LieuMetaGroupe
        LieuMetaGroupeDTO lieuMetaGroupeDTO = lieuMetaGroupeMapper.toDto(lieuMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLieuMetaGroupeMockMvc.perform(put("/api/lieu-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lieuMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LieuMetaGroupe in the database
        List<LieuMetaGroupe> lieuMetaGroupeList = lieuMetaGroupeRepository.findAll();
        assertThat(lieuMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LieuMetaGroupe in Elasticsearch
        verify(mockLieuMetaGroupeSearchRepository, times(0)).save(lieuMetaGroupe);
    }

    @Test
    @Transactional
    public void deleteLieuMetaGroupe() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);

        int databaseSizeBeforeDelete = lieuMetaGroupeRepository.findAll().size();

        // Delete the lieuMetaGroupe
        restLieuMetaGroupeMockMvc.perform(delete("/api/lieu-meta-groupes/{id}", lieuMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LieuMetaGroupe> lieuMetaGroupeList = lieuMetaGroupeRepository.findAll();
        assertThat(lieuMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LieuMetaGroupe in Elasticsearch
        verify(mockLieuMetaGroupeSearchRepository, times(1)).deleteById(lieuMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchLieuMetaGroupe() throws Exception {
        // Initialize the database
        lieuMetaGroupeRepository.saveAndFlush(lieuMetaGroupe);
        when(mockLieuMetaGroupeSearchRepository.search(queryStringQuery("id:" + lieuMetaGroupe.getId())))
            .thenReturn(Collections.singletonList(lieuMetaGroupe));
        // Search the lieuMetaGroupe
        restLieuMetaGroupeMockMvc.perform(get("/api/_search/lieu-meta-groupes?query=id:" + lieuMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lieuMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].departementGroupe").value(hasItem(DEFAULT_DEPARTEMENT_GROUPE)));
    }
}
