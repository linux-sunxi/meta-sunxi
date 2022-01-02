DESCRIPTION = "ARM Trusted Firmware Allwinner"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://licenses/LICENSE.MIT;md5=57d76440fc5c9183c79d1747d18d2410"

SRC_URI = "git://github.com/ARM-software/arm-trusted-firmware;protocol=https;branch=master"
SRCREV = "${PV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

COMPATIBLE_MACHINE = "(sun50i)"

PLATFORM:sun50i = "sun50i_h6"

LDFLAGS[unexport] = "1"

do_compile() {
    oe_runmake -C ${S} BUILD_BASE=${B} \
      CROSS_COMPILE=${TARGET_PREFIX} \
      PLAT=${PLATFORM} \
      bl31 \
      all
}

do_install() {
    install -D -p -m 0644 ${B}/${PLATFORM}/release/bl31.bin ${DEPLOY_DIR_IMAGE}/bl31.bin
}
