SOC_FAMILY ??= ""
include conf/machine/include/soc-family.inc

MACHINEOVERRIDES =. "sunxi:"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX_sun4i = "-sun4i"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
XSERVER = "xserver-xorg \
           xf86-input-evdev \
           xf86-input-mouse \
           xf86-input-keyboard"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
PREFERRED_VERSION_linux-mainline ?= "6.1.%"
PREFERRED_PROVIDER_u-boot ?= "u-boot"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

KERNEL_IMAGETYPE ?= "uImage"

IMAGE_FSTYPES += "ext3 tar.gz wic.gz wic.bmap"

MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

UBOOT_LOCALVERSION = "-g${@d.getVar('SRCPV', True).partition('+')[2][0:7]}"

UBOOT_ENTRYPOINT ?= "0x40008000"

SPL_BINARY ?= "u-boot-sunxi-with-spl.bin"

SERIAL_CONSOLE ?= "115200 ttyS0"
MACHINE_FEATURES ?= "alsa apm keyboard rtc serial screen usbgadget usbhost vfat"

# Mimic the sdcard_image-sunxi.bbclass
# image-version-info file was also generated and installed to boot partition,
# but it is missing here - can be added somehow (how?) if required
SUNXI_FEX_FILE ?= " "
SUNXI_BOOT_SPACE ?= "40"
IMAGE_BOOT_FILES ?= "${KERNEL_IMAGETYPE} boot.scr"
# ship fex file for sunxi kernel, devicetree file for mainline kernel
IMAGE_BOOT_FILES += "${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-sunxi', '${SUNXI_FEX_FILE}', '${KERNEL_DEVICETREE}', d)}"

IMAGE_BOOT_FILES:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'sunxi-env', 'sunxienv.txt', '', d)}"
IMAGE_BOOT_FILES:append = " ${@ '' if (d.getVar('SUNXI_OVERLAYS_ENABLE') or "").strip() == "" else 'devicetree/*.dtbo;dtbo/'}"


WKS_FILES ?= "sunxi-sdcard-image.wks.in"
WKS_FILE_DEPENDS ?= "virtual/kernel u-boot"
# in case of sunxi kernel, sunxi-board-fex must be built prior wic image assembly
WKS_FILE_DEPENDS += " ${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-sunxi', 'sunxi-board-fex', '', d)}"
