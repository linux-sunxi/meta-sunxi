require linux-mainline.inc

DESCRIPTION = "Mainline Longterm Linux kernel"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

LINUX_VERSION ?= "6.1.9"

SRC_URI[sha256sum] = "d60cf185693c386e7acd9f3eb3a94ae30ffbfee0a9447a20e83711e0bdf5922b"

SRC_URI:append:orange-pi-zero2  = " \
        file://defconfig \
        file://0001-drv-wireless-add-uwe5622-wifi-driver.patch \
        file://0002-drv-wireless-driver-for-uwe5622-allwinner-bugfix.patch \
        file://0003-drv-fix-incldue-path-for-unisocwcn.patch \
        file://0004-drv-wireless-adapt-uwe5622-wifi-driver-to-kernel-6.1.patch \
        file://0005-drv-fix-setting-mac-address-for-netdev-in-uwe5622.patch \
        file://0006-drv-add-dump_reg-and-sunxi-sysinfo-drivers.patch \
        file://0007-drv-add-sunxi_get_soc_chipid-and-sunxi_get_serial.patch \
        file://0008-drv-add-sunxi-addr-driver.patch \
        file://0009-dts-add-addr_mgt-device-tree-node.patch \
        file://0010-dts-add-wifi-power-regulator.patch \
        file://0011-dts-add-usb-to-h616.patch \
        file://0012-dts-orange-pi-zero2.patch \
"
