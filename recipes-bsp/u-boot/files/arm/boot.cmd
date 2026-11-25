# Default to (primary) SD
setenv rootdev mmcblk0p2
setenv mmcdev 0

if itest.b *0x28 == 0x02 ; then
    # U-Boot loaded from eMMC or secondary SD so use it for rootfs too
    echo "U-boot loaded from eMMC or secondary SD"
    setenv rootdev mmcblk1p2
    setenv mmcdev 1
fi

setenv bootargs console=${console} console=tty1 root=/dev/${rootdev} rootwait panic=10 ${extra}

load mmc ${mmcdev}:1 ${fdt_addr_r} ${fdtfile} || load mmc ${mmcdev}:1 ${fdt_addr_r} boot/allwinner/${fdtfile}
load mmc ${mmcdev}:1 ${kernel_addr_r} zImage || load mmc ${mmcdev}:1 ${kernel_addr_r} boot/zImage || load mmc ${mmcdev}:1 ${kernel_addr_r} uImage || load mmc ${mmcdev}:1 ${kernel_addr_r} boot/uImage
bootz ${kernel_addr_r} - ${fdt_addr_r} || bootm ${kernel_addr_r} - ${fdt_addr_r}