package com.netcracker.hb.console.services.hotel;

import com.netcracker.hb.dao.crud.CRUD;
import com.netcracker.hb.dao.crud.hotel.FloorCRUD;
import com.netcracker.hb.dao.crud.hotel.HotelCRUD;
import com.netcracker.hb.dao.crud.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekserveces.ValidationService;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import com.netcracker.hb.entities.hotel.Room;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class FloorService implements Service<Floor> {

  private static Service<Floor> floorService;

  private FloorService() {
  }

  public static synchronized Service<Floor> getFloorService() {
    if (floorService == null) {
      floorService = new FloorService();
    }
    return floorService;
  }

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();
  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  private static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();
  private static final ValidationService validationService = ValidationService.getValidationService();


  private int numIterator(){
    int iterator = 0;
    for(Floor floor :floorCRUD.searchObjects()){
      if(iterator<floor.getFloorNum()){
        iterator =floor.getFloorNum();
      }
    }
    return iterator+1;
  }

  @Override
  public void addObject() {
    log.info("<Start creating floor...");

    //Создали этаж
    Hotel hotel = hotelCRUD.searchObjectNum(1);
    int floorNum = numIterator();
    Floor floor = Floor.builder()
        .floorNum(floorNum)
        .roomsID(new HashSet<>())
        .uuid(UUID.randomUUID())
        .hotelId(hotel.getUuid())
        .build();
    floorCRUD.saveObject(floor);//сохраняем

    //Связали с отелем
    Set<UUID> floorsID = hotel.getFloorsID();
    floorsID.add(floor.getUuid());
    hotel.setFloorsID(floorsID);
    hotelCRUD.saveObject(hotel);//сохраняем

    log.info("End creating floor>");

  }


  @Override
  public void changeObject(Floor object) {
    log.info("Start changing floor " + floorCRUD.searchFileName(object));
    int userChoice;
    do {
      log.info("There is nothing u can do, build new floor or delete worst");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      if (userChoice != 666) {
        log.error("Choose correct num");
      }
    } while (userChoice != 666);

  }

  @Override
  public void displayObject(Floor object) {
    log.info("_______________________");
    log.info(floorCRUD.searchFileName(object));
    log.info("Floor num : " + object.getFloorNum());
    log.info("Rooms quantity : " + object.getRoomsID().size());
    log.info("Rooms num : ");

    for (UUID uuid : object.getRoomsID()) {
      Room room = roomsCRUD.searchUUIDObject(uuid);
      log.info(room.getRoomNum());
    }
    log.info("_______________________");

  }
}
