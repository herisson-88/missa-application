package fr.herisson.missa.service;

import fr.herisson.missa.domain.MembreMetaGroupe;
import fr.herisson.missa.repository.MembreMetaGroupeRepository;
import fr.herisson.missa.repository.search.MembreMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.MembreMetaGroupeDTO;
import fr.herisson.missa.service.mapper.MembreMetaGroupeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MembreMetaGroupe}.
 */
@Service
@Transactional
public class MembreMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(MembreMetaGroupeService.class);

    private final MembreMetaGroupeRepository membreMetaGroupeRepository;

    private final MembreMetaGroupeMapper membreMetaGroupeMapper;

    private final MembreMetaGroupeSearchRepository membreMetaGroupeSearchRepository;

    public MembreMetaGroupeService(MembreMetaGroupeRepository membreMetaGroupeRepository, MembreMetaGroupeMapper membreMetaGroupeMapper, MembreMetaGroupeSearchRepository membreMetaGroupeSearchRepository) {
        this.membreMetaGroupeRepository = membreMetaGroupeRepository;
        this.membreMetaGroupeMapper = membreMetaGroupeMapper;
        this.membreMetaGroupeSearchRepository = membreMetaGroupeSearchRepository;
    }

    /**
     * Save a membreMetaGroupe.
     *
     * @param membreMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public MembreMetaGroupeDTO save(MembreMetaGroupeDTO membreMetaGroupeDTO) {
        log.debug("Request to save MembreMetaGroupe : {}", membreMetaGroupeDTO);
        MembreMetaGroupe membreMetaGroupe = membreMetaGroupeMapper.toEntity(membreMetaGroupeDTO);
        membreMetaGroupe = membreMetaGroupeRepository.save(membreMetaGroupe);
        MembreMetaGroupeDTO result = membreMetaGroupeMapper.toDto(membreMetaGroupe);
        membreMetaGroupeSearchRepository.save(membreMetaGroupe);
        return result;
    }

    /**
     * Get all the membreMetaGroupes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MembreMetaGroupeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MembreMetaGroupes");
        return membreMetaGroupeRepository.findAll(pageable)
            .map(membreMetaGroupeMapper::toDto);
    }

    /**
     * Get one membreMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MembreMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get MembreMetaGroupe : {}", id);
        return membreMetaGroupeRepository.findById(id)
            .map(membreMetaGroupeMapper::toDto);
    }

    /**
     * Delete the membreMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MembreMetaGroupe : {}", id);
        membreMetaGroupeRepository.deleteById(id);
        membreMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the membreMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MembreMetaGroupeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MembreMetaGroupes for query {}", query);
        return membreMetaGroupeSearchRepository.search(queryStringQuery(query), pageable)
            .map(membreMetaGroupeMapper::toDto);
    }
}
