package fr.herisson.missa.service;

import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.repository.MetaGroupeRepository;
import fr.herisson.missa.repository.search.MetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.MetaGroupeDTO;
import fr.herisson.missa.service.mapper.MetaGroupeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MetaGroupe}.
 */
@Service
@Transactional
public class MetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(MetaGroupeService.class);

    private final MetaGroupeRepository metaGroupeRepository;

    private final MetaGroupeMapper metaGroupeMapper;

    private final MetaGroupeSearchRepository metaGroupeSearchRepository;

    public MetaGroupeService(MetaGroupeRepository metaGroupeRepository, MetaGroupeMapper metaGroupeMapper, MetaGroupeSearchRepository metaGroupeSearchRepository) {
        this.metaGroupeRepository = metaGroupeRepository;
        this.metaGroupeMapper = metaGroupeMapper;
        this.metaGroupeSearchRepository = metaGroupeSearchRepository;
    }

    /**
     * Save a metaGroupe.
     *
     * @param metaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public MetaGroupeDTO save(MetaGroupeDTO metaGroupeDTO) {
        log.debug("Request to save MetaGroupe : {}", metaGroupeDTO);
        MetaGroupe metaGroupe = metaGroupeMapper.toEntity(metaGroupeDTO);
        metaGroupe = metaGroupeRepository.save(metaGroupe);
        MetaGroupeDTO result = metaGroupeMapper.toDto(metaGroupe);
        metaGroupeSearchRepository.save(metaGroupe);
        return result;
    }

    /**
     * Get all the metaGroupes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MetaGroupeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MetaGroupes");
        return metaGroupeRepository.findAll(pageable)
            .map(metaGroupeMapper::toDto);
    }

    /**
     * Get one metaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get MetaGroupe : {}", id);
        return metaGroupeRepository.findById(id)
            .map(metaGroupeMapper::toDto);
    }

    /**
     * Delete the metaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaGroupe : {}", id);
        metaGroupeRepository.deleteById(id);
        metaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the metaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MetaGroupeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MetaGroupes for query {}", query);
        return metaGroupeSearchRepository.search(queryStringQuery(query), pageable)
            .map(metaGroupeMapper::toDto);
    }
}
