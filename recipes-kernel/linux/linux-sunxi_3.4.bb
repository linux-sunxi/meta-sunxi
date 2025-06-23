require linux.inc

DESCRIPTION = "Linux kernel for Allwinner a10/a20 processors"

COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i)"
RDEPENDS:${PN} += "sunxi-board-fex"

PV = "3.4.104"
PR = "r1"
SRCREV = "d47d367036be38c5180632ec8a3ad169a4593a88"

MACHINE_KERNEL_PR:append = "a"

SRC_URI += "git://github.com/linux-sunxi/linux-sunxi.git;branch=sunxi-3.4;protocol=https \
        file://0001-compiler-gcc-integrate-the-various-compiler-gcc-345-.patch \
        file://0002-use-static-inline-in-ARM-ftrace.patch \
        file://0003-gcc5-fixes.patch \
        file://0004-arm-put-user-asm-fix.patch \
        file://0005-silence-warning.patch \
        file://defconfig \
        "
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

#fix QA issue "Files/directories were installed but not shipped: /usr/src/debug"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

do_configure:prepend() {
    #fix arch QA issues ("Architecture did not match")
    rm -f ${S}/drivers/net/wireless/rtxx7x/tools/bin2h
    rm -f ${S}/modules/wifi/ar6302/AR6K_SDK_ISC.build_3.1_RC.329/host/lib/wac/wac
    rm -f ${S}/modules/wifi/ar6302/AR6K_SDK_ISC.build_3.1_RC.329/host/tools/pal_host_intf/pal_app
    rm -f ${S}/modules/wifi/nano-c047.12/obj/hic-proxy
    rm -f ${S}/modules/wifi/nano-c047.12/obj/x_mac_4.69.axf
    rm -f ${S}/modules/wifi/nano-c047.12/obj/x_mac_patch_4_65.axf
    rm -f ${S}/modules/wifi/nano-c047.12/obj/x_mac_4.66.axf
    rm -f ${S}/modules/wifi/nano-c047.12/obj/x_mac-v4.68.axf
    rm -f ${S}/modules/wifi/nano-c047.12/obj/x_mac.axf
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/wl
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/socket_noasd/x86/wl_server_socket
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/wifi_noasd/x86/wl_server_serial
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/wifi_noasd/x86/wl_server_wifi
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/make/wl
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/make/socket_noasd/x86/wl_server_socket
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/make/wifi_noasd/x86/wl_server_serial
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/make/wifi_noasd/x86/wl_server_wifi
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/make/dongle_noasd/x86/wl_server_dongle
    rm -f ${S}/modules/wifi/bcm40181/open-src/src/wl/exe/dongle_noasd/x86/wl_server_dongle
    rm -f ${S}/modules/wifi/bcm40181/apps/tc_cli
    rm -f ${S}/modules/wifi/bcm40181/apps/wfa_ca
    rm -f ${S}/modules/wifi/bcm40181/apps/dhd
    rm -f ${S}/modules/wifi/bcm40181/apps/ca_cli

    #fix ldflags QA issues ("No GNU_HASH in the elf binary")
    rm -f ${S}/modules/wifi/usi-bcm4329/v4.218.248.15/apps/epi_ttcp
    rm -f ${S}/modules/wifi/bcm40181/apps/epi_ttcp
}
