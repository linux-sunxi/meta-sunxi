SOC_FAMILY ??= ""
include conf/machine/include/soc-family.inc

MACHINEOVERRIDES =. "sunxi:sunxi64:"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-mainline"
PREFERRED_VERSION_linux-mainline ?= "6.6.%"
PREFERRED_PROVIDER_u-boot ?= "u-boot"
PREFERRED_PROVIDER_virtual/bootloader ?= "u-boot"

KERNEL_IMAGETYPE ?= "Image"

IMAGE_FSTYPES += "ext4 tar.gz wic.gz wic.bmap"

MACHINE_EXTRA_RRECOMMENDS = "kernel-modules"

UBOOT_LOCALVERSION = "-g${@d.getVar('SRCPV', True).partition('+')[2][0:7]}"

UBOOT_ENTRYPOINT ?= "0x40008000"
UBOOT_LOADADDRESS ?= "0x400080OB00"

#UBOOT_BINARY ?= "u-boot.itb"
SPL_BINARY ?= "u-boot-sunxi-with-spl.bin"

SERIAL_CONSOLES ?= "115200;ttyS0"
MACHINE_FEATURES ?= "alsa apm keyboard rtc serial screen usbgadget usbhost vfat"

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

SUNXI_BOOT_SPACE ?= "40"
IMAGE_BOOT_FILES ?= "${KERNEL_IMAGETYPE} ${KERNEL_DEVICETREE}"

WKS_FILES ?= "sunxi-sdcard-image.wks.in"
WKS_FILE_DEPENDS ?= "virtual/kernel u-boot"
