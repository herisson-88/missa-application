package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.OrganisateurMetaGroupe;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.repository.OrganisateurMetaGroupeRepository;
import fr.herisson.missa.repository.search.OrganisateurMetaGroupeSearchRepository;
import fr.herisson.missa.service.OrganisateurMetaGroupeService;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeDTO;
import fr.herisson.missa.service.mapper.OrganisateurMetaGroupeMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeCriteria;
import fr.herisson.missa.service.OrganisateurMetaGroupeQueryService;

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
 * Integration tests for the {@link OrganisateurMetaGroupeResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class OrganisateurMetaGroupeResourceIT {

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private OrganisateurMetaGroupeRepository organisateurMetaGroupeRepository;

    @Autowired
    private OrganisateurMetaGroupeMapper organisateurMetaGroupeMapper;

    @Autowired
    private OrganisateurMetaGroupeService organisateurMetaGroupeService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.OrganisateurMetaGroupeSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrganisateurMetaGroupeSearchRepository mockOrganisateurMetaGroupeSearchRepository;

    @Autowired
    private OrganisateurMetaGroupeQueryService organisateurMetaGroupeQueryService;

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

    private MockMvc restOrganisateurMetaGroupeMockMvc;

    private OrganisateurMetaGroupe organisateurMetaGroupe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganisateurMetaGroupeResource organisateurMetaGroupeResource = new OrganisateurMetaGroupeResource(organisateurMetaGroupeService, organisateurMetaGroupeQueryService);
        this.restOrganisateurMetaGroupeMockMvc = MockMvcBuilders.standaloneSetup(organisateurMetaGroupeResource)
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
    public static OrganisateurMetaGroupe createEntity(EntityManager em) {
        OrganisateurMetaGroupe organisateurMetaGroupe = new OrganisateurMetaGroupe()
            .site(DEFAULT_SITE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .mail(DEFAULT_MAIL)
            .adresse(DEFAULT_ADRESSE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .ville(DEFAULT_VILLE)
            .detail(DEFAULT_DETAIL);
        return organisateurMetaGroupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisateurMetaGroupe createUpdatedEntity(EntityManager em) {
        OrganisateurMetaGroupe organisateurMetaGroupe = new OrganisateurMetaGroupe()
            .site(UPDATED_SITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .detail(UPDATED_DETAIL);
        return organisateurMetaGroupe;
    }

    @BeforeEach
    public void initTest() {
        organisateurMetaGroupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisateurMetaGroupe() throws Exception {
        int databaseSizeBeforeCreate = organisateurMetaGroupeRepository.findAll().size();

        // Create the OrganisateurMetaGroupe
        OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO = organisateurMetaGroupeMapper.toDto(organisateurMetaGroupe);
        restOrganisateurMetaGroupeMockMvc.perform(post("/api/organisateur-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisateurMetaGroupeDTO)))
            .andExpect(status().isCreated());

        // Validate the OrganisateurMetaGroupe in the database
        List<OrganisateurMetaGroupe> organisateurMetaGroupeList = organisateurMetaGroupeRepository.findAll();
        assertThat(organisateurMetaGroupeList).hasSize(databaseSizeBeforeCreate + 1);
        OrganisateurMetaGroupe testOrganisateurMetaGroupe = organisateurMetaGroupeList.get(organisateurMetaGroupeList.size() - 1);
        assertThat(testOrganisateurMetaGroupe.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testOrganisateurMetaGroupe.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testOrganisateurMetaGroupe.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testOrganisateurMetaGroupe.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testOrganisateurMetaGroupe.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testOrganisateurMetaGroupe.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testOrganisateurMetaGroupe.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testOrganisateurMetaGroupe.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testOrganisateurMetaGroupe.getDetail()).isEqualTo(DEFAULT_DETAIL);

        // Validate the OrganisateurMetaGroupe in Elasticsearch
        verify(mockOrganisateurMetaGroupeSearchRepository, times(1)).save(testOrganisateurMetaGroupe);
    }

    @Test
    @Transactional
    public void createOrganisateurMetaGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisateurMetaGroupeRepository.findAll().size();

        // Create the OrganisateurMetaGroupe with an existing ID
        organisateurMetaGroupe.setId(1L);
        OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO = organisateurMetaGroupeMapper.toDto(organisateurMetaGroupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisateurMetaGroupeMockMvc.perform(post("/api/organisateur-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisateurMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisateurMetaGroupe in the database
        List<OrganisateurMetaGroupe> organisateurMetaGroupeList = organisateurMetaGroupeRepository.findAll();
        assertThat(organisateurMetaGroupeList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrganisateurMetaGroupe in Elasticsearch
        verify(mockOrganisateurMetaGroupeSearchRepository, times(0)).save(organisateurMetaGroupe);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupes() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/organisateur-meta-groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisateurMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getOrganisateurMetaGroupe() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get the organisateurMetaGroupe
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/organisateur-meta-groupes/{id}", organisateurMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisateurMetaGroupe.getId().intValue()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()));
    }


    @Test
    @Transactional
    public void getOrganisateurMetaGroupesByIdFiltering() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        Long id = organisateurMetaGroupe.getId();

        defaultOrganisateurMetaGroupeShouldBeFound("id.equals=" + id);
        defaultOrganisateurMetaGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultOrganisateurMetaGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrganisateurMetaGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultOrganisateurMetaGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrganisateurMetaGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesBySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where site equals to DEFAULT_SITE
        defaultOrganisateurMetaGroupeShouldBeFound("site.equals=" + DEFAULT_SITE);

        // Get all the organisateurMetaGroupeList where site equals to UPDATED_SITE
        defaultOrganisateurMetaGroupeShouldNotBeFound("site.equals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesBySiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where site not equals to DEFAULT_SITE
        defaultOrganisateurMetaGroupeShouldNotBeFound("site.notEquals=" + DEFAULT_SITE);

        // Get all the organisateurMetaGroupeList where site not equals to UPDATED_SITE
        defaultOrganisateurMetaGroupeShouldBeFound("site.notEquals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesBySiteIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where site in DEFAULT_SITE or UPDATED_SITE
        defaultOrganisateurMetaGroupeShouldBeFound("site.in=" + DEFAULT_SITE + "," + UPDATED_SITE);

        // Get all the organisateurMetaGroupeList where site equals to UPDATED_SITE
        defaultOrganisateurMetaGroupeShouldNotBeFound("site.in=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesBySiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where site is not null
        defaultOrganisateurMetaGroupeShouldBeFound("site.specified=true");

        // Get all the organisateurMetaGroupeList where site is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("site.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesBySiteContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where site contains DEFAULT_SITE
        defaultOrganisateurMetaGroupeShouldBeFound("site.contains=" + DEFAULT_SITE);

        // Get all the organisateurMetaGroupeList where site contains UPDATED_SITE
        defaultOrganisateurMetaGroupeShouldNotBeFound("site.contains=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesBySiteNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where site does not contain DEFAULT_SITE
        defaultOrganisateurMetaGroupeShouldNotBeFound("site.doesNotContain=" + DEFAULT_SITE);

        // Get all the organisateurMetaGroupeList where site does not contain UPDATED_SITE
        defaultOrganisateurMetaGroupeShouldBeFound("site.doesNotContain=" + UPDATED_SITE);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where nom equals to DEFAULT_NOM
        defaultOrganisateurMetaGroupeShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the organisateurMetaGroupeList where nom equals to UPDATED_NOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where nom not equals to DEFAULT_NOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the organisateurMetaGroupeList where nom not equals to UPDATED_NOM
        defaultOrganisateurMetaGroupeShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultOrganisateurMetaGroupeShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the organisateurMetaGroupeList where nom equals to UPDATED_NOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where nom is not null
        defaultOrganisateurMetaGroupeShouldBeFound("nom.specified=true");

        // Get all the organisateurMetaGroupeList where nom is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByNomContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where nom contains DEFAULT_NOM
        defaultOrganisateurMetaGroupeShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the organisateurMetaGroupeList where nom contains UPDATED_NOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where nom does not contain DEFAULT_NOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the organisateurMetaGroupeList where nom does not contain UPDATED_NOM
        defaultOrganisateurMetaGroupeShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where prenom equals to DEFAULT_PRENOM
        defaultOrganisateurMetaGroupeShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the organisateurMetaGroupeList where prenom equals to UPDATED_PRENOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where prenom not equals to DEFAULT_PRENOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the organisateurMetaGroupeList where prenom not equals to UPDATED_PRENOM
        defaultOrganisateurMetaGroupeShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultOrganisateurMetaGroupeShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the organisateurMetaGroupeList where prenom equals to UPDATED_PRENOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where prenom is not null
        defaultOrganisateurMetaGroupeShouldBeFound("prenom.specified=true");

        // Get all the organisateurMetaGroupeList where prenom is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByPrenomContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where prenom contains DEFAULT_PRENOM
        defaultOrganisateurMetaGroupeShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the organisateurMetaGroupeList where prenom contains UPDATED_PRENOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where prenom does not contain DEFAULT_PRENOM
        defaultOrganisateurMetaGroupeShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the organisateurMetaGroupeList where prenom does not contain UPDATED_PRENOM
        defaultOrganisateurMetaGroupeShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where telephone equals to DEFAULT_TELEPHONE
        defaultOrganisateurMetaGroupeShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the organisateurMetaGroupeList where telephone equals to UPDATED_TELEPHONE
        defaultOrganisateurMetaGroupeShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where telephone not equals to DEFAULT_TELEPHONE
        defaultOrganisateurMetaGroupeShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the organisateurMetaGroupeList where telephone not equals to UPDATED_TELEPHONE
        defaultOrganisateurMetaGroupeShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultOrganisateurMetaGroupeShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the organisateurMetaGroupeList where telephone equals to UPDATED_TELEPHONE
        defaultOrganisateurMetaGroupeShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where telephone is not null
        defaultOrganisateurMetaGroupeShouldBeFound("telephone.specified=true");

        // Get all the organisateurMetaGroupeList where telephone is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where telephone contains DEFAULT_TELEPHONE
        defaultOrganisateurMetaGroupeShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the organisateurMetaGroupeList where telephone contains UPDATED_TELEPHONE
        defaultOrganisateurMetaGroupeShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where telephone does not contain DEFAULT_TELEPHONE
        defaultOrganisateurMetaGroupeShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the organisateurMetaGroupeList where telephone does not contain UPDATED_TELEPHONE
        defaultOrganisateurMetaGroupeShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where mail equals to DEFAULT_MAIL
        defaultOrganisateurMetaGroupeShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the organisateurMetaGroupeList where mail equals to UPDATED_MAIL
        defaultOrganisateurMetaGroupeShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where mail not equals to DEFAULT_MAIL
        defaultOrganisateurMetaGroupeShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the organisateurMetaGroupeList where mail not equals to UPDATED_MAIL
        defaultOrganisateurMetaGroupeShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByMailIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultOrganisateurMetaGroupeShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the organisateurMetaGroupeList where mail equals to UPDATED_MAIL
        defaultOrganisateurMetaGroupeShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where mail is not null
        defaultOrganisateurMetaGroupeShouldBeFound("mail.specified=true");

        // Get all the organisateurMetaGroupeList where mail is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("mail.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByMailContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where mail contains DEFAULT_MAIL
        defaultOrganisateurMetaGroupeShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the organisateurMetaGroupeList where mail contains UPDATED_MAIL
        defaultOrganisateurMetaGroupeShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByMailNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where mail does not contain DEFAULT_MAIL
        defaultOrganisateurMetaGroupeShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the organisateurMetaGroupeList where mail does not contain UPDATED_MAIL
        defaultOrganisateurMetaGroupeShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where adresse equals to DEFAULT_ADRESSE
        defaultOrganisateurMetaGroupeShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the organisateurMetaGroupeList where adresse equals to UPDATED_ADRESSE
        defaultOrganisateurMetaGroupeShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where adresse not equals to DEFAULT_ADRESSE
        defaultOrganisateurMetaGroupeShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the organisateurMetaGroupeList where adresse not equals to UPDATED_ADRESSE
        defaultOrganisateurMetaGroupeShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultOrganisateurMetaGroupeShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the organisateurMetaGroupeList where adresse equals to UPDATED_ADRESSE
        defaultOrganisateurMetaGroupeShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where adresse is not null
        defaultOrganisateurMetaGroupeShouldBeFound("adresse.specified=true");

        // Get all the organisateurMetaGroupeList where adresse is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where adresse contains DEFAULT_ADRESSE
        defaultOrganisateurMetaGroupeShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the organisateurMetaGroupeList where adresse contains UPDATED_ADRESSE
        defaultOrganisateurMetaGroupeShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where adresse does not contain DEFAULT_ADRESSE
        defaultOrganisateurMetaGroupeShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the organisateurMetaGroupeList where adresse does not contain UPDATED_ADRESSE
        defaultOrganisateurMetaGroupeShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where codePostal equals to DEFAULT_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldBeFound("codePostal.equals=" + DEFAULT_CODE_POSTAL);

        // Get all the organisateurMetaGroupeList where codePostal equals to UPDATED_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldNotBeFound("codePostal.equals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByCodePostalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where codePostal not equals to DEFAULT_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldNotBeFound("codePostal.notEquals=" + DEFAULT_CODE_POSTAL);

        // Get all the organisateurMetaGroupeList where codePostal not equals to UPDATED_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldBeFound("codePostal.notEquals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByCodePostalIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where codePostal in DEFAULT_CODE_POSTAL or UPDATED_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldBeFound("codePostal.in=" + DEFAULT_CODE_POSTAL + "," + UPDATED_CODE_POSTAL);

        // Get all the organisateurMetaGroupeList where codePostal equals to UPDATED_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldNotBeFound("codePostal.in=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByCodePostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where codePostal is not null
        defaultOrganisateurMetaGroupeShouldBeFound("codePostal.specified=true");

        // Get all the organisateurMetaGroupeList where codePostal is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("codePostal.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByCodePostalContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where codePostal contains DEFAULT_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldBeFound("codePostal.contains=" + DEFAULT_CODE_POSTAL);

        // Get all the organisateurMetaGroupeList where codePostal contains UPDATED_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldNotBeFound("codePostal.contains=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByCodePostalNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where codePostal does not contain DEFAULT_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldNotBeFound("codePostal.doesNotContain=" + DEFAULT_CODE_POSTAL);

        // Get all the organisateurMetaGroupeList where codePostal does not contain UPDATED_CODE_POSTAL
        defaultOrganisateurMetaGroupeShouldBeFound("codePostal.doesNotContain=" + UPDATED_CODE_POSTAL);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where ville equals to DEFAULT_VILLE
        defaultOrganisateurMetaGroupeShouldBeFound("ville.equals=" + DEFAULT_VILLE);

        // Get all the organisateurMetaGroupeList where ville equals to UPDATED_VILLE
        defaultOrganisateurMetaGroupeShouldNotBeFound("ville.equals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where ville not equals to DEFAULT_VILLE
        defaultOrganisateurMetaGroupeShouldNotBeFound("ville.notEquals=" + DEFAULT_VILLE);

        // Get all the organisateurMetaGroupeList where ville not equals to UPDATED_VILLE
        defaultOrganisateurMetaGroupeShouldBeFound("ville.notEquals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByVilleIsInShouldWork() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where ville in DEFAULT_VILLE or UPDATED_VILLE
        defaultOrganisateurMetaGroupeShouldBeFound("ville.in=" + DEFAULT_VILLE + "," + UPDATED_VILLE);

        // Get all the organisateurMetaGroupeList where ville equals to UPDATED_VILLE
        defaultOrganisateurMetaGroupeShouldNotBeFound("ville.in=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where ville is not null
        defaultOrganisateurMetaGroupeShouldBeFound("ville.specified=true");

        // Get all the organisateurMetaGroupeList where ville is null
        defaultOrganisateurMetaGroupeShouldNotBeFound("ville.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByVilleContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where ville contains DEFAULT_VILLE
        defaultOrganisateurMetaGroupeShouldBeFound("ville.contains=" + DEFAULT_VILLE);

        // Get all the organisateurMetaGroupeList where ville contains UPDATED_VILLE
        defaultOrganisateurMetaGroupeShouldNotBeFound("ville.contains=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByVilleNotContainsSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        // Get all the organisateurMetaGroupeList where ville does not contain DEFAULT_VILLE
        defaultOrganisateurMetaGroupeShouldNotBeFound("ville.doesNotContain=" + DEFAULT_VILLE);

        // Get all the organisateurMetaGroupeList where ville does not contain UPDATED_VILLE
        defaultOrganisateurMetaGroupeShouldBeFound("ville.doesNotContain=" + UPDATED_VILLE);
    }


    @Test
    @Transactional
    public void getAllOrganisateurMetaGroupesByGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);
        MetaGroupe groupe = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupe);
        em.flush();
        organisateurMetaGroupe.setGroupe(groupe);
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);
        Long groupeId = groupe.getId();

        // Get all the organisateurMetaGroupeList where groupe equals to groupeId
        defaultOrganisateurMetaGroupeShouldBeFound("groupeId.equals=" + groupeId);

        // Get all the organisateurMetaGroupeList where groupe equals to groupeId + 1
        defaultOrganisateurMetaGroupeShouldNotBeFound("groupeId.equals=" + (groupeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganisateurMetaGroupeShouldBeFound(String filter) throws Exception {
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/organisateur-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisateurMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));

        // Check, that the count call also returns 1
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/organisateur-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganisateurMetaGroupeShouldNotBeFound(String filter) throws Exception {
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/organisateur-meta-groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/organisateur-meta-groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrganisateurMetaGroupe() throws Exception {
        // Get the organisateurMetaGroupe
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/organisateur-meta-groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisateurMetaGroupe() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        int databaseSizeBeforeUpdate = organisateurMetaGroupeRepository.findAll().size();

        // Update the organisateurMetaGroupe
        OrganisateurMetaGroupe updatedOrganisateurMetaGroupe = organisateurMetaGroupeRepository.findById(organisateurMetaGroupe.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisateurMetaGroupe are not directly saved in db
        em.detach(updatedOrganisateurMetaGroupe);
        updatedOrganisateurMetaGroupe
            .site(UPDATED_SITE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .ville(UPDATED_VILLE)
            .detail(UPDATED_DETAIL);
        OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO = organisateurMetaGroupeMapper.toDto(updatedOrganisateurMetaGroupe);

        restOrganisateurMetaGroupeMockMvc.perform(put("/api/organisateur-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisateurMetaGroupeDTO)))
            .andExpect(status().isOk());

        // Validate the OrganisateurMetaGroupe in the database
        List<OrganisateurMetaGroupe> organisateurMetaGroupeList = organisateurMetaGroupeRepository.findAll();
        assertThat(organisateurMetaGroupeList).hasSize(databaseSizeBeforeUpdate);
        OrganisateurMetaGroupe testOrganisateurMetaGroupe = organisateurMetaGroupeList.get(organisateurMetaGroupeList.size() - 1);
        assertThat(testOrganisateurMetaGroupe.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testOrganisateurMetaGroupe.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testOrganisateurMetaGroupe.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testOrganisateurMetaGroupe.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testOrganisateurMetaGroupe.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testOrganisateurMetaGroupe.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testOrganisateurMetaGroupe.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testOrganisateurMetaGroupe.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testOrganisateurMetaGroupe.getDetail()).isEqualTo(UPDATED_DETAIL);

        // Validate the OrganisateurMetaGroupe in Elasticsearch
        verify(mockOrganisateurMetaGroupeSearchRepository, times(1)).save(testOrganisateurMetaGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisateurMetaGroupe() throws Exception {
        int databaseSizeBeforeUpdate = organisateurMetaGroupeRepository.findAll().size();

        // Create the OrganisateurMetaGroupe
        OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO = organisateurMetaGroupeMapper.toDto(organisateurMetaGroupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisateurMetaGroupeMockMvc.perform(put("/api/organisateur-meta-groupes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(organisateurMetaGroupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganisateurMetaGroupe in the database
        List<OrganisateurMetaGroupe> organisateurMetaGroupeList = organisateurMetaGroupeRepository.findAll();
        assertThat(organisateurMetaGroupeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrganisateurMetaGroupe in Elasticsearch
        verify(mockOrganisateurMetaGroupeSearchRepository, times(0)).save(organisateurMetaGroupe);
    }

    @Test
    @Transactional
    public void deleteOrganisateurMetaGroupe() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);

        int databaseSizeBeforeDelete = organisateurMetaGroupeRepository.findAll().size();

        // Delete the organisateurMetaGroupe
        restOrganisateurMetaGroupeMockMvc.perform(delete("/api/organisateur-meta-groupes/{id}", organisateurMetaGroupe.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganisateurMetaGroupe> organisateurMetaGroupeList = organisateurMetaGroupeRepository.findAll();
        assertThat(organisateurMetaGroupeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrganisateurMetaGroupe in Elasticsearch
        verify(mockOrganisateurMetaGroupeSearchRepository, times(1)).deleteById(organisateurMetaGroupe.getId());
    }

    @Test
    @Transactional
    public void searchOrganisateurMetaGroupe() throws Exception {
        // Initialize the database
        organisateurMetaGroupeRepository.saveAndFlush(organisateurMetaGroupe);
        when(mockOrganisateurMetaGroupeSearchRepository.search(queryStringQuery("id:" + organisateurMetaGroupe.getId())))
            .thenReturn(Collections.singletonList(organisateurMetaGroupe));
        // Search the organisateurMetaGroupe
        restOrganisateurMetaGroupeMockMvc.perform(get("/api/_search/organisateur-meta-groupes?query=id:" + organisateurMetaGroupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisateurMetaGroupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
}
