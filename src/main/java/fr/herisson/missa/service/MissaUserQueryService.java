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

import fr.herisson.missa.domain.MissaUser;
import fr.herisson.missa.domain.*; // for static metamodels
import fr.herisson.missa.repository.MissaUserRepository;
import fr.herisson.missa.repository.search.MissaUserSearchRepository;
import fr.herisson.missa.service.dto.MissaUserCriteria;
import fr.herisson.missa.service.dto.MissaUserDTO;
import fr.herisson.missa.service.mapper.MissaUserMapper;

/**
 * Service for executing complex queries for {@link MissaUser} entities in the database.
 * The main input is a {@link MissaUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MissaUserDTO} or a {@link Page} of {@link MissaUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MissaUserQueryService extends QueryService<MissaUser> {

    private final Logger log = LoggerFactory.getLogger(MissaUserQueryService.class);

    private final MissaUserRepository missaUserRepository;

    private final MissaUserMapper missaUserMapper;

    private final MissaUserSearchRepository missaUserSearchRepository;

    public MissaUserQueryService(MissaUserRepository missaUserRepository, MissaUserMapper missaUserMapper, MissaUserSearchRepository missaUserSearchRepository) {
        this.missaUserRepository = missaUserRepository;
        this.missaUserMapper = missaUserMapper;
        this.missaUserSearchRepository = missaUserSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MissaUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MissaUserDTO> findByCriteria(MissaUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MissaUser> specification = createSpecification(criteria);
        return missaUserMapper.toDto(missaUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MissaUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MissaUserDTO> findByCriteria(MissaUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MissaUser> specification = createSpecification(criteria);
        return missaUserRepository.findAll(specification, page)
            .map(missaUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MissaUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MissaUser> specification = createSpecification(criteria);
        return missaUserRepository.count(specification);
    }

    /**
     * Function to convert {@link MissaUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MissaUser> createSpecification(MissaUserCriteria criteria) {
        Specification<MissaUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MissaUser_.id));
            }
            if (criteria.getCodePostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodePostal(), MissaUser_.codePostal));
            }
            if (criteria.getInitiales() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInitiales(), MissaUser_.initiales));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), MissaUser_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), MissaUser_.prenom));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), MissaUser_.mail));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), MissaUser_.etat));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(MissaUser_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getConnaisId() != null) {
                specification = specification.and(buildSpecification(criteria.getConnaisId(),
                    root -> root.join(MissaUser_.connais, JoinType.LEFT).get(Connaissance_.id)));
            }
            if (criteria.getMembreValidesId() != null) {
                specification = specification.and(buildSpecification(criteria.getMembreValidesId(),
                    root -> root.join(MissaUser_.membreValides, JoinType.LEFT).get(MembreMetaGroupe_.id)));
            }
            if (criteria.getMembresId() != null) {
                specification = specification.and(buildSpecification(criteria.getMembresId(),
                    root -> root.join(MissaUser_.membres, JoinType.LEFT).get(MembreMetaGroupe_.id)));
            }
            if (criteria.getMessagesDeMoiId() != null) {
                specification = specification.and(buildSpecification(criteria.getMessagesDeMoiId(),
                    root -> root.join(MissaUser_.messagesDeMois, JoinType.LEFT).get(MessageMetaGroupe_.id)));
            }
            if (criteria.getMailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getMailsId(),
                    root -> root.join(MissaUser_.mails, JoinType.LEFT).get(EnvoiDeMail_.id)));
            }
            if (criteria.getDemandesPartagesId() != null) {
                specification = specification.and(buildSpecification(criteria.getDemandesPartagesId(),
                    root -> root.join(MissaUser_.demandesPartages, JoinType.LEFT).get(PartageMetaGroupe_.id)));
            }
            if (criteria.getAValidePartagesId() != null) {
                specification = specification.and(buildSpecification(criteria.getAValidePartagesId(),
                    root -> root.join(MissaUser_.aValidePartages, JoinType.LEFT).get(PartageMetaGroupe_.id)));
            }
        }
        return specification;
    }
}
