resource "aws_ecr_repository" "api" {
  name = "paystream/api"
  image_scanning_configuration { scan_on_push = true }
}

resource "aws_ecr_repository" "worker" {
  name = "paystream/worker"
  image_scanning_configuration { scan_on_push = true }
}
