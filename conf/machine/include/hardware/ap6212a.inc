# Include for boards with AMPAK 6212A Wifi / Bluetooth module
# https://linux-sunxi.org/Wifi#Ampak

MACHINE_EXTRA_RRECOMMENDS:append = " kernel-module-brcmfmac"
MACHINE_EXTRA_RDEPENDS:append = " linux-firmware-bcm43430"

MACHINE_FEATURES:append = " bluetooth wifi"
