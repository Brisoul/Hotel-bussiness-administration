package com.netcracker.hb.Dao.CRUD.hotel;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Room;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.UUID;
import lombok.extern.log4j.Log4j;

@Log4j
public class RoomsCRUD implements CRUD<Room> {

  private static final CRUD<Room> roomsCRUD = new RoomsCRUD();
  private RoomsCRUD(){}
  public static CRUD<Room> getRoomsCRUD(){
    return roomsCRUD;
  }

  private static final CRUD<Floor> floorCRUD = FloorCRUD.getFloorCRUD();


  @Override
  public Room searchObject(int roomNum) {

    File roomFolderDirectory = new File(
        "entSAVE/room_entities");
    String[] roomList = roomFolderDirectory.list();
    Object room = null;
    try {
      for (String roomFolderName : roomList) {

        FileInputStream fileRoomIn = new FileInputStream(
            "entSAVE/room_entities/"
                + roomFolderName);
        ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
        Room object = (Room) objectRoomIn.readObject();
        //если номер комнаты совпадает то передаем
        if (object.getRoomNum() == roomNum) {
          room = object;
        }
        objectRoomIn.close();


      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      return (Room) room;
    }


  }

  @Override
  public Room searchUUIDObject(UUID uuid) {

    File roomFolderDirectory = new File("entSAVE/room_entities");
    String[] roomList = roomFolderDirectory.list();
    Object room = null;
    try {
      for (String roomFolderName : roomList) {

        FileInputStream fileRoomIn = new FileInputStream(
            "entSAVE/room_entities/"
                + roomFolderName);
        ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
        Room object = (Room) objectRoomIn.readObject();
        //если ююайди комнаты совпадает то передаем
        if (object.getUuid().equals(uuid)) {
          room = object;
        }
        objectRoomIn.close();

      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      return (Room) room;
    }


  }

  @Override
  public String searchFileName(Room room) {

    File roomFolderDirectory = new File(
        "entSAVE/room_entities");
    String[] roomList = roomFolderDirectory.list();
    String fileName = null;
    try {
      for (String roomFolderName : roomList) {

        FileInputStream fileRoomIn = new FileInputStream(
            "entSAVE/room_entities/"
                + roomFolderName);
        ObjectInputStream objectRoomIn = new ObjectInputStream(fileRoomIn);
        Room object = (Room) objectRoomIn.readObject();
        //если номер комнаты совпадает то передаем
        if (object.equals(room)) {
          fileName = roomFolderName;
        }
        objectRoomIn.close();

      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      return fileName;
    }


  }

  @Override
  public void deleteObject(Room room) {

    Room object = searchObject(room.getRoomNum());

    //удаляем из хешлиста привязанного флура
    Floor floor = floorCRUD.searchUUIDObject(object.getFloorId());
    Set floorHashSet = floor.getRoomsID();
    floorHashSet.remove(room.getUuid());
    floor.setRoomsID(floorHashSet);
    floorCRUD.saveObject(floor);


    object = searchObject(room.getRoomNum());
    //todo возможно прийдется удалять еще привязанных гостей
    //удаляем файл
    File deleteFile = new File("entSAVE/room_entities/" + searchFileName(object));
    if (deleteFile != null) {
      log.info("file "+searchFileName(object)+" successfully deleted");
      deleteFile.delete();
    }
  }

  @Override
  public void saveObject(Room room) {
    int roomNum = room.getRoomNum();
    try {

      FileOutputStream fileRoomOut = new FileOutputStream(

          "entSAVE/room_entities/"
               + room.getUuid() + "-room" + roomNum + ".txt");

      ObjectOutputStream objectRoomOut = new ObjectOutputStream(fileRoomOut);
      objectRoomOut.writeObject(room);
      objectRoomOut.close();
      log.info(
          "The "+room.getUuid()+"-room"+roomNum+" was successfully written to a file");

    } catch (
        Exception ex) {
      ex.printStackTrace();
    }

  }

}
