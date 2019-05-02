package org.epo.cms.edfs.services.common.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BICMSLookupResponse {

  @JsonProperty("ID06ReadDossier")
  private List<BICMSDossier> biCMSDossiers;

  public List<BICMSDossier> getID06ReadDossier() {
    List<BICMSDossier> newBiCMSDossiers = null;
    if(biCMSDossiers != null){
      newBiCMSDossiers = new ArrayList<>(biCMSDossiers.size());
      for(BICMSDossier bicmsDossier :biCMSDossiers){
         newBiCMSDossiers.add(bicmsDossier);
      }
    }   
    return newBiCMSDossiers;
  }

  public void setID06ReadDossier(List<BICMSDossier> iD06ReadDossier) {
    List<BICMSDossier> newID06ReadDossier = null;
    if(iD06ReadDossier != null){
      newID06ReadDossier = new ArrayList<>(iD06ReadDossier.size());
      for(BICMSDossier bicmsDossier :iD06ReadDossier){
        newID06ReadDossier.add(bicmsDossier);
     }
    }   
    this.biCMSDossiers = newID06ReadDossier;
  }

  @Override
  public String toString() {
    return "BICMSLookupResponse [biCMSDossiers=" + biCMSDossiers + "]";
  }

}
