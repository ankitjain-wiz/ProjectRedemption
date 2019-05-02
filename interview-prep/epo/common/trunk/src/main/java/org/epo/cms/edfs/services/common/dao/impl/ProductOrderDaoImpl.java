package org.epo.cms.edfs.services.common.dao.impl;

import org.epo.cms.edfs.services.common.dao.ProductOrderDao;
import org.epo.cms.edfs.services.common.exceptions.DAOException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.ProductOrder;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductOrderDaoImpl extends GenericDaoImpl<ProductOrder, Integer>
    implements ProductOrderDao {
/**
 * {@inheritDoc}
 */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ProductOrder findByProductOrderId(int productOrderId) throws DAOException {
    return (ProductOrder) currentSession().createCriteria(daoType)
        .add(Restrictions.eq("productOrderId", productOrderId)).uniqueResult();
  }
/**
 * {@inheritDoc}
 */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Integer saveProductOrder(ProductOrder productOrder) throws DAOException {
    currentSession().saveOrUpdate(productOrder);
    return productOrder.getProductOrderId();
  }
}

