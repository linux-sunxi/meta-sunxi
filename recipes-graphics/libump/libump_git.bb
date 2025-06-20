DESCRIPTION = "Unified Memory Provider userspace API source code needed for xf86-video-mali compilation"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=edf7fb6071cae7ec80d537a05ee17198"

inherit autotools

PV = "r4p0-00rel0+git${SRCPV}"
SRCREV = "ec0680628744f30b8fac35e41a7bd8e23e59c39f"

SRC_URI = "git://github.com/linux-sunxi/libump.git;protocol=https;branch=master"



