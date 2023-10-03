FILESEXTRAPATHS:prepend:sunxi := "${THISDIR}/files:"

DEPENDS:append:sunxi = " bc-native dtc-native swig-native python3-native flex-native bison-native "
DEPENDS:append:sun50i = " trusted-firmware-a"

COMPATIBLE_MACHINE:sunxi = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

DEFAULT_PREFERENCE:sun4i = "1"
DEFAULT_PREFERENCE:sun5i = "1"
DEFAULT_PREFERENCE:sun7i = "1"
DEFAULT_PREFERENCE:sun8i = "1"
DEFAULT_PREFERENCE:sun50i = "1"

SRC_URI:append:sunxi = " \
        file://0001-nanopi_neo_air_defconfig-Enable-eMMC-support.patch \
	file://0002-Added-nanopi-r1-board-support.patch \
	file://0003-sunxi-H6-Enable-Ethernet-on-Orange-Pi-One-Plus.patch \
        file://boot.cmd \
"

UBOOT_ENV_SUFFIX:sunxi = "scr"
UBOOT_ENV:sunxi = "boot"

EXTRA_OEMAKE:append:sunxi = ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" '
EXTRA_OEMAKE:append:sun50i = " BL31=${DEPLOY_DIR_IMAGE}/bl31.bin SCP=/dev/null"

do_compile:sun50i[depends] += "trusted-firmware-a:do_deploy"

do_compile:append:sunxi() {
    ${B}/tools/mkimage -C none -A arm -T script -d ${WORKDIR}/boot.cmd ${WORKDIR}/${UBOOT_ENV_BINARY}
}

# Add custom env files

do_install:append:sunxi() {

    if ${@bb.utils.contains('DISTRO_FEATURES','sunxi-env','false','true',d)}; then
        return
    fi
    
    echo "# sunxi boot parameters" > ${D}/boot/sunxienv.txt
    echo "# extra=" >> ${D}/boot/sunxienv.txt
    echo "overlays=${SUNXI_OVERLAYS_ENABLE}" >> ${D}/boot/sunxienv.txt    
    chmod 0644 ${D}/boot/sunxienv.txt

}

do_deploy:append:sunxi() {

    if ${@bb.utils.contains('DISTRO_FEATURES','sunxi-env','false','true',d)}; then
        return
    fi

    install -Dm 0644 ${D}/boot/sunxienv.txt ${DEPLOYDIR}/sunxienv.txt

}

FILES:${PN} += "${@bb.utils.contains('DISTRO_FEATURES','sunxi-env','/boot/sunxienv.txt','',d)}"
RDEPENDS:${PN}:append = " ${@ '' if (d.getVar('SUNXI_OVERLAYS_ENABLE') or "").strip() == "" else 'sunxi-overlays'}"
