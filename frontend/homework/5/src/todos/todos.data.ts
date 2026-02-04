import { Todo } from './entities/todo.entity';

export const todos: Todo[] = [
  new Todo(
    'Learn NestJS Basics',
    'Understand modules, controllers, and services.',
  ),

  new Todo(
    'Finish Node.js Revision',
    'Revise async/await, event loop, and Express.js concepts.',
  ),

  new Todo('Buy groceries'),

  new Todo(
    'Build Todo CRUD API',
    'Create endpoints for create, read, update, delete operations with DTO validation.',
  ),

  new Todo(
    'Submit frontend assessment project',
    'Complete HTML/CSS/JS task before tonight.',
  ),

  new Todo('Workout', '30 minutes running + stretching.'),

  new Todo(
    'Fix bug in API (PATCH /todos/:id)',
    'Handle update correctly when description is undefined.',
  ),

  new Todo(
    'Read AWS Load Balancer Notes',
    'Understand health checks, target groups, and auto scaling.',
  ),

  new Todo(
    'Prepare NestJS vs Spring Boot Presentation',
    'Include folder structure, startup time, DI comparison.',
  ),

  new Todo('Meditation'),
];
