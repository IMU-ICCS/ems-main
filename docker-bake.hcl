group "default" {
  targets = ["builder", "server", "client"]
}

target "common" {
  context = "."
  dockerfile = "Dockerfile"

  platforms = [
    "linux/amd64",
    "linux/arm64",
  ]

  cache-from = ["type=gha"]
  cache-to   = ["type=gha,mode=max"]
}

target "builder" {
  inherits = ["common"]
  target = "ems-server-builder"
  tags = [
    "ghcr.io/imu-iccs/ems-server-core-builder:latest",
  ]
}

target "server" {
  inherits = ["common"]
  target = "ems-server"
  tags = [
    "ghcr.io/imu-iccs/ems-server:8.0.0-snapshot",
    "ghcr.io/imu-iccs/ems-server:latest",
  ]
}

target "client" {
  inherits = ["common"]
  target = "ems-client"
  tags = [
    "ghcr.io/imu-iccs/ems-client:8.0.0-snapshot",
    "ghcr.io/imu-iccs/ems-client:latest",
  ]
}