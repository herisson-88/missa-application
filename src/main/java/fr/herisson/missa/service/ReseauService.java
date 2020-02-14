package fr.herisson.missa.service;

import fr.herisson.missa.domain.Reseau;
import fr.herisson.missa.repository.ReseauRepository;
import fr.herisson.missa.repository.search.ReseauSearchRepository;
import fr.herisson.missa.service.dto.ReseauDTO;
import fr.herisson.missa.service.mapper.ReseauMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Reseau}.
 */
@Service
@Transactional
public class ReseauService {

    private final Logger log = LoggerFactory.getLogger(ReseauService.class);

    private final ReseauRepository reseauRepository;

    private final ReseauMapper reseauMapper;

    private final ReseauSearchRepository reseauSearchRepository;

    public ReseauService(ReseauRepository reseauRepository, ReseauMapper reseauMapper, ReseauSearchRepository reseauSearchRepository) {
        this.reseauRepository = reseauRepository;
        this.reseauMapper = reseauMapper;
        this.reseauSearchRepository = reseauSearchRepository;
    }

    /**
     * Save a reseau.
     *
     * @param reseauDTO the entity to save.
     * @return the persisted entity.
     */
    public ReseauDTO save(ReseauDTO reseauDTO) {
        log.debug("Request to save Reseau : {}", reseauDTO);
        Reseau reseau = reseauMapper.toEntity(reseauDTO);
        reseau = reseauRepository.save(reseau);
        ReseauDTO result = reseauMapper.toDto(reseau);
        reseauSearchRepository.save(reseau);
        return result;
    }

    /**
     * Get all the reseaus.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReseauDTO> findAll() {
        log.debug("Request to get all Reseaus");
        return reseauRepository.findAll().stream()
            .map(reseauMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one reseau by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReseauDTO> findOne(Long id) {
        log.debug("Request to get Reseau : {}", id);
        return reseauRepository.findById(id)
            .map(reseauMapper::toDto);
    }

    /**
     * Delete the reseau by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Reseau : {}", id);
        reseauRepository.deleteById(id);
        reseauSearchRepository.deleteById(id);
    }

    /**
     * Search for the reseau corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ReseauDTO> search(String query) {
        log.debug("Request to search Reseaus for query {}", query);
        return StreamSupport
            .stream(reseauSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reseauMapper::toDto)
            .collect(Collectors.toList());
    }
}
