FILESEXTRAPATHS:prepend:sunxi := "${THISDIR}/files:"

DEPENDS:append:sunxi = " bc-native dtc-native swig-native python3-native flex-native bison-native "
DEPENDS:append:sun50i = " trusted-firmware-a"

COMPATIBLE_MACHINE:sunxi = "(sun4i|sun5i|sun7i|sun8i|sun9i|sun50i)"

DEFAULT_PREFERENCE:sun4i = "1"
DEFAULT_PREFERENCE:sun5i = "1"
DEFAULT_PREFERENCE:sun7i = "1"
DEFAULT_PREFERENCE:sun8i = "1"
DEFAULT_PREFERENCE:sun50i = "1"

SRC_URI:append:sunxi = " \
    file://0001-nanopi_neo_air_defconfig-Enable-eMMC-support.patch \
    file://0002-Added-nanopi-r1-board-support.patch \
    file://0003-sunxi-H6-Enable-Ethernet-on-Orange-Pi-One-Plus.patch \
    file://0004-OrangePi-3-LTS-support.patch \
    file://boot.cmd \
"

SRC_URI:append:sun9i = " \
    file://0001-Revert-sunxi-board-simplify-early-PMIC-setup-conditi.patch \
"

SRC_URI:append:mangopi-mq-t-t113 = " \
    file://0004-mangopi-mq-r-t113-Fix-serial-console.patch \
    "

UBOOT_ENV_SUFFIX:sunxi = "scr"
UBOOT_ENV:sunxi = "boot"

EXTRA_OEMAKE:append:sunxi = ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" '
EXTRA_OEMAKE:append:sun50i = " BL31=${DEPLOY_DIR_IMAGE}/bl31.bin SCP=/dev/null"

do_compile:sun50i[depends] += "trusted-firmware-a:do_deploy"

do_compile:append:sunxi() {
    ${B}/tools/mkimage -C none -A arm -T script -d ${UNPACKDIR}/boot.cmd ${UNPACKDIR}/${UBOOT_ENV_BINARY}
}
