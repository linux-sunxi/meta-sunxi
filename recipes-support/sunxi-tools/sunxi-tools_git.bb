DESCRIPTION = "Tools to help hacking Allwinner A10 and A20"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=97bd67b5d0309e452b637f76e1c9a23c"

PR = "r0"

DEPENDS += "libusb"

SRC_URI = "git://github.com/linux-sunxi/sunxi-tools;protocol=https;branch=master"
SRCREV = "9a3d62aa0c820b3dd42ba3409b2043f4143683cd"

inherit pkgconfig


BBCLASSEXTEND = "native nativesdk"

FILES:${PN} = "${bindir}/*"

CFLAGS = "-std=c99 -D_POSIX_C_SOURCE=200112L -I./include"
CFLAGS_class-native = "-std=c99 -D_POSIX_C_SOURCE=200112L -I./include"

do_install() {
    install -d ${D}/${bindir}
    install -m 755 ${S}/sunxi-bootinfo ${D}/${bindir}
    install -m 755 ${S}/bin2fex ${D}/${bindir}
    install -m 755 ${S}/sunxi-fel ${D}/${bindir}
    install -m 755 ${S}/fel-gpio ${D}/${bindir}
    install -m 755 ${S}/fex2bin ${D}/${bindir}
    install -m 755 ${S}/sunxi-fexc ${D}/${bindir}
    install -m 755 ${S}/sunxi-nand-part ${D}/${bindir}
    install -m 755 ${S}/sunxi-pio ${D}/${bindir}
}
