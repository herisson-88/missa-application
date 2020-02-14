package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.MissaUserService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.MissaUserDTO;
import fr.herisson.missa.service.dto.MissaUserCriteria;
import fr.herisson.missa.service.MissaUserQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.MissaUser}.
 */
@RestController
@RequestMapping("/api")
public class MissaUserResource {

    private final Logger log = LoggerFactory.getLogger(MissaUserResource.class);

    private static final String ENTITY_NAME = "missaUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MissaUserService missaUserService;

    private final MissaUserQueryService missaUserQueryService;

    public MissaUserResource(MissaUserService missaUserService, MissaUserQueryService missaUserQueryService) {
        this.missaUserService = missaUserService;
        this.missaUserQueryService = missaUserQueryService;
    }

    /**
     * {@code POST  /missa-users} : Create a new missaUser.
     *
     * @param missaUserDTO the missaUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new missaUserDTO, or with status {@code 400 (Bad Request)} if the missaUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/missa-users")
    public ResponseEntity<MissaUserDTO> createMissaUser(@Valid @RequestBody MissaUserDTO missaUserDTO) throws URISyntaxException {
        log.debug("REST request to save MissaUser : {}", missaUserDTO);
        if (missaUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new missaUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MissaUserDTO result = missaUserService.save(missaUserDTO);
        return ResponseEntity.created(new URI("/api/missa-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /missa-users} : Updates an existing missaUser.
     *
     * @param missaUserDTO the missaUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated missaUserDTO,
     * or with status {@code 400 (Bad Request)} if the missaUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the missaUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/missa-users")
    public ResponseEntity<MissaUserDTO> updateMissaUser(@Valid @RequestBody MissaUserDTO missaUserDTO) throws URISyntaxException {
        log.debug("REST request to update MissaUser : {}", missaUserDTO);
        if (missaUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MissaUserDTO result = missaUserService.save(missaUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, missaUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /missa-users} : get all the missaUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of missaUsers in body.
     */
    @GetMapping("/missa-users")
    public ResponseEntity<List<MissaUserDTO>> getAllMissaUsers(MissaUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MissaUsers by criteria: {}", criteria);
        Page<MissaUserDTO> page = missaUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /missa-users/count} : count all the missaUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/missa-users/count")
    public ResponseEntity<Long> countMissaUsers(MissaUserCriteria criteria) {
        log.debug("REST request to count MissaUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(missaUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /missa-users/:id} : get the "id" missaUser.
     *
     * @param id the id of the missaUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the missaUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/missa-users/{id}")
    public ResponseEntity<MissaUserDTO> getMissaUser(@PathVariable Long id) {
        log.debug("REST request to get MissaUser : {}", id);
        Optional<MissaUserDTO> missaUserDTO = missaUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(missaUserDTO);
    }

    /**
     * {@code DELETE  /missa-users/:id} : delete the "id" missaUser.
     *
     * @param id the id of the missaUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/missa-users/{id}")
    public ResponseEntity<Void> deleteMissaUser(@PathVariable Long id) {
        log.debug("REST request to delete MissaUser : {}", id);
        missaUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/missa-users?query=:query} : search for the missaUser corresponding
     * to the query.
     *
     * @param query the query of the missaUser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/missa-users")
    public ResponseEntity<List<MissaUserDTO>> searchMissaUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MissaUsers for query {}", query);
        Page<MissaUserDTO> page = missaUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
