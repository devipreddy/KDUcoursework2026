import { ResourceNotFoundException } from '../../common/exceptions/resource-not-found.exception';

export class TodoNotFoundException extends ResourceNotFoundException {
  constructor(id: string) {
    super('Todo', id);
  }
}
