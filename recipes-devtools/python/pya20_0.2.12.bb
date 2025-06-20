DESCRIPTION = "A module to control Allwinner GPIO,SPI and I2C channels"
HOMEPAGE = "https://pypi.python.org/pypi/pyA20"
SECTION = "devel/python"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=4e584373bb0f46ef1e423cb7df37847d"
DEPENDS = "python3"

# No GPIO mappings for other machines yet
COMPATIBLE_MACHINE = "(olinuxino-a13|olinuxino-a10|olinuxino-a20|olinuxino-a10lime|olinuxino-a20lime|olinuxino-a20lime2|olinuxino-a13som|olinuxino-a20som)"

SRC_URI = "https://pypi.python.org/packages/source/p/pyA20/pyA20-${PV}.tar.gz \
           file://mapping.h \
          "
S = "${UNPACKDIR}/pyA20-${PV}"

inherit setuptools3

do_compile:prepend() { 
    cp ${UNPACKDIR}/mapping.h ${S}/pyA20/gpio/mapping.h
}

SRC_URI[md5sum] = "cab03b4931199804603d1074f6d8f48f"
SRC_URI[sha256sum] = "4bef559a9c5a4d648d9834bad996cf2805b20d6063b8051029ffdf9deda2b536"
