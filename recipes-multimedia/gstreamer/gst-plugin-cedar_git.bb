SUMMARY = "CedarX Hardware Encoding GStreamer plug-in"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
HOMEPAGE = "http://github.com/ebutera/gst-plugin-cedar"
DEPENDS = "gstreamer gst-plugins-base"

inherit autotools pkgconfig

# 0.10.1 Initial Import
SRCREV = "908987a74d341be1376895023698667892fe5569"

SRC_URI = "git://github.com/ebutera/gst-plugin-cedar.git;protocol=https;branch=master"

PV = "0.10.1+git${SRCPV}"
PR = "r1"

FILES:${PN} += "${libdir}/gstreamer-0.10/*.so"
FILES:${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
FILES:${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES:${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
