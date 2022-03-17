package com.netcracker.hb.Dao.CRUD;

import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Room;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import java.util.Scanner;

@Slf4j
public class RoomsCRUD implements CRUD {

  private int roomNum;
  private Role role;
  private int floorId;
  private Set<Room> rooms = new HashSet<>();
  private Floor floor;

  Scanner in = new Scanner(System.in);

  @Override
  public void addObject() throws IOException, ClassNotFoundException {

    int userChoice;

    //Выбрали этаж
    log.info("Enter floor on which the room is located ");
    floorId = in.nextInt();
    FloorCRUD floorCRUD = new FloorCRUD();
    floor = floorCRUD.searchObject(floorId);

    //Выбрали номер комнаты
    log.info("Enter RoomNum");
    roomNum = in.nextInt();

    //Выбрали роль комнаты
    log.info("Enter usage(Role) of the room"
        + "1.Guest"
        + "2.Employee"
        + "3.Manager"
        + "4.Admin");
    userChoice = in.nextInt();
    switch (userChoice) {
      case 1:
        role = Role.GUEST;
        break;
      case 2:
        role = Role.SERVICE_EMPLOYEE;
        break;
      case 3:
        role = Role.MANAGER;
        break;
      case 4:
        role = Role.ADMIN;
        break;
      default:
        log.info("choose correct num");
    }

    //Создали комнату
    Room room = Room.builder()
        .floorId(floor)
        .roomNum(roomNum)
        .role(role)
        .uuid(UUID.randomUUID())
        .build();


    //Сохраняем комнату в room_entities
    try {

      FileOutputStream fileRoomOut = new FileOutputStream(

          "D:\\прогали\\Netcrack\\Project-Hotel-Administration\\entSAVE\\room_entities\\"
              + "{" + room.getUuid() + "}-room" + roomNum + ".txt");

      ObjectOutputStream objectRoomOut = new ObjectOutputStream(fileRoomOut);
      objectRoomOut.writeObject(room);
      objectRoomOut.close();
      log.info(
          "The {" + room.getUuid() + "}-room" + roomNum + " was successfully written to a file");

    } catch (Exception ex) {
      ex.printStackTrace();
    }


  }

  @Override
  public void displayObject() {

  }

  @Override
  public Room searchObject(int roomNum) {
    return null;

  }

  @Override
  public void deleteObject() {

  }

  @Override
  public void changeObject() {

  }

}
