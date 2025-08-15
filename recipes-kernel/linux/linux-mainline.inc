SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun9i|sun50i)"

inherit kernel

require linux.inc

LINUX_VERSION = "${PV}"

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "master"

DEPENDS += "rsync-native"

# Pull in the devicetree files into the rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "kernel-devicetree"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${UNPACKDIR}/linux-${PV}"

# get release version 5.x or 6.x based on version
KRELEASE = "${@d.getVar('PV', True).split('.')[0]}"

SRC_URI = "https://www.kernel.org/pub/linux/kernel/v${KRELEASE}.x/linux-${PV}.tar.xz \
	   file://defconfig \
	   file://sunxi-kmeta;type=kmeta;name=sunxi-kmeta;destsuffix=sunxi-kmeta \
"

# patches
SRC_URI:append = " \
	file://patches/0001-dts-orangepi-zero-Add-wifi-support.patch \
	file://patches/0002-dts-nanopi-neo-air-Add-camera-support.patch \
	file://patches/0003-dts-allwinner-bananapi-m2-zero-Enforce-consistent-MM.patch \
	file://patches/0004-dts-allwinner-bananapi-m64-Consistent-nodes-for-mmc-devices.patch \
	file://patches/0005-ARM-dts-sunxi-Add-MMC-alias-for-consistent-enumerati.patch \
	file://patches/0006-dts-allwinner-orangepi-zero-mmc-aliases-for-consiste.patch \
"

SRC_URI:append:use-mailine-graphics = " file://drm.cfg"
SRC_URI:append:bananapi = " file://axp20x.cfg"
SRC_URI:append:bananapi-m2-berry = " file://axp20x.cfg"
SRC_URI:append:bananapi-m2-zero = " file://axp20x.cfg"
SRC_URI:append:cubietruck = " file://axp20x.cfg"
SRC_URI:append:nanopi-neo-air = " file://cam500b.cfg"
SRC_URI:append:orange-pi-prime = " \
	file://0001-dts-sun50i-h5-enable-power-button-for-orange-pi-prime.patch \
	file://0002-dts-sun50i-h5-orange-pi-prime-add-regulator.patch \
	file://0003-dts-sun50i-h5-orange-pi-prime-add-rtl8723cs.patch \
"

FILES_${KERNEL_PACKAGE_NAME}-base:append = " ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/modules.builtin.modinfo"
