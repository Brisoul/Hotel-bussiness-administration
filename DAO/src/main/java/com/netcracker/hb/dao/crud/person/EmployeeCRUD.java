package com.netcracker.hb.dao.crud.person;


import com.netcracker.hb.dao.crud.CRUD;
import com.netcracker.hb.dao.crud.DatabaseProperties;
import com.netcracker.hb.dao.crud.hotel.RoomsCRUD;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.hotel.Room;
import com.netcracker.hb.entities.persons.Contract;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.PersonalCard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class EmployeeCRUD implements IEmployeeCRUD<Employee> {


  private static IEmployeeCRUD<Employee> employeeCRUD;

  private EmployeeCRUD() {
  }

  public static synchronized IEmployeeCRUD<Employee> getIEmployeeCRUD() {
    if (employeeCRUD == null) {
      employeeCRUD = new EmployeeCRUD();
    }
    return employeeCRUD;
  }

  private static final String START = "<Start searching employee....";
  private static final String END = "Employee was found>";
  private static final String ERROR = "Employee was not found>";
  private static final String EXCEPTION_ERROR = "Something bad with employee try catch";


  private static final CRUD<Room> roomCRUD = RoomsCRUD.getRoomsCRUD();
  private static final IGuestCRUD<PersonalCard> personalCardCRUD = PersonalCardCRUD.getPersonalCardCRUD();
  private static final IContractCRUD<Contract> contractCRUD = ContractCRUD.getContractCRUD();

  @Override
  public Employee searchObjectNameSurname(String name, String surname) {
    log.info(START);

    File employeeFolderDirectory = new File(DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH);
    String[] employeeList = employeeFolderDirectory.list();
    Employee employee = null;
    if (employeeList == null) {
      return null;
    }
    for (String employeeFolderName : employeeList) {
      try (
          FileInputStream fileEmployeeIn = new FileInputStream(
              DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH
                  + employeeFolderName);
          ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn)) {
        Employee object = (Employee) objectEmployeeIn.readObject();
        if (object.getSurname().equals(surname) && object.getName().equals(name)) {
          log.info(END);
          employee = object;
        }

      } catch (IOException | ClassNotFoundException exception) {
        log.error(EXCEPTION_ERROR, exception);
      }
    }
    if (employee == null) {
      log.error(ERROR);
    }
    return employee;
  }

  @Override
  public Employee searchObjectLogIn(String username, String password) {
    log.info(START);

    File employeeFolderDirectory = new File(DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH);
    String[] employeeList = employeeFolderDirectory.list();
    Employee employee = null;
    if (employeeList == null) {
      return null;
    }
    for (String employeeFolderName : employeeList) {
      try (
          FileInputStream fileEmployeeIn = new FileInputStream(
              DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH
                  + employeeFolderName);
          ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn)
      ) {
        Employee object = (Employee) objectEmployeeIn.readObject();
        if (object.getUsername().equals(username) && object.getPassword().equals(password)) {
          log.info(END);
          employee = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        log.error(EXCEPTION_ERROR, exception);
      }
    }
    if (employee == null) {
      log.error(ERROR);
    }
    return employee;
  }

  @Override
  public boolean searchObjectRole(Role role) {
    log.info(START);

    File employeeFolderDirectory = new File(DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH);
    String[] employeeList = employeeFolderDirectory.list();
    boolean searchStatus = false;
    if (employeeList == null) {
      return false;
    }
    for (String employeeFolderName : employeeList) {
      try (
          FileInputStream fileEmployeeIn = new FileInputStream(
              DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH
                  + employeeFolderName);
          ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
      ) {
        Employee object = (Employee) objectEmployeeIn.readObject();
        if (object.getRole() == role) {
          log.info(END);
          searchStatus = true;
        }

      } catch (ClassNotFoundException | IOException exception) {
        log.error(EXCEPTION_ERROR, exception);
      }
    }
    if (!searchStatus) {
      log.error(ERROR);
    }
    return searchStatus;
  }

  @Override
  public List<Employee> searchObjects() {
    log.info(START);
    File employeeFolderDirectory = new File(DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH);
    String[] employeeList = employeeFolderDirectory.list();
    List<Employee> employers = new ArrayList<>();
    if (employeeList == null) {
      return employers;
    }
    for (String employeeFolderName : employeeList) {
      try (
          FileInputStream fileEmployeeIn = new FileInputStream(
              DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH
                  + employeeFolderName);
          ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
      ) {
        Employee object = (Employee) objectEmployeeIn.readObject();
        employers.add(object);

      } catch (IOException | ClassNotFoundException exception) {
        log.error(EXCEPTION_ERROR, exception);
      }
    }
    if (employers.isEmpty()) {
      log.error(ERROR);
    }
    return employers;
  }

  @Override
  public Employee searchObjectNum(int roomNum) {
    log.info(START);
    //Ищем ююайдиник работников которые есть в данной комнате
    Room room = roomCRUD.searchObjectNum(roomNum);
    UUID uuid = null;
    for (UUID uuidStrange : room.getEmployeeID()) {
      //Если в комнате несколько работников оно вернет любого из них
      uuid = uuidStrange;
    }
    //По ююайдишнику находим и самого работника
    Employee employee = searchUUIDObject(uuid);

    if (employee == null || uuid == null) {
      log.error(ERROR);
    } else {
      log.info(END);
    }
    return employee;
  }

  @Override
  public Employee searchUUIDObject(UUID uuid) {

    log.info(START);
    if (uuid == null) {
      return null;
    }
    File employeeFolderDirectory = new File(DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH);
    String[] employeeList = employeeFolderDirectory.list();
    Employee employee = null;
    if (employeeList == null) {
      return null;
    }
    for (String employeeFolderName : employeeList) {
      try (
          FileInputStream fileEmployeeIn = new FileInputStream(
              DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH
                  + employeeFolderName);
          ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
      ) {
        Employee object = (Employee) objectEmployeeIn.readObject();
        //
        if (object != null && object.getUuid().equals(uuid)) {
          log.info(END);
          employee = object;

        }
      } catch (IOException | ClassNotFoundException exception) {
        log.error(EXCEPTION_ERROR, exception);
      }
    }
    if (employee == null) {
      log.error(ERROR);
    }

    return employee;
  }

  @Override
  public String searchFileName(Employee object) {
    log.info(START);
    File employeeFolderDirectory = new File(DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH);
    String[] employeeList = employeeFolderDirectory.list();
    String fileName = null;
    if (employeeList == null) {
      return null;
    }
    for (String employeeFolderName : employeeList) {
      try (
          FileInputStream fileEmployeeIn = new FileInputStream(
              DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH
                  + employeeFolderName);
          ObjectInputStream objectEmployeeIn = new ObjectInputStream(fileEmployeeIn);
      ) {
        Employee employee = (Employee) objectEmployeeIn.readObject();
        //
        if (object.equals(employee)) {
          log.info(END);
          fileName = employeeFolderName;
        }
      } catch (ClassNotFoundException | IOException exception) {
        log.error(EXCEPTION_ERROR, exception);
      }
    }
    if (fileName == null) {
      log.error(ERROR);
    }
    return fileName;
  }

  @Override
  public void deleteObject(Employee object) {
    //удаляем персональную карту

    if (object.getCardID() != null) {
      PersonalCard personalCard = personalCardCRUD.searchUUIDObject(object.getCardID());
      if (personalCard != null) {
        personalCardCRUD.deleteObject(personalCard);
      }
    }

    //Удаляем контракт
    if (object.getContractID() != null) {
      Contract contract = contractCRUD.searchUUIDObject(object.getContractID());
      if (contract != null) {
        contractCRUD.deleteObject(contract);
      }
    }

    //убираем из всех комнат за которыми закреплен
    if (!object.getRoomsID().isEmpty()) {
      for (UUID roomID : object.getRoomsID()) {
        Room room = roomCRUD.searchUUIDObject(roomID);
        room.deleteEmployee(object.getUuid());
        roomCRUD.saveObject(room);
      }
    }
    // удаляем
    Employee employee = searchUUIDObject(object.getUuid());
    File deleteFile = new File(
        DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH + searchFileName(employee));
    if (deleteFile.delete()) {
      log.info("Employee was successfully deleted>");
    } else {
      log.warn(ERROR);
    }

  }

  @Override
  public void saveObject(Employee employee) {
    log.info("<Start saving employee...");

    try (
        FileOutputStream fileEmployeeOut = new FileOutputStream(
            DatabaseProperties.EMPLOYEE_CRUD_ENTITIES_PATH + employee.getUuid()
                + "-employee.txt");
        ObjectOutputStream objectEmployeeOut = new ObjectOutputStream(fileEmployeeOut);
    ) {
      objectEmployeeOut.writeObject(employee);
      log.info("Success saving employee>");
    } catch (Exception exception) {
      log.error(EXCEPTION_ERROR, exception);
    }
  }
}
