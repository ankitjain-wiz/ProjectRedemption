package com.wahisplus.in.dao.outbreak;

import com.wahisplus.in.commons.ApplicationConstants;
import com.wahisplus.in.dto.review.EnterDto;
import com.wahisplus.in.dto.review.SendReportDto;
import com.wahisplus.wcommon.dao.GenericDaoImpl;
import com.wahisplus.wcommon.domain.area.AreaDetails;
import com.wahisplus.wcommon.domain.area.AreaScopeRoles;
import com.wahisplus.wcommon.domain.user.ContactInformation;
import com.wahisplus.wcommon.domain.user.Person;
import com.wahisplus.wcommon.domain.user.Preferences;
import com.wahisplus.wcommon.domain.user.UserRoles;
import com.wahisplus.wcommon.util.EntityConstants;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is dao repositary for report table saving the data in database wherein.
 * 
 * @author soprasteria
 * @version 1.0
 *
 */

@Repository
@Transactional
public class ReviewDaoImpl extends GenericDaoImpl<UserRoles, Long> implements ReviewDao {

  /**
   * {@inheritDoc}
   */
  @Override
  public SendReportDto populateDataforSender(int country) {

    SendReportDto sendReportModel = new SendReportDto();
    // for person table
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<UserRoles> query = builder.createQuery(UserRoles.class);
    UserRoles userRoles = fetchUserDetails(country, builder, query);
    // Find data for area and report
    CriteriaQuery<AreaDetails> queryArea = builder.createQuery(AreaDetails.class);
    Root<AreaDetails> areaRoot = queryArea.from(AreaDetails.class);
    queryArea.select(areaRoot)
        .where(builder.equal(areaRoot.get(EntityConstants.AREA_DETAILS_AREA_AREA)
            .get(EntityConstants.AREA_DETAILS_AREA_ID), country));
    Query<AreaDetails> q1 = currentSession().createQuery(queryArea);
    AreaDetails areaDetails = q1.uniqueResult();
    // For person
    if (userRoles != null) {
      Person person = fetchPersonInfo(builder, userRoles);
      if (person != null) {
        sendReportModel.setPath(person.getPreferences().get(0).getValue());
        sendReportModel.setFirstName(person.getFirstName());
        sendReportModel.setLastname(person.getLastName());
        sendReportModel.setTitle(person.getTitle());
        sendReportModel.setJobPosition(person.getJobPosition());
        List<ContactInformation> contactList = getContactInformation(person.getPersonId());
        for (ContactInformation contactInformation : contactList) {
          sendReportModel
              .setAddress(contactInformation.getAddress() + " " + contactInformation.getZipCode()
                  + " " + contactInformation.getTown() + " " + contactInformation.getAreaId());
          if (contactInformation.getContactType()
              .equalsIgnoreCase(ApplicationConstants.CONTACTPHONE)) {
            sendReportModel.setTelephone(contactInformation.getValue());
          } else if (contactInformation.getContactType()
              .equalsIgnoreCase(ApplicationConstants.CONTACTEMAIL)) {
            sendReportModel.setEmail(contactInformation.getValue());
          } else if (contactInformation.getContactType()
              .equalsIgnoreCase(ApplicationConstants.CONTACTFAX)) {
            sendReportModel.setFax(contactInformation.getValue());
          }
        }
      }
    }
    // setting the data in model class
    if (areaDetails != null) {
      sendReportModel.setArea(areaDetails.getName());
    }
    return sendReportModel;

  }

