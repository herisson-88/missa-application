package fr.herisson.missa.service;

import fr.herisson.missa.domain.PartageMetaGroupe;
import fr.herisson.missa.repository.PartageMetaGroupeRepository;
import fr.herisson.missa.repository.search.PartageMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.PartageMetaGroupeDTO;
import fr.herisson.missa.service.mapper.PartageMetaGroupeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PartageMetaGroupe}.
 */
@Service
@Transactional
public class PartageMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(PartageMetaGroupeService.class);

    private final PartageMetaGroupeRepository partageMetaGroupeRepository;

    private final PartageMetaGroupeMapper partageMetaGroupeMapper;

    private final PartageMetaGroupeSearchRepository partageMetaGroupeSearchRepository;

    public PartageMetaGroupeService(PartageMetaGroupeRepository partageMetaGroupeRepository, PartageMetaGroupeMapper partageMetaGroupeMapper, PartageMetaGroupeSearchRepository partageMetaGroupeSearchRepository) {
        this.partageMetaGroupeRepository = partageMetaGroupeRepository;
        this.partageMetaGroupeMapper = partageMetaGroupeMapper;
        this.partageMetaGroupeSearchRepository = partageMetaGroupeSearchRepository;
    }

    /**
     * Save a partageMetaGroupe.
     *
     * @param partageMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public PartageMetaGroupeDTO save(PartageMetaGroupeDTO partageMetaGroupeDTO) {
        log.debug("Request to save PartageMetaGroupe : {}", partageMetaGroupeDTO);
        PartageMetaGroupe partageMetaGroupe = partageMetaGroupeMapper.toEntity(partageMetaGroupeDTO);
        partageMetaGroupe = partageMetaGroupeRepository.save(partageMetaGroupe);
        PartageMetaGroupeDTO result = partageMetaGroupeMapper.toDto(partageMetaGroupe);
        partageMetaGroupeSearchRepository.save(partageMetaGroupe);
        return result;
    }

    /**
     * Get all the partageMetaGroupes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartageMetaGroupeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PartageMetaGroupes");
        return partageMetaGroupeRepository.findAll(pageable)
            .map(partageMetaGroupeMapper::toDto);
    }

    /**
     * Get one partageMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartageMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get PartageMetaGroupe : {}", id);
        return partageMetaGroupeRepository.findById(id)
            .map(partageMetaGroupeMapper::toDto);
    }

    /**
     * Delete the partageMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PartageMetaGroupe : {}", id);
        partageMetaGroupeRepository.deleteById(id);
        partageMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the partageMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartageMetaGroupeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PartageMetaGroupes for query {}", query);
        return partageMetaGroupeSearchRepository.search(queryStringQuery(query), pageable)
            .map(partageMetaGroupeMapper::toDto);
    }
}
