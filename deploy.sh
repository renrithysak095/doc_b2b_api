#!/bin/bash

MICROSERVICES_DIR="C:\\Users\\ksg-usr\\Desktop\\B2B_DOC_API\\b2b_doc_api"

echo "Building service1..."
cd "${MICROSERVICES_DIR}/auth-service"
./gradlew clean build

if [ $? -eq 0 ]; then
    echo "Service1 build successful!"
else
    echo "Service1 build failed!"
    exit 1
fi

echo "Building service2..."
cd "${MICROSERVICES_DIR}/config-server"
./gradlew clean build

if [ $? -eq 0 ]; then
    echo "Service2 build successful!"
else
    echo "Service2 build failed!"
    exit 1
fi

echo "All microservices built successfully!"
