import { HttpStatus } from '@nestjs/common';
import { AppException } from './app.exception';

export class ResourceNotFoundException extends AppException {
  constructor(resourceName: string, id: string) {
    super(
      `${resourceName.toUpperCase()}_NOT_FOUND`,
      `${resourceName} with id ${id} not found`,
      HttpStatus.NOT_FOUND,
    );
  }
}
