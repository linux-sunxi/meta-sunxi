require linux-mainline.inc

DESCRIPTION = "Mainline Longterm Linux kernel"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

SRC_URI += "https://www.kernel.org/pub/linux/kernel/v${KRELEASE}.x/linux-${PV}.tar.xz \
           file://sunxi-kmeta;type=kmeta;name=sunxi-kmeta;destsuffix=sunxi-kmeta \
           "
SRC_URI[sha256sum] = "b24c40a9e2188695803358567edda51c4080176424a57dbefca45e5ac44ec225"

KERNEL_FEATURES:append:orange-pi-3lts = " bsp/orange-pi-3lts/orange-pi-3lts-6_5.scc bsp/uwe5622/uwe5622-6_6.scc bsp/orange-pi-3lts/fix-rtc.scc"
KERNEL_FEATURES:append:orange-pi-zero2 = " bsp/h61x/orangepi-zero2-6_6.scc bsp/uwe5622/uwe5622-6_6.scc"
