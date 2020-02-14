package fr.herisson.missa.service;

import fr.herisson.missa.domain.LieuMetaGroupe;
import fr.herisson.missa.repository.LieuMetaGroupeRepository;
import fr.herisson.missa.repository.search.LieuMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.LieuMetaGroupeDTO;
import fr.herisson.missa.service.mapper.LieuMetaGroupeMapper;
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
 * Service Implementation for managing {@link LieuMetaGroupe}.
 */
@Service
@Transactional
public class LieuMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(LieuMetaGroupeService.class);

    private final LieuMetaGroupeRepository lieuMetaGroupeRepository;

    private final LieuMetaGroupeMapper lieuMetaGroupeMapper;

    private final LieuMetaGroupeSearchRepository lieuMetaGroupeSearchRepository;

    public LieuMetaGroupeService(LieuMetaGroupeRepository lieuMetaGroupeRepository, LieuMetaGroupeMapper lieuMetaGroupeMapper, LieuMetaGroupeSearchRepository lieuMetaGroupeSearchRepository) {
        this.lieuMetaGroupeRepository = lieuMetaGroupeRepository;
        this.lieuMetaGroupeMapper = lieuMetaGroupeMapper;
        this.lieuMetaGroupeSearchRepository = lieuMetaGroupeSearchRepository;
    }

    /**
     * Save a lieuMetaGroupe.
     *
     * @param lieuMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public LieuMetaGroupeDTO save(LieuMetaGroupeDTO lieuMetaGroupeDTO) {
        log.debug("Request to save LieuMetaGroupe : {}", lieuMetaGroupeDTO);
        LieuMetaGroupe lieuMetaGroupe = lieuMetaGroupeMapper.toEntity(lieuMetaGroupeDTO);
        lieuMetaGroupe = lieuMetaGroupeRepository.save(lieuMetaGroupe);
        LieuMetaGroupeDTO result = lieuMetaGroupeMapper.toDto(lieuMetaGroupe);
        lieuMetaGroupeSearchRepository.save(lieuMetaGroupe);
        return result;
    }

    /**
     * Get all the lieuMetaGroupes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LieuMetaGroupeDTO> findAll() {
        log.debug("Request to get all LieuMetaGroupes");
        return lieuMetaGroupeRepository.findAll().stream()
            .map(lieuMetaGroupeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one lieuMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LieuMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get LieuMetaGroupe : {}", id);
        return lieuMetaGroupeRepository.findById(id)
            .map(lieuMetaGroupeMapper::toDto);
    }

    /**
     * Delete the lieuMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LieuMetaGroupe : {}", id);
        lieuMetaGroupeRepository.deleteById(id);
        lieuMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the lieuMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LieuMetaGroupeDTO> search(String query) {
        log.debug("Request to search LieuMetaGroupes for query {}", query);
        return StreamSupport
            .stream(lieuMetaGroupeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(lieuMetaGroupeMapper::toDto)
            .collect(Collectors.toList());
    }
}
