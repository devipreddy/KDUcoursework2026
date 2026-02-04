export class TodoResponseDto {
  id: string;
  title: string;
  completed: boolean;

  constructor(todo: any) {
    this.id = todo.id;
    this.title = todo.title;
    this.completed = todo.completed;
  }
}
