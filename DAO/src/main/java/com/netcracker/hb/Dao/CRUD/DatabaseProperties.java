package com.netcracker.hb.Dao.CRUD;

import java.io.File;
import lombok.extern.log4j.Log4j;


@Log4j
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

  public void directoryInit(){
    File folderFloor = new File(FLOOR_CRUD_ENTITIES_PATH);
    File folderHotel = new File(HOTEL_CRUD_ENTITIES_PATH);
    File folderRoom = new File(ROOM_CRUD_ENTITIES_PATH);
    File folderContract = new File(CONTRACT_CRUD_ENTITIES_PATH);
    File folderGuest = new File(GUEST_CRUD_ENTITIES_PATH);
    File folderEmployee = new File(EMPLOYEE_CRUD_ENTITIES_PATH);
    File folderPersonalCard = new File(PERSONAL_CARD_CRUD_ENTITIES_PATH);

    if (!folderFloor.exists() && folderFloor.mkdirs()) {
      log.info("Folder floor folder was created");
    }
    if(!folderHotel.exists() && folderHotel.mkdirs()){
      log.info("Hotel folder was created");
    }
    if(!folderContract.exists() && folderContract.mkdirs()){
      log.info("Hotel Contract folder was created");
    }
    if(!folderEmployee.exists() && folderEmployee.mkdirs()){
      log.info("Hotel Employee folder was created");
    }
    if(!folderGuest.exists() && folderHotel.mkdirs()){
      log.info("Hotel floor folder was created");
    }
    if(!folderRoom.exists() && folderRoom.mkdirs()){
      log.info("Hotel room folder was created");
    }
    if(!folderPersonalCard.exists() && folderPersonalCard.mkdirs()){
      log.info("Hotel personal card folder was created");
    }
  }


}
