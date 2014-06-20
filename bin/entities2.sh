#! /bin/bash

#
# Simple script for converting entity data to map entries
#

cat entities/* | grep ENTITY  |grep CDATA | sed -ne"s/.*ENTITY \([^ ]*\)*.*&#\([0-9]*\);.*/ map.put(\"\&\1;\",\2);/p" > entities.txt
