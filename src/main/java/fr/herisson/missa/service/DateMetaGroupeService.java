package fr.herisson.missa.service;

import fr.herisson.missa.domain.DateMetaGroupe;
import fr.herisson.missa.repository.DateMetaGroupeRepository;
import fr.herisson.missa.repository.search.DateMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.DateMetaGroupeDTO;
import fr.herisson.missa.service.mapper.DateMetaGroupeMapper;
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
 * Service Implementation for managing {@link DateMetaGroupe}.
 */
@Service
@Transactional
public class DateMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(DateMetaGroupeService.class);

    private final DateMetaGroupeRepository dateMetaGroupeRepository;

    private final DateMetaGroupeMapper dateMetaGroupeMapper;

    private final DateMetaGroupeSearchRepository dateMetaGroupeSearchRepository;

    public DateMetaGroupeService(DateMetaGroupeRepository dateMetaGroupeRepository, DateMetaGroupeMapper dateMetaGroupeMapper, DateMetaGroupeSearchRepository dateMetaGroupeSearchRepository) {
        this.dateMetaGroupeRepository = dateMetaGroupeRepository;
        this.dateMetaGroupeMapper = dateMetaGroupeMapper;
        this.dateMetaGroupeSearchRepository = dateMetaGroupeSearchRepository;
    }

    /**
     * Save a dateMetaGroupe.
     *
     * @param dateMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public DateMetaGroupeDTO save(DateMetaGroupeDTO dateMetaGroupeDTO) {
        log.debug("Request to save DateMetaGroupe : {}", dateMetaGroupeDTO);
        DateMetaGroupe dateMetaGroupe = dateMetaGroupeMapper.toEntity(dateMetaGroupeDTO);
        dateMetaGroupe = dateMetaGroupeRepository.save(dateMetaGroupe);
        DateMetaGroupeDTO result = dateMetaGroupeMapper.toDto(dateMetaGroupe);
        dateMetaGroupeSearchRepository.save(dateMetaGroupe);
        return result;
    }

    /**
     * Get all the dateMetaGroupes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DateMetaGroupeDTO> findAll() {
        log.debug("Request to get all DateMetaGroupes");
        return dateMetaGroupeRepository.findAll().stream()
            .map(dateMetaGroupeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dateMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DateMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get DateMetaGroupe : {}", id);
        return dateMetaGroupeRepository.findById(id)
            .map(dateMetaGroupeMapper::toDto);
    }

    /**
     * Delete the dateMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DateMetaGroupe : {}", id);
        dateMetaGroupeRepository.deleteById(id);
        dateMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the dateMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DateMetaGroupeDTO> search(String query) {
        log.debug("Request to search DateMetaGroupes for query {}", query);
        return StreamSupport
            .stream(dateMetaGroupeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(dateMetaGroupeMapper::toDto)
            .collect(Collectors.toList());
    }
}
