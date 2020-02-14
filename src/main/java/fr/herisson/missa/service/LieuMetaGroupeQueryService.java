package fr.herisson.missa.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import fr.herisson.missa.domain.LieuMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.LieuMetaGroupeRepository;
import fr.herisson.missa.repository.search.LieuMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.LieuMetaGroupeCriteria;
import fr.herisson.missa.service.dto.LieuMetaGroupeDTO;
import fr.herisson.missa.service.mapper.LieuMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link LieuMetaGroupe} entities in the database.
 * The main input is a {@link LieuMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LieuMetaGroupeDTO} or a {@link Page} of {@link LieuMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LieuMetaGroupeQueryService extends QueryService<LieuMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(LieuMetaGroupeQueryService.class);

    private final LieuMetaGroupeRepository lieuMetaGroupeRepository;

    private final LieuMetaGroupeMapper lieuMetaGroupeMapper;

    private final LieuMetaGroupeSearchRepository lieuMetaGroupeSearchRepository;

    public LieuMetaGroupeQueryService(LieuMetaGroupeRepository lieuMetaGroupeRepository, LieuMetaGroupeMapper lieuMetaGroupeMapper, LieuMetaGroupeSearchRepository lieuMetaGroupeSearchRepository) {
        this.lieuMetaGroupeRepository = lieuMetaGroupeRepository;
        this.lieuMetaGroupeMapper = lieuMetaGroupeMapper;
        this.lieuMetaGroupeSearchRepository = lieuMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LieuMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LieuMetaGroupeDTO> findByCriteria(LieuMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LieuMetaGroupe> specification = createSpecification(criteria);
        return lieuMetaGroupeMapper.toDto(lieuMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LieuMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LieuMetaGroupeDTO> findByCriteria(LieuMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LieuMetaGroupe> specification = createSpecification(criteria);
        return lieuMetaGroupeRepository.findAll(specification, page)
            .map(lieuMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LieuMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LieuMetaGroupe> specification = createSpecification(criteria);
        return lieuMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link LieuMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LieuMetaGroupe> createSpecification(LieuMetaGroupeCriteria criteria) {
        Specification<LieuMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LieuMetaGroupe_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), LieuMetaGroupe_.nom));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), LieuMetaGroupe_.adresse));
            }
            if (criteria.getCodePostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodePostal(), LieuMetaGroupe_.codePostal));
            }
            if (criteria.getVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVille(), LieuMetaGroupe_.ville));
            }
            if (criteria.getLat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLat(), LieuMetaGroupe_.lat));
            }
            if (criteria.getLon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLon(), LieuMetaGroupe_.lon));
            }
            if (criteria.getDepartementGroupe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartementGroupe(), LieuMetaGroupe_.departementGroupe));
            }
            if (criteria.getGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeId(),
                    root -> root.join(LieuMetaGroupe_.groupe, JoinType.LEFT).get(MetaGroupe_.id)));
            }
        }
        return specification;
    }
}
