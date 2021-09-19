#!/bin/bash

export_script="inkscape_png_exporter.py"
flipper_script="png_flipper.py"
inkscape="/usr/bin/inkscape" 
source="./elements/"
destination="../default/default_data"

python3 $export_script $inkscape "${source}buttons/back-button" $destination 180
python3 $export_script $inkscape "${source}buttons/button" $destination 180
python3 $export_script $inkscape "${source}buttons/exit-button" $destination 180
python3 $export_script $inkscape "${source}buttons/file-button" $destination 150
python3 $export_script $inkscape "${source}buttons/folder-button" $destination 150
python3 $export_script $inkscape "${source}buttons/mini-buttons" $destination 180
python3 $export_script $inkscape "${source}buttons/menu-file-button" $destination 300
python3 $export_script $inkscape "${source}buttons/pin-button" $destination 180
python3 $export_script $inkscape "${source}buttons/round-image-button" $destination 180
python3 $export_script $inkscape "${source}images" $destination 96
python3 $export_script $inkscape "${source}number-input" $destination 150
python3 $export_script $inkscape "${source}panels" $destination 150
python3 $export_script $inkscape "${source}scrollbar" $destination 150
echo "Create rotated copies?"
read
python3 $flipper_script "${destination}/number-input_button_vertical" "_down" "FlipY"
python3 $flipper_script "${destination}/scrollbar_button_vertical" "_down" "FlipY"
python3 $flipper_script "${destination}/icon-button/history-direction" "_right" "FlipX"
read
