SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

inherit kernel

require linux.inc

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "master"

DEPENDS += "rsync-native"

# Pull in the devicetree files into the rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "kernel-devicetree"

# if sunxi-env featture is enable devicetrees need to be compiled with symbols
KERNEL_DTC_FLAGS += " ${@bb.utils.contains('DISTRO_FEATURES','sunxi-env','-@ -H epapr','',d)}"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/linux-${PV}"

# get release version 5.x or 6.x based on version
KRELEASE = "${@d.getVar('PV', True).split('.')[0]}"

SRC_URI = "https://www.kernel.org/pub/linux/kernel/v${KRELEASE}.x/linux-${PV}.tar.xz \
	file://0001-dts-orange-pi-zero-Add-wifi-support.patch \
	file://0002-dts-nanopi-neo-air-add-camera.patch \
	file://0003-dts-allwinner-bananapi-m2-zreo-Enforce-consistent-MM.patch \
	file://0004-dts-allwinner-bananapi-m64-Consistent-nodes-for-mmc-devices.patch \
	file://defconfig \
"

SRC_URI:append:use-mailine-graphics = " file://drm.cfg"
SRC_URI:append:bananapi = " file://axp20x.cfg"
SRC_URI:append:bananapi-m2-zero = " file://axp20x.cfg"
SRC_URI:append:cubietruck = " file://axp20x.cfg"
SRC_URI:append:nanopi-neo-air = " file://cam500b.cfg"

FILES_${KERNEL_PACKAGE_NAME}-base:append = " ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/modules.builtin.modinfo"
