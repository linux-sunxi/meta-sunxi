SUMMARY = "Xradio WiFi driver for orangepi-zero"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

inherit module

PV = "0.1"
PR = "r0"

RDEPENDS:${PN} += "xradio-firmware"

COMPATIBLE_MACHINE = "orange-pi-zero"

SRCREV = "547521df27d1a80fb5542e93fa13bd09fb2259a2"

SRC_URI = "\
	   git://github.com/fifteenhex/xradio.git;protocol=http;branch=master \
          "


EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}"

KERNEL_MODULE_AUTOLOAD += "xradio_wlan"
