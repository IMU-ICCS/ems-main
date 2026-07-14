# variable "CORE_VERSION" {
#   default = "unknown"
# }

variable "REGISTRY" {
  default = "ghcr.io/imu-iccs"
}

variable "COMMIT_SHA" {
  default = "unknown-sha"
}

# variable "COMMIT_SAFE_TIME" {
#   default = "unknown-time"
# }

# Single platform per bake invocation - set per matrix leg in CI
variable "PLATFORM" {
  default = "linux/amd64"
}

# Suffix for temporary per-arch tags, e.g. "amd64" or "arm64"
# Used to keep matrix legs from clobbering each other's pushed tags
# and to keep GHA cache scopes separate per architecture.
variable "ARCH_TAG" {
  default = "amd64"
}

variable "DOCKER_IMAGE" {
  default = ""
}
variable "BUILD_DESCR" {
  default = ""
}


group "default" {
  targets = ["builder", "server", "client"]
}

target "common" {
  context    = "."
  dockerfile = "Dockerfile"

  platforms = [PLATFORM]

  cache-from = ["type=gha,scope=ems-${ARCH_TAG}"]
  cache-to   = ["type=gha,scope=ems-${ARCH_TAG},mode=max"]
}

target "builder" {
  inherits = ["common"]
  target   = "ems-server-builder"
  tags = [
    "${REGISTRY}/ems-server-core-builder:${COMMIT_SHA}-${ARCH_TAG}",
  ]
  args = {
    DOCKER_IMAGE = DOCKER_IMAGE
    BUILD_DESCR  = BUILD_DESCR
  }
}

target "server" {
  inherits = ["common"]
  target   = "ems-server"

  # Reuse the already-built "builder" target's output for this platform
  # instead of recompiling the ems-server-builder stage.
  contexts = {
    ems-server-builder = "target:builder"
  }

  tags = [
    "${REGISTRY}/ems-server:${COMMIT_SHA}-${ARCH_TAG}",
  ]
}

target "client" {
  inherits = ["common"]
  target   = "ems-client"

  contexts = {
    ems-server-builder = "target:builder"
  }

  tags = [
    "${REGISTRY}/ems-client:${COMMIT_SHA}-${ARCH_TAG}",
  ]
}