variable "CORE_VERSION" {
  default = "unknown"
}

variable "REGISTRY" {
  default = "ghcr.io/imu-iccs"
}

variable "COMMIT_SHA" {
  default = "unknown-sha"
}

variable "COMMIT_SAFE_TIME" {
  default = "unknown-time"
}

group "default" {
  targets = ["builder", "server", "client"]
}

target "common" {
  context    = "."
  dockerfile = "Dockerfile"

  platforms = [
    "linux/amd64",
    "linux/arm64",
  ]

  cache-from = ["type=gha,scope=ems-shared"]
  cache-to   = ["type=gha,scope=ems-shared,mode=max"]
}

target "builder" {
  inherits = ["common"]
  target   = "ems-server-builder"
  tags = [
    "${REGISTRY}/ems-server-core-builder:${CORE_VERSION}",
    "${REGISTRY}/ems-server-core-builder:latest",
    "${REGISTRY}/ems-server-core-builder:${COMMIT_SHA}",
    "${REGISTRY}/ems-server-core-builder:${COMMIT_SHA}-${COMMIT_SAFE_TIME}",
  ]
}

target "server" {
  inherits = ["common"]
  target   = "ems-server"
  contexts = {
    ems-server-builder = "target:builder"
  }
  tags = [
    "${REGISTRY}/ems-server:${CORE_VERSION}",
    "${REGISTRY}/ems-server:latest",
    "${REGISTRY}/ems-server:${COMMIT_SHA}",
    "${REGISTRY}/ems-server:${COMMIT_SHA}-${COMMIT_SAFE_TIME}",
  ]
}

target "client" {
  inherits = ["common"]
  target   = "ems-client"
  contexts = {
    ems-server-builder = "target:builder"
  }
  tags = [
    "${REGISTRY}/ems-client:${CORE_VERSION}",
    "${REGISTRY}/ems-client:latest",
    "${REGISTRY}/ems-client:${COMMIT_SHA}",
    "${REGISTRY}/ems-client:${COMMIT_SHA}-${COMMIT_SAFE_TIME}",
  ]
}