import { readEmployeeData } from "./src/fileReader.js";

import { generateSummaryReport, generateDepartmentReport, generateTopEarnersReport} from "./src/reportUtils.js";

async function main() {
  const employeesData = await readEmployeeData("./data/employees.json");

  const commandInCLI = process.argv[2];

  const summaryReportPath = "./reports/summary.txt";
  const departmentReportPath = "./reports/department.txt";
  const topEarnersReportPath = "./reports/topEarners.txt";

  if (!commandInCLI) {
    await generateSummaryReport(employeesData, summaryReportPath);
    await generateDepartmentReport(employeesData, "Engineering", departmentReportPath);
    await generateTopEarnersReport(employeesData, 4, topEarnersReportPath);

  } else if (commandInCLI === "summary") {
    await generateSummaryReport(employeesData, summaryReportPath);

  } else if (commandInCLI === "department") {
    const departmentName = process.argv[3] || "Engineering";
    await generateDepartmentReport(employeesData, departmentName, departmentReportPath);

  } else if (commandInCLI === "top") {
    const count = parseInt(process.argv[3]) || 4;
    await generateTopEarnersReport(employeesData, count, topEarnersReportPath);

  } else {
    console.log("Invalid Command. Please check and try again!");
  }
}

main();
