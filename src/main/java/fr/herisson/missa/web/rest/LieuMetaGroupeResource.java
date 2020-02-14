package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.LieuMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.LieuMetaGroupeDTO;
import fr.herisson.missa.service.dto.LieuMetaGroupeCriteria;
import fr.herisson.missa.service.LieuMetaGroupeQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.LieuMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class LieuMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(LieuMetaGroupeResource.class);

    private static final String ENTITY_NAME = "lieuMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LieuMetaGroupeService lieuMetaGroupeService;

    private final LieuMetaGroupeQueryService lieuMetaGroupeQueryService;

    public LieuMetaGroupeResource(LieuMetaGroupeService lieuMetaGroupeService, LieuMetaGroupeQueryService lieuMetaGroupeQueryService) {
        this.lieuMetaGroupeService = lieuMetaGroupeService;
        this.lieuMetaGroupeQueryService = lieuMetaGroupeQueryService;
    }

    /**
     * {@code POST  /lieu-meta-groupes} : Create a new lieuMetaGroupe.
     *
     * @param lieuMetaGroupeDTO the lieuMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lieuMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the lieuMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lieu-meta-groupes")
    public ResponseEntity<LieuMetaGroupeDTO> createLieuMetaGroupe(@Valid @RequestBody LieuMetaGroupeDTO lieuMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save LieuMetaGroupe : {}", lieuMetaGroupeDTO);
        if (lieuMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new lieuMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LieuMetaGroupeDTO result = lieuMetaGroupeService.save(lieuMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/lieu-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lieu-meta-groupes} : Updates an existing lieuMetaGroupe.
     *
     * @param lieuMetaGroupeDTO the lieuMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lieuMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the lieuMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lieuMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lieu-meta-groupes")
    public ResponseEntity<LieuMetaGroupeDTO> updateLieuMetaGroupe(@Valid @RequestBody LieuMetaGroupeDTO lieuMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update LieuMetaGroupe : {}", lieuMetaGroupeDTO);
        if (lieuMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LieuMetaGroupeDTO result = lieuMetaGroupeService.save(lieuMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lieuMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lieu-meta-groupes} : get all the lieuMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lieuMetaGroupes in body.
     */
    @GetMapping("/lieu-meta-groupes")
    public ResponseEntity<List<LieuMetaGroupeDTO>> getAllLieuMetaGroupes(LieuMetaGroupeCriteria criteria) {
        log.debug("REST request to get LieuMetaGroupes by criteria: {}", criteria);
        List<LieuMetaGroupeDTO> entityList = lieuMetaGroupeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /lieu-meta-groupes/count} : count all the lieuMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/lieu-meta-groupes/count")
    public ResponseEntity<Long> countLieuMetaGroupes(LieuMetaGroupeCriteria criteria) {
        log.debug("REST request to count LieuMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(lieuMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /lieu-meta-groupes/:id} : get the "id" lieuMetaGroupe.
     *
     * @param id the id of the lieuMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lieuMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lieu-meta-groupes/{id}")
    public ResponseEntity<LieuMetaGroupeDTO> getLieuMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get LieuMetaGroupe : {}", id);
        Optional<LieuMetaGroupeDTO> lieuMetaGroupeDTO = lieuMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lieuMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /lieu-meta-groupes/:id} : delete the "id" lieuMetaGroupe.
     *
     * @param id the id of the lieuMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lieu-meta-groupes/{id}")
    public ResponseEntity<Void> deleteLieuMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete LieuMetaGroupe : {}", id);
        lieuMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/lieu-meta-groupes?query=:query} : search for the lieuMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the lieuMetaGroupe search.
     * @return the result of the search.
     */
    @GetMapping("/_search/lieu-meta-groupes")
    public List<LieuMetaGroupeDTO> searchLieuMetaGroupes(@RequestParam String query) {
        log.debug("REST request to search LieuMetaGroupes for query {}", query);
        return lieuMetaGroupeService.search(query);
    }
}
