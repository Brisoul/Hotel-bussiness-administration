package com.netcracker.hb.console.services;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.FloorCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.RoomsCRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Room;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class RoomService implements Service<Room> {



  Scanner in = new Scanner(System.in);
  public ValidationService validationService = new ValidationService();
  public static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();
  public static final CRUD<Room> roomsCRUD = RoomsCRUD.getRoomsCRUD();

  @Override
  public void addObject() {

    //Выбрали номер комнаты
    log.info("Enter room num");
    int roomNum = validationService.correctNumberChoice();
    //Выбрали роль комнаты
    Role role = validationService.correctRoleChoice();
    //Выбрали этаж
    log.info("Enter floor on which the room is located ");
    int floorNum = validationService.correctNumberChoice();
    Floor floor = floorCRUD.searchObject(floorNum);
    // TODO: 21.03.2022 можно ввести несуществующий этаж

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

  }

  @Override
  public void changeObject(Room object) {

  }

}
