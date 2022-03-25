package com.netcracker.hb.console;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;
import com.netcracker.hb.entities.hotel.Hotel;

public class UtilizeManager {
  private static final UtilizeManager utilizeManager = new UtilizeManager();
  private UtilizeManager(){
  }

  public static UtilizeManager getUtilizeManager() {
    return utilizeManager;
  }

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();

  public void utilize(){


    hotelCRUD.deleteObject(hotelCRUD.searchObjectNum(1));
  }

}
