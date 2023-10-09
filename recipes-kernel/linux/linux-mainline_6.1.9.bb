require linux-mainline.inc

DESCRIPTION = "Mainline Longterm Linux kernel"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

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


SRC_URI:append:orange-pi-3lts  = " \
        file://orangepi-3lts.cfg \
        file://0001-Input-axp20x-pek-allow-wakeup-after-shutdown.patch \
        file://0002-clk-Implement-protected-clocks-for-all-OF-clock-prov.patch \
        file://0003-Revert-clk-qcom-Support-protected-clocks-property.patch \
        file://0007-rtc-sun6i-Allow-RTC-wakeup-after-shutdown.patch \
        file://0009-firmware-arm_scpi-Support-unidirectional-mailbox-cha.patch \
        file://0012-arm64-dts-allwinner-h6-Add-SCPI-protocol.patch \
        file://0013-ASoC-hdmi-codec-fix-channel-allocation.patch \
        file://0028-mfd-Add-support-for-AC200.patch \
        file://0029-net-phy-Add-support-for-AC200-EPHY.patch \
        file://0031-arm64-dts-h6-deinterlace.patch \
        file://0043-HACK-h6-Add-HDMI-sound-card.patch \
        file://0049-arm64-dts-allwinner-h6-Add-AC200-EPHY-related-nodes.patch \
        file://0053-mmc-sunxi-fix-unusuable-eMMC-on-some-H6-boards-by-di.patch \
        file://0065-wip-fix-H6-4k-60.patch \
        file://0066-arm64-dts-allwinner-h6-Fix-Cedrus-IOMMU-again.patch \
        file://0073-iommu-sun50i-Allow-page-sizes-multiple-of-4096.patch \
        file://0077-OrangePi-3-LTS-support.patch \
        file://2000-arm64-dts-allwinner-Enforce-consistent-MMC-numbering.patch \
"
#  For wifi and BT these are under testing 
#        file://1000-OrangePi-UWE5622.patch \
#        file://1004-orange-pi-3-lts-wifi-bt.patch \
#"

