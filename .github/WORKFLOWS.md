# GitHub Actions CI/CD Documentation

## Overview
This project uses GitHub Actions for continuous integration, deployment, and code quality checks.

## Workflows

### 1. Android CI (`android-ci.yml`)
**Triggers**: Push and PR to `main` and `develop` branches

**Jobs**:
- **build**: Compiles the project, runs tests, and builds debug APK
- **lint**: Runs Android lint checks and uploads results

**Artifacts**:
- `app-debug.apk` - Debug build artifact
- `lint-results` - Lint analysis reports

### 2. Release Build (`release.yml`)
**Triggers**: Push to tags matching `v*` (e.g., `v1.0.0`)

**Jobs**:
- **release**: Builds release APK, signs it (if keystore configured), and creates GitHub release

**Artifacts**:
- `app-release.apk` - Signed release APK

**Required Secrets** (optional, for signing):
- `KEYSTORE_FILE` - Base64 encoded keystore file
- `KEY_ALIAS` - Keystore alias
- `KEYSTORE_PASSWORD` - Keystore password
- `KEY_PASSWORD` - Key password

### 3. Code Quality (`code-quality.yml`)
**Triggers**: Push and PR to `main` and `develop` branches

**Jobs**:
- **test**: Runs unit tests and generates test reports
- **ktlint**: Checks Kotlin code style (optional)
- **detekt**: Runs static code analysis (optional)

**Artifacts**:
- `test-results` - JUnit test results
- `detekt-report` - Static analysis report

### 4. Pull Request Checks (`pr-checks.yml`)
**Triggers**: PR opened, synchronized, or reopened

**Jobs**:
- **validate**: Validates Gradle wrapper, checks formatting, runs lint and tests
- **size-check**: Measures APK size and comments on PR

**Features**:
- Automatic PR comments with build status
- APK size tracking

### 5. Security Scan (`security.yml`)
**Triggers**: 
- Weekly schedule (Monday midnight)
- Push to `main`
- PR to `main`

**Jobs**:
- **dependency-check**: Scans for vulnerable dependencies
- **codeql**: GitHub CodeQL security analysis
- **gradle-scan**: Checks for available dependency updates

**Artifacts**:
- `dependency-check-report` - Vulnerability report
- `dependency-updates` - Available updates report

### 6. Dependabot (`dependabot.yml`)
**Schedule**: Weekly on Monday at 09:00

**Updates**:
- Gradle dependencies
- GitHub Actions versions

**Configuration**:
- Max 10 PRs for Gradle dependencies
- Max 5 PRs for GitHub Actions
- Auto-labels: `dependencies`, `gradle`, `github-actions`

## Setup Instructions

### 1. Enable GitHub Actions
Actions are enabled by default. Workflows will run automatically on triggers.

### 2. Configure Secrets (Optional - for Release Signing)
Go to repository Settings → Secrets and variables → Actions:

```bash
# Generate keystore (if you don't have one)
keytool -genkey -v -keystore release.keystore -alias release -keyalg RSA -keysize 2048 -validity 10000

# Convert keystore to base64
base64 release.keystore > keystore.base64

# Add secrets in GitHub:
KEYSTORE_FILE=<content of keystore.base64>
KEY_ALIAS=release
KEYSTORE_PASSWORD=<your keystore password>
KEY_PASSWORD=<your key password>
```

### 3. Branch Protection Rules (Recommended)
Settings → Branches → Add rule for `main`:
- ✅ Require status checks to pass before merging
- ✅ Require branches to be up to date before merging
- Select: `build`, `lint`, `test`, `validate`

### 4. Enable Dependabot Alerts
Settings → Security → Dependabot alerts → Enable

## Workflow Status Badges

Add to README.md:

```markdown
![Android CI](https://github.com/YOUR_USERNAME/IR-Remote/workflows/Android%20CI/badge.svg)
![Code Quality](https://github.com/YOUR_USERNAME/IR-Remote/workflows/Code%20Quality/badge.svg)
![Security Scan](https://github.com/YOUR_USERNAME/IR-Remote/workflows/Security%20Scan/badge.svg)
```

## Creating a Release

### Manual Release
```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

### Automated Release
The `release.yml` workflow will:
1. Build release APK
2. Sign APK (if secrets configured)
3. Create GitHub release
4. Upload APK as release asset

## Troubleshooting

### Build Fails
- Check JDK version (must be 17)
- Verify Gradle wrapper is executable
- Check dependency versions

### Signing Fails
- Verify all secrets are set correctly
- Check keystore password
- Ensure keystore is base64 encoded

### Tests Fail
- Run tests locally first: `./gradlew test`
- Check test reports in artifacts

### Lint Errors
- Run locally: `./gradlew lint`
- Fix issues or update lint baseline

## Local Testing

Test workflows locally using [act](https://github.com/nektos/act):

```bash
# Install act
brew install act  # macOS
choco install act  # Windows

# Run workflow
act -j build
act -j test
```

## Performance Optimization

### Caching
All workflows use Gradle caching to speed up builds:
```yaml
cache: gradle
```

### Parallel Jobs
Independent jobs run in parallel:
- `build` and `lint` run simultaneously
- `test`, `ktlint`, and `detekt` run in parallel

### Conditional Execution
Some steps only run when needed:
- Signing only if secrets exist
- Artifact upload only on failure/success

## Monitoring

### View Workflow Runs
Repository → Actions → Select workflow

### Download Artifacts
Workflow run → Artifacts section → Download

### Check Logs
Workflow run → Select job → View logs

## Best Practices

1. **Keep workflows fast**: Use caching, parallel jobs
2. **Fail fast**: Run quick checks first (lint, format)
3. **Meaningful names**: Clear job and step names
4. **Artifact retention**: Set appropriate retention periods
5. **Security**: Never commit secrets, use GitHub Secrets
6. **Documentation**: Keep this file updated

## Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android CI/CD Best Practices](https://developer.android.com/studio/build/building-cmdline)
- [Gradle Build Cache](https://docs.gradle.org/current/userguide/build_cache.html)
