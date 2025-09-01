resource "aws_codebuild_project" "paystream_build" {
  name         = "paystream-build"
  service_role = aws_iam_role.codebuild_role.arn

  artifacts { type = "NO_ARTIFACTS" }

  environment {
    compute_type    = "BUILD_GENERAL1_SMALL"
    image           = "aws/codebuild/standard:7.0"
    type            = "LINUX_CONTAINER"
    privileged_mode = true  # Docker-in-Docker

    environment_variable {
      name  = "AWS_DEFAULT_REGION"
      value = var.aws_region
    }
    environment_variable {
      name  = "ACCOUNT_ID"
      value = var.account_id
    }
    environment_variable {
      name  = "API_REPO"
      value = aws_ecr_repository.api.repository_url
    }
    environment_variable {
      name  = "WORKER_REPO"
      value = aws_ecr_repository.worker.repository_url
    }
    environment_variable {
      name  = "IMAGE_TAG"
      value = var.image_tag
    }
  }

  source {
    type            = "GITHUB"
    location        = "https://github.com/mehrabha/paystream-poc"
    git_clone_depth = 1
    buildspec       = "buildspec.yml"
  }

  logs_config {
    cloudwatch_logs { 
      group_name = "/codebuild/paystream-build" 
      stream_name = "build" 
    }
  }
}
