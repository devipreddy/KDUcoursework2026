import { employees } from "./Employee.js";

//Task 3.1
function extractEmployeeInformation(employee){
    const{name, department, salary} = employee;
    return `${name} works in ${department} and earns${salary}`;


}

//Task 3.2
function topAndBottomPaidEmployees(employees){
    const sorted = [...employees].sort((emp1,emp2)=> emp2.salary - emp1.salary);

    const [top, ...rest] = sorted;
    const bottom = rest[rest.length - 1];
    return [top,bottom];
}

//Task 3.3
function getMergeSkillsOfEmployees(emp1,emp2){

    const merged = [...emp1.skills,...emp2.skills];
    return [...new Set(merged)];


}

//Task 3.4
function employeeStats(...employees) {

  const totalNumberOfEmployees = employees.length;
  const totalAgeOfEmployees = employees.reduce((sum, emp) => sum + emp.age, 0);
  const averageAgeOfEmployees = totalAgeOfEmployees / totalNumberOfEmployees;

  return [totalNumberOfEmployees,averageAgeOfEmployees];

}

export { extractEmployeeInformation, topAndBottomPaidEmployees, getMergeSkillsOfEmployees, employeeStats};
