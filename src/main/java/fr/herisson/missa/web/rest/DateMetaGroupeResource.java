package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.DateMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.DateMetaGroupeDTO;
import fr.herisson.missa.service.dto.DateMetaGroupeCriteria;
import fr.herisson.missa.service.DateMetaGroupeQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.DateMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class DateMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(DateMetaGroupeResource.class);

    private static final String ENTITY_NAME = "dateMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DateMetaGroupeService dateMetaGroupeService;

    private final DateMetaGroupeQueryService dateMetaGroupeQueryService;

    public DateMetaGroupeResource(DateMetaGroupeService dateMetaGroupeService, DateMetaGroupeQueryService dateMetaGroupeQueryService) {
        this.dateMetaGroupeService = dateMetaGroupeService;
        this.dateMetaGroupeQueryService = dateMetaGroupeQueryService;
    }

    /**
     * {@code POST  /date-meta-groupes} : Create a new dateMetaGroupe.
     *
     * @param dateMetaGroupeDTO the dateMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dateMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the dateMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/date-meta-groupes")
    public ResponseEntity<DateMetaGroupeDTO> createDateMetaGroupe(@RequestBody DateMetaGroupeDTO dateMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save DateMetaGroupe : {}", dateMetaGroupeDTO);
        if (dateMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dateMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DateMetaGroupeDTO result = dateMetaGroupeService.save(dateMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/date-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /date-meta-groupes} : Updates an existing dateMetaGroupe.
     *
     * @param dateMetaGroupeDTO the dateMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dateMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the dateMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dateMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/date-meta-groupes")
    public ResponseEntity<DateMetaGroupeDTO> updateDateMetaGroupe(@RequestBody DateMetaGroupeDTO dateMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update DateMetaGroupe : {}", dateMetaGroupeDTO);
        if (dateMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DateMetaGroupeDTO result = dateMetaGroupeService.save(dateMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dateMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /date-meta-groupes} : get all the dateMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dateMetaGroupes in body.
     */
    @GetMapping("/date-meta-groupes")
    public ResponseEntity<List<DateMetaGroupeDTO>> getAllDateMetaGroupes(DateMetaGroupeCriteria criteria) {
        log.debug("REST request to get DateMetaGroupes by criteria: {}", criteria);
        List<DateMetaGroupeDTO> entityList = dateMetaGroupeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /date-meta-groupes/count} : count all the dateMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/date-meta-groupes/count")
    public ResponseEntity<Long> countDateMetaGroupes(DateMetaGroupeCriteria criteria) {
        log.debug("REST request to count DateMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(dateMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /date-meta-groupes/:id} : get the "id" dateMetaGroupe.
     *
     * @param id the id of the dateMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dateMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/date-meta-groupes/{id}")
    public ResponseEntity<DateMetaGroupeDTO> getDateMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get DateMetaGroupe : {}", id);
        Optional<DateMetaGroupeDTO> dateMetaGroupeDTO = dateMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dateMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /date-meta-groupes/:id} : delete the "id" dateMetaGroupe.
     *
     * @param id the id of the dateMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/date-meta-groupes/{id}")
    public ResponseEntity<Void> deleteDateMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete DateMetaGroupe : {}", id);
        dateMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/date-meta-groupes?query=:query} : search for the dateMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the dateMetaGroupe search.
     * @return the result of the search.
     */
    @GetMapping("/_search/date-meta-groupes")
    public List<DateMetaGroupeDTO> searchDateMetaGroupes(@RequestParam String query) {
        log.debug("REST request to search DateMetaGroupes for query {}", query);
        return dateMetaGroupeService.search(query);
    }
}
