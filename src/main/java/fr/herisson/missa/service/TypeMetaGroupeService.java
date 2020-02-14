package fr.herisson.missa.service;

import fr.herisson.missa.domain.TypeMetaGroupe;
import fr.herisson.missa.repository.TypeMetaGroupeRepository;
import fr.herisson.missa.repository.search.TypeMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.TypeMetaGroupeDTO;
import fr.herisson.missa.service.mapper.TypeMetaGroupeMapper;
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
 * Service Implementation for managing {@link TypeMetaGroupe}.
 */
@Service
@Transactional
public class TypeMetaGroupeService {

    private final Logger log = LoggerFactory.getLogger(TypeMetaGroupeService.class);

    private final TypeMetaGroupeRepository typeMetaGroupeRepository;

    private final TypeMetaGroupeMapper typeMetaGroupeMapper;

    private final TypeMetaGroupeSearchRepository typeMetaGroupeSearchRepository;

    public TypeMetaGroupeService(TypeMetaGroupeRepository typeMetaGroupeRepository, TypeMetaGroupeMapper typeMetaGroupeMapper, TypeMetaGroupeSearchRepository typeMetaGroupeSearchRepository) {
        this.typeMetaGroupeRepository = typeMetaGroupeRepository;
        this.typeMetaGroupeMapper = typeMetaGroupeMapper;
        this.typeMetaGroupeSearchRepository = typeMetaGroupeSearchRepository;
    }

    /**
     * Save a typeMetaGroupe.
     *
     * @param typeMetaGroupeDTO the entity to save.
     * @return the persisted entity.
     */
    public TypeMetaGroupeDTO save(TypeMetaGroupeDTO typeMetaGroupeDTO) {
        log.debug("Request to save TypeMetaGroupe : {}", typeMetaGroupeDTO);
        TypeMetaGroupe typeMetaGroupe = typeMetaGroupeMapper.toEntity(typeMetaGroupeDTO);
        typeMetaGroupe = typeMetaGroupeRepository.save(typeMetaGroupe);
        TypeMetaGroupeDTO result = typeMetaGroupeMapper.toDto(typeMetaGroupe);
        typeMetaGroupeSearchRepository.save(typeMetaGroupe);
        return result;
    }

    /**
     * Get all the typeMetaGroupes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TypeMetaGroupeDTO> findAll() {
        log.debug("Request to get all TypeMetaGroupes");
        return typeMetaGroupeRepository.findAll().stream()
            .map(typeMetaGroupeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one typeMetaGroupe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeMetaGroupeDTO> findOne(Long id) {
        log.debug("Request to get TypeMetaGroupe : {}", id);
        return typeMetaGroupeRepository.findById(id)
            .map(typeMetaGroupeMapper::toDto);
    }

    /**
     * Delete the typeMetaGroupe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeMetaGroupe : {}", id);
        typeMetaGroupeRepository.deleteById(id);
        typeMetaGroupeSearchRepository.deleteById(id);
    }

    /**
     * Search for the typeMetaGroupe corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TypeMetaGroupeDTO> search(String query) {
        log.debug("Request to search TypeMetaGroupes for query {}", query);
        return StreamSupport
            .stream(typeMetaGroupeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(typeMetaGroupeMapper::toDto)
            .collect(Collectors.toList());
    }
}
