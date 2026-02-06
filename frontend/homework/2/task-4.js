import { employees } from "./Employee.js";

function getAnalytics(employees){

    const skillsOfEmployees = [...employees].flatMap(emp => emp.skills);

    return skillsOfEmployees.reduce((skillsset, skill)=>{

        if(skillsset[skill]){
            skillsset[skill]++;
        }else{
            skillsset[skill] = 1;
        }

        return skillsset;
    },{});


}

export { getAnalytics };
