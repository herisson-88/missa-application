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

import fr.herisson.missa.domain.OrganisateurMetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.OrganisateurMetaGroupeRepository;
import fr.herisson.missa.repository.search.OrganisateurMetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeCriteria;
import fr.herisson.missa.service.dto.OrganisateurMetaGroupeDTO;
import fr.herisson.missa.service.mapper.OrganisateurMetaGroupeMapper;

/**
 * Service for executing complex queries for {@link OrganisateurMetaGroupe} entities in the database.
 * The main input is a {@link OrganisateurMetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganisateurMetaGroupeDTO} or a {@link Page} of {@link OrganisateurMetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganisateurMetaGroupeQueryService extends QueryService<OrganisateurMetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(OrganisateurMetaGroupeQueryService.class);

    private final OrganisateurMetaGroupeRepository organisateurMetaGroupeRepository;

    private final OrganisateurMetaGroupeMapper organisateurMetaGroupeMapper;

    private final OrganisateurMetaGroupeSearchRepository organisateurMetaGroupeSearchRepository;

    public OrganisateurMetaGroupeQueryService(OrganisateurMetaGroupeRepository organisateurMetaGroupeRepository, OrganisateurMetaGroupeMapper organisateurMetaGroupeMapper, OrganisateurMetaGroupeSearchRepository organisateurMetaGroupeSearchRepository) {
        this.organisateurMetaGroupeRepository = organisateurMetaGroupeRepository;
        this.organisateurMetaGroupeMapper = organisateurMetaGroupeMapper;
        this.organisateurMetaGroupeSearchRepository = organisateurMetaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link OrganisateurMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganisateurMetaGroupeDTO> findByCriteria(OrganisateurMetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrganisateurMetaGroupe> specification = createSpecification(criteria);
        return organisateurMetaGroupeMapper.toDto(organisateurMetaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganisateurMetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganisateurMetaGroupeDTO> findByCriteria(OrganisateurMetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrganisateurMetaGroupe> specification = createSpecification(criteria);
        return organisateurMetaGroupeRepository.findAll(specification, page)
            .map(organisateurMetaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganisateurMetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrganisateurMetaGroupe> specification = createSpecification(criteria);
        return organisateurMetaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link OrganisateurMetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrganisateurMetaGroupe> createSpecification(OrganisateurMetaGroupeCriteria criteria) {
        Specification<OrganisateurMetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrganisateurMetaGroupe_.id));
            }
            if (criteria.getSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSite(), OrganisateurMetaGroupe_.site));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), OrganisateurMetaGroupe_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), OrganisateurMetaGroupe_.prenom));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), OrganisateurMetaGroupe_.telephone));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), OrganisateurMetaGroupe_.mail));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), OrganisateurMetaGroupe_.adresse));
            }
            if (criteria.getCodePostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodePostal(), OrganisateurMetaGroupe_.codePostal));
            }
            if (criteria.getVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVille(), OrganisateurMetaGroupe_.ville));
            }
            if (criteria.getGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeId(),
                    root -> root.join(OrganisateurMetaGroupe_.groupe, JoinType.LEFT).get(MetaGroupe_.id)));
            }
        }
        return specification;
    }
}
