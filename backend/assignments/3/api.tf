############################################
# API Gateway HTTP API
############################################
resource "aws_apigatewayv2_api" "api" {
  name          = "devi-counter-api"
  protocol_type = "HTTP"

  cors_configuration {
    allow_origins = ["*"]
    allow_methods = ["GET", "PUT", "OPTIONS"]
    allow_headers = ["*"]
  }
}

############################################
# Default Stage (MANDATORY for HTTP API)
############################################
resource "aws_apigatewayv2_stage" "default" {
  api_id      = aws_apigatewayv2_api.api.id
  name        = "$default"
  auto_deploy = true
}

############################################
# Lambda Integrations
############################################
resource "aws_apigatewayv2_integration" "get" {
  api_id           = aws_apigatewayv2_api.api.id
  integration_type = "AWS_PROXY"
  integration_uri  = aws_lambda_function.get_counter.invoke_arn
}

resource "aws_apigatewayv2_integration" "put" {
  api_id           = aws_apigatewayv2_api.api.id
  integration_type = "AWS_PROXY"
  integration_uri  = aws_lambda_function.update_counter.invoke_arn
}

############################################
# Routes
############################################
resource "aws_apigatewayv2_route" "get" {
  api_id    = aws_apigatewayv2_api.api.id
  route_key = "GET /counter"
  target    = "integrations/${aws_apigatewayv2_integration.get.id}"
}

resource "aws_apigatewayv2_route" "put" {
  api_id    = aws_apigatewayv2_api.api.id
  route_key = "PUT /counter"
  target    = "integrations/${aws_apigatewayv2_integration.put.id}"
}

############################################
# Lambda Permissions (REQUIRED)
############################################
resource "aws_lambda_permission" "apigw_get" {
  statement_id  = "AllowAPIGatewayInvokeGet"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.get_counter.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.api.execution_arn}/*/*"
}

resource "aws_lambda_permission" "apigw_put" {
  statement_id  = "AllowAPIGatewayInvokePut"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.update_counter.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.api.execution_arn}/*/*"
}
