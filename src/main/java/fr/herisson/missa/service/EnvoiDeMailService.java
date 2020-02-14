package fr.herisson.missa.service;

import fr.herisson.missa.domain.EnvoiDeMail;
import fr.herisson.missa.repository.EnvoiDeMailRepository;
import fr.herisson.missa.repository.search.EnvoiDeMailSearchRepository;
import fr.herisson.missa.service.dto.EnvoiDeMailDTO;
import fr.herisson.missa.service.mapper.EnvoiDeMailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EnvoiDeMail}.
 */
@Service
@Transactional
public class EnvoiDeMailService {

    private final Logger log = LoggerFactory.getLogger(EnvoiDeMailService.class);

    private final EnvoiDeMailRepository envoiDeMailRepository;

    private final EnvoiDeMailMapper envoiDeMailMapper;

    private final EnvoiDeMailSearchRepository envoiDeMailSearchRepository;

    public EnvoiDeMailService(EnvoiDeMailRepository envoiDeMailRepository, EnvoiDeMailMapper envoiDeMailMapper, EnvoiDeMailSearchRepository envoiDeMailSearchRepository) {
        this.envoiDeMailRepository = envoiDeMailRepository;
        this.envoiDeMailMapper = envoiDeMailMapper;
        this.envoiDeMailSearchRepository = envoiDeMailSearchRepository;
    }

    /**
     * Save a envoiDeMail.
     *
     * @param envoiDeMailDTO the entity to save.
     * @return the persisted entity.
     */
    public EnvoiDeMailDTO save(EnvoiDeMailDTO envoiDeMailDTO) {
        log.debug("Request to save EnvoiDeMail : {}", envoiDeMailDTO);
        EnvoiDeMail envoiDeMail = envoiDeMailMapper.toEntity(envoiDeMailDTO);
        envoiDeMail = envoiDeMailRepository.save(envoiDeMail);
        EnvoiDeMailDTO result = envoiDeMailMapper.toDto(envoiDeMail);
        envoiDeMailSearchRepository.save(envoiDeMail);
        return result;
    }

    /**
     * Get all the envoiDeMails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnvoiDeMailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EnvoiDeMails");
        return envoiDeMailRepository.findAll(pageable)
            .map(envoiDeMailMapper::toDto);
    }

    /**
     * Get all the envoiDeMails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EnvoiDeMailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return envoiDeMailRepository.findAllWithEagerRelationships(pageable).map(envoiDeMailMapper::toDto);
    }

    /**
     * Get one envoiDeMail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnvoiDeMailDTO> findOne(Long id) {
        log.debug("Request to get EnvoiDeMail : {}", id);
        return envoiDeMailRepository.findOneWithEagerRelationships(id)
            .map(envoiDeMailMapper::toDto);
    }

    /**
     * Delete the envoiDeMail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnvoiDeMail : {}", id);
        envoiDeMailRepository.deleteById(id);
        envoiDeMailSearchRepository.deleteById(id);
    }

    /**
     * Search for the envoiDeMail corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnvoiDeMailDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EnvoiDeMails for query {}", query);
        return envoiDeMailSearchRepository.search(queryStringQuery(query), pageable)
            .map(envoiDeMailMapper::toDto);
    }
}
