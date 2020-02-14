package fr.herisson.missa.web.rest;

import fr.herisson.missa.service.MessageMetaGroupeService;
import fr.herisson.missa.web.rest.errors.BadRequestAlertException;
import fr.herisson.missa.service.dto.MessageMetaGroupeDTO;
import fr.herisson.missa.service.dto.MessageMetaGroupeCriteria;
import fr.herisson.missa.service.MessageMetaGroupeQueryService;

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
 * REST controller for managing {@link fr.herisson.missa.domain.MessageMetaGroupe}.
 */
@RestController
@RequestMapping("/api")
public class MessageMetaGroupeResource {

    private final Logger log = LoggerFactory.getLogger(MessageMetaGroupeResource.class);

    private static final String ENTITY_NAME = "messageMetaGroupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageMetaGroupeService messageMetaGroupeService;

    private final MessageMetaGroupeQueryService messageMetaGroupeQueryService;

    public MessageMetaGroupeResource(MessageMetaGroupeService messageMetaGroupeService, MessageMetaGroupeQueryService messageMetaGroupeQueryService) {
        this.messageMetaGroupeService = messageMetaGroupeService;
        this.messageMetaGroupeQueryService = messageMetaGroupeQueryService;
    }

    /**
     * {@code POST  /message-meta-groupes} : Create a new messageMetaGroupe.
     *
     * @param messageMetaGroupeDTO the messageMetaGroupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageMetaGroupeDTO, or with status {@code 400 (Bad Request)} if the messageMetaGroupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-meta-groupes")
    public ResponseEntity<MessageMetaGroupeDTO> createMessageMetaGroupe(@Valid @RequestBody MessageMetaGroupeDTO messageMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to save MessageMetaGroupe : {}", messageMetaGroupeDTO);
        if (messageMetaGroupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new messageMetaGroupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageMetaGroupeDTO result = messageMetaGroupeService.save(messageMetaGroupeDTO);
        return ResponseEntity.created(new URI("/api/message-meta-groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-meta-groupes} : Updates an existing messageMetaGroupe.
     *
     * @param messageMetaGroupeDTO the messageMetaGroupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageMetaGroupeDTO,
     * or with status {@code 400 (Bad Request)} if the messageMetaGroupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageMetaGroupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-meta-groupes")
    public ResponseEntity<MessageMetaGroupeDTO> updateMessageMetaGroupe(@Valid @RequestBody MessageMetaGroupeDTO messageMetaGroupeDTO) throws URISyntaxException {
        log.debug("REST request to update MessageMetaGroupe : {}", messageMetaGroupeDTO);
        if (messageMetaGroupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageMetaGroupeDTO result = messageMetaGroupeService.save(messageMetaGroupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageMetaGroupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-meta-groupes} : get all the messageMetaGroupes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageMetaGroupes in body.
     */
    @GetMapping("/message-meta-groupes")
    public ResponseEntity<List<MessageMetaGroupeDTO>> getAllMessageMetaGroupes(MessageMetaGroupeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MessageMetaGroupes by criteria: {}", criteria);
        Page<MessageMetaGroupeDTO> page = messageMetaGroupeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-meta-groupes/count} : count all the messageMetaGroupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/message-meta-groupes/count")
    public ResponseEntity<Long> countMessageMetaGroupes(MessageMetaGroupeCriteria criteria) {
        log.debug("REST request to count MessageMetaGroupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(messageMetaGroupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /message-meta-groupes/:id} : get the "id" messageMetaGroupe.
     *
     * @param id the id of the messageMetaGroupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageMetaGroupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-meta-groupes/{id}")
    public ResponseEntity<MessageMetaGroupeDTO> getMessageMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to get MessageMetaGroupe : {}", id);
        Optional<MessageMetaGroupeDTO> messageMetaGroupeDTO = messageMetaGroupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageMetaGroupeDTO);
    }

    /**
     * {@code DELETE  /message-meta-groupes/:id} : delete the "id" messageMetaGroupe.
     *
     * @param id the id of the messageMetaGroupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-meta-groupes/{id}")
    public ResponseEntity<Void> deleteMessageMetaGroupe(@PathVariable Long id) {
        log.debug("REST request to delete MessageMetaGroupe : {}", id);
        messageMetaGroupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/message-meta-groupes?query=:query} : search for the messageMetaGroupe corresponding
     * to the query.
     *
     * @param query the query of the messageMetaGroupe search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/message-meta-groupes")
    public ResponseEntity<List<MessageMetaGroupeDTO>> searchMessageMetaGroupes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MessageMetaGroupes for query {}", query);
        Page<MessageMetaGroupeDTO> page = messageMetaGroupeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
