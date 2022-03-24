package com.netcracker.hb.console.services.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.FloorCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Room;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class RoomService implements Service<Room> {

  private static final Service<Room> roomService = new RoomService();
  private RoomService(){}
  public static Service<Room> getRoomService(){
    return roomService;
  }



  Scanner in = new Scanner(System.in);
  public ValidationService validationService = ValidationService.getValidationService();
  public static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  public static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();

  private static int roomNum;
  @Override
  public void addObject() {

    log.info("<Start creating room...");

    //Выбрали роль комнаты
    Role role = validationService.validationRoleChoice();
    //Выбрали этаж
    log.info("Enter floor on which the room is located ");
    int floorNum = validationService.validationNumberChoice();
    Floor floor = floorCRUD.searchObjectNum(floorNum);
    // TODO: 21.03.2022 можно ввести несуществующий этаж

    //Выбрали номер комнаты
    roomNum +=1;

    //Создали комнату
    Room room = Room.builder()
        .floorId(floor.getUuid())
        .roomNum(roomNum)
        .role(role)
        .uuid(UUID.randomUUID())
        .build();
    roomsCRUD.saveObject(room);

    //закидываем в хешсет флура этаж
    Set roomsID = floor.getRoomsID();
    roomsID.add(room.getUuid());
    floor.setRoomsID(roomsID);
    floorCRUD.saveObject(floor);

    log.info("End creating room>");

  }

  @Override
  public void changeObject(Room object) {

  }

  @Override
  public void displayObject(Room object) {

  }

}
