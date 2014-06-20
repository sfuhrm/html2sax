#! /bin/bash

#html2sax.sh - Call the sample program that parses one HTML url
#Copyright (C) 2008  Stephan Fuhrmann
#
#This library is free software; you can redistribute it and/or
#modify it under the terms of the GNU Lesser General Public
#License as published by the Free Software Foundation; either
#version 2.1 of the License, or (at your option) any later version.
#
#This library is distributed in the hope that it will be useful,
#but WITHOUT ANY WARRANTY; without even the implied warranty of
#MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
#Lesser General Public License for more details.
#
#You should have received a copy of the GNU Lesser General Public
#License along with this library; if not, write to the Free Software
#Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

# where this script is located
SCRIPTROOT=$(dirname $0)
JAR=$SCRIPTROOT/../dist/html2sax-latest.jar
CP=.:$JAR

# search stuff for class path
for DIR in $SCRIPTROOT/../lib $SCRIPTROOT $SCRIPTROOT/lib; do
	if [ -d $DIR ]; then
		for i in $DIR/*.jar; do
			if [ -f $i ]; then
			    CP=$CP":"$i
			fi
		done
	fi
done

echo java $JAVA_OPTS  -cp $CP de.tynne.htmltosax.Sample $@
java $JAVA_OPTS -cp $CP de.tynne.htmltosax.Sample $@
