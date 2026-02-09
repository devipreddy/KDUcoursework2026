terraform {
  backend "s3" {
    bucket  = "my-terraform-state-devi-prasad"
    key     = "serverless-counter/terraform.tfstate"
    region  = "ap-south-1"
    encrypt = true
  }
}
