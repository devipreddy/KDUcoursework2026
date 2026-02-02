import { writeReport } from "./reportGenerator.js";


async function generateSummaryReport(employees, outputPath) {
  const totalEmployees = employees.length;

  const totalSalary = employees.reduce((sum, emp) => sum + emp.salary,0);

  const avgSalary = totalSalary / totalEmployees;


  const departmentStats = {};

  employees.forEach((emp) => {
    if (!departmentStats[emp.department]) {departmentStats[emp.department] = {count: 0,totalSalary: 0};
    }

    departmentStats[emp.department].count++;

    departmentStats[emp.department].totalSalary += emp.salary;
  });

  let report = `SUMMARY REPORT \n\n`;

  report += `Total Employees: ${totalEmployees}\n`;

  report += `Total Company Salary: ${totalSalary}\n`;

  report += `Average Salary: ${avgSalary.toFixed(2)}\n\n`;

  report += `Department Wise Breakdown\n`;

  for (let dept in departmentStats) {
    const deptData = departmentStats[dept];

    const deptAvg = deptData.totalSalary / deptData.count;

    report += `\nDepartment: ${dept}\n`;

    report += `Employees: ${deptData.count}\n`;

    report += `Total Salary: ${deptData.totalSalary}\n`;
    report += `Average Salary: ${deptAvg.toFixed(2)}\n`;
  }

  await writeReport(outputPath, report);

}


async function generateDepartmentReport(employees, department, outputPath) {
  const deptEmployees = employees.filter((emp) => emp.department.toLowerCase() === department.toLowerCase());

  if (deptEmployees.length === 0) {
    await writeReport(outputPath, `No employees found in ${department}`);
    return;
  }

  const totalSalary = deptEmployees.reduce((sum, emp) => sum + emp.salary,0);

  const avgSalary = totalSalary / deptEmployees.length;

  let report = `DEPARTMENT REPORT \n\n`;

  report += `Department: ${department}\n`;

  report += `Number of Employees: ${deptEmployees.length}\n\n`;

  report += `Employee List \n`;

  deptEmployees.forEach((emp) => {report += `- ${emp.name}: ${emp.salary}\n`;});

  report += `\nTotal Salary: ${totalSalary}\n`;
  report += `Average Salary: ${avgSalary.toFixed(2)}\n`;

  await writeReport(outputPath, report);
}



async function generateTopEarnersReport(employees, count, outputPath) {
  const topEarners = [...employees].sort((a, b) => b.salary - a.salary).slice(0, count);
  let report = `TOP EARNERS REPORT: \n\n`;

  topEarners.forEach((emp, index) => {report += `${index + 1}. ${emp.name} (${emp.department}) - ${emp.salary}\n`; });

  await writeReport(outputPath, report);
}

export {generateSummaryReport, generateDepartmentReport, generateTopEarnersReport };
