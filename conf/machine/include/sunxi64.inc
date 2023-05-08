SOC_FAMILY ??= ""
include conf/machine/include/soc-family.inc

MACHINEOVERRIDES =. "sunxi:sunxi64:"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
PREFERRED_PROVIDER_u-boot ?= "u-boot"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

KERNEL_IMAGETYPE ?= "Image"

IMAGE_CLASSES += "sdcard_image-sunxi"
IMAGE_FSTYPES += "ext4 tar.gz wic.gz wic.bmap"

MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

UBOOT_LOCALVERSION = "-g${@d.getVar('SRCPV', True).partition('+')[2][0:7]}"

UBOOT_ENTRYPOINT ?= "0x40008000"
UBOOT_LOADADDRESS ?= "0x400080OB00"

#UBOOT_BINARY ?= "u-boot.itb"
SPL_BINARY ?= "u-boot-sunxi-with-spl.bin"

SERIAL_CONSOLE ?= "115200 ttyS0"
MACHINE_FEATURES ?= "alsa apm keyboard rtc serial screen usbgadget usbhost vfat"

# location in deploy dir of the dbts
# arm64 dbts are under <vendor>/dts but is deployed under DEPLOYDIR 
do_fix_device_tree_location() {

    for kdt in ${KERNEL_DEVICETREE}
    do
            local dbt_dir=$(dirname ${kdt})
            if [ "." != "${dbt_dir}" ] ; then
              local dbt=$(basename ${kdt})
              local dst=${DEPLOY_DIR_IMAGE}/${dbt_dir}/${dbt}
              if [ ! -f ${dst} ] ; then
               mkdir -p ${DEPLOY_DIR_IMAGE}/$dbt_dir
               ln -s ${DEPLOY_DIR_IMAGE}/${dbt} ${dst}
              fi
            fi
     done
}


addtask do_fix_device_tree_location after do_write_wks_template before do_image_wic

SUNXI_FEX_FILE ?= " "
SUNXI_BOOT_SPACE ?= "40"
IMAGE_BOOT_FILES ?= "${KERNEL_IMAGETYPE} boot.scr"
# ship fex file for sunxi kernel, devicetree file for mainline kernel
IMAGE_BOOT_FILES += "${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-sunxi', '${SUNXI_FEX_FILE}', '${KERNEL_DEVICETREE}', d)}"
WKS_FILES ?= "sunxi-sdcard-image.wks.in"
WKS_FILE_DEPENDS ?= "virtual/kernel u-boot"
# in case of sunxi kernel, sunxi-board-fex must be built prior wic image assembly
WKS_FILE_DEPENDS += " ${@bb.utils.contains('PREFERRED_PROVIDER_virtual/kernel', 'linux-sunxi', 'sunxi-board-fex', '', d)}"
