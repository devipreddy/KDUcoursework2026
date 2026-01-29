import { employees } from "./Employee.js";

import { getEmployeeInfo, addSkill, compareEmployees } from "./task-1.js";

import { filterByExperience, getEmployeeSummaries, getAverageSalary, getEmployeesPerDepartment, getHighestPaidEmployee, sortByExperience } from "./task-2.js";

import { extractEmployeeInformation, topAndBottomPaidEmployees, getMergeSkillsOfEmployees, employeeStats } from "./task-3.js";

import { getAnalytics } from "./task-4.js";



console.log("\nTask 1.2 Output:");
console.log(getEmployeeInfo(employees[0]));

console.log("\nTask 1.2 Add Skill Output:");
addSkill(employees[0], "MongoDB");
console.log(employees[0].skills);

console.log("\nTask 1.3 Compare Employees Output:");
console.log(compareEmployees(employees[0], employees[3]));

console.log("\nTask 2.2 Filter By Experience Output:");
console.log(filterByExperience(employees, 5));

console.log("\nTask 2.3 Employee Summaries Output:");
console.log(getEmployeeSummaries(employees));

console.log("\nTask 2.4 Average Salary Output:");
console.log(getAverageSalary(employees));

console.log("\nTask 2.4 Department Counts Output:");
console.log(getEmployeesPerDepartment(employees));

console.log("\nTask 2.5 Highest Paid Employee Output:");
console.log(getHighestPaidEmployee(employees));

console.log("\nTask 2.5 Sort By Experience Output:");
console.log(sortByExperience(employees));

console.log("\nTask 3.1 Object Destructuring Output:");
console.log(extractEmployeeInformation(employees[2]));

console.log("\nTask 3.2 Top & Bottom Paid Output:");
console.log(topAndBottomPaidEmployees(employees));

console.log("\nTask 3.3 Merge Skills Output:");
console.log(getMergeSkillsOfEmployees(employees[0], employees[4]));

console.log("\nTask 3.4 Rest Operator Stats Output:");
console.log(employeeStats(employees[0], employees[1], employees[2]));

console.log("\nTask 4.1 Skill Analytics Output:");
console.log(getAnalytics(employees));
