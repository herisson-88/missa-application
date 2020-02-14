package fr.herisson.missa.web.rest;

import fr.herisson.missa.MissaApp;
import fr.herisson.missa.config.TestSecurityConfiguration;
import fr.herisson.missa.domain.Reseau;
import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.TypeMetaGroupe;
import fr.herisson.missa.repository.ReseauRepository;
import fr.herisson.missa.repository.search.ReseauSearchRepository;
import fr.herisson.missa.service.ReseauService;
import fr.herisson.missa.service.dto.ReseauDTO;
import fr.herisson.missa.service.mapper.ReseauMapper;
import fr.herisson.missa.web.rest.errors.ExceptionTranslator;
import fr.herisson.missa.service.dto.ReseauCriteria;
import fr.herisson.missa.service.ReseauQueryService;

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
 * Integration tests for the {@link ReseauResource} REST controller.
 */
@SpringBootTest(classes = {MissaApp.class, TestSecurityConfiguration.class})
public class ReseauResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ReseauRepository reseauRepository;

    @Autowired
    private ReseauMapper reseauMapper;

    @Autowired
    private ReseauService reseauService;

    /**
     * This repository is mocked in the fr.herisson.missa.repository.search test package.
     *
     * @see fr.herisson.missa.repository.search.ReseauSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReseauSearchRepository mockReseauSearchRepository;

    @Autowired
    private ReseauQueryService reseauQueryService;

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

    private MockMvc restReseauMockMvc;

    private Reseau reseau;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReseauResource reseauResource = new ReseauResource(reseauService, reseauQueryService);
        this.restReseauMockMvc = MockMvcBuilders.standaloneSetup(reseauResource)
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
    public static Reseau createEntity(EntityManager em) {
        Reseau reseau = new Reseau()
            .nom(DEFAULT_NOM);
        return reseau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reseau createUpdatedEntity(EntityManager em) {
        Reseau reseau = new Reseau()
            .nom(UPDATED_NOM);
        return reseau;
    }

    @BeforeEach
    public void initTest() {
        reseau = createEntity(em);
    }

    @Test
    @Transactional
    public void createReseau() throws Exception {
        int databaseSizeBeforeCreate = reseauRepository.findAll().size();

        // Create the Reseau
        ReseauDTO reseauDTO = reseauMapper.toDto(reseau);
        restReseauMockMvc.perform(post("/api/reseaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reseauDTO)))
            .andExpect(status().isCreated());

        // Validate the Reseau in the database
        List<Reseau> reseauList = reseauRepository.findAll();
        assertThat(reseauList).hasSize(databaseSizeBeforeCreate + 1);
        Reseau testReseau = reseauList.get(reseauList.size() - 1);
        assertThat(testReseau.getNom()).isEqualTo(DEFAULT_NOM);

        // Validate the Reseau in Elasticsearch
        verify(mockReseauSearchRepository, times(1)).save(testReseau);
    }

    @Test
    @Transactional
    public void createReseauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reseauRepository.findAll().size();

        // Create the Reseau with an existing ID
        reseau.setId(1L);
        ReseauDTO reseauDTO = reseauMapper.toDto(reseau);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReseauMockMvc.perform(post("/api/reseaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reseauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reseau in the database
        List<Reseau> reseauList = reseauRepository.findAll();
        assertThat(reseauList).hasSize(databaseSizeBeforeCreate);

        // Validate the Reseau in Elasticsearch
        verify(mockReseauSearchRepository, times(0)).save(reseau);
    }


    @Test
    @Transactional
    public void getAllReseaus() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get all the reseauList
        restReseauMockMvc.perform(get("/api/reseaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reseau.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getReseau() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get the reseau
        restReseauMockMvc.perform(get("/api/reseaus/{id}", reseau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reseau.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getReseausByIdFiltering() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        Long id = reseau.getId();

        defaultReseauShouldBeFound("id.equals=" + id);
        defaultReseauShouldNotBeFound("id.notEquals=" + id);

        defaultReseauShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReseauShouldNotBeFound("id.greaterThan=" + id);

        defaultReseauShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReseauShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReseausByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get all the reseauList where nom equals to DEFAULT_NOM
        defaultReseauShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the reseauList where nom equals to UPDATED_NOM
        defaultReseauShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllReseausByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get all the reseauList where nom not equals to DEFAULT_NOM
        defaultReseauShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the reseauList where nom not equals to UPDATED_NOM
        defaultReseauShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllReseausByNomIsInShouldWork() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get all the reseauList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultReseauShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the reseauList where nom equals to UPDATED_NOM
        defaultReseauShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllReseausByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get all the reseauList where nom is not null
        defaultReseauShouldBeFound("nom.specified=true");

        // Get all the reseauList where nom is null
        defaultReseauShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllReseausByNomContainsSomething() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get all the reseauList where nom contains DEFAULT_NOM
        defaultReseauShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the reseauList where nom contains UPDATED_NOM
        defaultReseauShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllReseausByNomNotContainsSomething() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        // Get all the reseauList where nom does not contain DEFAULT_NOM
        defaultReseauShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the reseauList where nom does not contain UPDATED_NOM
        defaultReseauShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllReseausByGroupesIsEqualToSomething() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);
        MetaGroupe groupes = MetaGroupeResourceIT.createEntity(em);
        em.persist(groupes);
        em.flush();
        reseau.addGroupes(groupes);
        reseauRepository.saveAndFlush(reseau);
        Long groupesId = groupes.getId();

        // Get all the reseauList where groupes equals to groupesId
        defaultReseauShouldBeFound("groupesId.equals=" + groupesId);

        // Get all the reseauList where groupes equals to groupesId + 1
        defaultReseauShouldNotBeFound("groupesId.equals=" + (groupesId + 1));
    }


    @Test
    @Transactional
    public void getAllReseausByTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);
        TypeMetaGroupe types = TypeMetaGroupeResourceIT.createEntity(em);
        em.persist(types);
        em.flush();
        reseau.addTypes(types);
        reseauRepository.saveAndFlush(reseau);
        Long typesId = types.getId();

        // Get all the reseauList where types equals to typesId
        defaultReseauShouldBeFound("typesId.equals=" + typesId);

        // Get all the reseauList where types equals to typesId + 1
        defaultReseauShouldNotBeFound("typesId.equals=" + (typesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReseauShouldBeFound(String filter) throws Exception {
        restReseauMockMvc.perform(get("/api/reseaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reseau.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restReseauMockMvc.perform(get("/api/reseaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReseauShouldNotBeFound(String filter) throws Exception {
        restReseauMockMvc.perform(get("/api/reseaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReseauMockMvc.perform(get("/api/reseaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReseau() throws Exception {
        // Get the reseau
        restReseauMockMvc.perform(get("/api/reseaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReseau() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        int databaseSizeBeforeUpdate = reseauRepository.findAll().size();

        // Update the reseau
        Reseau updatedReseau = reseauRepository.findById(reseau.getId()).get();
        // Disconnect from session so that the updates on updatedReseau are not directly saved in db
        em.detach(updatedReseau);
        updatedReseau
            .nom(UPDATED_NOM);
        ReseauDTO reseauDTO = reseauMapper.toDto(updatedReseau);

        restReseauMockMvc.perform(put("/api/reseaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reseauDTO)))
            .andExpect(status().isOk());

        // Validate the Reseau in the database
        List<Reseau> reseauList = reseauRepository.findAll();
        assertThat(reseauList).hasSize(databaseSizeBeforeUpdate);
        Reseau testReseau = reseauList.get(reseauList.size() - 1);
        assertThat(testReseau.getNom()).isEqualTo(UPDATED_NOM);

        // Validate the Reseau in Elasticsearch
        verify(mockReseauSearchRepository, times(1)).save(testReseau);
    }

    @Test
    @Transactional
    public void updateNonExistingReseau() throws Exception {
        int databaseSizeBeforeUpdate = reseauRepository.findAll().size();

        // Create the Reseau
        ReseauDTO reseauDTO = reseauMapper.toDto(reseau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReseauMockMvc.perform(put("/api/reseaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reseauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reseau in the database
        List<Reseau> reseauList = reseauRepository.findAll();
        assertThat(reseauList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Reseau in Elasticsearch
        verify(mockReseauSearchRepository, times(0)).save(reseau);
    }

    @Test
    @Transactional
    public void deleteReseau() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);

        int databaseSizeBeforeDelete = reseauRepository.findAll().size();

        // Delete the reseau
        restReseauMockMvc.perform(delete("/api/reseaus/{id}", reseau.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reseau> reseauList = reseauRepository.findAll();
        assertThat(reseauList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Reseau in Elasticsearch
        verify(mockReseauSearchRepository, times(1)).deleteById(reseau.getId());
    }

    @Test
    @Transactional
    public void searchReseau() throws Exception {
        // Initialize the database
        reseauRepository.saveAndFlush(reseau);
        when(mockReseauSearchRepository.search(queryStringQuery("id:" + reseau.getId())))
            .thenReturn(Collections.singletonList(reseau));
        // Search the reseau
        restReseauMockMvc.perform(get("/api/_search/reseaus?query=id:" + reseau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reseau.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
}
