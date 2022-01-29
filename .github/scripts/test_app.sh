#!/bin/bash

set -eo pipefail

xcodebuild -project iosApp/iosApp.xcodeproj \
            -scheme iosApp \
            -destination platform=iOS\ Simulator,OS=15.2,name=iPhone\ 11 \
            clean test | xcpretty
