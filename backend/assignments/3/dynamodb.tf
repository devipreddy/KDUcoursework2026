resource "aws_dynamodb_table" "counter" {
  name         = "Devi-Prasad-CounterTable"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "counterId"

  attribute {
    name = "counterId"
    type = "S"
  }
}
