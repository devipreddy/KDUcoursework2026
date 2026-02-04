import { Injectable } from '@nestjs/common';
import { CreateTodoDto } from './dto/create-todo.dto';
import { todos } from './todos.data';
import { Todo } from './entities/todo.entity';
import { TodoNotFoundException } from './exceptions/todo-not-found.exception';
import { UpdateTodoDto } from './dto/update-todo.dto';
import { TodoResponseDto } from './dto/response-todo.dto';
import { Logger } from '@nestjs/common';
@Injectable()
export class TodosService {
  private todoList: Todo[] = todos;
  private readonly logger = new Logger(TodosService.name);

  create(createTodoDto: CreateTodoDto) {
    this.logger.log(`Creating todo with title: ${createTodoDto.title}`);
    const newTodo = new Todo(createTodoDto.title, createTodoDto.description);
    this.todoList.push(newTodo);
    return new TodoResponseDto(newTodo);
  }

  findAll() {
    this.logger.log('Fetching all todos');
    return this.todoList.map((todo) => new TodoResponseDto(todo));
  }

  findOne(id: string) {
    this.logger.log('Fetching todo with tittle id: ${id}');
    const todo = this.todoList.find((todoItem) => todoItem.id === id);

    if (!todo) {
      throw new TodoNotFoundException(id);
    }

    return new TodoResponseDto(todo);
  }

  update(id: string, updateTodoDto: UpdateTodoDto) {
    this.logger.log('Updating the todo with id: ${id}');
    const todo = this.todoList.find((t) => t.id === id);

    if (!todo) {
      throw new TodoNotFoundException(id);
    }

    if (updateTodoDto.title !== undefined) {
      todo.title = updateTodoDto.title;
    }

    if (updateTodoDto.description !== undefined) {
      todo.description = updateTodoDto.description;
    }

    if (updateTodoDto.completed !== undefined) {
      todo.completed = updateTodoDto.completed;
    }

    todo.updatedAt = new Date();

    return new TodoResponseDto(todo);
  }


  remove(id: string) {
    this.logger.log('Deleting the todo with id: ${id}');
    const index = this.todoList.findIndex((t) => t.id === id);

    if (index === -1) {
      throw new TodoNotFoundException(id);
    }

    this.todoList.splice(index, 1);

    return {message: `Todo with id ${id} deleted successfully`,};
  }
}
