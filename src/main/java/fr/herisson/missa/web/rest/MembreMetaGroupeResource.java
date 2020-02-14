package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.MembreMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.MembreMetaGroupeDTO;
import fr.herisson.missa.service.dto.MembreMetaGroupeCriteria;
import fr.herisson.missa.service.MembreMetaGroupeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
 * REST controller for managing {@link fr.herisson.missa.domain.MembreMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class MembreMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(MembreMetaGroupeResource.class);

    private static final String ENTITY_NAME = "membreMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembreMetaGroupeService membreMetaGroupeService;

    private final MembreMetaGroupeQueryService membreMetaGroupeQueryService;

    public MembreMetaGroupeResource(MembreMetaGroupeService membreMetaGroupeService, MembreMetaGroupeQueryService membreMetaGroupeQueryService) {
        this.membreMetaGroupeService = membreMetaGroupeService;
        this.membreMetaGroupeQueryService = membreMetaGroupeQueryService;
    }

    /**
     * {@code POST  /membre-meta-groupes} : Create a new membreMetaGroupe.
     *
     * @param membreMetaGroupeDTO the membreMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membreMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the membreMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membre-meta-groupes")
    public ResponseEntity<MembreMetaGroupeDTO> createMembreMetaGroupe(@Valid @RequestBody MembreMetaGroupeDTO membreMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save MembreMetaGroupe : {}", membreMetaGroupeDTO);
        if (membreMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new membreMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembreMetaGroupeDTO result = membreMetaGroupeService.save(membreMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/membre-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membre-meta-groupes} : Updates an existing membreMetaGroupe.
     *
     * @param membreMetaGroupeDTO the membreMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membreMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the membreMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membreMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membre-meta-groupes")
    public ResponseEntity<MembreMetaGroupeDTO> updateMembreMetaGroupe(@Valid @RequestBody MembreMetaGroupeDTO membreMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update MembreMetaGroupe : {}", membreMetaGroupeDTO);
        if (membreMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembreMetaGroupeDTO result = membreMetaGroupeService.save(membreMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, membreMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /membre-meta-groupes} : get all the membreMetaGroupes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membreMetaGroupes in body.
     */
    @GetMapping("/membre-meta-groupes")
    public ResponseEntity<List<MembreMetaGroupeDTO>> getAllMembreMetaGroupes(MembreMetaGroupeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MembreMetaGroupes by criteria: {}", criteria);
        Page<MembreMetaGroupeDTO> page = membreMetaGroupeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membre-meta-groupes/count} : count all the membreMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/membre-meta-groupes/count")
    public ResponseEntity<Long> countMembreMetaGroupes(MembreMetaGroupeCriteria criteria) {
        log.debug("REST request to count MembreMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(membreMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /membre-meta-groupes/:id} : get the "id" membreMetaGroupe.
     *
     * @param id the id of the membreMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membreMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membre-meta-groupes/{id}")
    public ResponseEntity<MembreMetaGroupeDTO> getMembreMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get MembreMetaGroupe : {}", id);
        Optional<MembreMetaGroupeDTO> membreMetaGroupeDTO = membreMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membreMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /membre-meta-groupes/:id} : delete the "id" membreMetaGroupe.
     *
     * @param id the id of the membreMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membre-meta-groupes/{id}")
    public ResponseEntity<Void> deleteMembreMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete MembreMetaGroupe : {}", id);
        membreMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/membre-meta-groupes?query=:query} : search for the membreMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the membreMetaGroupe search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/membre-meta-groupes")
    public ResponseEntity<List<MembreMetaGroupeDTO>> searchMembreMetaGroupes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MembreMetaGroupes for query {}", query);
        Page<MembreMetaGroupeDTO> page = membreMetaGroupeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
