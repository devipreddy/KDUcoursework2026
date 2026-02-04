import { HttpException, HttpStatus } from '@nestjs/common';

export class AppException extends HttpException {
  constructor(
    public errorCode: string,
    message: string,
    statusCode: HttpStatus,
  ) {
    super(
      {
        errorCode,
        message,
      },
      statusCode,
    );
  }
}
