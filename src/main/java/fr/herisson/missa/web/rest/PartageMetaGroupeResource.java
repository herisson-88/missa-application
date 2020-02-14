package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.PartageMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.PartageMetaGroupeDTO;
import fr.herisson.missa.service.dto.PartageMetaGroupeCriteria;
import fr.herisson.missa.service.PartageMetaGroupeQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link fr.herisson.missa.domain.PartageMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class PartageMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(PartageMetaGroupeResource.class);

    private static final String ENTITY_NAME = "partageMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartageMetaGroupeService partageMetaGroupeService;

    private final PartageMetaGroupeQueryService partageMetaGroupeQueryService;

    public PartageMetaGroupeResource(PartageMetaGroupeService partageMetaGroupeService, PartageMetaGroupeQueryService partageMetaGroupeQueryService) {
        this.partageMetaGroupeService = partageMetaGroupeService;
        this.partageMetaGroupeQueryService = partageMetaGroupeQueryService;
    }

    /**
     * {@code POST  /partage-meta-groupes} : Create a new partageMetaGroupe.
     *
     * @param partageMetaGroupeDTO the partageMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partageMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the partageMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partage-meta-groupes")
    public ResponseEntity<PartageMetaGroupeDTO> createPartageMetaGroupe(@RequestBody PartageMetaGroupeDTO partageMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save PartageMetaGroupe : {}", partageMetaGroupeDTO);
        if (partageMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new partageMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartageMetaGroupeDTO result = partageMetaGroupeService.save(partageMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/partage-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partage-meta-groupes} : Updates an existing partageMetaGroupe.
     *
     * @param partageMetaGroupeDTO the partageMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partageMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the partageMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partageMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partage-meta-groupes")
    public ResponseEntity<PartageMetaGroupeDTO> updatePartageMetaGroupe(@RequestBody PartageMetaGroupeDTO partageMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update PartageMetaGroupe : {}", partageMetaGroupeDTO);
        if (partageMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartageMetaGroupeDTO result = partageMetaGroupeService.save(partageMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, partageMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /partage-meta-groupes} : get all the partageMetaGroupes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partageMetaGroupes in body.
     */
    @GetMapping("/partage-meta-groupes")
    public ResponseEntity<List<PartageMetaGroupeDTO>> getAllPartageMetaGroupes(PartageMetaGroupeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PartageMetaGroupes by criteria: {}", criteria);
        Page<PartageMetaGroupeDTO> page = partageMetaGroupeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partage-meta-groupes/count} : count all the partageMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/partage-meta-groupes/count")
    public ResponseEntity<Long> countPartageMetaGroupes(PartageMetaGroupeCriteria criteria) {
        log.debug("REST request to count PartageMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(partageMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /partage-meta-groupes/:id} : get the "id" partageMetaGroupe.
     *
     * @param id the id of the partageMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partageMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partage-meta-groupes/{id}")
    public ResponseEntity<PartageMetaGroupeDTO> getPartageMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get PartageMetaGroupe : {}", id);
        Optional<PartageMetaGroupeDTO> partageMetaGroupeDTO = partageMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partageMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /partage-meta-groupes/:id} : delete the "id" partageMetaGroupe.
     *
     * @param id the id of the partageMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partage-meta-groupes/{id}")
    public ResponseEntity<Void> deletePartageMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete PartageMetaGroupe : {}", id);
        partageMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/partage-meta-groupes?query=:query} : search for the partageMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the partageMetaGroupe search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/partage-meta-groupes")
    public ResponseEntity<List<PartageMetaGroupeDTO>> searchPartageMetaGroupes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PartageMetaGroupes for query {}", query);
        Page<PartageMetaGroupeDTO> page = partageMetaGroupeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
