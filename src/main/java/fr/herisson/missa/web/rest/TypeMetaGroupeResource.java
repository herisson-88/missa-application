package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.TypeMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.TypeMetaGroupeDTO;
import fr.herisson.missa.service.dto.TypeMetaGroupeCriteria;
import fr.herisson.missa.service.TypeMetaGroupeQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.TypeMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class TypeMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(TypeMetaGroupeResource.class);

    private static final String ENTITY_NAME = "typeMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeMetaGroupeService typeMetaGroupeService;

    private final TypeMetaGroupeQueryService typeMetaGroupeQueryService;

    public TypeMetaGroupeResource(TypeMetaGroupeService typeMetaGroupeService, TypeMetaGroupeQueryService typeMetaGroupeQueryService) {
        this.typeMetaGroupeService = typeMetaGroupeService;
        this.typeMetaGroupeQueryService = typeMetaGroupeQueryService;
    }

    /**
     * {@code POST  /type-meta-groupes} : Create a new typeMetaGroupe.
     *
     * @param typeMetaGroupeDTO the typeMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the typeMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-meta-groupes")
    public ResponseEntity<TypeMetaGroupeDTO> createTypeMetaGroupe(@Valid @RequestBody TypeMetaGroupeDTO typeMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save TypeMetaGroupe : {}", typeMetaGroupeDTO);
        if (typeMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeMetaGroupeDTO result = typeMetaGroupeService.save(typeMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/type-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-meta-groupes} : Updates an existing typeMetaGroupe.
     *
     * @param typeMetaGroupeDTO the typeMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the typeMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-meta-groupes")
    public ResponseEntity<TypeMetaGroupeDTO> updateTypeMetaGroupe(@Valid @RequestBody TypeMetaGroupeDTO typeMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update TypeMetaGroupe : {}", typeMetaGroupeDTO);
        if (typeMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeMetaGroupeDTO result = typeMetaGroupeService.save(typeMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-meta-groupes} : get all the typeMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeMetaGroupes in body.
     */
    @GetMapping("/type-meta-groupes")
    public ResponseEntity<List<TypeMetaGroupeDTO>> getAllTypeMetaGroupes(TypeMetaGroupeCriteria criteria) {
        log.debug("REST request to get TypeMetaGroupes by criteria: {}", criteria);
        List<TypeMetaGroupeDTO> entityList = typeMetaGroupeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /type-meta-groupes/count} : count all the typeMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/type-meta-groupes/count")
    public ResponseEntity<Long> countTypeMetaGroupes(TypeMetaGroupeCriteria criteria) {
        log.debug("REST request to count TypeMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /type-meta-groupes/:id} : get the "id" typeMetaGroupe.
     *
     * @param id the id of the typeMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-meta-groupes/{id}")
    public ResponseEntity<TypeMetaGroupeDTO> getTypeMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get TypeMetaGroupe : {}", id);
        Optional<TypeMetaGroupeDTO> typeMetaGroupeDTO = typeMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /type-meta-groupes/:id} : delete the "id" typeMetaGroupe.
     *
     * @param id the id of the typeMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-meta-groupes/{id}")
    public ResponseEntity<Void> deleteTypeMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete TypeMetaGroupe : {}", id);
        typeMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/type-meta-groupes?query=:query} : search for the typeMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the typeMetaGroupe search.
     * @return the result of the search.
     */
    @GetMapping("/_search/type-meta-groupes")
    public List<TypeMetaGroupeDTO> searchTypeMetaGroupes(@RequestParam String query) {
        log.debug("REST request to search TypeMetaGroupes for query {}", query);
        return typeMetaGroupeService.search(query);
    }
}
