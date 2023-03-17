# The brcmfmac driver looks for NVRAM files using the first entry in board
# compatible since kernel >= 5.0:
# https://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git/commit/?id=0ad4b55b2f29784f93875e6231bf57cd233624a2
# We create a link to the AP6212 module NVRAM file so that firmware is
# autodetected by the driver.
# WARNING: The following commit is required for NVRAM files to be included in
# linux-firmware-bcm43430 package:
# http://git.openembedded.org/openembedded-core/commit/?id=dde0f79f32fa6bab045ef60199903f74c4cc3393
do_install:append:sunxi() {
	ln -sf -r ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.AP6212.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.xunlong,orangepi-zero-plus2.txt
	ln -sf -r ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.AP6212.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.xunlong,orangepi-zero-plus2-h3.txt
	ln -sf -r ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.AP6212.txt ${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac43430-sdio.friendlyarm,nanopi-neo-plus2.txt
}