  /**
   * Fetch user details.
   *
   * @param country
   *          the country
   * @param builder
   *          the builder
   * @param query
   *          the query
   * @return the user roles
   */
  private UserRoles fetchUserDetails(int country, CriteriaBuilder builder,
      CriteriaQuery<UserRoles> query) {
    Root<UserRoles> userroot = query.from(UserRoles.class);
    Root<AreaScopeRoles> areaScopeRules = query.from(AreaScopeRoles.class);
    query.where(builder.and(
        builder.equal(areaScopeRules.get(EntityConstants.AREA_DETAILS_AREA_AREA)
            .get(EntityConstants.AREA_SCOPE_ROLES_AREA_ID), country),
        builder.equal(userroot.get(EntityConstants.ROLE).get(EntityConstants.R_NAME),
            ApplicationConstants.USERROLE)));
    query.select(userroot);
    Query<UserRoles> q = currentSession().createQuery(query);
    return q.uniqueResult();
  }

  /**
   * Fetch person info.
   *
   * @param builder
   *          the builder
   * @param userRoles
   *          the user roles
   * @return the person
   */
  private Person fetchPersonInfo(CriteriaBuilder builder, UserRoles userRoles) {
    CriteriaQuery<Person> queryPerson = builder.createQuery(Person.class);
    Root<Person> personroot = queryPerson.from(Person.class);
    Root<Preferences> preferencesroot = queryPerson.from(Preferences.class);
    queryPerson.where(builder.and(builder.equal(preferencesroot.get(EntityConstants.ACTIVE), true),
        builder.equal(personroot.get(EntityConstants.PERSON_USER_ID),
            userRoles.getUsers().getUserId()),
        builder.equal(preferencesroot.get(EntityConstants.PREFERENCE_TYPE),
            ApplicationConstants.PREFERENCETYPE)));
    queryPerson.select(personroot);
    Query<Person> qp = currentSession().createQuery(queryPerson);
    return qp.uniqueResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EnterDto populateDataforEnter(int userId) {

    EnterDto enterModel = new EnterDto();
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<Person> query = builder.createQuery(Person.class);
    Root<Person> personRoot = query.from(Person.class);
    query.select(personRoot)
        .where(builder.equal(personRoot.get(EntityConstants.PERSON_USER_ID), userId));
    Query<Person> q = currentSession().createQuery(query);

    Person person = q.uniqueResult();

    if (person != null) {
      List<Preferences> pref = person.getPreferences();
      enterModel.setEntertitle(person.getTitle());
      enterModel.setEnterFirstName(person.getFirstName());
      enterModel.setEnterLastName(person.getLastName());
      enterModel.setEnterImagePath(pref.get(0).getValue());
      List<ContactInformation> contactList = getContactInformation(person.getPersonId());
      for (ContactInformation contactInformation : contactList) {
        enterModel.setEnterAddress(contactInformation.getAddress());
        if (contactInformation.getContactType()
            .equalsIgnoreCase(ApplicationConstants.CONTACTPHONE)) {
          enterModel.setEnterContactNum(contactInformation.getValue());
        } else if (contactInformation.getContactType()
            .equalsIgnoreCase(ApplicationConstants.CONTACTEMAIL)) {
          enterModel.setEnterEmail(contactInformation.getValue());
        }
      }
    }

    return enterModel;

  }

  /**
   * This method is used to fetch the contact details on the basis of person id.
   * 
   * @param personId
   *          as persion id.
   * @return its return the contact information.
   */

  private List<ContactInformation> getContactInformation(Integer personId) {
    CriteriaBuilder querybuilder = currentSession().getCriteriaBuilder();
    CriteriaQuery<ContactInformation> conatctQuery = querybuilder
        .createQuery(ContactInformation.class);
    Root<ContactInformation> contactRoot = conatctQuery.from(ContactInformation.class);
    conatctQuery.select(contactRoot)
        .where(querybuilder.and(
            querybuilder.equal(contactRoot.get(EntityConstants.CONTACT_PERSON_ID), personId),
            contactRoot.get(EntityConstants.CONTACT_TYPE).in(ApplicationConstants.CONTACTPHONE,
                ApplicationConstants.CONTACTEMAIL)));
    Query<ContactInformation> qu = currentSession().createQuery(conatctQuery);
    return qu.getResultList();
  }

}
