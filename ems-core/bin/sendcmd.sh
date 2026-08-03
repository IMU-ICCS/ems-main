#!/usr/bin/env bash
#
# Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
#
# This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
# Esper library is used, in which case it is subject to the terms of General Public License v2.0.
# If a copy of the MPL was not distributed with this file, you can obtain one at
# https://www.mozilla.org/en-US/MPL/2.0/
#

set -euo pipefail

# Require at least one command line argument
if [[ $# -eq 0 ]]; then
    echo "ERROR: No command specified" >&2
    echo "Usage: $0 <command> [args...]" >&2
    exit 1
fi

# Home directory
BASE_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)"

# Source environment variables file
SETENV_FILE="$BASE_DIR/conf/setenv.sh"

if [[ ! -f "$SETENV_FILE" ]]; then
    echo "WARN: Environment file not found: $SETENV_FILE" >&2
    #exit 1
else
    set -a
    source "$SETENV_FILE"
    set +a
fi

# Check socket file environment variable
if [[ -z "${COMMON_COMMAND_SOCKET_PATH:-}" ]]; then
    echo "ERROR: COMMON_COMMAND_SOCKET_PATH is not set" >&2
    exit 1
fi

# Resolve relative socket paths against the home directory
if [[ "$COMMON_COMMAND_SOCKET_PATH" != /* ]]; then
    SOCKET_FILE="$BASE_DIR/$COMMON_COMMAND_SOCKET_PATH"
else
    SOCKET_FILE="$COMMON_COMMAND_SOCKET_PATH"
fi

# Normalize socket path
SOCKET_FILE="$(realpath "$SOCKET_FILE")"

# Validate socket
if [[ ! -S "$SOCKET_FILE" ]]; then
    echo "ERROR: Unix socket does not exist: $SOCKET_FILE" >&2
    exit 1
fi

# Generate request ID
if command -v uuidgen >/dev/null 2>&1; then
    REQUEST_ID=$(uuidgen)
else
    REQUEST_ID=$RANDOM
fi

# Build message
MESSAGE="$REQUEST_ID"
for ARG in "$@"; do
    MESSAGE+="|$ARG"
done

# Send command
echo "$MESSAGE" | timeout 3 nc -U -N "$SOCKET_FILE"