package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.OrganisateurMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeDTO;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeCriteria;
import fr.herisson.missa.service.OrganisateurMetaGroupeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link fr.herisson.missa.domain.OrganisateurMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class OrganisateurMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(OrganisateurMetaGroupeResource.class);

    private static final String ENTITY_NAME = "organisateurMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganisateurMetaGroupeService organisateurMetaGroupeService;

    private final OrganisateurMetaGroupeQueryService organisateurMetaGroupeQueryService;

    public OrganisateurMetaGroupeResource(OrganisateurMetaGroupeService organisateurMetaGroupeService, OrganisateurMetaGroupeQueryService organisateurMetaGroupeQueryService) {
        this.organisateurMetaGroupeService = organisateurMetaGroupeService;
        this.organisateurMetaGroupeQueryService = organisateurMetaGroupeQueryService;
    }

    /**
     * {@code POST  /organisateur-meta-groupes} : Create a new organisateurMetaGroupe.
     *
     * @param organisateurMetaGroupeDTO the organisateurMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organisateurMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the organisateurMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organisateur-meta-groupes")
    public ResponseEntity<OrganisateurMetaGroupeDTO> createOrganisateurMetaGroupe(@Valid @RequestBody OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save OrganisateurMetaGroupe : {}", organisateurMetaGroupeDTO);
        if (organisateurMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new organisateurMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganisateurMetaGroupeDTO result = organisateurMetaGroupeService.save(organisateurMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/organisateur-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organisateur-meta-groupes} : Updates an existing organisateurMetaGroupe.
     *
     * @param organisateurMetaGroupeDTO the organisateurMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organisateurMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the organisateurMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organisateurMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organisateur-meta-groupes")
    public ResponseEntity<OrganisateurMetaGroupeDTO> updateOrganisateurMetaGroupe(@Valid @RequestBody OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update OrganisateurMetaGroupe : {}", organisateurMetaGroupeDTO);
        if (organisateurMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganisateurMetaGroupeDTO result = organisateurMetaGroupeService.save(organisateurMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organisateurMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organisateur-meta-groupes} : get all the organisateurMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organisateurMetaGroupes in body.
     */
    @GetMapping("/organisateur-meta-groupes")
    public ResponseEntity<List<OrganisateurMetaGroupeDTO>> getAllOrganisateurMetaGroupes(OrganisateurMetaGroupeCriteria criteria) {
        log.debug("REST request to get OrganisateurMetaGroupes by criteria: {}", criteria);
        List<OrganisateurMetaGroupeDTO> entityList = organisateurMetaGroupeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /organisateur-meta-groupes/count} : count all the organisateurMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/organisateur-meta-groupes/count")
    public ResponseEntity<Long> countOrganisateurMetaGroupes(OrganisateurMetaGroupeCriteria criteria) {
        log.debug("REST request to count OrganisateurMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(organisateurMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /organisateur-meta-groupes/:id} : get the "id" organisateurMetaGroupe.
     *
     * @param id the id of the organisateurMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organisateurMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organisateur-meta-groupes/{id}")
    public ResponseEntity<OrganisateurMetaGroupeDTO> getOrganisateurMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get OrganisateurMetaGroupe : {}", id);
        Optional<OrganisateurMetaGroupeDTO> organisateurMetaGroupeDTO = organisateurMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organisateurMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /organisateur-meta-groupes/:id} : delete the "id" organisateurMetaGroupe.
     *
     * @param id the id of the organisateurMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organisateur-meta-groupes/{id}")
    public ResponseEntity<Void> deleteOrganisateurMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete OrganisateurMetaGroupe : {}", id);
        organisateurMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/organisateur-meta-groupes?query=:query} : search for the organisateurMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the organisateurMetaGroupe search.
     * @return the result of the search.
     */
    @GetMapping("/_search/organisateur-meta-groupes")
    public List<OrganisateurMetaGroupeDTO> searchOrganisateurMetaGroupes(@RequestParam String query) {
        log.debug("REST request to search OrganisateurMetaGroupes for query {}", query);
        return organisateurMetaGroupeService.search(query);
    }
}
