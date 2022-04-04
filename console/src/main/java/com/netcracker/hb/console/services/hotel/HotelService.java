package com.netcracker.hb.console.services.hotel;

import com.netcracker.hb.dao.crud.CRUD;
import com.netcracker.hb.dao.crud.hotel.HotelCRUD;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.entities.hotel.Hotel;
import java.util.HashSet;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class HotelService implements Service<Hotel> {

  private static Service<Hotel> hotelService;

  private HotelService() {
  }

  public static synchronized Service<Hotel> getHotelService() {
    if(hotelService ==null){
      hotelService = new HotelService();
    }
    return hotelService;
  }

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();

  @Override
  public void addObject() {
    log.info("<Start creating hotel...");

    Hotel hotel = Hotel.builder()
        .floorsID(new HashSet<>())
        .uuid(UUID.randomUUID())
        .build();

    hotelCRUD.saveObject(hotel);

    log.info("End creating hotel>");
  }

  @Override
  public void changeObject(Hotel hotel) {
    //U cannot change object hotel u can only rebuild it
  }

  @Override
  public void displayObject(Hotel object) {
    final String BORDER = "_______________________";
    log.info(BORDER);
    log.info(hotelCRUD.searchFileName(object));
    log.info(BORDER);
    log.info("Hotel have " + object.getFloorsID().size() + "Floors");
    log.info(BORDER);
  }


}
