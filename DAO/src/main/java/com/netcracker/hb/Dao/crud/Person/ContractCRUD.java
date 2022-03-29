package com.netcracker.hb.Dao.crud.Person;

import com.netcracker.hb.Dao.crud.DatabaseProperties;
import com.netcracker.hb.entities.Role;
import com.netcracker.hb.entities.persons.Contract;
import com.netcracker.hb.entities.persons.Employee;
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
public class ContractCRUD implements IContractCRUD<Contract> {


  private static IContractCRUD<Contract> contractCRUD;

  private ContractCRUD() {
  }

  public static synchronized IContractCRUD<Contract> getContractCRUD() {
    if (contractCRUD == null) {
      contractCRUD = new ContractCRUD();
    }
    return contractCRUD;
  }


  private static final String START = "<Start searching employee contract....";
  private static final String END = "employee contract was found>";
  private static final String ERROR = "employee contract was not found>";

  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();

  @Override
  public Contract searchObjectNameSurname(String name, String surname) {
    log.info(START);
    File contractFolderDirectory = new File(DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH);
    String[] contractList = contractFolderDirectory.list();
    Contract contract = null;
    if (contractList == null) {
      return null;
    }
    for (String contractFolderName : contractList) {
      try (

          FileInputStream fileContractIn = new FileInputStream(
              DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH
                  + contractFolderName); ObjectInputStream objectContractIn = new ObjectInputStream(
          fileContractIn);) {
        Contract object = (Contract) objectContractIn.readObject();
        Employee employee = employeeCRUD.searchObjectNameSurname(name, surname);

        if (employee.getContractID().equals(object.getUuid())) {
          log.info(END);
          contract = object;
        }

      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (contract == null) {
      log.error(ERROR);
    }
    return contract;
  }

  @Override
  public List<Contract> searchObjects() {
    log.info(START);

    File contractFolderDirectory = new File(DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH);
    String[] contractList = contractFolderDirectory.list();
    List<Contract> contracts = new ArrayList<>();
    if (contractList == null) {
      return null;
    }
    for (String contractFolderName : contractList) {
      try (FileInputStream fileContractIn = new FileInputStream(
          DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH
              + contractFolderName); ObjectInputStream objectContractIn = new ObjectInputStream(
          fileContractIn);) {
        Contract object = (Contract) objectContractIn.readObject();
        contracts.add(object);

      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (contracts.isEmpty()) {
      log.error(ERROR);
    }
    return contracts;
  }

  //не должно работать
  @Override
  public Contract searchObjectNum(int num) {
    //тк у контракта номера нет, то и работать такой поиск не должен

    return null;
  }

  @Override
  public Contract searchUUIDObject(UUID uuid) {
    log.info(START);
    if (uuid == null) {
      return null;
    }

    File contractFolderDirectory = new File(DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH);
    String[] contractList = contractFolderDirectory.list();
    Contract contract = null;
    if (contractList == null) {
      return null;
    }
    for (String contractFolderName : contractList) {
      try (FileInputStream fileContractIn = new FileInputStream(
          DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH
              + contractFolderName); ObjectInputStream objectContractIn = new ObjectInputStream(
          fileContractIn);) {
        Contract object = (Contract) objectContractIn.readObject();
        if (object.getUuid().equals(uuid)) {
          log.info(END);
          contract = object;
        }
      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (contract == null) {
      log.error(ERROR);
    }
    return contract;
  }

  @Override
  public String searchFileName(Contract contract) {
    log.info(START);

    File contractFolderDirectory = new File(DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH);
    String[] contractList = contractFolderDirectory.list();
    String fileName = null;
    if (contractList == null) {
      return null;
    }
    for (String contractFolderName : contractList) {
      try (FileInputStream fileContractIn = new FileInputStream(
          DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH
              + contractFolderName); ObjectInputStream objectContractIn = new ObjectInputStream(
          fileContractIn);) {

        Contract object = (Contract) objectContractIn.readObject();
        if (object.equals(contract)) {
          log.info("contract was found>");
          fileName = contractFolderName;
        }

      } catch (IOException | ClassNotFoundException exception) {
        exception.printStackTrace();
      }
    }
    if (fileName == null) {
      log.error(ERROR);
    }
    return fileName;
  }

  @Override
  public void deleteObject(Contract object) {

    //отвязываем от работника
    Employee employee = employeeCRUD.searchUUIDObject(object.getEmployeeID());
    employee.setContractID(null);

    // удаляем
    Contract contract = searchUUIDObject(object.getUuid());
    File deleteFile = new File(
        DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH + searchFileName(contract));

    if (deleteFile.delete()) {
      log.info("Contract was successfully deleted>");
    } else {
      log.warn(ERROR);
    }


  }

  @Override
  public void saveObject(Contract contract) {

    log.info("<Start saving contract...");
    try (FileOutputStream fileContractOut = new FileOutputStream(
        DatabaseProperties.CONTRACT_CRUD_ENTITIES_PATH + contract.getUuid()
            + "-personalCard.txt"); ObjectOutputStream objectContractOut = new ObjectOutputStream(
        fileContractOut);) {
      objectContractOut.writeObject(contract);
      log.info("Success saving contract>");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
