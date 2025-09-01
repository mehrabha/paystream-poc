data "aws_iam_policy_document" "cb_assume" {
  statement {
    actions = ["sts:AssumeRole"]
    principals { 
      type = "Service" 
      identifiers = ["codebuild.amazonaws.com"] 
    }
  }
}

resource "aws_iam_role" "codebuild_role" {
  name               = "paystream-codebuild-role"
  assume_role_policy = data.aws_iam_policy_document.cb_assume.json
}

data "aws_iam_policy_document" "cb_policy" {
  statement {
    actions   = ["logs:CreateLogGroup", "logs:CreateLogStream", "logs:PutLogEvents"]
    resources = ["*"]
  }

  statement {
    actions   = ["ecr:GetAuthorizationToken"]
    resources = ["*"]
  }

  statement {
    actions   = [
      "ecr:BatchCheckLayerAvailability",
      "ecr:CompleteLayerUpload",
      "ecr:InitiateLayerUpload",
      "ecr:PutImage",
      "ecr:UploadLayerPart",
      "ecr:BatchGetImage",
      "ecr:GetDownloadUrlForLayer"
    ]
    resources = ["*"]
  }

  statement {
    actions   = ["s3:GetObject", "s3:PutObject", "s3:ListBucket"]
    resources = ["*"]
  }
}

resource "aws_iam_policy" "codebuild_policy" {
  name   = "paystream-codebuild-policy"
  policy = data.aws_iam_policy_document.cb_policy.json
}

resource "aws_iam_role_policy_attachment" "cb_attach" {
  role       = aws_iam_role.codebuild_role.name
  policy_arn = aws_iam_policy.codebuild_policy.arn
}
