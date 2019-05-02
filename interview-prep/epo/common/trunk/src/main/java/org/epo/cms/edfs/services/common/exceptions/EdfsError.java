package org.epo.cms.edfs.services.common.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EdfsError {
  private final long timestamp = System.currentTimeMillis();
  private final String path;
  private final String status;
  private final String message;
}
