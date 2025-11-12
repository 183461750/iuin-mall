#!/usr/bin/env sh

exec 3<&0
exec 0</dev/null
APP_NAME="Gradle"
APP_BASE_NAME=${0##*/}
APP_HOME=$(cd "$(dirname "$0")" && pwd -P)

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS="-Dfile.encoding=UTF-8"

# Find java.exe
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/bin/java" ] ; then
        JAVACMD="$JAVA_HOME/bin/java"
    else
        JAVACMD="java"
    fi
else
    JAVACMD="java"
fi

# Determine the Java command to use to start the JVM.
if [ -z "$JAVACMD" ] ; then
    JAVACMD="java"
    if ! command -v java >/dev/null 2>&1; then
        echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
        echo ""
        echo "Please set the JAVA_HOME variable in your environment to match the"
        echo "location of your Java installation."
        exit 1
    fi
fi

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- "$DEFAULT_JVM_OPTS" "$JAVA_OPTS" "$GRADLE_OPTS" "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"

# Execute Gradle
exec "$JAVACMD" "$@"