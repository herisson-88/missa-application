package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.EnvoiDeMailService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.EnvoiDeMailDTO;
import fr.herisson.missa.service.dto.EnvoiDeMailCriteria;
import fr.herisson.missa.service.EnvoiDeMailQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.EnvoiDeMail}.
 */
@RestController
@RequestMapping("/api")
public class EnvoiDeMailResource {

    private final Logger log = LoggerFactory.getLogger(EnvoiDeMailResource.class);

    private static final String ENTITY_NAME = "envoiDeMail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvoiDeMailService envoiDeMailService;

    private final EnvoiDeMailQueryService envoiDeMailQueryService;

    public EnvoiDeMailResource(EnvoiDeMailService envoiDeMailService, EnvoiDeMailQueryService envoiDeMailQueryService) {
        this.envoiDeMailService = envoiDeMailService;
        this.envoiDeMailQueryService = envoiDeMailQueryService;
    }

    /**
     * {@code POST  /envoi-de-mails} : Create a new envoiDeMail.
     *
     * @param envoiDeMailDTO the envoiDeMailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new envoiDeMailDTO, or with status {@code 400 (Bad Request)} if the envoiDeMail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/envoi-de-mails")
    public ResponseEntity<EnvoiDeMailDTO> createEnvoiDeMail(@Valid @RequestBody EnvoiDeMailDTO envoiDeMailDTO) throws URISyntaxException {
        log.debug("REST request to save EnvoiDeMail : {}", envoiDeMailDTO);
        if (envoiDeMailDTO.getId() != null) {
            throw new BadRequestAlertException("A new envoiDeMail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvoiDeMailDTO result = envoiDeMailService.save(envoiDeMailDTO);
        return ResponseEntity.created(new URI("/api/envoi-de-mails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /envoi-de-mails} : Updates an existing envoiDeMail.
     *
     * @param envoiDeMailDTO the envoiDeMailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated envoiDeMailDTO,
     * or with status {@code 400 (Bad Request)} if the envoiDeMailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the envoiDeMailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/envoi-de-mails")
    public ResponseEntity<EnvoiDeMailDTO> updateEnvoiDeMail(@Valid @RequestBody EnvoiDeMailDTO envoiDeMailDTO) throws URISyntaxException {
        log.debug("REST request to update EnvoiDeMail : {}", envoiDeMailDTO);
        if (envoiDeMailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnvoiDeMailDTO result = envoiDeMailService.save(envoiDeMailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, envoiDeMailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /envoi-de-mails} : get all the envoiDeMails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of envoiDeMails in body.
     */
    @GetMapping("/envoi-de-mails")
    public ResponseEntity<List<EnvoiDeMailDTO>> getAllEnvoiDeMails(EnvoiDeMailCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EnvoiDeMails by criteria: {}", criteria);
        Page<EnvoiDeMailDTO> page = envoiDeMailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /envoi-de-mails/count} : count all the envoiDeMails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/envoi-de-mails/count")
    public ResponseEntity<Long> countEnvoiDeMails(EnvoiDeMailCriteria criteria) {
        log.debug("REST request to count EnvoiDeMails by criteria: {}", criteria);
        return ResponseEntity.ok().body(envoiDeMailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /envoi-de-mails/:id} : get the "id" envoiDeMail.
     *
     * @param id the id of the envoiDeMailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the envoiDeMailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/envoi-de-mails/{id}")
    public ResponseEntity<EnvoiDeMailDTO> getEnvoiDeMail(@PathVariable Long id) {
        log.debug("REST request to get EnvoiDeMail : {}", id);
        Optional<EnvoiDeMailDTO> envoiDeMailDTO = envoiDeMailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(envoiDeMailDTO);
    }

    /**
     * {@code DELETE  /envoi-de-mails/:id} : delete the "id" envoiDeMail.
     *
     * @param id the id of the envoiDeMailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/envoi-de-mails/{id}")
    public ResponseEntity<Void> deleteEnvoiDeMail(@PathVariable Long id) {
        log.debug("REST request to delete EnvoiDeMail : {}", id);
        envoiDeMailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/envoi-de-mails?query=:query} : search for the envoiDeMail corresponding
     * to the query.
     *
     * @param query the query of the envoiDeMail search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/envoi-de-mails")
    public ResponseEntity<List<EnvoiDeMailDTO>> searchEnvoiDeMails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EnvoiDeMails for query {}", query);
        Page<EnvoiDeMailDTO> page = envoiDeMailService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
