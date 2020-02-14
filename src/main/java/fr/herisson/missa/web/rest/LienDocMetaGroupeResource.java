package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.LienDocMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.LienDocMetaGroupeDTO;
import fr.herisson.missa.service.dto.LienDocMetaGroupeCriteria;
import fr.herisson.missa.service.LienDocMetaGroupeQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.LienDocMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class LienDocMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(LienDocMetaGroupeResource.class);

    private static final String ENTITY_NAME = "lienDocMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LienDocMetaGroupeService lienDocMetaGroupeService;

    private final LienDocMetaGroupeQueryService lienDocMetaGroupeQueryService;

    public LienDocMetaGroupeResource(LienDocMetaGroupeService lienDocMetaGroupeService, LienDocMetaGroupeQueryService lienDocMetaGroupeQueryService) {
        this.lienDocMetaGroupeService = lienDocMetaGroupeService;
        this.lienDocMetaGroupeQueryService = lienDocMetaGroupeQueryService;
    }

    /**
     * {@code POST  /lien-doc-meta-groupes} : Create a new lienDocMetaGroupe.
     *
     * @param lienDocMetaGroupeDTO the lienDocMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lienDocMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the lienDocMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lien-doc-meta-groupes")
    public ResponseEntity<LienDocMetaGroupeDTO> createLienDocMetaGroupe(@Valid @RequestBody LienDocMetaGroupeDTO lienDocMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save LienDocMetaGroupe : {}", lienDocMetaGroupeDTO);
        if (lienDocMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new lienDocMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LienDocMetaGroupeDTO result = lienDocMetaGroupeService.save(lienDocMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/lien-doc-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lien-doc-meta-groupes} : Updates an existing lienDocMetaGroupe.
     *
     * @param lienDocMetaGroupeDTO the lienDocMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lienDocMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the lienDocMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lienDocMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lien-doc-meta-groupes")
    public ResponseEntity<LienDocMetaGroupeDTO> updateLienDocMetaGroupe(@Valid @RequestBody LienDocMetaGroupeDTO lienDocMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update LienDocMetaGroupe : {}", lienDocMetaGroupeDTO);
        if (lienDocMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LienDocMetaGroupeDTO result = lienDocMetaGroupeService.save(lienDocMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lienDocMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lien-doc-meta-groupes} : get all the lienDocMetaGroupes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lienDocMetaGroupes in body.
     */
    @GetMapping("/lien-doc-meta-groupes")
    public ResponseEntity<List<LienDocMetaGroupeDTO>> getAllLienDocMetaGroupes(LienDocMetaGroupeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LienDocMetaGroupes by criteria: {}", criteria);
        Page<LienDocMetaGroupeDTO> page = lienDocMetaGroupeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lien-doc-meta-groupes/count} : count all the lienDocMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lien-doc-meta-groupes/count")
    public ResponseEntity<Long> countLienDocMetaGroupes(LienDocMetaGroupeCriteria criteria) {
        log.debug("REST request to count LienDocMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(lienDocMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lien-doc-meta-groupes/:id} : get the "id" lienDocMetaGroupe.
     *
     * @param id the id of the lienDocMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lienDocMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lien-doc-meta-groupes/{id}")
    public ResponseEntity<LienDocMetaGroupeDTO> getLienDocMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get LienDocMetaGroupe : {}", id);
        Optional<LienDocMetaGroupeDTO> lienDocMetaGroupeDTO = lienDocMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lienDocMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /lien-doc-meta-groupes/:id} : delete the "id" lienDocMetaGroupe.
     *
     * @param id the id of the lienDocMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lien-doc-meta-groupes/{id}")
    public ResponseEntity<Void> deleteLienDocMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete LienDocMetaGroupe : {}", id);
        lienDocMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/lien-doc-meta-groupes?query=:query} : search for the lienDocMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the lienDocMetaGroupe search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/lien-doc-meta-groupes")
    public ResponseEntity<List<LienDocMetaGroupeDTO>> searchLienDocMetaGroupes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LienDocMetaGroupes for query {}", query);
        Page<LienDocMetaGroupeDTO> page = lienDocMetaGroupeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
