FROM mcr.microsoft.com/devcontainers/java:21-bookworm

# Install Maven 3.9+
ARG MAVEN_VERSION=3.9.9
RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    | tar -xz -C /opt \
    && ln -sf /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/local/bin/mvn

# Install Python 3 (for slides server)
RUN apt-get update && apt-get install -y --no-install-recommends python3 \
    && rm -rf /var/lib/apt/lists/*

# Install Ollama CLI (to pull models from the app container)
RUN curl -fsSL https://ollama.com/install.sh | sh || true
