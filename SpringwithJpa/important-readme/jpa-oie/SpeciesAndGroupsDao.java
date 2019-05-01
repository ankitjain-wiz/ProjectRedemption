package com.wahisplus.in.dao.outbreak;

import com.wahisplus.in.model.OutbreakSpecies;
import com.wahisplus.wcommon.dao.GenericDao;
import com.wahisplus.wcommon.domain.species.ProductionTypeSpecies;
import com.wahisplus.wcommon.domain.species.SpeciesAndGroups;

import java.util.List;

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
public interface SpeciesAndGroupsDao extends GenericDao<SpeciesAndGroups, Integer> {

  /**
   * Gets the specie by name.
   *
   * @param specieName the specie name
   * @return the specie by name
   */
  SpeciesAndGroups getSpecieByName(String specieName);

  /**
   * Gets the parent specie.
   *
   * @param specieId the specie id
   * @return the parent specie
   */
  List<OutbreakSpecies> getParentSpecie(Integer specieId);

  /**
   * Gets the production of first level species.
   *
   * @param speciesName the species name
   * @return the production of first level species
   */
  List<ProductionTypeSpecies> getProductionOfFirstLevelSpecies(String speciesName);
}
