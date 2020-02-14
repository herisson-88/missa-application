package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.TypeMetaGroupe;
import fr.herisson.missa.domain.LienDocMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.Reseau;
import fr.herisson.missa.repository.TypeMetaGroupeRepository;
import fr.herisson.missa.repository.search.TypeMetaGroupeSearchRepository;
import fr.herisson.missa.service.TypeMetaGroupeService;
import fr.herisson.missa.service.dto.TypeMetaGroupeDTO;
import fr.herisson.missa.service.mapper.TypeMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.TypeMetaGroupeCriteria;
import fr.herisson.missa.service.TypeMetaGroupeQueryService;

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
 * Integration tests for the {@link TypeMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class TypeMetaGroupeResourceIT {

    private static final String DEFAULT_TYPE_DU_GROUPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_DU_GROUPE = "BBBBBBBBBB";

    private static final String DEFAULT_ICONE_FA = "AAAAAAAAAA";
    private static final String UPDATED_ICONE_FA = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDRE_MAIL = 1;
    private static final Integer UPDATED_ORDRE_MAIL = 2;
    private static final Integer SMALLER_ORDRE_MAIL = 1 - 1;

    @Autowired
    private TypeMetaGroupeRepository typeMetaGroupeRepository;

    @Autowired
    private TypeMetaGroupeMapper typeMetaGroupeMapper;

    @Autowired
    private TypeMetaGroupeService typeMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.TypeMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private TypeMetaGroupeSearchRepository mockTypeMetaGroupeSearchRepository;

    @Autowired
    private TypeMetaGroupeQueryService typeMetaGroupeQueryService;

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

    private MockMvc restTypeMetaGroupeMockMvc;

    private TypeMetaGroupe typeMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeMetaGroupeResource typeMetaGroupeResource = new TypeMetaGroupeResource(typeMetaGroupeService, typeMetaGroupeQueryService);
        this.restTypeMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(typeMetaGroupeResource)
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
    public static TypeMetaGroupe createEntity(EntityManager em) {
        TypeMetaGroupe typeMetaGroupe = new TypeMetaGroupe()
            .typeDuGroupe(DEFAULT_TYPE_DU_GROUPE)
            .iconeFa(DEFAULT_ICONE_FA)
            .detail(DEFAULT_DETAIL)
            .ordreMail(DEFAULT_ORDRE_MAIL);
        return typeMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeMetaGroupe createUpdatedEntity(EntityManager em) {
        TypeMetaGroupe typeMetaGroupe = new TypeMetaGroupe()
            .typeDuGroupe(UPDATED_TYPE_DU_GROUPE)
            .iconeFa(UPDATED_ICONE_FA)
            .detail(UPDATED_DETAIL)
            .ordreMail(UPDATED_ORDRE_MAIL);
        return typeMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        typeMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = typeMetaGroupeRepository.findAll().size();

        // Create the TypeMetaGroupe
        TypeMetaGroupeDTO typeMetaGroupeDTO = typeMetaGroupeMapper.toDto(typeMetaGroupe);
        restTypeMetaGroupeMockMvc.perform(post("/api/type-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeMetaGroupe in the database
        List<TypeMetaGroupe> typeMetaGroupeList = typeMetaGroupeRepository.findAll();
        assertThat(typeMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        TypeMetaGroupe testTypeMetaGroupe = typeMetaGroupeList.get(typeMetaGroupeList.size() - 1);
        assertThat(testTypeMetaGroupe.getTypeDuGroupe()).isEqualTo(DEFAULT_TYPE_DU_GROUPE);
        assertThat(testTypeMetaGroupe.getIconeFa()).isEqualTo(DEFAULT_ICONE_FA);
        assertThat(testTypeMetaGroupe.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testTypeMetaGroupe.getOrdreMail()).isEqualTo(DEFAULT_ORDRE_MAIL);

        // Validate the TypeMetaGroupe in Elasticsearch
        verify(mockTypeMetaGroupeSearchRepository, times(1)).save(testTypeMetaGroupe);
    }

    @Test
    @Transactional
    public void createTypeMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeMetaGroupeRepository.findAll().size();

        // Create the TypeMetaGroupe with an existing ID
        typeMetaGroupe.setId(1L);
        TypeMetaGroupeDTO typeMetaGroupeDTO = typeMetaGroupeMapper.toDto(typeMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeMetaGroupeMockMvc.perform(post("/api/type-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMetaGroupe in the database
        List<TypeMetaGroupe> typeMetaGroupeList = typeMetaGroupeRepository.findAll();
        assertThat(typeMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the TypeMetaGroupe in Elasticsearch
        verify(mockTypeMetaGroupeSearchRepository, times(0)).save(typeMetaGroupe);
    }


    @Test
    @Transactional
    public void checkTypeDuGroupeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeMetaGroupeRepository.findAll().size();
        // set the field null
        typeMetaGroupe.setTypeDuGroupe(null);

        // Create the TypeMetaGroupe, which fails.
        TypeMetaGroupeDTO typeMetaGroupeDTO = typeMetaGroupeMapper.toDto(typeMetaGroupe);

        restTypeMetaGroupeMockMvc.perform(post("/api/type-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        List<TypeMetaGroupe> typeMetaGroupeList = typeMetaGroupeRepository.findAll();
        assertThat(typeMetaGroupeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupes() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList
        restTypeMetaGroupeMockMvc.perform(get("/api/type-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeDuGroupe").value(hasItem(DEFAULT_TYPE_DU_GROUPE)))
            .andExpect(jsonPath("$.[*].iconeFa").value(hasItem(DEFAULT_ICONE_FA)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].ordreMail").value(hasItem(DEFAULT_ORDRE_MAIL)));
    }
    
    @Test
    @Transactional
    public void getTypeMetaGroupe() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get the typeMetaGroupe
        restTypeMetaGroupeMockMvc.perform(get("/api/type-meta-groupes/{id}", typeMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.typeDuGroupe").value(DEFAULT_TYPE_DU_GROUPE))
            .andExpect(jsonPath("$.iconeFa").value(DEFAULT_ICONE_FA))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.ordreMail").value(DEFAULT_ORDRE_MAIL));
    }


    @Test
    @Transactional
    public void getTypeMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        Long id = typeMetaGroupe.getId();

        defaultTypeMetaGroupeShouldBeFound("id.equals=" + id);
        defaultTypeMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultTypeMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypeMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultTypeMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypeMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTypeMetaGroupesByTypeDuGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where typeDuGroupe equals to DEFAULT_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldBeFound("typeDuGroupe.equals=" + DEFAULT_TYPE_DU_GROUPE);

        // Get all the typeMetaGroupeList where typeDuGroupe equals to UPDATED_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldNotBeFound("typeDuGroupe.equals=" + UPDATED_TYPE_DU_GROUPE);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByTypeDuGroupeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where typeDuGroupe not equals to DEFAULT_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldNotBeFound("typeDuGroupe.notEquals=" + DEFAULT_TYPE_DU_GROUPE);

        // Get all the typeMetaGroupeList where typeDuGroupe not equals to UPDATED_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldBeFound("typeDuGroupe.notEquals=" + UPDATED_TYPE_DU_GROUPE);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByTypeDuGroupeIsInShouldWork() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where typeDuGroupe in DEFAULT_TYPE_DU_GROUPE or UPDATED_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldBeFound("typeDuGroupe.in=" + DEFAULT_TYPE_DU_GROUPE + "," + UPDATED_TYPE_DU_GROUPE);

        // Get all the typeMetaGroupeList where typeDuGroupe equals to UPDATED_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldNotBeFound("typeDuGroupe.in=" + UPDATED_TYPE_DU_GROUPE);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByTypeDuGroupeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where typeDuGroupe is not null
        defaultTypeMetaGroupeShouldBeFound("typeDuGroupe.specified=true");

        // Get all the typeMetaGroupeList where typeDuGroupe is null
        defaultTypeMetaGroupeShouldNotBeFound("typeDuGroupe.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypeMetaGroupesByTypeDuGroupeContainsSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where typeDuGroupe contains DEFAULT_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldBeFound("typeDuGroupe.contains=" + DEFAULT_TYPE_DU_GROUPE);

        // Get all the typeMetaGroupeList where typeDuGroupe contains UPDATED_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldNotBeFound("typeDuGroupe.contains=" + UPDATED_TYPE_DU_GROUPE);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByTypeDuGroupeNotContainsSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where typeDuGroupe does not contain DEFAULT_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldNotBeFound("typeDuGroupe.doesNotContain=" + DEFAULT_TYPE_DU_GROUPE);

        // Get all the typeMetaGroupeList where typeDuGroupe does not contain UPDATED_TYPE_DU_GROUPE
        defaultTypeMetaGroupeShouldBeFound("typeDuGroupe.doesNotContain=" + UPDATED_TYPE_DU_GROUPE);
    }


    @Test
    @Transactional
    public void getAllTypeMetaGroupesByIconeFaIsEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where iconeFa equals to DEFAULT_ICONE_FA
        defaultTypeMetaGroupeShouldBeFound("iconeFa.equals=" + DEFAULT_ICONE_FA);

        // Get all the typeMetaGroupeList where iconeFa equals to UPDATED_ICONE_FA
        defaultTypeMetaGroupeShouldNotBeFound("iconeFa.equals=" + UPDATED_ICONE_FA);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByIconeFaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where iconeFa not equals to DEFAULT_ICONE_FA
        defaultTypeMetaGroupeShouldNotBeFound("iconeFa.notEquals=" + DEFAULT_ICONE_FA);

        // Get all the typeMetaGroupeList where iconeFa not equals to UPDATED_ICONE_FA
        defaultTypeMetaGroupeShouldBeFound("iconeFa.notEquals=" + UPDATED_ICONE_FA);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByIconeFaIsInShouldWork() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where iconeFa in DEFAULT_ICONE_FA or UPDATED_ICONE_FA
        defaultTypeMetaGroupeShouldBeFound("iconeFa.in=" + DEFAULT_ICONE_FA + "," + UPDATED_ICONE_FA);

        // Get all the typeMetaGroupeList where iconeFa equals to UPDATED_ICONE_FA
        defaultTypeMetaGroupeShouldNotBeFound("iconeFa.in=" + UPDATED_ICONE_FA);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByIconeFaIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where iconeFa is not null
        defaultTypeMetaGroupeShouldBeFound("iconeFa.specified=true");

        // Get all the typeMetaGroupeList where iconeFa is null
        defaultTypeMetaGroupeShouldNotBeFound("iconeFa.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypeMetaGroupesByIconeFaContainsSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where iconeFa contains DEFAULT_ICONE_FA
        defaultTypeMetaGroupeShouldBeFound("iconeFa.contains=" + DEFAULT_ICONE_FA);

        // Get all the typeMetaGroupeList where iconeFa contains UPDATED_ICONE_FA
        defaultTypeMetaGroupeShouldNotBeFound("iconeFa.contains=" + UPDATED_ICONE_FA);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByIconeFaNotContainsSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where iconeFa does not contain DEFAULT_ICONE_FA
        defaultTypeMetaGroupeShouldNotBeFound("iconeFa.doesNotContain=" + DEFAULT_ICONE_FA);

        // Get all the typeMetaGroupeList where iconeFa does not contain UPDATED_ICONE_FA
        defaultTypeMetaGroupeShouldBeFound("iconeFa.doesNotContain=" + UPDATED_ICONE_FA);
    }


    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail equals to DEFAULT_ORDRE_MAIL
        defaultTypeMetaGroupeShouldBeFound("ordreMail.equals=" + DEFAULT_ORDRE_MAIL);

        // Get all the typeMetaGroupeList where ordreMail equals to UPDATED_ORDRE_MAIL
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.equals=" + UPDATED_ORDRE_MAIL);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail not equals to DEFAULT_ORDRE_MAIL
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.notEquals=" + DEFAULT_ORDRE_MAIL);

        // Get all the typeMetaGroupeList where ordreMail not equals to UPDATED_ORDRE_MAIL
        defaultTypeMetaGroupeShouldBeFound("ordreMail.notEquals=" + UPDATED_ORDRE_MAIL);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsInShouldWork() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail in DEFAULT_ORDRE_MAIL or UPDATED_ORDRE_MAIL
        defaultTypeMetaGroupeShouldBeFound("ordreMail.in=" + DEFAULT_ORDRE_MAIL + "," + UPDATED_ORDRE_MAIL);

        // Get all the typeMetaGroupeList where ordreMail equals to UPDATED_ORDRE_MAIL
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.in=" + UPDATED_ORDRE_MAIL);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail is not null
        defaultTypeMetaGroupeShouldBeFound("ordreMail.specified=true");

        // Get all the typeMetaGroupeList where ordreMail is null
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail is greater than or equal to DEFAULT_ORDRE_MAIL
        defaultTypeMetaGroupeShouldBeFound("ordreMail.greaterThanOrEqual=" + DEFAULT_ORDRE_MAIL);

        // Get all the typeMetaGroupeList where ordreMail is greater than or equal to UPDATED_ORDRE_MAIL
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.greaterThanOrEqual=" + UPDATED_ORDRE_MAIL);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail is less than or equal to DEFAULT_ORDRE_MAIL
        defaultTypeMetaGroupeShouldBeFound("ordreMail.lessThanOrEqual=" + DEFAULT_ORDRE_MAIL);

        // Get all the typeMetaGroupeList where ordreMail is less than or equal to SMALLER_ORDRE_MAIL
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.lessThanOrEqual=" + SMALLER_ORDRE_MAIL);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsLessThanSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail is less than DEFAULT_ORDRE_MAIL
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.lessThan=" + DEFAULT_ORDRE_MAIL);

        // Get all the typeMetaGroupeList where ordreMail is less than UPDATED_ORDRE_MAIL
        defaultTypeMetaGroupeShouldBeFound("ordreMail.lessThan=" + UPDATED_ORDRE_MAIL);
    }

    @Test
    @Transactional
    public void getAllTypeMetaGroupesByOrdreMailIsGreaterThanSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        // Get all the typeMetaGroupeList where ordreMail is greater than DEFAULT_ORDRE_MAIL
        defaultTypeMetaGroupeShouldNotBeFound("ordreMail.greaterThan=" + DEFAULT_ORDRE_MAIL);

        // Get all the typeMetaGroupeList where ordreMail is greater than SMALLER_ORDRE_MAIL
        defaultTypeMetaGroupeShouldBeFound("ordreMail.greaterThan=" + SMALLER_ORDRE_MAIL);
    }


    @Test
    @Transactional
    public void getAllTypeMetaGroupesByDefaultDocsIsEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);
        LienDocMetaGroupe defaultDocs = LienDocMetaGroupeResourceIT.createEntity(em);
        em.persist(defaultDocs);
        em.flush();
        typeMetaGroupe.addDefaultDocs(defaultDocs);
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);
        Long defaultDocsId = defaultDocs.getId();

        // Get all the typeMetaGroupeList where defaultDocs equals to defaultDocsId
        defaultTypeMetaGroupeShouldBeFound("defaultDocsId.equals=" + defaultDocsId);

        // Get all the typeMetaGroupeList where defaultDocs equals to defaultDocsId + 1
        defaultTypeMetaGroupeShouldNotBeFound("defaultDocsId.equals=" + (defaultDocsId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeMetaGroupesByGroupesIsEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);
        MetaGroupe groupes = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupes);
        em.flush();
        typeMetaGroupe.addGroupes(groupes);
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);
        Long groupesId = groupes.getId();

        // Get all the typeMetaGroupeList where groupes equals to groupesId
        defaultTypeMetaGroupeShouldBeFound("groupesId.equals=" + groupesId);

        // Get all the typeMetaGroupeList where groupes equals to groupesId + 1
        defaultTypeMetaGroupeShouldNotBeFound("groupesId.equals=" + (groupesId + 1));
    }


    @Test
    @Transactional
    public void getAllTypeMetaGroupesByReseauIsEqualToSomething() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);
        Reseau reseau = ReseauResourceIT.createEntity(em);
        em.persist(reseau);
        em.flush();
        typeMetaGroupe.setReseau(reseau);
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);
        Long reseauId = reseau.getId();

        // Get all the typeMetaGroupeList where reseau equals to reseauId
        defaultTypeMetaGroupeShouldBeFound("reseauId.equals=" + reseauId);

        // Get all the typeMetaGroupeList where reseau equals to reseauId + 1
        defaultTypeMetaGroupeShouldNotBeFound("reseauId.equals=" + (reseauId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypeMetaGroupeShouldBeFound(String filter) throws Exception {
        restTypeMetaGroupeMockMvc.perform(get("/api/type-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeDuGroupe").value(hasItem(DEFAULT_TYPE_DU_GROUPE)))
            .andExpect(jsonPath("$.[*].iconeFa").value(hasItem(DEFAULT_ICONE_FA)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].ordreMail").value(hasItem(DEFAULT_ORDRE_MAIL)));

        // Check, that the count call also returns 1
        restTypeMetaGroupeMockMvc.perform(get("/api/type-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypeMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restTypeMetaGroupeMockMvc.perform(get("/api/type-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeMetaGroupeMockMvc.perform(get("/api/type-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTypeMetaGroupe() throws Exception {
        // Get the typeMetaGroupe
        restTypeMetaGroupeMockMvc.perform(get("/api/type-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeMetaGroupe() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        int databaseSizeBeforeUpdate = typeMetaGroupeRepository.findAll().size();

        // Update the typeMetaGroupe
        TypeMetaGroupe updatedTypeMetaGroupe = typeMetaGroupeRepository.findById(typeMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedTypeMetaGroupe are not directly saved in db
        em.detach(updatedTypeMetaGroupe);
        updatedTypeMetaGroupe
            .typeDuGroupe(UPDATED_TYPE_DU_GROUPE)
            .iconeFa(UPDATED_ICONE_FA)
            .detail(UPDATED_DETAIL)
            .ordreMail(UPDATED_ORDRE_MAIL);
        TypeMetaGroupeDTO typeMetaGroupeDTO = typeMetaGroupeMapper.toDto(updatedTypeMetaGroupe);

        restTypeMetaGroupeMockMvc.perform(put("/api/type-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the TypeMetaGroupe in the database
        List<TypeMetaGroupe> typeMetaGroupeList = typeMetaGroupeRepository.findAll();
        assertThat(typeMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        TypeMetaGroupe testTypeMetaGroupe = typeMetaGroupeList.get(typeMetaGroupeList.size() - 1);
        assertThat(testTypeMetaGroupe.getTypeDuGroupe()).isEqualTo(UPDATED_TYPE_DU_GROUPE);
        assertThat(testTypeMetaGroupe.getIconeFa()).isEqualTo(UPDATED_ICONE_FA);
        assertThat(testTypeMetaGroupe.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testTypeMetaGroupe.getOrdreMail()).isEqualTo(UPDATED_ORDRE_MAIL);

        // Validate the TypeMetaGroupe in Elasticsearch
        verify(mockTypeMetaGroupeSearchRepository, times(1)).save(testTypeMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = typeMetaGroupeRepository.findAll().size();

        // Create the TypeMetaGroupe
        TypeMetaGroupeDTO typeMetaGroupeDTO = typeMetaGroupeMapper.toDto(typeMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeMetaGroupeMockMvc.perform(put("/api/type-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMetaGroupe in the database
        List<TypeMetaGroupe> typeMetaGroupeList = typeMetaGroupeRepository.findAll();
        assertThat(typeMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TypeMetaGroupe in Elasticsearch
        verify(mockTypeMetaGroupeSearchRepository, times(0)).save(typeMetaGroupe);
    }

    @Test
    @Transactional
    public void deleteTypeMetaGroupe() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);

        int databaseSizeBeforeDelete = typeMetaGroupeRepository.findAll().size();

        // Delete the typeMetaGroupe
        restTypeMetaGroupeMockMvc.perform(delete("/api/type-meta-groupes/{id}", typeMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeMetaGroupe> typeMetaGroupeList = typeMetaGroupeRepository.findAll();
        assertThat(typeMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TypeMetaGroupe in Elasticsearch
        verify(mockTypeMetaGroupeSearchRepository, times(1)).deleteById(typeMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchTypeMetaGroupe() throws Exception {
        // Initialize the database
        typeMetaGroupeRepository.saveAndFlush(typeMetaGroupe);
        when(mockTypeMetaGroupeSearchRepository.search(queryStringQuery("id:" + typeMetaGroupe.getId())))
            .thenReturn(Collections.singletonList(typeMetaGroupe));
        // Search the typeMetaGroupe
        restTypeMetaGroupeMockMvc.perform(get("/api/_search/type-meta-groupes?query=id:" + typeMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeDuGroupe").value(hasItem(DEFAULT_TYPE_DU_GROUPE)))
            .andExpect(jsonPath("$.[*].iconeFa").value(hasItem(DEFAULT_ICONE_FA)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].ordreMail").value(hasItem(DEFAULT_ORDRE_MAIL)));
    }
}
