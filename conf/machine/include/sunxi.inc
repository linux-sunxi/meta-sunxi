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
PREFERRED_VERSION_linux-mainline ?= "6.18.%"
PREFERRED_PROVIDER_u-boot ?= "u-boot"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

KERNEL_IMAGETYPE ?= "uImage"

IMAGE_FSTYPES += "ext3 tar.gz wic.gz wic.bmap"

MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

UBOOT_LOCALVERSION = "-g${@d.getVar('SRCPV', True).partition('+')[2][0:7]}"

UBOOT_ENTRYPOINT ?= "0x40008000"

SPL_BINARY ?= "u-boot-sunxi-with-spl.bin"

SERIAL_CONSOLES ?= "115200;ttyS0"
MACHINE_FEATURES ?= "alsa apm keyboard rtc serial screen usbgadget usbhost vfat"

# Mimic the sdcard_image-sunxi.bbclass
# image-version-info file was also generated and installed to boot partition,
# but it is missing here - can be added somehow (how?) if required
SUNXI_FEX_FILE ?= " "
SUNXI_BOOT_SPACE ?= "40"
IMAGE_BOOT_FILES ?= "${KERNEL_IMAGETYPE} boot.scr"

# U-Boot asks for allwinner/<board>.dtb on SoCs that use OF_UPSTREAM (A10,
# A13, V3s, T113...) and for <board>.dtb on the rest, so install both.
def sunxi_dtb_boot_files(d):
    files = []
    for dtb in (d.getVar('KERNEL_DEVICETREE') or '').split():
        name = os.path.basename(dtb)
        files.append(name)
        if dtb != name:
            files.append(name + ';' + dtb)
    return ' '.join(files)

# ship fex file for sunxi kernel, devicetree file for mainline kernel
IMAGE_BOOT_FILES += "${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-sunxi', '${SUNXI_FEX_FILE}', sunxi_dtb_boot_files(d), d)}"
WKS_FILES ?= "sunxi-sdcard-image.wks.in"
WKS_FILE_DEPENDS ?= "virtual/kernel u-boot"
# in case of sunxi kernel, sunxi-board-fex must be built prior wic image assembly
WKS_FILE_DEPENDS += " ${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-sunxi', 'sunxi-board-fex', '', d)}"
