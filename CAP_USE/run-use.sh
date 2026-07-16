#!/bin/bash

set -e

echo "====================================="
echo "STEP 1 - BUILD example-plugin"
echo "====================================="

ROOT_DIR="/home/levanvinh/vinh-thesis-sourcecode/Luan an/Tool/udml/CAP/CAP_USE"
VERSION="7.1.1"

PLUGIN_NAME="example-plugin-$VERSION.jar"
USE_ZIP="use-$VERSION.zip"

PLUGIN_JAR="$ROOT_DIR/example-plugin/target/$PLUGIN_NAME"
PLUGIN_DEST="$ROOT_DIR/use-assembly/src/main/resources/plugins"

ASSEMBLY_TARGET="$ROOT_DIR/use-assembly/target"
USE_DIR="$ASSEMBLY_TARGET/use-$VERSION"
BIN_DIR="$USE_DIR/bin"

cd "$ROOT_DIR"

mvn clean package -pl example-plugin -am || { echo "Build example-plugin FAILED"; exit 1; }

echo "====================================="
echo "STEP 2 - COPY PLUGIN JAR"
echo "====================================="

if [ ! -f "$PLUGIN_JAR" ]; then
    echo "Plugin jar not found: $PLUGIN_JAR"
    exit 1
fi

cp -f "$PLUGIN_JAR" "$PLUGIN_DEST"

echo "====================================="
echo "STEP 3 - BUILD use-assembly"
echo "====================================="

mvn package -pl use-assembly || { echo "Build use-assembly FAILED"; exit 1; }

echo "====================================="
echo "STEP 4 - CLEAN OLD USE FOLDER"
echo "====================================="

if [ -d "$USE_DIR" ]; then
    echo "Deleting old folder: $USE_DIR"
    rm -rf "$USE_DIR"
fi

echo "====================================="
echo "STEP 5 - UNZIP PACKAGE"
echo "====================================="

if [ ! -f "$ASSEMBLY_TARGET/$USE_ZIP" ]; then
    echo "Zip file not found: $ASSEMBLY_TARGET/$USE_ZIP"
    exit 1
fi

unzip -o "$ASSEMBLY_TARGET/$USE_ZIP" -d "$ASSEMBLY_TARGET"

echo "====================================="
echo "STEP 6 - START USE APP"
echo "====================================="

if [ ! -d "$BIN_DIR" ]; then
    echo "Bin directory not found: $BIN_DIR"
    exit 1
fi

cd "$BIN_DIR"
echo "Contents of BIN_DIR:"
ls -la

if [ -f "./start_use.sh" ]; then
    chmod +x ./start_use.sh
    ./start_use.sh
elif [ -f "./use.sh" ]; then
    chmod +x ./use.sh
    ./use.sh
elif [ -f "./start_use.bat" ]; then
    echo "start_use.sh not found. Falling back to Java direct run..."
    cd "$USE_DIR"
    java -cp "lib/*" org.tzi.use.main.Main
else
    echo "No launcher found in $BIN_DIR"
    echo "Trying direct Java startup..."
    cd "$USE_DIR"
    if [ -d "lib" ]; then
        java -cp "lib/*" org.tzi.use.main.Main
    else
        echo "lib directory not found in $USE_DIR"
        exit 1
    fi
fi

echo "====================================="
echo "USE STARTED SUCCESSFULLY"
echo "====================================="