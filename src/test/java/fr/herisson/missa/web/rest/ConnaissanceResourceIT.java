package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.Connaissance;
import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.repository.ConnaissanceRepository;
import fr.herisson.missa.repository.search.ConnaissanceSearchRepository;
import fr.herisson.missa.service.ConnaissanceService;
import fr.herisson.missa.service.dto.ConnaissanceDTO;
import fr.herisson.missa.service.mapper.ConnaissanceMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.ConnaissanceCriteria;
import fr.herisson.missa.service.ConnaissanceQueryService;

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
 * Integration tests for the {@link ConnaissanceResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class ConnaissanceResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ID_FACE_BOOK = "AAAAAAAAAA";
    private static final String UPDATED_ID_FACE_BOOK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARRAINE = false;
    private static final Boolean UPDATED_PARRAINE = true;

    @Autowired
    private ConnaissanceRepository connaissanceRepository;

    @Autowired
    private ConnaissanceMapper connaissanceMapper;

    @Autowired
    private ConnaissanceService connaissanceService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.ConnaissanceSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConnaissanceSearchRepository mockConnaissanceSearchRepository;

    @Autowired
    private ConnaissanceQueryService connaissanceQueryService;

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

    private MockMvc restConnaissanceMockMvc;

    private Connaissance connaissance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConnaissanceResource connaissanceResource = new ConnaissanceResource(connaissanceService, connaissanceQueryService);
        this.restConnaissanceMockMvc = MockMvcBuilders.standaloneSetup(connaissanceResource)
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
    public static Connaissance createEntity(EntityManager em) {
        Connaissance connaissance = new Connaissance()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .mail(DEFAULT_MAIL)
            .idFaceBook(DEFAULT_ID_FACE_BOOK)
            .parraine(DEFAULT_PARRAINE);
        return connaissance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connaissance createUpdatedEntity(EntityManager em) {
        Connaissance connaissance = new Connaissance()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .mail(UPDATED_MAIL)
            .idFaceBook(UPDATED_ID_FACE_BOOK)
            .parraine(UPDATED_PARRAINE);
        return connaissance;
    }

    @BeforeEach
    public void initTest() {
        connaissance = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnaissance() throws Exception {
        int databaseSizeBeforeCreate = connaissanceRepository.findAll().size();

        // Create the Connaissance
        ConnaissanceDTO connaissanceDTO = connaissanceMapper.toDto(connaissance);
        restConnaissanceMockMvc.perform(post("/api/connaissances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connaissanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Connaissance in the database
        List<Connaissance> connaissanceList = connaissanceRepository.findAll();
        assertThat(connaissanceList).hasSize(databaseSizeBeforeCreate + 1);
        Connaissance testConnaissance = connaissanceList.get(connaissanceList.size() - 1);
        assertThat(testConnaissance.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testConnaissance.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testConnaissance.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testConnaissance.getIdFaceBook()).isEqualTo(DEFAULT_ID_FACE_BOOK);
        assertThat(testConnaissance.isParraine()).isEqualTo(DEFAULT_PARRAINE);

        // Validate the Connaissance in Elasticsearch
        verify(mockConnaissanceSearchRepository, times(1)).save(testConnaissance);
    }

    @Test
    @Transactional
    public void createConnaissanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connaissanceRepository.findAll().size();

        // Create the Connaissance with an existing ID
        connaissance.setId(1L);
        ConnaissanceDTO connaissanceDTO = connaissanceMapper.toDto(connaissance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnaissanceMockMvc.perform(post("/api/connaissances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connaissanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Connaissance in the database
        List<Connaissance> connaissanceList = connaissanceRepository.findAll();
        assertThat(connaissanceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Connaissance in Elasticsearch
        verify(mockConnaissanceSearchRepository, times(0)).save(connaissance);
    }


    @Test
    @Transactional
    public void getAllConnaissances() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList
        restConnaissanceMockMvc.perform(get("/api/connaissances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].idFaceBook").value(hasItem(DEFAULT_ID_FACE_BOOK)))
            .andExpect(jsonPath("$.[*].parraine").value(hasItem(DEFAULT_PARRAINE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConnaissance() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get the connaissance
        restConnaissanceMockMvc.perform(get("/api/connaissances/{id}", connaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connaissance.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.idFaceBook").value(DEFAULT_ID_FACE_BOOK))
            .andExpect(jsonPath("$.parraine").value(DEFAULT_PARRAINE.booleanValue()));
    }


    @Test
    @Transactional
    public void getConnaissancesByIdFiltering() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        Long id = connaissance.getId();

        defaultConnaissanceShouldBeFound("id.equals=" + id);
        defaultConnaissanceShouldNotBeFound("id.notEquals=" + id);

        defaultConnaissanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConnaissanceShouldNotBeFound("id.greaterThan=" + id);

        defaultConnaissanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConnaissanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConnaissancesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where nom equals to DEFAULT_NOM
        defaultConnaissanceShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the connaissanceList where nom equals to UPDATED_NOM
        defaultConnaissanceShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where nom not equals to DEFAULT_NOM
        defaultConnaissanceShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the connaissanceList where nom not equals to UPDATED_NOM
        defaultConnaissanceShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultConnaissanceShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the connaissanceList where nom equals to UPDATED_NOM
        defaultConnaissanceShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where nom is not null
        defaultConnaissanceShouldBeFound("nom.specified=true");

        // Get all the connaissanceList where nom is null
        defaultConnaissanceShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllConnaissancesByNomContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where nom contains DEFAULT_NOM
        defaultConnaissanceShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the connaissanceList where nom contains UPDATED_NOM
        defaultConnaissanceShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where nom does not contain DEFAULT_NOM
        defaultConnaissanceShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the connaissanceList where nom does not contain UPDATED_NOM
        defaultConnaissanceShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllConnaissancesByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where prenom equals to DEFAULT_PRENOM
        defaultConnaissanceShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the connaissanceList where prenom equals to UPDATED_PRENOM
        defaultConnaissanceShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where prenom not equals to DEFAULT_PRENOM
        defaultConnaissanceShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the connaissanceList where prenom not equals to UPDATED_PRENOM
        defaultConnaissanceShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultConnaissanceShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the connaissanceList where prenom equals to UPDATED_PRENOM
        defaultConnaissanceShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where prenom is not null
        defaultConnaissanceShouldBeFound("prenom.specified=true");

        // Get all the connaissanceList where prenom is null
        defaultConnaissanceShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllConnaissancesByPrenomContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where prenom contains DEFAULT_PRENOM
        defaultConnaissanceShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the connaissanceList where prenom contains UPDATED_PRENOM
        defaultConnaissanceShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where prenom does not contain DEFAULT_PRENOM
        defaultConnaissanceShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the connaissanceList where prenom does not contain UPDATED_PRENOM
        defaultConnaissanceShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllConnaissancesByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where mail equals to DEFAULT_MAIL
        defaultConnaissanceShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the connaissanceList where mail equals to UPDATED_MAIL
        defaultConnaissanceShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where mail not equals to DEFAULT_MAIL
        defaultConnaissanceShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the connaissanceList where mail not equals to UPDATED_MAIL
        defaultConnaissanceShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByMailIsInShouldWork() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultConnaissanceShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the connaissanceList where mail equals to UPDATED_MAIL
        defaultConnaissanceShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where mail is not null
        defaultConnaissanceShouldBeFound("mail.specified=true");

        // Get all the connaissanceList where mail is null
        defaultConnaissanceShouldNotBeFound("mail.specified=false");
    }
                @Test
    @Transactional
    public void getAllConnaissancesByMailContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where mail contains DEFAULT_MAIL
        defaultConnaissanceShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the connaissanceList where mail contains UPDATED_MAIL
        defaultConnaissanceShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByMailNotContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where mail does not contain DEFAULT_MAIL
        defaultConnaissanceShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the connaissanceList where mail does not contain UPDATED_MAIL
        defaultConnaissanceShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }


    @Test
    @Transactional
    public void getAllConnaissancesByIdFaceBookIsEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where idFaceBook equals to DEFAULT_ID_FACE_BOOK
        defaultConnaissanceShouldBeFound("idFaceBook.equals=" + DEFAULT_ID_FACE_BOOK);

        // Get all the connaissanceList where idFaceBook equals to UPDATED_ID_FACE_BOOK
        defaultConnaissanceShouldNotBeFound("idFaceBook.equals=" + UPDATED_ID_FACE_BOOK);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByIdFaceBookIsNotEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where idFaceBook not equals to DEFAULT_ID_FACE_BOOK
        defaultConnaissanceShouldNotBeFound("idFaceBook.notEquals=" + DEFAULT_ID_FACE_BOOK);

        // Get all the connaissanceList where idFaceBook not equals to UPDATED_ID_FACE_BOOK
        defaultConnaissanceShouldBeFound("idFaceBook.notEquals=" + UPDATED_ID_FACE_BOOK);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByIdFaceBookIsInShouldWork() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where idFaceBook in DEFAULT_ID_FACE_BOOK or UPDATED_ID_FACE_BOOK
        defaultConnaissanceShouldBeFound("idFaceBook.in=" + DEFAULT_ID_FACE_BOOK + "," + UPDATED_ID_FACE_BOOK);

        // Get all the connaissanceList where idFaceBook equals to UPDATED_ID_FACE_BOOK
        defaultConnaissanceShouldNotBeFound("idFaceBook.in=" + UPDATED_ID_FACE_BOOK);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByIdFaceBookIsNullOrNotNull() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where idFaceBook is not null
        defaultConnaissanceShouldBeFound("idFaceBook.specified=true");

        // Get all the connaissanceList where idFaceBook is null
        defaultConnaissanceShouldNotBeFound("idFaceBook.specified=false");
    }
                @Test
    @Transactional
    public void getAllConnaissancesByIdFaceBookContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where idFaceBook contains DEFAULT_ID_FACE_BOOK
        defaultConnaissanceShouldBeFound("idFaceBook.contains=" + DEFAULT_ID_FACE_BOOK);

        // Get all the connaissanceList where idFaceBook contains UPDATED_ID_FACE_BOOK
        defaultConnaissanceShouldNotBeFound("idFaceBook.contains=" + UPDATED_ID_FACE_BOOK);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByIdFaceBookNotContainsSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where idFaceBook does not contain DEFAULT_ID_FACE_BOOK
        defaultConnaissanceShouldNotBeFound("idFaceBook.doesNotContain=" + DEFAULT_ID_FACE_BOOK);

        // Get all the connaissanceList where idFaceBook does not contain UPDATED_ID_FACE_BOOK
        defaultConnaissanceShouldBeFound("idFaceBook.doesNotContain=" + UPDATED_ID_FACE_BOOK);
    }


    @Test
    @Transactional
    public void getAllConnaissancesByParraineIsEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where parraine equals to DEFAULT_PARRAINE
        defaultConnaissanceShouldBeFound("parraine.equals=" + DEFAULT_PARRAINE);

        // Get all the connaissanceList where parraine equals to UPDATED_PARRAINE
        defaultConnaissanceShouldNotBeFound("parraine.equals=" + UPDATED_PARRAINE);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByParraineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where parraine not equals to DEFAULT_PARRAINE
        defaultConnaissanceShouldNotBeFound("parraine.notEquals=" + DEFAULT_PARRAINE);

        // Get all the connaissanceList where parraine not equals to UPDATED_PARRAINE
        defaultConnaissanceShouldBeFound("parraine.notEquals=" + UPDATED_PARRAINE);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByParraineIsInShouldWork() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where parraine in DEFAULT_PARRAINE or UPDATED_PARRAINE
        defaultConnaissanceShouldBeFound("parraine.in=" + DEFAULT_PARRAINE + "," + UPDATED_PARRAINE);

        // Get all the connaissanceList where parraine equals to UPDATED_PARRAINE
        defaultConnaissanceShouldNotBeFound("parraine.in=" + UPDATED_PARRAINE);
    }

    @Test
    @Transactional
    public void getAllConnaissancesByParraineIsNullOrNotNull() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        // Get all the connaissanceList where parraine is not null
        defaultConnaissanceShouldBeFound("parraine.specified=true");

        // Get all the connaissanceList where parraine is null
        defaultConnaissanceShouldNotBeFound("parraine.specified=false");
    }

    @Test
    @Transactional
    public void getAllConnaissancesByConnuParIsEqualToSomething() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);
        MissaUser connuPar = MissaUserResourceIT.createEntity(em);
        em.persist(connuPar);
        em.flush();
        connaissance.setConnuPar(connuPar);
        connaissanceRepository.saveAndFlush(connaissance);
        Long connuParId = connuPar.getId();

        // Get all the connaissanceList where connuPar equals to connuParId
        defaultConnaissanceShouldBeFound("connuParId.equals=" + connuParId);

        // Get all the connaissanceList where connuPar equals to connuParId + 1
        defaultConnaissanceShouldNotBeFound("connuParId.equals=" + (connuParId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConnaissanceShouldBeFound(String filter) throws Exception {
        restConnaissanceMockMvc.perform(get("/api/connaissances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].idFaceBook").value(hasItem(DEFAULT_ID_FACE_BOOK)))
            .andExpect(jsonPath("$.[*].parraine").value(hasItem(DEFAULT_PARRAINE.booleanValue())));

        // Check, that the count call also returns 1
        restConnaissanceMockMvc.perform(get("/api/connaissances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConnaissanceShouldNotBeFound(String filter) throws Exception {
        restConnaissanceMockMvc.perform(get("/api/connaissances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConnaissanceMockMvc.perform(get("/api/connaissances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConnaissance() throws Exception {
        // Get the connaissance
        restConnaissanceMockMvc.perform(get("/api/connaissances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnaissance() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        int databaseSizeBeforeUpdate = connaissanceRepository.findAll().size();

        // Update the connaissance
        Connaissance updatedConnaissance = connaissanceRepository.findById(connaissance.getId()).get();
        // Disconnect from session so that the updates on updatedConnaissance are not directly saved in db
        em.detach(updatedConnaissance);
        updatedConnaissance
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .mail(UPDATED_MAIL)
            .idFaceBook(UPDATED_ID_FACE_BOOK)
            .parraine(UPDATED_PARRAINE);
        ConnaissanceDTO connaissanceDTO = connaissanceMapper.toDto(updatedConnaissance);

        restConnaissanceMockMvc.perform(put("/api/connaissances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connaissanceDTO)))
            .andExpect(status().isOk());

        // Validate the Connaissance in the database
        List<Connaissance> connaissanceList = connaissanceRepository.findAll();
        assertThat(connaissanceList).hasSize(databaseSizeBeforeUpdate);
        Connaissance testConnaissance = connaissanceList.get(connaissanceList.size() - 1);
        assertThat(testConnaissance.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testConnaissance.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testConnaissance.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testConnaissance.getIdFaceBook()).isEqualTo(UPDATED_ID_FACE_BOOK);
        assertThat(testConnaissance.isParraine()).isEqualTo(UPDATED_PARRAINE);

        // Validate the Connaissance in Elasticsearch
        verify(mockConnaissanceSearchRepository, times(1)).save(testConnaissance);
    }

    @Test
    @Transactional
    public void updateNonExistingConnaissance() throws Exception {
        int databaseSizeBeforeUpdate = connaissanceRepository.findAll().size();

        // Create the Connaissance
        ConnaissanceDTO connaissanceDTO = connaissanceMapper.toDto(connaissance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnaissanceMockMvc.perform(put("/api/connaissances")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connaissanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Connaissance in the database
        List<Connaissance> connaissanceList = connaissanceRepository.findAll();
        assertThat(connaissanceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Connaissance in Elasticsearch
        verify(mockConnaissanceSearchRepository, times(0)).save(connaissance);
    }

    @Test
    @Transactional
    public void deleteConnaissance() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);

        int databaseSizeBeforeDelete = connaissanceRepository.findAll().size();

        // Delete the connaissance
        restConnaissanceMockMvc.perform(delete("/api/connaissances/{id}", connaissance.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Connaissance> connaissanceList = connaissanceRepository.findAll();
        assertThat(connaissanceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Connaissance in Elasticsearch
        verify(mockConnaissanceSearchRepository, times(1)).deleteById(connaissance.getId());
    }

    @Test
    @Transactional
    public void searchConnaissance() throws Exception {
        // Initialize the database
        connaissanceRepository.saveAndFlush(connaissance);
        when(mockConnaissanceSearchRepository.search(queryStringQuery("id:" + connaissance.getId())))
            .thenReturn(Collections.singletonList(connaissance));
        // Search the connaissance
        restConnaissanceMockMvc.perform(get("/api/_search/connaissances?query=id:" + connaissance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connaissance.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].idFaceBook").value(hasItem(DEFAULT_ID_FACE_BOOK)))
            .andExpect(jsonPath("$.[*].parraine").value(hasItem(DEFAULT_PARRAINE.booleanValue())));
    }
}
