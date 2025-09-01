variable "aws_region"       { type = string }
variable "account_id"       { type = string }
variable "image_tag"    {   
    type = string 
    default = "latest" 
}
variable "aws_profile" { 
    type = string 
    default = "default" 
}
