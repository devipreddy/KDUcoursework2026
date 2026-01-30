

console.log("Task 1.1: Task Constructor\n");

function Task(title, priority) {
  this.id = Date.now();
  this.title = title;
  this.prioritySet = ["low", "medium", "high"];
  this.priority = this.prioritySet.includes(priority)? priority: "low";
  this.completed = false;
}


let t1 = new Task("Learn Prototypes", "high");
console.log("Task Created:", t1);



console.log("\nTask 1.2: Prototype Methods\n");

Task.prototype.markComplete = function () {
  this.completed = true;
  return this;
};



Task.prototype.updatePriority = function (newPriority) {
  if (this.prioritySet.includes(newPriority)) {
    this.priority = newPriority;
  } else {
    console.log("Invalid value for Priority! Use low, medium, or high.");
  }
  return this;
};


Task.prototype.getInfo = function () { 
    return `Task [${this.id}]: ${this.title} (${this.priority}) - Completed: ${this.completed}`;
};



console.log("\nBefore Update:", t1.getInfo());


t1.markComplete().updatePriority("medium");


console.log("After Update:", t1.getInfo());




console.log("\nTask 1.3: PriorityTask Constructor\n");



class PriorityTask extends Task {
  constructor(title, priority, dueDate = null) {
    super(title, priority);
    this.dueDate = dueDate;

  }

  getInfo() {
    let information = super.getInfo();

    if (this.dueDate) {
      information = information + ` , Due Date: ${this.dueDate}`;

    }

    return information;
  }

}


let p1 = new PriorityTask("Submit Assignment", "high", "2026-02-05");


console.log("Priority Task:", p1.getInfo());




console.log("\n Task 1.4: getAllTasksInfo()\n");


Task.prototype.getAllTasksInfo = function (tasksArray) {
  return tasksArray.map(task => task.getInfo());
};


console.log(t1.getAllTasksInfo([t1, p1]));




console.log("\n Task 2.1: createTaskAsync()\n");



function createTaskAsync(title, priority) {
  console.log("Creating task...");

  return new Promise((resolve) => { setTimeout(() => { console.log("Task created!");
      resolve(new Task(title, priority));}, 1000);});

}


createTaskAsync("Async Task Example", "medium").then(task => console.log("Created Async Task:", task.getInfo()));



console.log("\n Task 2.2: demonstrateEventLoop()\n");


function demonstrateEventLoop() {

            setTimeout(() => {console.log("1");
                setTimeout(() => { console.log("4");
                    Promise.resolve().then(() => { console.log("3");});
                    setTimeout(() => { console.log("2");}, 2000);
                }, 2000);

            }, 2000);
}


demonstrateEventLoop();





console.log("\n Task 2.3: createAndSaveTask()\n");



function createAndSaveTask(title, priority) {
  let savedTask;
  return createTaskAsync(title, priority).then((task1) => { savedTask = task1;
      return createTaskAsync("Second Task", "high");})

    .then(() => { console.log(" Task created and saved successfully!");
      return savedTask;
    })

    .catch((error) => {console.log(" Error:", error);});

}



createAndSaveTask("Original Task", "medium").then(task => console.log("Saved Task:", task.getInfo()));





console.log("\n Task 2.4: createMultipleTasksAsync()\n");

function createMultipleTasksAsync(taskDataArray) {
  console.log(`Creating ${taskDataArray.length} tasks...\n`);

  let promiseList = taskDataArray.map(task => createTaskAsync(task.title, task.priority));

  return Promise.all(promiseList).then(tasks => {console.log("\n All tasks created!");
      return tasks;}).catch(err => console.log(" Error:", err));

}




createMultipleTasksAsync([{ title: "Task A", priority: "low" }, { title: "Task B", priority: "medium" },{ title: "Task C", priority: "high" }]).then(allTasks => { console.log("\nBatch Created Tasks:");

  allTasks.forEach(task => console.log(task.getInfo()));

});
