resource "aws_lambda_function" "get_counter" {
  function_name = "devi-get-counter"
  role          = aws_iam_role.lambda_role.arn
  handler       = "get_counter.handler"
  runtime       = "python3.11"
  filename      = "lambda/get_counter.zip"
}

resource "aws_lambda_function" "update_counter" {
  function_name = "devi-update-counter"
  role          = aws_iam_role.lambda_role.arn
  handler       = "update_counter.handler"
  runtime       = "python3.11"
  filename      = "lambda/update_counter.zip"
}
