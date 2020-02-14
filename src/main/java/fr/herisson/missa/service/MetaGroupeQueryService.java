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

import fr.herisson.missa.domain.MetaGroupe;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.MetaGroupeRepository;
import fr.herisson.missa.repository.search.MetaGroupeSearchRepository;
import fr.herisson.missa.service.dto.MetaGroupeCriteria;
import fr.herisson.missa.service.dto.MetaGroupeDTO;
import fr.herisson.missa.service.mapper.MetaGroupeMapper;

/**
 * Service for executing complex queries for {@link MetaGroupe} entities in the database.
 * The main input is a {@link MetaGroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MetaGroupeDTO} or a {@link Page} of {@link MetaGroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MetaGroupeQueryService extends QueryService<MetaGroupe> {

    private final Logger log = LoggerFactory.getLogger(MetaGroupeQueryService.class);

    private final MetaGroupeRepository metaGroupeRepository;

    private final MetaGroupeMapper metaGroupeMapper;

    private final MetaGroupeSearchRepository metaGroupeSearchRepository;

    public MetaGroupeQueryService(MetaGroupeRepository metaGroupeRepository, MetaGroupeMapper metaGroupeMapper, MetaGroupeSearchRepository metaGroupeSearchRepository) {
        this.metaGroupeRepository = metaGroupeRepository;
        this.metaGroupeMapper = metaGroupeMapper;
        this.metaGroupeSearchRepository = metaGroupeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MetaGroupeDTO> findByCriteria(MetaGroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MetaGroupe> specification = createSpecification(criteria);
        return metaGroupeMapper.toDto(metaGroupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MetaGroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MetaGroupeDTO> findByCriteria(MetaGroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MetaGroupe> specification = createSpecification(criteria);
        return metaGroupeRepository.findAll(specification, page)
            .map(metaGroupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MetaGroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MetaGroupe> specification = createSpecification(criteria);
        return metaGroupeRepository.count(specification);
    }

    /**
     * Function to convert {@link MetaGroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MetaGroupe> createSpecification(MetaGroupeCriteria criteria) {
        Specification<MetaGroupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MetaGroupe_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), MetaGroupe_.nom));
            }
            if (criteria.getAutoriteValidation() != null) {
                specification = specification.and(buildSpecification(criteria.getAutoriteValidation(), MetaGroupe_.autoriteValidation));
            }
            if (criteria.getMembreParent() != null) {
                specification = specification.and(buildSpecification(criteria.getMembreParent(), MetaGroupe_.membreParent));
            }
            if (criteria.getVisibilite() != null) {
                specification = specification.and(buildSpecification(criteria.getVisibilite(), MetaGroupe_.visibilite));
            }
            if (criteria.getDroitEnvoiDeMail() != null) {
                specification = specification.and(buildSpecification(criteria.getDroitEnvoiDeMail(), MetaGroupe_.droitEnvoiDeMail));
            }
            if (criteria.getDemandeDiffusionVerticale() != null) {
                specification = specification.and(buildSpecification(criteria.getDemandeDiffusionVerticale(), MetaGroupe_.demandeDiffusionVerticale));
            }
            if (criteria.getMessagerieModeree() != null) {
                specification = specification.and(buildSpecification(criteria.getMessagerieModeree(), MetaGroupe_.messagerieModeree));
            }
            if (criteria.getGroupeFinal() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeFinal(), MetaGroupe_.groupeFinal));
            }
            if (criteria.getDateValidation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateValidation(), MetaGroupe_.dateValidation));
            }
            if (criteria.getMembresId() != null) {
                specification = specification.and(buildSpecification(criteria.getMembresId(),
                    root -> root.join(MetaGroupe_.membres, JoinType.LEFT).get(MembreMetaGroupe_.id)));
            }
            if (criteria.getDatesId() != null) {
                specification = specification.and(buildSpecification(criteria.getDatesId(),
                    root -> root.join(MetaGroupe_.dates, JoinType.LEFT).get(DateMetaGroupe_.id)));
            }
            if (criteria.getLieuId() != null) {
                specification = specification.and(buildSpecification(criteria.getLieuId(),
                    root -> root.join(MetaGroupe_.lieus, JoinType.LEFT).get(LieuMetaGroupe_.id)));
            }
            if (criteria.getDocsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocsId(),
                    root -> root.join(MetaGroupe_.docs, JoinType.LEFT).get(LienDocMetaGroupe_.id)));
            }
            if (criteria.getCoordonneesOrganisateursId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoordonneesOrganisateursId(),
                    root -> root.join(MetaGroupe_.coordonneesOrganisateurs, JoinType.LEFT).get(OrganisateurMetaGroupe_.id)));
            }
            if (criteria.getSousGroupesId() != null) {
                specification = specification.and(buildSpecification(criteria.getSousGroupesId(),
                    root -> root.join(MetaGroupe_.sousGroupes, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getMessagesDuGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getMessagesDuGroupeId(),
                    root -> root.join(MetaGroupe_.messagesDuGroupes, JoinType.LEFT).get(MessageMetaGroupe_.id)));
            }
            if (criteria.getMailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getMailsId(),
                    root -> root.join(MetaGroupe_.mails, JoinType.LEFT).get(EnvoiDeMail_.id)));
            }
            if (criteria.getPartagesVersId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartagesVersId(),
                    root -> root.join(MetaGroupe_.partagesVers, JoinType.LEFT).get(PartageMetaGroupe_.id)));
            }
            if (criteria.getPartagesRecusId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartagesRecusId(),
                    root -> root.join(MetaGroupe_.partagesRecuses, JoinType.LEFT).get(PartageMetaGroupe_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(MetaGroupe_.parent, JoinType.LEFT).get(MetaGroupe_.id)));
            }
            if (criteria.getTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeId(),
                    root -> root.join(MetaGroupe_.type, JoinType.LEFT).get(TypeMetaGroupe_.id)));
            }
            if (criteria.getReseauId() != null) {
                specification = specification.and(buildSpecification(criteria.getReseauId(),
                    root -> root.join(MetaGroupe_.reseau, JoinType.LEFT).get(Reseau_.id)));
            }
            if (criteria.getMessageMailReferentId() != null) {
                specification = specification.and(buildSpecification(criteria.getMessageMailReferentId(),
                    root -> root.join(MetaGroupe_.messageMailReferents, JoinType.LEFT).get(EnvoiDeMail_.id)));
            }
        }
        return specification;
    }
}
