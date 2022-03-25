package com.netcracker.hb.console.services.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.FloorCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import com.netcracker.hb.entities.hotel.Room;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class FloorService implements Service<Floor> {

  private static final Service<Floor> floorService = new FloorService();
  private FloorService(){}
  public static Service<Floor> getFloorService(){
    return floorService;
  }

  private static int floorNum;
  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();
  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  private static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();
  private static final ValidationService validationService = ValidationService.getValidationService();


  @Override
  public void addObject() {
    log.info("<Start creating floor...");

    //Создали этаж
    Hotel hotel = hotelCRUD.searchObjectNum(1);
    //todo ломается при перезапуске программы
    floorNum += 1;
    Floor floor = Floor.builder()
        .floorNum(floorNum)
        .roomsID(new HashSet<>())
        .uuid(UUID.randomUUID())
        .hotelId(hotel.getUuid())
        .build();
    floorCRUD.saveObject(floor);//сохраняем

    //Связали с отелем
    Set floorsID = hotel.getFloorsID();
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
      log.info("1.Set floor num");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("U CANT CHANGE FLOOR NUM, BUILD ANOTHER FLOOR");
          break;
        case 666:
          log.info("see u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }

  @Override
  public void displayObject(Floor object) {
    log.info("_______________________");
    log.info(floorCRUD.searchFileName(object));
    log.info("Номер этажа : " + object.getFloorNum());
    log.info("Количество комнат : " + object.getRoomsID().size());
    log.info("Номера комнат : ");

    for(UUID uuid : object.getRoomsID()){
      Room room = roomsCRUD.searchUUIDObject(uuid);
      log.info(room.getRoomNum());
    }
    log.info("_______________________");

  }
}
