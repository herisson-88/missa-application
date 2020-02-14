package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.ConnaissanceService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.ConnaissanceDTO;
import fr.herisson.missa.service.dto.ConnaissanceCriteria;
import fr.herisson.missa.service.ConnaissanceQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.Connaissance}.
 */
@RestController
@RequestMapping("/api")
public class ConnaissanceResource {

    private final Logger log = LoggerFactory.getLogger(ConnaissanceResource.class);

    private static final String ENTITY_NAME = "connaissance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnaissanceService connaissanceService;

    private final ConnaissanceQueryService connaissanceQueryService;

    public ConnaissanceResource(ConnaissanceService connaissanceService, ConnaissanceQueryService connaissanceQueryService) {
        this.connaissanceService = connaissanceService;
        this.connaissanceQueryService = connaissanceQueryService;
    }

    /**
     * {@code POST  /connaissances} : Create a new connaissance.
     *
     * @param connaissanceDTO the connaissanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connaissanceDTO, or with status {@code 400 (Bad Request)} if the connaissance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connaissances")
    public ResponseEntity<ConnaissanceDTO> createConnaissance(@Valid @RequestBody ConnaissanceDTO connaissanceDTO) throws URISyntaxException {
        log.debug("REST request to save Connaissance : {}", connaissanceDTO);
        if (connaissanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new connaissance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnaissanceDTO result = connaissanceService.save(connaissanceDTO);
        return ResponseEntity.created(new URI("/api/connaissances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connaissances} : Updates an existing connaissance.
     *
     * @param connaissanceDTO the connaissanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connaissanceDTO,
     * or with status {@code 400 (Bad Request)} if the connaissanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connaissanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connaissances")
    public ResponseEntity<ConnaissanceDTO> updateConnaissance(@Valid @RequestBody ConnaissanceDTO connaissanceDTO) throws URISyntaxException {
        log.debug("REST request to update Connaissance : {}", connaissanceDTO);
        if (connaissanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnaissanceDTO result = connaissanceService.save(connaissanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, connaissanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connaissances} : get all the connaissances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connaissances in body.
     */
    @GetMapping("/connaissances")
    public ResponseEntity<List<ConnaissanceDTO>> getAllConnaissances(ConnaissanceCriteria criteria) {
        log.debug("REST request to get Connaissances by criteria: {}", criteria);
        List<ConnaissanceDTO> entityList = connaissanceQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /connaissances/count} : count all the connaissances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/connaissances/count")
    public ResponseEntity<Long> countConnaissances(ConnaissanceCriteria criteria) {
        log.debug("REST request to count Connaissances by criteria: {}", criteria);
        return ResponseEntity.ok().body(connaissanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /connaissances/:id} : get the "id" connaissance.
     *
     * @param id the id of the connaissanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connaissanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connaissances/{id}")
    public ResponseEntity<ConnaissanceDTO> getConnaissance(@PathVariable Long id) {
        log.debug("REST request to get Connaissance : {}", id);
        Optional<ConnaissanceDTO> connaissanceDTO = connaissanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connaissanceDTO);
    }

    /**
     * {@code DELETE  /connaissances/:id} : delete the "id" connaissance.
     *
     * @param id the id of the connaissanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connaissances/{id}")
    public ResponseEntity<Void> deleteConnaissance(@PathVariable Long id) {
        log.debug("REST request to delete Connaissance : {}", id);
        connaissanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/connaissances?query=:query} : search for the connaissance corresponding
     * to the query.
     *
     * @param query the query of the connaissance search.
     * @return the result of the search.
     */
    @GetMapping("/_search/connaissances")
    public List<ConnaissanceDTO> searchConnaissances(@RequestParam String query) {
        log.debug("REST request to search Connaissances for query {}", query);
        return connaissanceService.search(query);
    }
}
