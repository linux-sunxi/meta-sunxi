SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun9i|sun50i)"

inherit kernel

require linux.inc

LINUX_VERSION = "${PV}"

KERNEL_DTB_PREFIX = "0"

python() {
    # return version in int form
    def kernel_version():
        ver = d.getVar('PV', True).split('.')
        return int("".join(ver[0])),int("".join(ver[1]))

    # kernel 6.5 have updated dts path add 'allwinner' prefix automatically
    major, minor = kernel_version()
    if major >= 6 and minor >= 5:
        dt = d.getVar('KERNEL_DEVICETREE', True)
        if not "allwinner" in dt:
            dt = "allwinner/{0}".format(dt)
            d.setVar('KERNEL_DEVICETREE', dt)
        d.setVar('KERNEL_DTB_PREFIX', "1")
}

# Since we're not using git, this doesn't make a difference, but we need to fill
# in something or kernel-yocto.bbclass will fail.
KBRANCH ?= "master"

DEPENDS += "rsync-native"

# Pull in the devicetree files into the rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "kernel-devicetree"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/linux-${PV}"

# get release version 5.x or 6.x based on version
KRELEASE = "${@d.getVar('PV', True).split('.')[0]}"

SRC_URI = "https://www.kernel.org/pub/linux/kernel/v${KRELEASE}.x/linux-${PV}.tar.xz \
	   file://defconfig \
	   file://sunxi-kmeta;type=kmeta;name=sunxi-kmeta;destsuffix=sunxi-kmeta \
"

# append patches for kernels before 6.5 and after based on version
SRC_URI += "${@oe.utils.ifelse(d.getVar('KERNEL_DTB_PREFIX') == '1', d.getVar('SOURCES_K65'), d.getVar('SOURCES'))}"

SOURCES_K65 = " \
	file://6.5/0001-dts-orangepi-zero-Add-wifi-support.patch \
	file://6.5/0002-dts-nanopi-neo-air-Add-camera-support.patch \
	file://6.5/0003-dts-allwinner-bananapi-m2-zero-Enforce-consistent-MM.patch \
	file://6.5/0004-dts-allwinner-bananapi-m64-Consistent-nodes-for-mmc-devices.patch \
"

SOURCES = " \
	file://0001-dts-orange-pi-zero-Add-wifi-support.patch \
	file://0002-dts-nanopi-neo-air-add-camera.patch \
	file://0003-dts-allwinner-bananapi-m2-zreo-Enforce-consistent-MM.patch \
	file://0004-dts-allwinner-bananapi-m64-Consistent-nodes-for-mmc-devices.patch \
"

SRC_URI:append:use-mailine-graphics = " file://drm.cfg"
SRC_URI:append:bananapi = " file://axp20x.cfg"
SRC_URI:append:bananapi-m2-zero = " file://axp20x.cfg"
SRC_URI:append:cubietruck = " file://axp20x.cfg"
SRC_URI:append:nanopi-neo-air = " file://cam500b.cfg"
SRC_URI:append:orange-pi-prime = " \
	file://0001-dts-sun50i-h5-enable-power-button-for-orange-pi-prime.patch \
	file://0002-dts-sun50i-h5-orange-pi-prime-add-regulator.patch \
	file://0003-dts-sun50i-h5-orange-pi-prime-add-rtl8723cs.patch \
"

FILES_${KERNEL_PACKAGE_NAME}-base:append = " ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/modules.builtin.modinfo"
