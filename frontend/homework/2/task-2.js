import { employees } from "./Employee.js";

//Task 2.1
console.log(employees);

//Task 2.2

function filterByExperience(employees, minExperience){

    return employees.filter((emp) => emp.experience >= minExperience);

}

//Task 2.3

function getEmployeeSummaries(employees) {
  return employees.map((emp) => {
    return `${emp.name} works in (${emp.department}) – and earn a salary of ${emp.salary}`;
  });
}

//Task 2.4

function getAverageSalary(employees){

    const sumOfSalary = employees.reduce((sum, emp) => {sum = sum + emp.salary},0 );

    return sumOfSalary / employees.length;

}

function getEmployeesPerDepartment(employees){
    return employees.reduce((countOfEmp, emp)=> {
        if (countOfEmp[emp.department]){
            countOfEmp[emp.department]++;
        }
        else{
            countOfEmp[emp.department] = 1;
        }

        return countOfEmp;
    },{});

}

//Task 2.5


function getHighestPaidEmployee(employees) {

  const salaries = employees.map(emp => emp.salary);

  const maxSalary = Math.max(...salaries);

  return employees.find(emp => emp.salary === maxSalary);
}


function sortByExperience(employees) {
  return [...employees].sort((a, b) => b.experience - a.experience);
}

export { filterByExperience, getEmployeeSummaries, getAverageSalary, getEmployeesPerDepartment, getHighestPaidEmployee, sortByExperience};
