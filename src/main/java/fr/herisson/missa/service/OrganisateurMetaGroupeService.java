package fr.herisson.missa.service;

import fr.herisson.missa.domain.OrganisateurMetaGroupe;
import fr.herisson.missa.repository.OrganisateurMetaGroupeRepository;
import fr.herisson.missa.repository.search.OrganisateurMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeDTO;
import fr.herisson.missa.service.mapper.OrganisateurMetaGroupeMapper;
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
 * Service Implementation for managing {@link OrganisateurMetaGroupe}.
 */
@Service
@Transactional
public class OrganisateurMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(OrganisateurMetaGroupeService.class);

    private final OrganisateurMetaGroupeRepository organisateurMetaGroupeRepository;

    private final OrganisateurMetaGroupeMapper organisateurMetaGroupeMapper;

    private final OrganisateurMetaGroupeSearchRepository organisateurMetaGroupeSearchRepository;

    public OrganisateurMetaGroupeService(OrganisateurMetaGroupeRepository organisateurMetaGroupeRepository, OrganisateurMetaGroupeMapper organisateurMetaGroupeMapper, OrganisateurMetaGroupeSearchRepository organisateurMetaGroupeSearchRepository) {
        this.organisateurMetaGroupeRepository = organisateurMetaGroupeRepository;
        this.organisateurMetaGroupeMapper = organisateurMetaGroupeMapper;
        this.organisateurMetaGroupeSearchRepository = organisateurMetaGroupeSearchRepository;
    }

    /**
     * Save a organisateurMetaGroupe.
     *
     * @param organisateurMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganisateurMetaGroupeDTO save(OrganisateurMetaGroupeDTO organisateurMetaGroupeDTO) {
        log.debug("Request to save OrganisateurMetaGroupe : {}", organisateurMetaGroupeDTO);
        OrganisateurMetaGroupe organisateurMetaGroupe = organisateurMetaGroupeMapper.toEntity(organisateurMetaGroupeDTO);
        organisateurMetaGroupe = organisateurMetaGroupeRepository.save(organisateurMetaGroupe);
        OrganisateurMetaGroupeDTO result = organisateurMetaGroupeMapper.toDto(organisateurMetaGroupe);
        organisateurMetaGroupeSearchRepository.save(organisateurMetaGroupe);
        return result;
    }

    /**
     * Get all the organisateurMetaGroupes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrganisateurMetaGroupeDTO> findAll() {
        log.debug("Request to get all OrganisateurMetaGroupes");
        return organisateurMetaGroupeRepository.findAll().stream()
            .map(organisateurMetaGroupeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one organisateurMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganisateurMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get OrganisateurMetaGroupe : {}", id);
        return organisateurMetaGroupeRepository.findById(id)
            .map(organisateurMetaGroupeMapper::toDto);
    }

    /**
     * Delete the organisateurMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrganisateurMetaGroupe : {}", id);
        organisateurMetaGroupeRepository.deleteById(id);
        organisateurMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the organisateurMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrganisateurMetaGroupeDTO> search(String query) {
        log.debug("Request to search OrganisateurMetaGroupes for query {}", query);
        return StreamSupport
            .stream(organisateurMetaGroupeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(organisateurMetaGroupeMapper::toDto)
            .collect(Collectors.toList());
    }
}
