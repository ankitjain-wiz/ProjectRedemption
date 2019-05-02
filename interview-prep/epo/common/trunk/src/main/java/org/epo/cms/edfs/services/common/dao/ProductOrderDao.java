package org.epo.cms.edfs.services.common.dao;

import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.ProductOrder;


public interface ProductOrderDao extends GenericDao<ProductOrder, Integer> {

   ProductOrder findByProductOrderId(int productOrderId);

   Integer saveProductOrder(ProductOrder productOrder);

}
