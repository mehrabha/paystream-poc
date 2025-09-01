# artifacts.tf
resource "aws_s3_bucket" "cp_artifacts" {
  bucket = "paystream-codepipeline-artifacts-${data.aws_caller_identity.current.account_id}"
}

resource "aws_s3_bucket_versioning" "cp_artifacts" {
  bucket = aws_s3_bucket.cp_artifacts.id
  versioning_configuration { status = "Enabled" }
}

resource "aws_s3_bucket_public_access_block" "cp_artifacts" {
  bucket                  = aws_s3_bucket.cp_artifacts.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}
