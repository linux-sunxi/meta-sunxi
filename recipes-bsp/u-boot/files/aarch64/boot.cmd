# Load kernel and DTB from the MMC device U-Boot is booting from. The
# distro boot command sets ${devnum}; default to mmc 0 (SD) otherwise.
if test -z "${devnum}"; then
	setenv devnum 0
fi

# Default to (primary) SD
rootdev=mmcblk0p2
if itest.b *0x28 == 0x02 ; then
	# U-Boot loaded from eMMC or secondary SD so use it for rootfs too
	echo "U-boot loaded from eMMC or secondary SD"
	rootdev=mmcblk1p2
fi
setenv rootarg /dev/${rootdev}
# Prefer the PARTUUID of the root partition on the boot device: Linux
# numbers the MMC devices in probe order, so mmcblkN can differ from
# the guess above.
if part uuid mmc ${devnum}:2 rootuuid; then
	setenv rootarg PARTUUID=${rootuuid}
fi
setenv bootargs console=${console} console=tty1 root=${rootarg} rootwait panic=10 ${extra}
load mmc ${devnum}:1 ${fdt_addr_r} ${fdtfile}
load mmc ${devnum}:1 ${kernel_addr_r} Image
booti ${kernel_addr_r} - ${fdt_addr_r}
