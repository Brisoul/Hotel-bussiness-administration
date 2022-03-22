package com.netcracker.hb.console;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;

import com.netcracker.hb.console.services.FloorService;
import com.netcracker.hb.console.services.HotelService;
import com.netcracker.hb.console.services.RoomService;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.ValidationService;
import com.netcracker.hb.entities.hotel.Hotel;
import com.netcracker.hb.entities.hotel.Room;
import lombok.extern.log4j.Log4j;

@Log4j
public class InitializationManager {

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();
  private static final Service<Room> roomService = new RoomService();
  private static final Service<Hotel> hotelService = new HotelService();

  private static final ValidationService validationService = new ValidationService();
  private static final FloorService floorService = new FloorService();


  public void initialize() {

    if (hotelCRUD.searchObject(1) == null) {

      log.info("Hotel was not created start creating base hotel...");

      //создаем базовый пустой отель
      hotelService.addObject();

      //пихаем туда этажи
      log.info("How many floors do u wanna add to hotel?");
      int floorQuantity = validationService.correctNumberChoice();
      for (int i = 0; i < floorQuantity; i++) {
        floorService.addObject();
      }

      //запихиваем пару комнат
      log.info("Choose numbers of rooms");
      int roomQuantity = validationService.correctNumberChoice();
      for (int i = 0; i < roomQuantity; i++) {
        roomService.addObject();
      }



    } else {
      log.info("hotel is already created");
    }

  }


}
