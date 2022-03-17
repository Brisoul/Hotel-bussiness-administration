package com.netcracker.hb.Dao.CRUD;

import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import java.io.File;
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


@Slf4j
public class FloorCRUD implements CRUD {

  private Set<Floor> floors = new HashSet<>();
  private static int floorNum = 0;

  @Override
  public void addObject() {

    //сатическая переменная будет автоматически итерировать номер создаваемого этажа независимо
    // от пользователя
    floorNum += 1;
    Floor floor = Floor.builder().floorNum(floorNum).uuid(UUID.randomUUID()).build();

    //Запись объекта флур в файлы флур
    try {
      FileOutputStream fileFloorOut = new FileOutputStream(

          "D:\\прогали\\Netcrack\\Project-Hotel-Administration\\entSAVE\\floor_entities\\"
              + "{" + floor.getUuid() + "}-floor" + floorNum + ".txt");

      ObjectOutputStream objectFloorOut = new ObjectOutputStream(fileFloorOut);
      objectFloorOut.writeObject(floor);
      objectFloorOut.close();
      log.info(
          "The {" + floor.getUuid() + "}-floor" + floorNum + " was successfully written to a file");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //Теперь нужно записать флур в хешсет отеля

    HotelCRUD hotelCRUD = new HotelCRUD();
    Hotel hotel = hotelCRUD.searchObject(1);

    //терь в найденный хотель пихаем этаж

    if (hotel.getFloors().add(floor)) {
      log.info("floor successfully adding to hotel");
    }

    //и сохраняем отель

    hotelCRUD.saveObject(hotel);

  }

  @Override
  public void displayObject() {

  }

  @Override
  public Floor searchObject(int floorNum) {

    File floorFolderDirectory = new File(
        "D:\\прогали\\Netcrack\\Project-Hotel-Administration\\entSAVE\\floor_entities\\");
    String[] floorList = floorFolderDirectory.list();
    Object floor = null;
    try {
      for (String floorFolderName : floorList) {

        FileInputStream fileFloorIn = new FileInputStream(
            "D:\\прогали\\Netcrack\\Project-Hotel-Administration\\entSAVE\\floor_entities\\"
                + floorFolderName);
        ObjectInputStream objectFloorIn = new ObjectInputStream(fileFloorIn);
        Floor object = (Floor)objectFloorIn.readObject();
        //если номер этажа совпадает то передаем
        if (object.getFloorNum() == floorNum) {
          floor = objectFloorIn.readObject();
          log.info("Floor successfully found");
        }
        objectFloorIn.close();

      }
    } catch (FileNotFoundException exception) {
      exception.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      return (Floor)floor;
    }

  }

  @Override
  public void deleteObject() {

  }

  @Override
  public void changeObject() {

  }


}
