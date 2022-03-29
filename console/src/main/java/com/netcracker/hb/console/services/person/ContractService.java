package com.netcracker.hb.console.services.person;

import com.netcracker.hb.Dao.crud.CRUD;
import com.netcracker.hb.Dao.crud.Person.ContractCRUD;
import com.netcracker.hb.Dao.crud.Person.EmployeeCRUD;
import com.netcracker.hb.Dao.crud.Person.IEmployeeCRUD;
import com.netcracker.hb.console.services.IPersonalCard;
import com.netcracker.hb.console.services.chekserveces.ValidationService;
import com.netcracker.hb.entities.persons.Contract;
import com.netcracker.hb.entities.persons.Employee;
import com.netcracker.hb.entities.persons.Person;
import java.util.Date;
import java.util.UUID;
import lombok.extern.log4j.Log4j;


@Log4j
public class ContractService implements IPersonalCard<Contract> {

  private static IPersonalCard<Contract> contractService ;

  private ContractService() {
  }

  public static synchronized IPersonalCard<Contract> getContractService() {
    if(contractService ==null){
      contractService = new ContractService();
    }
    return contractService;
  }

  private static final IEmployeeCRUD<Employee> employeeCRUD = EmployeeCRUD.getIEmployeeCRUD();
  private static final ValidationService validationService = ValidationService.getValidationService();
  private static final CRUD<Contract> contractCRUD = ContractCRUD.getContractCRUD();

  @Override
  public void addObject() {

    Employee employee = validationService.validationSearchEmployeeNameSurname();

    Contract contract = Contract.builder()
        .uuid(UUID.randomUUID())
        .employeeID(employee.getUuid())
        .build();
    contractCRUD.saveObject(contract);
    employee.setContractID(contract.getUuid());
    employeeCRUD.saveObject(employee);
  }

  @Override
  public void changeObject(Contract object) {
    log.info("Start work with contract");
    int userChoice;
    long years;
    Date date;
    do {
      log.info("1.set begin contract date");
      log.info("2.set end contract date");
      log.info("3.set Experience");
      log.info("4.set Salary");
      log.info("5.set WorkTime");
      log.info("666.Back to previous menu");
      userChoice = validationService.validationNumberChoice();
      switch (userChoice) {
        case 1:
          log.info("write how many years do u wanna add to date begin(from 1970 :В)");
          years = validationService.validationNumberChoice();
          years *= 360L * 86400000L;
          date = new Date(years);
          object.setBeginContract(date);
          contractCRUD.saveObject(object);
          break;
        case 2:
          log.info("write how many years do u wanna add to date end(from 1970 :В)");
          years = validationService.validationNumberChoice();
          years *= 360L * 86400000L;
          date = new Date(years);
          object.setBeginContract(date);
          contractCRUD.saveObject(object);
          break;
        case 3:
          log.info("Enter Experience in years");
          object.setExperience(validationService.validationNumberChoice());
          contractCRUD.saveObject(object);
          break;
        case 4:
          log.info("Enter salary in dollars");
          object.setSalary(validationService.validationNumberChoice());
          contractCRUD.saveObject(object);
          break;
        case 5:
          log.info("Enter work time in hours");
          object.setWorkTime(validationService.validationNumberChoice());
          contractCRUD.saveObject(object);
          break;
        case 666:
          log.info("See u!");
          break;
        default:
          log.error("Choose correct num");
          break;
      }

    } while (userChoice != 666);

  }

  @Override
  public void displayObject(Contract object) {
    final String BORDER = "_______________________";
    log.info(BORDER);
    log.info(BORDER);
    log.info(contractCRUD.searchFileName(object));
    log.info("BeginContract " + object.getBeginContract());
    log.info("EndContract " + object.getEndContract());
    log.info("Experience (years)" + object.getExperience());
    log.info("Salary (three hundred bucks)" + object.getSalary());
    log.info("WorkTime (hours)" + object.getWorkTime());
    log.info(BORDER);
    log.info(BORDER);

  }

  @Override
  public void addObjectPerson(Person person) {
    Employee employee = (Employee) person;

    Contract contract = Contract.builder()
        .uuid(UUID.randomUUID())
        .employeeID(employee.getUuid())
        .build();
    contractCRUD.saveObject(contract);
    employee.setContractID(contract.getUuid());
    employeeCRUD.saveObject(employee);
  }
}
