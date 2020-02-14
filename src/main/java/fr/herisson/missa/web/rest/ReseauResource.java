package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.ReseauService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.ReseauDTO;
import fr.herisson.missa.service.dto.ReseauCriteria;
import fr.herisson.missa.service.ReseauQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link fr.herisson.missa.domain.Reseau}.
 */
@RestController
@RequestMapping("/api")
public class ReseauResource {

    private final Logger log = LoggerFactory.getLogger(ReseauResource.class);

    private static final String ENTITY_NAME = "reseau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReseauService reseauService;

    private final ReseauQueryService reseauQueryService;

    public ReseauResource(ReseauService reseauService, ReseauQueryService reseauQueryService) {
        this.reseauService = reseauService;
        this.reseauQueryService = reseauQueryService;
    }

    /**
     * {@code POST  /reseaus} : Create a new reseau.
     *
     * @param reseauDTO the reseauDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reseauDTO, or with status {@code 400 (Bad Request)} if the reseau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reseaus")
    public ResponseEntity<ReseauDTO> createReseau(@RequestBody ReseauDTO reseauDTO) throws URISyntaxException {
        log.debug("REST request to save Reseau : {}", reseauDTO);
        if (reseauDTO.getId() != null) {
            throw new BadRequestAlertException("A new reseau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReseauDTO result = reseauService.save(reseauDTO);
        return ResponseEntity.created(new URI("/api/reseaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reseaus} : Updates an existing reseau.
     *
     * @param reseauDTO the reseauDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reseauDTO,
     * or with status {@code 400 (Bad Request)} if the reseauDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reseauDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reseaus")
    public ResponseEntity<ReseauDTO> updateReseau(@RequestBody ReseauDTO reseauDTO) throws URISyntaxException {
        log.debug("REST request to update Reseau : {}", reseauDTO);
        if (reseauDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReseauDTO result = reseauService.save(reseauDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reseauDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reseaus} : get all the reseaus.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reseaus in body.
     */
    @GetMapping("/reseaus")
    public ResponseEntity<List<ReseauDTO>> getAllReseaus(ReseauCriteria criteria) {
        log.debug("REST request to get Reseaus by criteria: {}", criteria);
        List<ReseauDTO> entityList = reseauQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /reseaus/count} : count all the reseaus.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reseaus/count")
    public ResponseEntity<Long> countReseaus(ReseauCriteria criteria) {
        log.debug("REST request to count Reseaus by criteria: {}", criteria);
        return ResponseEntity.ok().body(reseauQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reseaus/:id} : get the "id" reseau.
     *
     * @param id the id of the reseauDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reseauDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reseaus/{id}")
    public ResponseEntity<ReseauDTO> getReseau(@PathVariable Long id) {
        log.debug("REST request to get Reseau : {}", id);
        Optional<ReseauDTO> reseauDTO = reseauService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reseauDTO);
    }

    /**
     * {@code DELETE  /reseaus/:id} : delete the "id" reseau.
     *
     * @param id the id of the reseauDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reseaus/{id}")
    public ResponseEntity<Void> deleteReseau(@PathVariable Long id) {
        log.debug("REST request to delete Reseau : {}", id);
        reseauService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/reseaus?query=:query} : search for the reseau corresponding
     * to the query.
     *
     * @param query the query of the reseau search.
     * @return the result of the search.
     */
    @GetMapping("/_search/reseaus")
    public List<ReseauDTO> searchReseaus(@RequestParam String query) {
        log.debug("REST request to search Reseaus for query {}", query);
        return reseauService.search(query);
    }
}
