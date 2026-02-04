import {
  ExceptionFilter,
  Catch,
  ArgumentsHost,
  HttpException,
  HttpStatus,
} from '@nestjs/common';

@Catch()
export class GlobalExceptionFilter implements ExceptionFilter {
  catch(exception: unknown, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse();
    const request = ctx.getRequest();

    let status = HttpStatus.INTERNAL_SERVER_ERROR;
    let message: any = 'Internal server error';
    let errorCode = 'INTERNAL_ERROR';

    if (exception instanceof HttpException) {
      status = exception.getStatus();
      const exceptionResponse: any = exception.getResponse();

      if (typeof exceptionResponse === 'object') {
        message = exceptionResponse.message;
        errorCode = exceptionResponse.errorCode || errorCode;
      } else {
        message = exceptionResponse;
      }
    }

    response.status(status).json({
      success: false,
      errorCode: errorCode,
      statusCode: status,
      message: message,
      path: request.url,
      timestamp: new Date().toISOString(),
    });
  }
}
