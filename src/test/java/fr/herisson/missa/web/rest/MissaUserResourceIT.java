package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.domain.User;
import fr.herisson.missa.domain.Connaissance;
import fr.herisson.missa.domain.MembreMetaGroupe;
import fr.herisson.missa.domain.MessageMetaGroupe;
import fr.herisson.missa.domain.EnvoiDeMail;
import fr.herisson.missa.domain.PartageMetaGroupe;
import fr.herisson.missa.repository.MissaUserRepository;
import fr.herisson.missa.repository.search.MissaUserSearchRepository;
import fr.herisson.missa.service.MissaUserService;
import fr.herisson.missa.service.dto.MissaUserDTO;
import fr.herisson.missa.service.mapper.MissaUserMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.MissaUserCriteria;
import fr.herisson.missa.service.MissaUserQueryService;

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

import fr.herisson.missa.domain.enumeration.EtatUser;
/**
 * Integration tests for the {@link MissaUserResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class MissaUserResourceIT {

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_INITIALES = "AAAAAAAAAA";
    private static final String UPDATED_INITIALES = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final EtatUser DEFAULT_ETAT = EtatUser.CREE;
    private static final EtatUser UPDATED_ETAT = EtatUser.VALIDE;

    @Autowired
    private MissaUserRepository missaUserRepository;

    @Autowired
    private MissaUserMapper missaUserMapper;

    @Autowired
    private MissaUserService missaUserService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.MissaUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private MissaUserSearchRepository mockMissaUserSearchRepository;

    @Autowired
    private MissaUserQueryService missaUserQueryService;

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

    private MockMvc restMissaUserMockMvc;

    private MissaUser missaUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MissaUserResource missaUserResource = new MissaUserResource(missaUserService, missaUserQueryService);
        this.restMissaUserMockMvc = MockMvcBuilders.standaloneSetup(missaUserResource)
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
    public static MissaUser createEntity(EntityManager em) {
        MissaUser missaUser = new MissaUser()
            .codePostal(DEFAULT_CODE_POSTAL)
            .initiales(DEFAULT_INITIALES)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .mail(DEFAULT_MAIL)
            .etat(DEFAULT_ETAT);
        return missaUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MissaUser createUpdatedEntity(EntityManager em) {
        MissaUser missaUser = new MissaUser()
            .codePostal(UPDATED_CODE_POSTAL)
            .initiales(UPDATED_INITIALES)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .mail(UPDATED_MAIL)
            .etat(UPDATED_ETAT);
        return missaUser;
    }

    @BeforeEach
    public void initTest() {
        missaUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMissaUser() throws Exception {
        int databaseSizeBeforeCreate = missaUserRepository.findAll().size();

        // Create the MissaUser
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);
        restMissaUserMockMvc.perform(post("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isCreated());

        // Validate the MissaUser in the database
        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeCreate + 1);
        MissaUser testMissaUser = missaUserList.get(missaUserList.size() - 1);
        assertThat(testMissaUser.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testMissaUser.getInitiales()).isEqualTo(DEFAULT_INITIALES);
        assertThat(testMissaUser.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMissaUser.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMissaUser.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testMissaUser.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the MissaUser in Elasticsearch
        verify(mockMissaUserSearchRepository, times(1)).save(testMissaUser);
    }

    @Test
    @Transactional
    public void createMissaUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = missaUserRepository.findAll().size();

        // Create the MissaUser with an existing ID
        missaUser.setId(1L);
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMissaUserMockMvc.perform(post("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MissaUser in the database
        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the MissaUser in Elasticsearch
        verify(mockMissaUserSearchRepository, times(0)).save(missaUser);
    }


    @Test
    @Transactional
    public void checkCodePostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = missaUserRepository.findAll().size();
        // set the field null
        missaUser.setCodePostal(null);

        // Create the MissaUser, which fails.
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);

        restMissaUserMockMvc.perform(post("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isBadRequest());

        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInitialesIsRequired() throws Exception {
        int databaseSizeBeforeTest = missaUserRepository.findAll().size();
        // set the field null
        missaUser.setInitiales(null);

        // Create the MissaUser, which fails.
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);

        restMissaUserMockMvc.perform(post("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isBadRequest());

        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = missaUserRepository.findAll().size();
        // set the field null
        missaUser.setNom(null);

        // Create the MissaUser, which fails.
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);

        restMissaUserMockMvc.perform(post("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isBadRequest());

        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = missaUserRepository.findAll().size();
        // set the field null
        missaUser.setPrenom(null);

        // Create the MissaUser, which fails.
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);

        restMissaUserMockMvc.perform(post("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isBadRequest());

        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = missaUserRepository.findAll().size();
        // set the field null
        missaUser.setMail(null);

        // Create the MissaUser, which fails.
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);

        restMissaUserMockMvc.perform(post("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isBadRequest());

        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMissaUsers() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList
        restMissaUserMockMvc.perform(get("/api/missa-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(missaUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].initiales").value(hasItem(DEFAULT_INITIALES)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
    
    @Test
    @Transactional
    public void getMissaUser() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get the missaUser
        restMissaUserMockMvc.perform(get("/api/missa-users/{id}", missaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(missaUser.getId().intValue()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.initiales").value(DEFAULT_INITIALES))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }


    @Test
    @Transactional
    public void getMissaUsersByIdFiltering() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        Long id = missaUser.getId();

        defaultMissaUserShouldBeFound("id.equals=" + id);
        defaultMissaUserShouldNotBeFound("id.notEquals=" + id);

        defaultMissaUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMissaUserShouldNotBeFound("id.greaterThan=" + id);

        defaultMissaUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMissaUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMissaUsersByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where codePostal equals to DEFAULT_CODE_POSTAL
        defaultMissaUserShouldBeFound("codePostal.equals=" + DEFAULT_CODE_POSTAL);

        // Get all the missaUserList where codePostal equals to UPDATED_CODE_POSTAL
        defaultMissaUserShouldNotBeFound("codePostal.equals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByCodePostalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where codePostal not equals to DEFAULT_CODE_POSTAL
        defaultMissaUserShouldNotBeFound("codePostal.notEquals=" + DEFAULT_CODE_POSTAL);

        // Get all the missaUserList where codePostal not equals to UPDATED_CODE_POSTAL
        defaultMissaUserShouldBeFound("codePostal.notEquals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByCodePostalIsInShouldWork() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where codePostal in DEFAULT_CODE_POSTAL or UPDATED_CODE_POSTAL
        defaultMissaUserShouldBeFound("codePostal.in=" + DEFAULT_CODE_POSTAL + "," + UPDATED_CODE_POSTAL);

        // Get all the missaUserList where codePostal equals to UPDATED_CODE_POSTAL
        defaultMissaUserShouldNotBeFound("codePostal.in=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByCodePostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where codePostal is not null
        defaultMissaUserShouldBeFound("codePostal.specified=true");

        // Get all the missaUserList where codePostal is null
        defaultMissaUserShouldNotBeFound("codePostal.specified=false");
    }
                @Test
    @Transactional
    public void getAllMissaUsersByCodePostalContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where codePostal contains DEFAULT_CODE_POSTAL
        defaultMissaUserShouldBeFound("codePostal.contains=" + DEFAULT_CODE_POSTAL);

        // Get all the missaUserList where codePostal contains UPDATED_CODE_POSTAL
        defaultMissaUserShouldNotBeFound("codePostal.contains=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByCodePostalNotContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where codePostal does not contain DEFAULT_CODE_POSTAL
        defaultMissaUserShouldNotBeFound("codePostal.doesNotContain=" + DEFAULT_CODE_POSTAL);

        // Get all the missaUserList where codePostal does not contain UPDATED_CODE_POSTAL
        defaultMissaUserShouldBeFound("codePostal.doesNotContain=" + UPDATED_CODE_POSTAL);
    }


    @Test
    @Transactional
    public void getAllMissaUsersByInitialesIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where initiales equals to DEFAULT_INITIALES
        defaultMissaUserShouldBeFound("initiales.equals=" + DEFAULT_INITIALES);

        // Get all the missaUserList where initiales equals to UPDATED_INITIALES
        defaultMissaUserShouldNotBeFound("initiales.equals=" + UPDATED_INITIALES);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByInitialesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where initiales not equals to DEFAULT_INITIALES
        defaultMissaUserShouldNotBeFound("initiales.notEquals=" + DEFAULT_INITIALES);

        // Get all the missaUserList where initiales not equals to UPDATED_INITIALES
        defaultMissaUserShouldBeFound("initiales.notEquals=" + UPDATED_INITIALES);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByInitialesIsInShouldWork() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where initiales in DEFAULT_INITIALES or UPDATED_INITIALES
        defaultMissaUserShouldBeFound("initiales.in=" + DEFAULT_INITIALES + "," + UPDATED_INITIALES);

        // Get all the missaUserList where initiales equals to UPDATED_INITIALES
        defaultMissaUserShouldNotBeFound("initiales.in=" + UPDATED_INITIALES);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByInitialesIsNullOrNotNull() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where initiales is not null
        defaultMissaUserShouldBeFound("initiales.specified=true");

        // Get all the missaUserList where initiales is null
        defaultMissaUserShouldNotBeFound("initiales.specified=false");
    }
                @Test
    @Transactional
    public void getAllMissaUsersByInitialesContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where initiales contains DEFAULT_INITIALES
        defaultMissaUserShouldBeFound("initiales.contains=" + DEFAULT_INITIALES);

        // Get all the missaUserList where initiales contains UPDATED_INITIALES
        defaultMissaUserShouldNotBeFound("initiales.contains=" + UPDATED_INITIALES);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByInitialesNotContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where initiales does not contain DEFAULT_INITIALES
        defaultMissaUserShouldNotBeFound("initiales.doesNotContain=" + DEFAULT_INITIALES);

        // Get all the missaUserList where initiales does not contain UPDATED_INITIALES
        defaultMissaUserShouldBeFound("initiales.doesNotContain=" + UPDATED_INITIALES);
    }


    @Test
    @Transactional
    public void getAllMissaUsersByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where nom equals to DEFAULT_NOM
        defaultMissaUserShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the missaUserList where nom equals to UPDATED_NOM
        defaultMissaUserShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where nom not equals to DEFAULT_NOM
        defaultMissaUserShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the missaUserList where nom not equals to UPDATED_NOM
        defaultMissaUserShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByNomIsInShouldWork() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultMissaUserShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the missaUserList where nom equals to UPDATED_NOM
        defaultMissaUserShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where nom is not null
        defaultMissaUserShouldBeFound("nom.specified=true");

        // Get all the missaUserList where nom is null
        defaultMissaUserShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllMissaUsersByNomContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where nom contains DEFAULT_NOM
        defaultMissaUserShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the missaUserList where nom contains UPDATED_NOM
        defaultMissaUserShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByNomNotContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where nom does not contain DEFAULT_NOM
        defaultMissaUserShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the missaUserList where nom does not contain UPDATED_NOM
        defaultMissaUserShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllMissaUsersByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where prenom equals to DEFAULT_PRENOM
        defaultMissaUserShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the missaUserList where prenom equals to UPDATED_PRENOM
        defaultMissaUserShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where prenom not equals to DEFAULT_PRENOM
        defaultMissaUserShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the missaUserList where prenom not equals to UPDATED_PRENOM
        defaultMissaUserShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultMissaUserShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the missaUserList where prenom equals to UPDATED_PRENOM
        defaultMissaUserShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where prenom is not null
        defaultMissaUserShouldBeFound("prenom.specified=true");

        // Get all the missaUserList where prenom is null
        defaultMissaUserShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllMissaUsersByPrenomContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where prenom contains DEFAULT_PRENOM
        defaultMissaUserShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the missaUserList where prenom contains UPDATED_PRENOM
        defaultMissaUserShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where prenom does not contain DEFAULT_PRENOM
        defaultMissaUserShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the missaUserList where prenom does not contain UPDATED_PRENOM
        defaultMissaUserShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllMissaUsersByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where mail equals to DEFAULT_MAIL
        defaultMissaUserShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the missaUserList where mail equals to UPDATED_MAIL
        defaultMissaUserShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where mail not equals to DEFAULT_MAIL
        defaultMissaUserShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the missaUserList where mail not equals to UPDATED_MAIL
        defaultMissaUserShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByMailIsInShouldWork() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultMissaUserShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the missaUserList where mail equals to UPDATED_MAIL
        defaultMissaUserShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where mail is not null
        defaultMissaUserShouldBeFound("mail.specified=true");

        // Get all the missaUserList where mail is null
        defaultMissaUserShouldNotBeFound("mail.specified=false");
    }
                @Test
    @Transactional
    public void getAllMissaUsersByMailContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where mail contains DEFAULT_MAIL
        defaultMissaUserShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the missaUserList where mail contains UPDATED_MAIL
        defaultMissaUserShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByMailNotContainsSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where mail does not contain DEFAULT_MAIL
        defaultMissaUserShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the missaUserList where mail does not contain UPDATED_MAIL
        defaultMissaUserShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }


    @Test
    @Transactional
    public void getAllMissaUsersByEtatIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where etat equals to DEFAULT_ETAT
        defaultMissaUserShouldBeFound("etat.equals=" + DEFAULT_ETAT);

        // Get all the missaUserList where etat equals to UPDATED_ETAT
        defaultMissaUserShouldNotBeFound("etat.equals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByEtatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where etat not equals to DEFAULT_ETAT
        defaultMissaUserShouldNotBeFound("etat.notEquals=" + DEFAULT_ETAT);

        // Get all the missaUserList where etat not equals to UPDATED_ETAT
        defaultMissaUserShouldBeFound("etat.notEquals=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByEtatIsInShouldWork() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where etat in DEFAULT_ETAT or UPDATED_ETAT
        defaultMissaUserShouldBeFound("etat.in=" + DEFAULT_ETAT + "," + UPDATED_ETAT);

        // Get all the missaUserList where etat equals to UPDATED_ETAT
        defaultMissaUserShouldNotBeFound("etat.in=" + UPDATED_ETAT);
    }

    @Test
    @Transactional
    public void getAllMissaUsersByEtatIsNullOrNotNull() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        // Get all the missaUserList where etat is not null
        defaultMissaUserShouldBeFound("etat.specified=true");

        // Get all the missaUserList where etat is null
        defaultMissaUserShouldNotBeFound("etat.specified=false");
    }

    @Test
    @Transactional
    public void getAllMissaUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        missaUser.setUser(user);
        missaUserRepository.saveAndFlush(missaUser);
        String userId = user.getId();

        // Get all the missaUserList where user equals to userId
        defaultMissaUserShouldBeFound("userId.equals=" + userId);

        // Get all the missaUserList where user equals to userId + 1
        defaultMissaUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllMissaUsersByConnaisIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        Connaissance connais = ConnaissanceResourceIT.createEntity(em);
        em.persist(connais);
        em.flush();
        missaUser.addConnais(connais);
        missaUserRepository.saveAndFlush(missaUser);
        Long connaisId = connais.getId();

        // Get all the missaUserList where connais equals to connaisId
        defaultMissaUserShouldBeFound("connaisId.equals=" + connaisId);

        // Get all the missaUserList where connais equals to connaisId + 1
        defaultMissaUserShouldNotBeFound("connaisId.equals=" + (connaisId + 1));
    }


    @Test
    @Transactional
    public void getAllMissaUsersByMembreValidesIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        MembreMetaGroupe membreValides = MembreMetaGroupeResourceIT.createEntity(em);
        em.persist(membreValides);
        em.flush();
        missaUser.addMembreValides(membreValides);
        missaUserRepository.saveAndFlush(missaUser);
        Long membreValidesId = membreValides.getId();

        // Get all the missaUserList where membreValides equals to membreValidesId
        defaultMissaUserShouldBeFound("membreValidesId.equals=" + membreValidesId);

        // Get all the missaUserList where membreValides equals to membreValidesId + 1
        defaultMissaUserShouldNotBeFound("membreValidesId.equals=" + (membreValidesId + 1));
    }


    @Test
    @Transactional
    public void getAllMissaUsersByMembresIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        MembreMetaGroupe membres = MembreMetaGroupeResourceIT.createEntity(em);
        em.persist(membres);
        em.flush();
        missaUser.addMembres(membres);
        missaUserRepository.saveAndFlush(missaUser);
        Long membresId = membres.getId();

        // Get all the missaUserList where membres equals to membresId
        defaultMissaUserShouldBeFound("membresId.equals=" + membresId);

        // Get all the missaUserList where membres equals to membresId + 1
        defaultMissaUserShouldNotBeFound("membresId.equals=" + (membresId + 1));
    }


    @Test
    @Transactional
    public void getAllMissaUsersByMessagesDeMoiIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        MessageMetaGroupe messagesDeMoi = MessageMetaGroupeResourceIT.createEntity(em);
        em.persist(messagesDeMoi);
        em.flush();
        missaUser.addMessagesDeMoi(messagesDeMoi);
        missaUserRepository.saveAndFlush(missaUser);
        Long messagesDeMoiId = messagesDeMoi.getId();

        // Get all the missaUserList where messagesDeMoi equals to messagesDeMoiId
        defaultMissaUserShouldBeFound("messagesDeMoiId.equals=" + messagesDeMoiId);

        // Get all the missaUserList where messagesDeMoi equals to messagesDeMoiId + 1
        defaultMissaUserShouldNotBeFound("messagesDeMoiId.equals=" + (messagesDeMoiId + 1));
    }


    @Test
    @Transactional
    public void getAllMissaUsersByMailsIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        EnvoiDeMail mails = EnvoiDeMailResourceIT.createEntity(em);
        em.persist(mails);
        em.flush();
        missaUser.addMails(mails);
        missaUserRepository.saveAndFlush(missaUser);
        Long mailsId = mails.getId();

        // Get all the missaUserList where mails equals to mailsId
        defaultMissaUserShouldBeFound("mailsId.equals=" + mailsId);

        // Get all the missaUserList where mails equals to mailsId + 1
        defaultMissaUserShouldNotBeFound("mailsId.equals=" + (mailsId + 1));
    }


    @Test
    @Transactional
    public void getAllMissaUsersByDemandesPartagesIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        PartageMetaGroupe demandesPartages = PartageMetaGroupeResourceIT.createEntity(em);
        em.persist(demandesPartages);
        em.flush();
        missaUser.addDemandesPartages(demandesPartages);
        missaUserRepository.saveAndFlush(missaUser);
        Long demandesPartagesId = demandesPartages.getId();

        // Get all the missaUserList where demandesPartages equals to demandesPartagesId
        defaultMissaUserShouldBeFound("demandesPartagesId.equals=" + demandesPartagesId);

        // Get all the missaUserList where demandesPartages equals to demandesPartagesId + 1
        defaultMissaUserShouldNotBeFound("demandesPartagesId.equals=" + (demandesPartagesId + 1));
    }


    @Test
    @Transactional
    public void getAllMissaUsersByAValidePartagesIsEqualToSomething() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        PartageMetaGroupe aValidePartages = PartageMetaGroupeResourceIT.createEntity(em);
        em.persist(aValidePartages);
        em.flush();
        missaUser.addAValidePartages(aValidePartages);
        missaUserRepository.saveAndFlush(missaUser);
        Long aValidePartagesId = aValidePartages.getId();

        // Get all the missaUserList where aValidePartages equals to aValidePartagesId
        defaultMissaUserShouldBeFound("aValidePartagesId.equals=" + aValidePartagesId);

        // Get all the missaUserList where aValidePartages equals to aValidePartagesId + 1
        defaultMissaUserShouldNotBeFound("aValidePartagesId.equals=" + (aValidePartagesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMissaUserShouldBeFound(String filter) throws Exception {
        restMissaUserMockMvc.perform(get("/api/missa-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(missaUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].initiales").value(hasItem(DEFAULT_INITIALES)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));

        // Check, that the count call also returns 1
        restMissaUserMockMvc.perform(get("/api/missa-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMissaUserShouldNotBeFound(String filter) throws Exception {
        restMissaUserMockMvc.perform(get("/api/missa-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMissaUserMockMvc.perform(get("/api/missa-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMissaUser() throws Exception {
        // Get the missaUser
        restMissaUserMockMvc.perform(get("/api/missa-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMissaUser() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        int databaseSizeBeforeUpdate = missaUserRepository.findAll().size();

        // Update the missaUser
        MissaUser updatedMissaUser = missaUserRepository.findById(missaUser.getId()).get();
        // Disconnect from session so that the updates on updatedMissaUser are not directly saved in db
        em.detach(updatedMissaUser);
        updatedMissaUser
            .codePostal(UPDATED_CODE_POSTAL)
            .initiales(UPDATED_INITIALES)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .mail(UPDATED_MAIL)
            .etat(UPDATED_ETAT);
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(updatedMissaUser);

        restMissaUserMockMvc.perform(put("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isOk());

        // Validate the MissaUser in the database
        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeUpdate);
        MissaUser testMissaUser = missaUserList.get(missaUserList.size() - 1);
        assertThat(testMissaUser.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testMissaUser.getInitiales()).isEqualTo(UPDATED_INITIALES);
        assertThat(testMissaUser.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMissaUser.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMissaUser.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testMissaUser.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the MissaUser in Elasticsearch
        verify(mockMissaUserSearchRepository, times(1)).save(testMissaUser);
    }

    @Test
    @Transactional
    public void updateNonExistingMissaUser() throws Exception {
        int databaseSizeBeforeUpdate = missaUserRepository.findAll().size();

        // Create the MissaUser
        MissaUserDTO missaUserDTO = missaUserMapper.toDto(missaUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMissaUserMockMvc.perform(put("/api/missa-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missaUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MissaUser in the database
        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MissaUser in Elasticsearch
        verify(mockMissaUserSearchRepository, times(0)).save(missaUser);
    }

    @Test
    @Transactional
    public void deleteMissaUser() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);

        int databaseSizeBeforeDelete = missaUserRepository.findAll().size();

        // Delete the missaUser
        restMissaUserMockMvc.perform(delete("/api/missa-users/{id}", missaUser.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MissaUser> missaUserList = missaUserRepository.findAll();
        assertThat(missaUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MissaUser in Elasticsearch
        verify(mockMissaUserSearchRepository, times(1)).deleteById(missaUser.getId());
    }

    @Test
    @Transactional
    public void searchMissaUser() throws Exception {
        // Initialize the database
        missaUserRepository.saveAndFlush(missaUser);
        when(mockMissaUserSearchRepository.search(queryStringQuery("id:" + missaUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(missaUser), PageRequest.of(0, 1), 1));
        // Search the missaUser
        restMissaUserMockMvc.perform(get("/api/_search/missa-users?query=id:" + missaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(missaUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].initiales").value(hasItem(DEFAULT_INITIALES)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }
}
