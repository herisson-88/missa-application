package fr.herisson.missa.service;

import fr.herisson.missa.domain.LienDocMetaGroupe;
import fr.herisson.missa.repository.LienDocMetaGroupeRepository;
import fr.herisson.missa.repository.search.LienDocMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.LienDocMetaGroupeDTO;
import fr.herisson.missa.service.mapper.LienDocMetaGroupeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LienDocMetaGroupe}.
 */
@Service
@Transactional
public class LienDocMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(LienDocMetaGroupeService.class);

    private final LienDocMetaGroupeRepository lienDocMetaGroupeRepository;

    private final LienDocMetaGroupeMapper lienDocMetaGroupeMapper;

    private final LienDocMetaGroupeSearchRepository lienDocMetaGroupeSearchRepository;

    public LienDocMetaGroupeService(LienDocMetaGroupeRepository lienDocMetaGroupeRepository, LienDocMetaGroupeMapper lienDocMetaGroupeMapper, LienDocMetaGroupeSearchRepository lienDocMetaGroupeSearchRepository) {
        this.lienDocMetaGroupeRepository = lienDocMetaGroupeRepository;
        this.lienDocMetaGroupeMapper = lienDocMetaGroupeMapper;
        this.lienDocMetaGroupeSearchRepository = lienDocMetaGroupeSearchRepository;
    }

    /**
     * Save a lienDocMetaGroupe.
     *
     * @param lienDocMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public LienDocMetaGroupeDTO save(LienDocMetaGroupeDTO lienDocMetaGroupeDTO) {
        log.debug("Request to save LienDocMetaGroupe : {}", lienDocMetaGroupeDTO);
        LienDocMetaGroupe lienDocMetaGroupe = lienDocMetaGroupeMapper.toEntity(lienDocMetaGroupeDTO);
        lienDocMetaGroupe = lienDocMetaGroupeRepository.save(lienDocMetaGroupe);
        LienDocMetaGroupeDTO result = lienDocMetaGroupeMapper.toDto(lienDocMetaGroupe);
        lienDocMetaGroupeSearchRepository.save(lienDocMetaGroupe);
        return result;
    }

    /**
     * Get all the lienDocMetaGroupes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LienDocMetaGroupeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LienDocMetaGroupes");
        return lienDocMetaGroupeRepository.findAll(pageable)
            .map(lienDocMetaGroupeMapper::toDto);
    }

    /**
     * Get one lienDocMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LienDocMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get LienDocMetaGroupe : {}", id);
        return lienDocMetaGroupeRepository.findById(id)
            .map(lienDocMetaGroupeMapper::toDto);
    }

    /**
     * Delete the lienDocMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LienDocMetaGroupe : {}", id);
        lienDocMetaGroupeRepository.deleteById(id);
        lienDocMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the lienDocMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LienDocMetaGroupeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LienDocMetaGroupes for query {}", query);
        return lienDocMetaGroupeSearchRepository.search(queryStringQuery(query), pageable)
            .map(lienDocMetaGroupeMapper::toDto);
    }
}
