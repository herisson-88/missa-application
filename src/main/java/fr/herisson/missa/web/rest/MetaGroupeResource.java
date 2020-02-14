package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.MetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.MetaGroupeDTO;
import fr.herisson.missa.service.dto.MetaGroupeCriteria;
import fr.herisson.missa.service.MetaGroupeQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.MetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class MetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(MetaGroupeResource.class);

    private static final String ENTITY_NAME = "metaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaGroupeService metaGroupeService;

    private final MetaGroupeQueryService metaGroupeQueryService;

    public MetaGroupeResource(MetaGroupeService metaGroupeService, MetaGroupeQueryService metaGroupeQueryService) {
        this.metaGroupeService = metaGroupeService;
        this.metaGroupeQueryService = metaGroupeQueryService;
    }

    /**
     * {@code POST  /meta-groupes} : Create a new metaGroupe.
     *
     * @param metaGroupeDTO the metaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaGroupeDTO, or with status {@code 400 (Bad Request)} if the metaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meta-groupes")
    public ResponseEntity<MetaGroupeDTO> createMetaGroupe(@Valid @RequestBody MetaGroupeDTO metaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save MetaGroupe : {}", metaGroupeDTO);
        if (metaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new metaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaGroupeDTO result = metaGroupeService.save(metaGroupeDTO);
        return ResponseEntity.created(new URI("/api/meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meta-groupes} : Updates an existing metaGroupe.
     *
     * @param metaGroupeDTO the metaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the metaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meta-groupes")
    public ResponseEntity<MetaGroupeDTO> updateMetaGroupe(@Valid @RequestBody MetaGroupeDTO metaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update MetaGroupe : {}", metaGroupeDTO);
        if (metaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MetaGroupeDTO result = metaGroupeService.save(metaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, metaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meta-groupes} : get all the metaGroupes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metaGroupes in body.
     */
    @GetMapping("/meta-groupes")
    public ResponseEntity<List<MetaGroupeDTO>> getAllMetaGroupes(MetaGroupeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MetaGroupes by criteria: {}", criteria);
        Page<MetaGroupeDTO> page = metaGroupeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meta-groupes/count} : count all the metaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/meta-groupes/count")
    public ResponseEntity<Long> countMetaGroupes(MetaGroupeCriteria criteria) {
        log.debug("REST request to count MetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(metaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /meta-groupes/:id} : get the "id" metaGroupe.
     *
     * @param id the id of the metaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meta-groupes/{id}")
    public ResponseEntity<MetaGroupeDTO> getMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get MetaGroupe : {}", id);
        Optional<MetaGroupeDTO> metaGroupeDTO = metaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaGroupeDTO);
    }

    /**
     * {@code DELETE  /meta-groupes/:id} : delete the "id" metaGroupe.
     *
     * @param id the id of the metaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meta-groupes/{id}")
    public ResponseEntity<Void> deleteMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete MetaGroupe : {}", id);
        metaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/meta-groupes?query=:query} : search for the metaGroupe corresponding
     * to the query.
     *
     * @param query the query of the metaGroupe search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/meta-groupes")
    public ResponseEntity<List<MetaGroupeDTO>> searchMetaGroupes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MetaGroupes for query {}", query);
        Page<MetaGroupeDTO> page = metaGroupeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
