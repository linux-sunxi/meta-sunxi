SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

inherit kernel

require linux.inc

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "master"

# Pull in the devicetree files into the rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "kernel-devicetree"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/linux-${PV}"
	
SRC_URI[md5sum] = "911065be61e90bc9afefd2c0ffa944c1"
SRC_URI[sha256sum] = "43bfea3a6b24b4e5f63190409a199bee8cb93dbea01c52ad7f017078ebdf7c9b"

SRC_URI = "https://www.kernel.org/pub/linux/kernel/v5.x/linux-${PV}.tar.xz \
        file://0003-ARM-dts-nanopi-neo-air-Add-WiFi-eMMC.patch \
        file://defconfig \
        "

SRC_URI_append_orange-pi-zero += "\
	file://0001-add-wifi-support.patch \
	"
