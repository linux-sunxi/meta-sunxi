#!/bin/bash

PREFIX_MISC="https://raw.githubusercontent.com/armbian/build/main/patch/misc/"
PREFIX_RK="https://raw.githubusercontent.com/armbian/build/main/patch/kernel/archive/rockchip64-6.6/"

if [ $# -lt 1 ] ; then
	echo "usage $0 <scc file>"
        exit 1
fi

while read e ; do

 f=$(echo $e | cut -f 2 -d ' ')

 url="${PREFIX_RK}${f}"
 if echo $f | grep 'wireless-' >/dev/null ; then
   url="${PREFIX_MISC}${f}"
 fi
 echo "Downloading $f [$url] ..."
 wget "${url}" -O "./$f"

done < <(cat $1 | grep -v ^# | grep '^patch')




