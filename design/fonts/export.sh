#!/bin/bash

source="./converted"
destination="../textures/#gui/default/default_data/fonts"

# '-r': recursively
cp -r $source $destination
read
