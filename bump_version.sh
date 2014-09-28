#!/bin/sh
[ ! $# = 1 ] && echo usage "$0 version" && exit

gsed -i.bak "/^(defproject enq4/c \
(defproject enq4 \"$1\"" project.clj

# gsed -i.bak "/^(def my-version/c \
# (def my-version \"$1\")" src/ape/models.clj

gsed -i.bak "/^VERSION =/c \
VERSION = $1" Makefile

echo $1 > VERSION

