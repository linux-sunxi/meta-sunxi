# Include for boards with AMPAK 6212A Wifi / Bluetooth module
# https://linux-sunxi.org/Wifi#Ampak

MACHINE_EXTRA_RRECOMMENDS:append = " kernel-module-brcmfmac kernel-module-hci-uart"
MACHINE_EXTRA_RDEPENDS:append = " linux-firmware-bcm43430 armbian-firmware-ap6212"

MACHINE_FEATURES:append = " bluetooth wifi"

KERNEL_FEATURES:append = "features/ap6212/ap6212.scc"
