package com.netcracker.hb.console;

import com.netcracker.hb.Dao.CRUD.CRUD;
import com.netcracker.hb.Dao.CRUD.Person.IEmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.CRUD.hotel.HotelCRUD;

import com.netcracker.hb.console.services.chekServeces.ValidationService;
import com.netcracker.hb.console.services.hotel.FloorService;
import com.netcracker.hb.console.services.hotel.HotelService;
import com.netcracker.hb.console.services.hotel.RoomService;
import com.netcracker.hb.console.services.Service;
import com.netcracker.hb.console.services.person.EmployeeService;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Floor;
import com.netcracker.hb.entities.hotel.Hotel;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Employee;
import lombok.extern.log4j.Log4j;

@Log4j
public class InitializationManager {

  private static InitializationManager initializationManager;
  private InitializationManager(){}

  public static InitializationManager getInitializationManager() {
    if(initializationManager ==null){
      initializationManager = new InitializationManager();
    }
    return initializationManager;
  }

  private static final CRUD<Hotel> hotelCRUD = HotelCRUD.getHotelCRUD();
  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();

  private static final Service<Room> roomService = RoomService.getRoomService();
  private static final Service<Hotel> hotelService = HotelService.getHotelService();
  private static final Service<Floor> floorService = FloorService.getFloorService();
  private static final Service<Employee> employeeService = EmployeeService.getEmployeeService();

  private static final ValidationService validationService = ValidationService.getValidationService();


  public void initializeHotel() {

    if (hotelCRUD.searchObjectNum(1) == null) {

      log.info("Hotel was not created start creating base hotel...");

      //создаем базовый пустой отель
      hotelService.addObject();

      //пихаем туда этажи
      log.info("Choose numbers of floors");
      int floorQuantity = validationService.validationNumberChoice();
      for (int i = 0; i < floorQuantity; i++) {
        floorService.addObject();
      }

      //запихиваем пару комнат
      log.info("Choose numbers of rooms");
      int roomQuantity = validationService.validationNumberChoice();
      for (int i = 0; i < roomQuantity; i++) {
        roomService.addObject();
      }

    } else {
      log.info("hotel is already created");
    }

  }

  public void initializeAdmin(){
    //проверяем есть ли минимальное количество людей
    // минимальное количество 1 гость 1 манагер 1 админ 1 работник
    //если нет создаем недостающее количество

    do {
      if (employeeCRUD.searchObjectRole(Role.ADMIN)) {
        log.info("Admin already created u can start work");
      } else {
        log.info("Admin role was not created please create Admin. Start creating");
        employeeService.addObject();
      }
    }while (!employeeCRUD.searchObjectRole(Role.ADMIN));

  }

  public void displayAll(){
    Hotel hotel = hotelCRUD.searchObjectNum(1);
    hotelService.displayObject(hotel);
  }


}
