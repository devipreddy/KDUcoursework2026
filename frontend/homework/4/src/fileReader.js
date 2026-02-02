import {readFile} from "fs/promises";

export async function readEmployeeData(filepath){
    try{
        const fileData = await readFile(filepath, "utf-8");

        const employeeData = JSON.parse(fileData);

        return employeeData;
    } catch{ error}{
        console.log("Error Reading the File:", error.message);

        return [];

    }

}

