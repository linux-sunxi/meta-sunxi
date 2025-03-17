# Include for boards with AMPAK AP6330 Wifi / Bluetooth module
# https://linux-sunxi.org/Wifi#Ampak

MACHINE_EXTRA_RRECOMMENDS:append = " kernel-module-brcmfmac"
MACHINE_EXTRA_RDEPENDS:append = " linux-firmware-bcm4330"

MACHINE_FEATURES:append = " bluetooth wifi"
