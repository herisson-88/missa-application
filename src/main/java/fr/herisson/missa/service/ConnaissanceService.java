package fr.herisson.missa.service;

import fr.herisson.missa.domain.Connaissance;
import fr.herisson.missa.repository.ConnaissanceRepository;
import fr.herisson.missa.repository.search.ConnaissanceSearchRepository;
import fr.herisson.missa.service.dto.ConnaissanceDTO;
import fr.herisson.missa.service.mapper.ConnaissanceMapper;
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
 * Service Implementation for managing {@link Connaissance}.
 */
@Service
@Transactional
public class ConnaissanceService {

    private final Logger log = LoggerFactory.getLogger(ConnaissanceService.class);

    private final ConnaissanceRepository connaissanceRepository;

    private final ConnaissanceMapper connaissanceMapper;

    private final ConnaissanceSearchRepository connaissanceSearchRepository;

    public ConnaissanceService(ConnaissanceRepository connaissanceRepository, ConnaissanceMapper connaissanceMapper, ConnaissanceSearchRepository connaissanceSearchRepository) {
        this.connaissanceRepository = connaissanceRepository;
        this.connaissanceMapper = connaissanceMapper;
        this.connaissanceSearchRepository = connaissanceSearchRepository;
    }

    /**
     * Save a connaissance.
     *
     * @param connaissanceDTO the entity to save.
     * @return the persisted entity.
     */
    public ConnaissanceDTO save(ConnaissanceDTO connaissanceDTO) {
        log.debug("Request to save Connaissance : {}", connaissanceDTO);
        Connaissance connaissance = connaissanceMapper.toEntity(connaissanceDTO);
        connaissance = connaissanceRepository.save(connaissance);
        ConnaissanceDTO result = connaissanceMapper.toDto(connaissance);
        connaissanceSearchRepository.save(connaissance);
        return result;
    }

    /**
     * Get all the connaissances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConnaissanceDTO> findAll() {
        log.debug("Request to get all Connaissances");
        return connaissanceRepository.findAll().stream()
            .map(connaissanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one connaissance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConnaissanceDTO> findOne(Long id) {
        log.debug("Request to get Connaissance : {}", id);
        return connaissanceRepository.findById(id)
            .map(connaissanceMapper::toDto);
    }

    /**
     * Delete the connaissance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Connaissance : {}", id);
        connaissanceRepository.deleteById(id);
        connaissanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the connaissance corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConnaissanceDTO> search(String query) {
        log.debug("Request to search Connaissances for query {}", query);
        return StreamSupport
            .stream(connaissanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(connaissanceMapper::toDto)
            .collect(Collectors.toList());
    }
}
