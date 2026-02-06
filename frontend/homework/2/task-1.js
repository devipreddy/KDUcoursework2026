import { employees } from "./Employee.js";

// Task 1.2
function getEmployeeInfo(employee) {
  return `${employee.name} works in ${employee.department} and earns ${employee.salary}`;
}

function addSkill(employee, skill){
    employee.skills.push(skill);
}

//Task 1.3

employees.forEach((emp) => {
  emp.getFullInfo = function () {
    return ` ${this.name} (${this.department}) earns ₹${this.salary}, with an Experience of  ${this.experience} years and also has these skills: ${this.skills.join(", ")}
    `;
  };
});


function compareEmployees(emp1, emp2) {
  const result = compareSkills(emp1.skills, emp2.skills);

  if (result === 1) return emp1.name;
  if (result === -1) return emp2.name;

  return "Both employees have equal skills";
}

function compareSkills(arr1, arr2) {
  if (arr1.length > arr2.length) return 1;
  if (arr2.length > arr1.length) return -1;
  return 0;
}

export { getEmployeeInfo, addSkill, compareEmployees};
