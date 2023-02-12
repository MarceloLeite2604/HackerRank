#!/bin/bash

archetype_group_id="com.github.marceloleite2604";
readonly archetype_group_id;

archetype_artifact="hackerrank-solution-archetype";
readonly archetype_artifact;

archetype_version="1.0"
readonly archetype_version;

group_id="com.github.marceloleite2604.hackerrank";
readonly group_id;

version="1.0";
readonly version;

url="https://github.com/MarceloLeite2604/hackerrank";
readonly url;

java_version="15";
readonly java_version;

function print_usage() {
    1>&2 echo -e "Usage: $(basename "$0") <problem-name>";
    1>&2 echo -e "Where:";
    1>&2 echo -e "\t<problem-name> - Hackerrank problem name (replacing spaces by dash).";
}

if [[ $# -lt 1 ]];
then
    print_usage;
    exit 1;
fi;

artifact_id="$1";
readonly artifact_id;

if [[ ! "$artifact_id" =~ ^[0-9A-Za-z\-]+$ ]];
then
    print_usage;
    1>&2 echo -e "Invalid problem name: $artifact_id";
    exit 1;
fi;

if [[ -d "$PWD/$artifact_id" ]];
then
    1>&2 echo "Cannot create project. A directory with name \"$artifact_id\" already exists.";
    exit 1;
fi;

maven_bin=$(which mvn);
readonly maven_bin;

if [[ -z "$maven_bin" ]];
then
    1>&2 echo "Maven binary not found. Please install it with \"sudo apt install maven\".";
    exit 1;
fi;

$maven_bin archetype:generate -B \
    -DarchetypeGroupId="$archetype_group_id" \
    -DarchetypeArtifactId="$archetype_artifact" \
    -DarchetypeVersion="$archetype_version" \
    -DgroupId="$group_id" \
    -DartifactId="$artifact_id" \
    -Dversion="$version" \
    -Durl="$url" \
    -DjavaVersion="$java_version";

idea_bin="$(which intellij-idea-community)";

if [[ -n "$idea_bin" ]];
then
    pom_xml_file_path="$PWD/$artifact_id/pom.xml";
    if [[ ! -f $pom_xml_file_path ]];
    then
        1>&2 echo "Warning: Could not find pom.xml file under \"$artifact_id\" directory.";
        exit 0;
    fi;

    echo "IntelliJ Idea found. Opening project \"$artifact_id\".";
    $idea_bin "$artifact_id" >>/dev/null &
fi;