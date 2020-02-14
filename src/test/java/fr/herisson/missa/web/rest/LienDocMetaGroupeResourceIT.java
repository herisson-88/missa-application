package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.LienDocMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.TypeMetaGroupe;
import fr.herisson.missa.repository.LienDocMetaGroupeRepository;
import fr.herisson.missa.repository.search.LienDocMetaGroupeSearchRepository;
import fr.herisson.missa.service.LienDocMetaGroupeService;
import fr.herisson.missa.service.dto.LienDocMetaGroupeDTO;
import fr.herisson.missa.service.mapper.LienDocMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.LienDocMetaGroupeCriteria;
import fr.herisson.missa.service.LienDocMetaGroupeQueryService;

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

import fr.herisson.missa.domain.enumeration.TypeDoc;
/**
 * Integration tests for the {@link LienDocMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class LienDocMetaGroupeResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final TypeDoc DEFAULT_TYPE_DU_DOC = TypeDoc.DOC;
    private static final TypeDoc UPDATED_TYPE_DU_DOC = TypeDoc.IMAGE;

    private static final byte[] DEFAULT_ICONE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICONE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ICONE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICONE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOC_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private LienDocMetaGroupeRepository lienDocMetaGroupeRepository;

    @Autowired
    private LienDocMetaGroupeMapper lienDocMetaGroupeMapper;

    @Autowired
    private LienDocMetaGroupeService lienDocMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.LienDocMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private LienDocMetaGroupeSearchRepository mockLienDocMetaGroupeSearchRepository;

    @Autowired
    private LienDocMetaGroupeQueryService lienDocMetaGroupeQueryService;

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

    private MockMvc restLienDocMetaGroupeMockMvc;

    private LienDocMetaGroupe lienDocMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LienDocMetaGroupeResource lienDocMetaGroupeResource = new LienDocMetaGroupeResource(lienDocMetaGroupeService, lienDocMetaGroupeQueryService);
        this.restLienDocMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(lienDocMetaGroupeResource)
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
    public static LienDocMetaGroupe createEntity(EntityManager em) {
        LienDocMetaGroupe lienDocMetaGroupe = new LienDocMetaGroupe()
            .nom(DEFAULT_NOM)
            .typeDuDoc(DEFAULT_TYPE_DU_DOC)
            .icone(DEFAULT_ICONE)
            .iconeContentType(DEFAULT_ICONE_CONTENT_TYPE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .doc(DEFAULT_DOC)
            .docContentType(DEFAULT_DOC_CONTENT_TYPE)
            .detail(DEFAULT_DETAIL);
        return lienDocMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LienDocMetaGroupe createUpdatedEntity(EntityManager em) {
        LienDocMetaGroupe lienDocMetaGroupe = new LienDocMetaGroupe()
            .nom(UPDATED_NOM)
            .typeDuDoc(UPDATED_TYPE_DU_DOC)
            .icone(UPDATED_ICONE)
            .iconeContentType(UPDATED_ICONE_CONTENT_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .detail(UPDATED_DETAIL);
        return lienDocMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        lienDocMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createLienDocMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = lienDocMetaGroupeRepository.findAll().size();

        // Create the LienDocMetaGroupe
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO = lienDocMetaGroupeMapper.toDto(lienDocMetaGroupe);
        restLienDocMetaGroupeMockMvc.perform(post("/api/lien-doc-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lienDocMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the LienDocMetaGroupe in the database
        List<LienDocMetaGroupe> lienDocMetaGroupeList = lienDocMetaGroupeRepository.findAll();
        assertThat(lienDocMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        LienDocMetaGroupe testLienDocMetaGroupe = lienDocMetaGroupeList.get(lienDocMetaGroupeList.size() - 1);
        assertThat(testLienDocMetaGroupe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testLienDocMetaGroupe.getTypeDuDoc()).isEqualTo(DEFAULT_TYPE_DU_DOC);
        assertThat(testLienDocMetaGroupe.getIcone()).isEqualTo(DEFAULT_ICONE);
        assertThat(testLienDocMetaGroupe.getIconeContentType()).isEqualTo(DEFAULT_ICONE_CONTENT_TYPE);
        assertThat(testLienDocMetaGroupe.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testLienDocMetaGroupe.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testLienDocMetaGroupe.getDoc()).isEqualTo(DEFAULT_DOC);
        assertThat(testLienDocMetaGroupe.getDocContentType()).isEqualTo(DEFAULT_DOC_CONTENT_TYPE);
        assertThat(testLienDocMetaGroupe.getDetail()).isEqualTo(DEFAULT_DETAIL);

        // Validate the LienDocMetaGroupe in Elasticsearch
        verify(mockLienDocMetaGroupeSearchRepository, times(1)).save(testLienDocMetaGroupe);
    }

    @Test
    @Transactional
    public void createLienDocMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lienDocMetaGroupeRepository.findAll().size();

        // Create the LienDocMetaGroupe with an existing ID
        lienDocMetaGroupe.setId(1L);
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO = lienDocMetaGroupeMapper.toDto(lienDocMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLienDocMetaGroupeMockMvc.perform(post("/api/lien-doc-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lienDocMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LienDocMetaGroupe in the database
        List<LienDocMetaGroupe> lienDocMetaGroupeList = lienDocMetaGroupeRepository.findAll();
        assertThat(lienDocMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the LienDocMetaGroupe in Elasticsearch
        verify(mockLienDocMetaGroupeSearchRepository, times(0)).save(lienDocMetaGroupe);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = lienDocMetaGroupeRepository.findAll().size();
        // set the field null
        lienDocMetaGroupe.setNom(null);

        // Create the LienDocMetaGroupe, which fails.
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO = lienDocMetaGroupeMapper.toDto(lienDocMetaGroupe);

        restLienDocMetaGroupeMockMvc.perform(post("/api/lien-doc-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lienDocMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        List<LienDocMetaGroupe> lienDocMetaGroupeList = lienDocMetaGroupeRepository.findAll();
        assertThat(lienDocMetaGroupeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeDuDocIsRequired() throws Exception {
        int databaseSizeBeforeTest = lienDocMetaGroupeRepository.findAll().size();
        // set the field null
        lienDocMetaGroupe.setTypeDuDoc(null);

        // Create the LienDocMetaGroupe, which fails.
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO = lienDocMetaGroupeMapper.toDto(lienDocMetaGroupe);

        restLienDocMetaGroupeMockMvc.perform(post("/api/lien-doc-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lienDocMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        List<LienDocMetaGroupe> lienDocMetaGroupeList = lienDocMetaGroupeRepository.findAll();
        assertThat(lienDocMetaGroupeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupes() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList
        restLienDocMetaGroupeMockMvc.perform(get("/api/lien-doc-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lienDocMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].typeDuDoc").value(hasItem(DEFAULT_TYPE_DU_DOC.toString())))
            .andExpect(jsonPath("$.[*].iconeContentType").value(hasItem(DEFAULT_ICONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icone").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICONE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].docContentType").value(hasItem(DEFAULT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].doc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOC))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getLienDocMetaGroupe() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get the lienDocMetaGroupe
        restLienDocMetaGroupeMockMvc.perform(get("/api/lien-doc-meta-groupes/{id}", lienDocMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lienDocMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.typeDuDoc").value(DEFAULT_TYPE_DU_DOC.toString()))
            .andExpect(jsonPath("$.iconeContentType").value(DEFAULT_ICONE_CONTENT_TYPE))
            .andExpect(jsonPath("$.icone").value(Base64Utils.encodeToString(DEFAULT_ICONE)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.docContentType").value(DEFAULT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.doc").value(Base64Utils.encodeToString(DEFAULT_DOC)))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()));
    }


    @Test
    @Transactional
    public void getLienDocMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        Long id = lienDocMetaGroupe.getId();

        defaultLienDocMetaGroupeShouldBeFound("id.equals=" + id);
        defaultLienDocMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultLienDocMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLienDocMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultLienDocMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLienDocMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where nom equals to DEFAULT_NOM
        defaultLienDocMetaGroupeShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the lienDocMetaGroupeList where nom equals to UPDATED_NOM
        defaultLienDocMetaGroupeShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where nom not equals to DEFAULT_NOM
        defaultLienDocMetaGroupeShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the lienDocMetaGroupeList where nom not equals to UPDATED_NOM
        defaultLienDocMetaGroupeShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultLienDocMetaGroupeShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the lienDocMetaGroupeList where nom equals to UPDATED_NOM
        defaultLienDocMetaGroupeShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where nom is not null
        defaultLienDocMetaGroupeShouldBeFound("nom.specified=true");

        // Get all the lienDocMetaGroupeList where nom is null
        defaultLienDocMetaGroupeShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllLienDocMetaGroupesByNomContainsSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where nom contains DEFAULT_NOM
        defaultLienDocMetaGroupeShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the lienDocMetaGroupeList where nom contains UPDATED_NOM
        defaultLienDocMetaGroupeShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where nom does not contain DEFAULT_NOM
        defaultLienDocMetaGroupeShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the lienDocMetaGroupeList where nom does not contain UPDATED_NOM
        defaultLienDocMetaGroupeShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByTypeDuDocIsEqualToSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where typeDuDoc equals to DEFAULT_TYPE_DU_DOC
        defaultLienDocMetaGroupeShouldBeFound("typeDuDoc.equals=" + DEFAULT_TYPE_DU_DOC);

        // Get all the lienDocMetaGroupeList where typeDuDoc equals to UPDATED_TYPE_DU_DOC
        defaultLienDocMetaGroupeShouldNotBeFound("typeDuDoc.equals=" + UPDATED_TYPE_DU_DOC);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByTypeDuDocIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where typeDuDoc not equals to DEFAULT_TYPE_DU_DOC
        defaultLienDocMetaGroupeShouldNotBeFound("typeDuDoc.notEquals=" + DEFAULT_TYPE_DU_DOC);

        // Get all the lienDocMetaGroupeList where typeDuDoc not equals to UPDATED_TYPE_DU_DOC
        defaultLienDocMetaGroupeShouldBeFound("typeDuDoc.notEquals=" + UPDATED_TYPE_DU_DOC);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByTypeDuDocIsInShouldWork() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where typeDuDoc in DEFAULT_TYPE_DU_DOC or UPDATED_TYPE_DU_DOC
        defaultLienDocMetaGroupeShouldBeFound("typeDuDoc.in=" + DEFAULT_TYPE_DU_DOC + "," + UPDATED_TYPE_DU_DOC);

        // Get all the lienDocMetaGroupeList where typeDuDoc equals to UPDATED_TYPE_DU_DOC
        defaultLienDocMetaGroupeShouldNotBeFound("typeDuDoc.in=" + UPDATED_TYPE_DU_DOC);
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByTypeDuDocIsNullOrNotNull() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        // Get all the lienDocMetaGroupeList where typeDuDoc is not null
        defaultLienDocMetaGroupeShouldBeFound("typeDuDoc.specified=true");

        // Get all the lienDocMetaGroupeList where typeDuDoc is null
        defaultLienDocMetaGroupeShouldNotBeFound("typeDuDoc.specified=false");
    }

    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);
        MetaGroupe groupe = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupe);
        em.flush();
        lienDocMetaGroupe.setGroupe(groupe);
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);
        Long groupeId = groupe.getId();

        // Get all the lienDocMetaGroupeList where groupe equals to groupeId
        defaultLienDocMetaGroupeShouldBeFound("groupeId.equals=" + groupeId);

        // Get all the lienDocMetaGroupeList where groupe equals to groupeId + 1
        defaultLienDocMetaGroupeShouldNotBeFound("groupeId.equals=" + (groupeId + 1));
    }


    @Test
    @Transactional
    public void getAllLienDocMetaGroupesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);
        TypeMetaGroupe type = TypeMetaGroupeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        lienDocMetaGroupe.setType(type);
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);
        Long typeId = type.getId();

        // Get all the lienDocMetaGroupeList where type equals to typeId
        defaultLienDocMetaGroupeShouldBeFound("typeId.equals=" + typeId);

        // Get all the lienDocMetaGroupeList where type equals to typeId + 1
        defaultLienDocMetaGroupeShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLienDocMetaGroupeShouldBeFound(String filter) throws Exception {
        restLienDocMetaGroupeMockMvc.perform(get("/api/lien-doc-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lienDocMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].typeDuDoc").value(hasItem(DEFAULT_TYPE_DU_DOC.toString())))
            .andExpect(jsonPath("$.[*].iconeContentType").value(hasItem(DEFAULT_ICONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icone").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICONE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].docContentType").value(hasItem(DEFAULT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].doc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOC))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));

        // Check, that the count call also returns 1
        restLienDocMetaGroupeMockMvc.perform(get("/api/lien-doc-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLienDocMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restLienDocMetaGroupeMockMvc.perform(get("/api/lien-doc-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLienDocMetaGroupeMockMvc.perform(get("/api/lien-doc-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLienDocMetaGroupe() throws Exception {
        // Get the lienDocMetaGroupe
        restLienDocMetaGroupeMockMvc.perform(get("/api/lien-doc-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLienDocMetaGroupe() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        int databaseSizeBeforeUpdate = lienDocMetaGroupeRepository.findAll().size();

        // Update the lienDocMetaGroupe
        LienDocMetaGroupe updatedLienDocMetaGroupe = lienDocMetaGroupeRepository.findById(lienDocMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedLienDocMetaGroupe are not directly saved in db
        em.detach(updatedLienDocMetaGroupe);
        updatedLienDocMetaGroupe
            .nom(UPDATED_NOM)
            .typeDuDoc(UPDATED_TYPE_DU_DOC)
            .icone(UPDATED_ICONE)
            .iconeContentType(UPDATED_ICONE_CONTENT_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .detail(UPDATED_DETAIL);
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO = lienDocMetaGroupeMapper.toDto(updatedLienDocMetaGroupe);

        restLienDocMetaGroupeMockMvc.perform(put("/api/lien-doc-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lienDocMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the LienDocMetaGroupe in the database
        List<LienDocMetaGroupe> lienDocMetaGroupeList = lienDocMetaGroupeRepository.findAll();
        assertThat(lienDocMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        LienDocMetaGroupe testLienDocMetaGroupe = lienDocMetaGroupeList.get(lienDocMetaGroupeList.size() - 1);
        assertThat(testLienDocMetaGroupe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testLienDocMetaGroupe.getTypeDuDoc()).isEqualTo(UPDATED_TYPE_DU_DOC);
        assertThat(testLienDocMetaGroupe.getIcone()).isEqualTo(UPDATED_ICONE);
        assertThat(testLienDocMetaGroupe.getIconeContentType()).isEqualTo(UPDATED_ICONE_CONTENT_TYPE);
        assertThat(testLienDocMetaGroupe.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testLienDocMetaGroupe.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testLienDocMetaGroupe.getDoc()).isEqualTo(UPDATED_DOC);
        assertThat(testLienDocMetaGroupe.getDocContentType()).isEqualTo(UPDATED_DOC_CONTENT_TYPE);
        assertThat(testLienDocMetaGroupe.getDetail()).isEqualTo(UPDATED_DETAIL);

        // Validate the LienDocMetaGroupe in Elasticsearch
        verify(mockLienDocMetaGroupeSearchRepository, times(1)).save(testLienDocMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingLienDocMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = lienDocMetaGroupeRepository.findAll().size();

        // Create the LienDocMetaGroupe
        LienDocMetaGroupeDTO lienDocMetaGroupeDTO = lienDocMetaGroupeMapper.toDto(lienDocMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLienDocMetaGroupeMockMvc.perform(put("/api/lien-doc-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lienDocMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LienDocMetaGroupe in the database
        List<LienDocMetaGroupe> lienDocMetaGroupeList = lienDocMetaGroupeRepository.findAll();
        assertThat(lienDocMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LienDocMetaGroupe in Elasticsearch
        verify(mockLienDocMetaGroupeSearchRepository, times(0)).save(lienDocMetaGroupe);
    }

    @Test
    @Transactional
    public void deleteLienDocMetaGroupe() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);

        int databaseSizeBeforeDelete = lienDocMetaGroupeRepository.findAll().size();

        // Delete the lienDocMetaGroupe
        restLienDocMetaGroupeMockMvc.perform(delete("/api/lien-doc-meta-groupes/{id}", lienDocMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LienDocMetaGroupe> lienDocMetaGroupeList = lienDocMetaGroupeRepository.findAll();
        assertThat(lienDocMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LienDocMetaGroupe in Elasticsearch
        verify(mockLienDocMetaGroupeSearchRepository, times(1)).deleteById(lienDocMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchLienDocMetaGroupe() throws Exception {
        // Initialize the database
        lienDocMetaGroupeRepository.saveAndFlush(lienDocMetaGroupe);
        when(mockLienDocMetaGroupeSearchRepository.search(queryStringQuery("id:" + lienDocMetaGroupe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lienDocMetaGroupe), PageRequest.of(0, 1), 1));
        // Search the lienDocMetaGroupe
        restLienDocMetaGroupeMockMvc.perform(get("/api/_search/lien-doc-meta-groupes?query=id:" + lienDocMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lienDocMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].typeDuDoc").value(hasItem(DEFAULT_TYPE_DU_DOC.toString())))
            .andExpect(jsonPath("$.[*].iconeContentType").value(hasItem(DEFAULT_ICONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icone").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICONE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].docContentType").value(hasItem(DEFAULT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].doc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOC))))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
}
