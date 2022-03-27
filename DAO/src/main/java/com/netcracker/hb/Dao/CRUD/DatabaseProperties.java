package com.netcracker.hb.Dao.CRUD;

public class DatabaseProperties {


  private static final String FLOOR_CRUD_ENTITIES_PATH = "entSAVE/floor_entities/";
  private static final String HOTEL_CRUD_ENTITIES_PATH = "entSAVE/hotel_entities/";
  private static final String ROOM_CRUD_ENTITIES_PATH = "entSAVE/room_entities/";
  private static final String CONTRACT_CRUD_ENTITIES_PATH = "entSAVE/contract_entities/";
  private static final String GUEST_CRUD_ENTITIES_PATH = "entSAVE/guest_entities/";
  private static final String EMPLOYEE_CRUD_ENTITIES_PATH = "entSAVE/employee_entities/";
  private static final String PERSONAL_CARD_CRUD_ENTITIES_PATH = "entSAVE/personalcard_entities/";

  public static String getContractCrudEntitiesPath() {
    return CONTRACT_CRUD_ENTITIES_PATH;
  }

  public static String getEmployeeCrudEntitiesPath() {
    return EMPLOYEE_CRUD_ENTITIES_PATH;
  }

  public static String getFloorCrudEntitiesPath() {
    return FLOOR_CRUD_ENTITIES_PATH;
  }

  public static String getGuestCrudEntitiesPath() {
    return GUEST_CRUD_ENTITIES_PATH;
  }

  public static String getHotelCrudEntitiesPath() {
    return HOTEL_CRUD_ENTITIES_PATH;
  }

  public static String getPersonalCardCrudEntitiesPath() {
    return PERSONAL_CARD_CRUD_ENTITIES_PATH;
  }

  public static String getRoomCrudEntitiesPath() {
    return ROOM_CRUD_ENTITIES_PATH;
  }
}
