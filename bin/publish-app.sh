#!/bin/bash
#
# Script to publish clojure app as git branch
# Will build master or the branch to publish.
# It will push to gh-pages
#

# Enable stop on error
# https://stackoverflow.com/questions/24460504/bash-stop-on-error-in-sourced-script
[[ $- == *e* ]] && state=-e || state=+e
set -e

BUILD_BRANCH="master"
TMP_BRANCH="pubish_prepare_hello_kitty_x0"
TARGET_BRANCH="gh-pages"
BACKUP_BRANCH="gh-pages-$(date +"%Y-%m-%d_%H-%M-%S")"
PUBLISH_DIR="docs"

echo "Stash all files before we move"
git stash push

echo "Clean and build the release."
git co $BUILD_BRANCH && lein clean && lein release

echo "Move to $TMP_BRANCH and commit"
git br -D $TMP_BRANCH && git checkout --orphan $TMP_BRANCH

mkdir -p $PUBLISH_DIR && cp -r resources/public/* $PUBLISH_DIR
git checkout --orphan $TMP_BRANCH
git reset
git add $PUBLISH_DIR
git commit -m "Publishd app $(date)"

echo "Rename branch $TARGET_BRANCH -> $BACKUP_BRANCH"
git branch -M $TARGET_BRANCH $BACKUP_BRANCH

echo "Rename branch $TMP_BRANCH -> $TARGET_BRANCH"
git branch -M $TMP_BRANCH $TARGET_BRANCH

echo "Publish"
git push -f origin $BACKUP_BRANCH
git push -f origin $TARGET_BRANCH

echo "Restore branch"
git add . && git reset --hard && git checkout $BUILD_BRANCH && git stash pop

# Restore stop on error
set "$state"