package com.wahisplus.in.dao.outbreak;

import com.wahisplus.in.model.OutbreakSpecies;
import com.wahisplus.wcommon.dao.GenericDaoImpl;
import com.wahisplus.wcommon.domain.species.ProductionTypeSpecies;
import com.wahisplus.wcommon.domain.species.SpeciesAndGroups;
import com.wahisplus.wcommon.util.EntityConstants;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This Dao Implementation is used to define database method details for Species
 * and Groups table.
 * 
 * @author soprasteria
 * @version 1.0
 */
@Repository
@Transactional
public class SpeciesAndGroupsDaoImpl extends GenericDaoImpl<SpeciesAndGroups, Integer> implements SpeciesAndGroupsDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public SpeciesAndGroups getSpecieByName(String specieName) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<SpeciesAndGroups> criteriaQuery = builder.createQuery(SpeciesAndGroups.class);
    Root<SpeciesAndGroups> speciesRoot = criteriaQuery.from(SpeciesAndGroups.class);
    criteriaQuery.where(
        builder.equal(builder.lower(speciesRoot.get(EntityConstants.SPECIE_GROUPS_NAME)), specieName.toLowerCase()));
    Query<SpeciesAndGroups> query = currentSession().createQuery(criteriaQuery);
    return query.uniqueResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<OutbreakSpecies> getParentSpecie(Integer specieId) {

    List<OutbreakSpecies> speciesList = new ArrayList<>();
    OutbreakSpecies outbreakSpecies = new OutbreakSpecies();
    SpeciesAndGroups speciesAndGroups = currentSession().find(SpeciesAndGroups.class, specieId);
    String name = speciesAndGroups.getName();
    Integer parentspecieid = null;
    if (speciesAndGroups.getParentSpecie() != null) {
      parentspecieid = speciesAndGroups.getParentSpecie().getSpecieId();
      outbreakSpecies.setParentspeciesId(parentspecieid);
    }
    outbreakSpecies.setSpeciesId(specieId);
    outbreakSpecies.setSpeciesName(name);
    speciesList.add(outbreakSpecies);
    while (parentspecieid != null) {
      outbreakSpecies = new OutbreakSpecies();
      outbreakSpecies.setSpeciesId(parentspecieid);
      speciesAndGroups = currentSession().find(SpeciesAndGroups.class, parentspecieid);
      name = speciesAndGroups.getName();
      if (speciesAndGroups.getParentSpecie() != null) {
        parentspecieid = speciesAndGroups.getParentSpecie().getSpecieId();
      } else {
        parentspecieid = null;
      }
      if (parentspecieid != null) {
        outbreakSpecies.setParentspeciesId(parentspecieid);
      }
      outbreakSpecies.setSpeciesName(name);
      speciesList.add(outbreakSpecies);
    }
    return speciesList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProductionTypeSpecies> getProductionOfFirstLevelSpecies(String speciesName) {
    SpeciesAndGroups species = getSpecieByName(speciesName);
    SpeciesAndGroups parentSpecie = species;
    while (parentSpecie.getParentSpecie() != null) {
      parentSpecie = currentSession().find(SpeciesAndGroups.class, parentSpecie.getParentSpecie().getSpecieId());
    }
    return parentSpecie.getSpeciesForProdType();
  }
}
