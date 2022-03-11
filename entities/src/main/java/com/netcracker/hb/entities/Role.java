package com.netcracker.hb.entities;

import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Role implements Serializable {
  ADMIN,
  MANAGER,
  SERVICE_EMPLOYEE,
  GUEST

}
