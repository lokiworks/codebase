#!/bin/bash
#
set -e

dst_dir=$1
sys_name=$2
if [ -z "$sys_name" ]; then
    echo "usage gen_script.sh dst_dir sys_name"
    exit 1
fi



cur_dir=$(pwd)
cd "$dst_dir"

script_file=${sys_name}.sql

if [ ! -f "$script_file" ]; then
    echo "$script_file not be found"
    exit 1
fi


# test git command
if ! command -v git &> /dev/null; then
    echo "git cound not be found"
    exit 1
fi


# recover backup branch
if ! git checkout master &> /dev/null; then
    echo "checkout master failed"
    exit 1
fi

current_file=$(mktemp)

if ! cp "$script_file" "$current_file" &> /dev/null; then
    echo "copy $script_file $current_file failed"
    exit 1
fi


# list latest tag
latest_tag=$(git describe --tags "$(git rev-list --tags --max-count=1)")

if [ -z "$latest_tag" ]; then
    echo "current repo no tag."
    exit 1
fi

if ! git checkout "$latest_tag" &> /dev/null; then
    echo "checkout tags failed"
    exit 1
fi

tag_file=$(mktemp)
if ! cp "$script_file" "$tag_file" &> /dev/null; then
    echo "copy $script_file $tag_file failed"
    exit 1
fi

rollback=$(schemalex "$current_file" "$tag_file" )
echo "$rollback" > "$cur_dir"/rollback.sql

diff=$(schemalex "$tag_file" "$current_file" )
echo "$diff" > "$cur_dir"/diff.sql
