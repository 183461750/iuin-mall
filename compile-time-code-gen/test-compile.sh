#!/bin/bash

# 设置环境变量
PROJECT_DIR="/Users/fa/dev/projects/IdeaProjects/me/iuin-main_1/iuin-mall/compile-time-code-gen"
SRC_DIR="$PROJECT_DIR/src/main/java"
TEST_DIR="$PROJECT_DIR/src/test/java"
BUILD_DIR="$PROJECT_DIR/build"
CLASSES_DIR="$BUILD_DIR/classes"
RESOURCES_DIR="$PROJECT_DIR/src/main/resources"

# 创建构建目录
mkdir -p "$CLASSES_DIR"

# 编译注解处理器
echo "编译注解处理器..."
javac -d "$CLASSES_DIR" \
  -cp "$SRC_DIR" \
  "$SRC_DIR/com/iuin/annotation/ClassConstant.java" \
  "$SRC_DIR/com/iuin/annotation/handler/ClassConstantHandler.java"

# 复制资源文件
cp -r "$RESOURCES_DIR"/* "$CLASSES_DIR"

# 编译测试类并应用注解处理器
echo "编译测试类并应用注解处理器..."
javac -d "$CLASSES_DIR" \
  -cp "$CLASSES_DIR" \
  "$TEST_DIR/com/iuin/annotation/test/UserDTO.java"

echo "编译完成！请检查生成的类文件。"