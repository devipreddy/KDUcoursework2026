export class Todo {
  id: string;
  title: string;
  description?: string;
  completed: boolean;
  createdAt: Date;
  updatedAt: Date;

  constructor(title: string, description?: string) {
    this.id = crypto.randomUUID();
    this.title = title;
    this.description = description;
    this.completed = false;
    this.createdAt = new Date();
    this.updatedAt = new Date();
  }
}
