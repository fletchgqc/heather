# Code style and linting
- Detekt rules are located at config/detekt/default-detekt-config.yml with overrides in config/detekt/detekt.yml. All code must comply with these rules for a successful build.

# Build configuration
- Use Gradle daemon for faster builds - run `./gradlew` commands without the `--no-daemon` flag.