#!/bin/bash

docker run --rm -d -p 27017:27017 $(docker build ./mongoDB -q)
