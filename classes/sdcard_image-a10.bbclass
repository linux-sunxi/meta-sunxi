inherit image_types

#
# Create an image that can by written onto a SD card using dd.
# Originally written for rasberrypi adapt for the needs of allwinner a10 based boards
#
# The disk layout used is:
#
#    0                      -> 8*1024                           - reserverd
#    8*1024                 -> 32*1024                          - 
#    32*1024                -> 2048*1024                        - 
#    2048*1024              -> BOOT_SPACE                       - bootloader and kernel
#
#


# Boot partition volume id
BOOTDD_VOLUME_ID ?= "${MACHINE}"

# Boot partition size [in KiB]
BOOT_SPACE ?= "20480"

# First partition begin at sector 2048 : 2048*1024 = 2097152
IMAGE_ROOTFS_ALIGNMENT = "2048"

# Use an uncompressed ext3 by default as rootfs
SDIMG_ROOTFS_TYPE ?= "ext3"
SDIMG_ROOTFS = "${IMAGE_NAME}.rootfs.${SDIMG_ROOTFS_TYPE}"

IMAGE_DEPENDS_a10-sdimg = " \
			parted-native \
			mtools-native \
			dosfstools-native \
			virtual/kernel \
			virtual/bootloader \
			"

# SD card image name
SDIMG = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.a10-sdimg"

IMAGEDATESTAMP = "${@time.strftime('%Y.%m.%d',time.gmtime())}"

IMAGE_CMD_a10-sdimg () {

	# Align partitions
	BOOT_SPACE_ALIGNED=$(expr ${BOOT_SPACE} + ${IMAGE_ROOTFS_ALIGNMENT} - 1)
	BOOT_SPACE_ALIGNED=$(expr ${BOOT_SPACE_ALIGNED} - ${BOOT_SPACE_ALIGNED} % ${IMAGE_ROOTFS_ALIGNMENT})
	SDIMG_SIZE=$(expr ${IMAGE_ROOTFS_ALIGNMENT} + ${BOOT_SPACE_ALIGNED} + $ROOTFS_SIZE + ${IMAGE_ROOTFS_ALIGNMENT})

	# Initialize sdcard image file
	dd if=/dev/zero of=${SDIMG} bs=1 count=0 seek=$(expr 1024 \* ${SDIMG_SIZE})

	# Create partition table
	parted -s ${SDIMG} mklabel msdos
	# Create boot partition and mark it as bootable
	parted -s ${SDIMG} unit KiB mkpart primary fat32 ${IMAGE_ROOTFS_ALIGNMENT} $(expr ${BOOT_SPACE_ALIGNED} \+ ${IMAGE_ROOTFS_ALIGNMENT})
	parted -s ${SDIMG} set 1 boot on
	# Create rootfs partition
	parted -s ${SDIMG} unit KiB mkpart primary ext2 $(expr ${BOOT_SPACE_ALIGNED} \+ ${IMAGE_ROOTFS_ALIGNMENT}) $(expr ${BOOT_SPACE_ALIGNED} \+ ${IMAGE_ROOTFS_ALIGNMENT} \+ ${ROOTFS_SIZE})
	parted ${SDIMG} print

	# Create a vfat image with boot files
	BOOT_BLOCKS=$(LC_ALL=C parted -s ${SDIMG} unit b print | awk '/ 1 / { print substr($4, 1, length($4 -1)) / 512 /2 }')
	mkfs.vfat -n "${BOOTDD_VOLUME_ID}" -S 512 -C ${WORKDIR}/boot.img $BOOT_BLOCKS
	mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/u-boot.bin ::
	mcopy -i ${WORKDIR}/boot.img -s ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${MACHINE}.bin ::uImage

	# Add stamp file
	echo "${IMAGE_NAME}-${IMAGEDATESTAMP}" > ${WORKDIR}/image-version-info
	mcopy -i ${WORKDIR}/boot.img -v ${WORKDIR}//image-version-info ::

	# Burn Partitions
	dd if=${WORKDIR}/boot.img of=${SDIMG} conv=notrunc seek=1 bs=$(expr ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) && sync && sync
	# If SDIMG_ROOTFS_TYPE is a .xz file use xzcat
	if [[ "$SDIMG_ROOTFS_TYPE" == *.xz ]]
	then
		xzcat ${SDIMG_ROOTFS} | dd of=${SDIMG} conv=notrunc seek=1 bs=$(expr 1024 \* ${BOOT_SPACE_ALIGNED} + ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) && sync && sync
	else
		dd if=${SDIMG_ROOTFS} of=${SDIMG} conv=notrunc seek=1 bs=$(expr 1024 \* ${BOOT_SPACE_ALIGNED} + ${IMAGE_ROOTFS_ALIGNMENT} \* 1024) && sync && sync
	fi

	#write u-boot-spl.bin and u-boot
	dd if=${DEPLOY_DIR_IMAGE}/sunxi-spl.bin of=${SDIMG} bs=1024 seek=8 conv=notrunc
	dd if=${DEPLOY_DIR_IMAGE}/u-boot.bin of=${SDIMG} bs=1024 seek=32 conv=notrunc
}
