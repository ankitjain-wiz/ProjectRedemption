package org.epo.cms.edfs.services.common.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 

/**
 * The ServicePermission interface defines a custom annotation that can be used to specify the
 * needed permission (from authorisation module) to execute the respective service method. This
 * custom annotation can be applied on each individual method in the controller.
 */
public @interface ServicePermission {

   String value();

}
