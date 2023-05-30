# boot.cmd with overlay support
# edit sunxienv.txt to add overlays

setenv load_addr "0x45000000"
# Default env variables
setenv ov_error "n"
setenv rootdev "mmcblk${devnum}p2"
setenv earlycon "n" 
setenv overlays 

echo "meta-sunxi boot devtype=${devtype} devnum=${devnum}"

# Print boot source
itest.b *0x28 == 0x00 && echo "U-boot loaded from SD"
itest.b *0x28 == 0x01 && echo "U-boot loaded from NAND"
itest.b *0x28 == 0x02 && echo "U-boot loaded from eMMC or secondary SD"
itest.b *0x28 == 0x03 && echo "U-boot loaded from SPI"

# Load sunxienv.txt
if test -e ${devtype} ${devnum} sunxienv.txt; then
	load ${devtype} ${devnum} ${load_addr} sunxienv.txt
	env import -t ${load_addr} ${filesize}
	echo "sunxienv.txt loaded"
fi


if itest.b *0x28 == 0x02 ; then
	# U-Boot loaded from eMMC or secondary SD so use it for rootfs too
	echo "U-boot loaded from eMMC or secondary SD"
	rootdev=mmcblk1p2
fi

# bootargs
if test "${earlycon}" = "y"; then setenv extra "earlycon ${extra}"; fi
setenv bootargs console=${console} console=tty1 root=/dev/${rootdev} rootwait panic=10 ${extra}

# load fdt
load ${devtype} ${devnum} ${fdt_addr_r} ${fdtfile} || load ${devtype} ${devnum} ${fdt_addr_r} boot/${fdtfile}

# apply overlays
if test -n "${overlays}"; then
  fdt addr ${fdt_addr_r}
  fdt resize 65536
  for ov in ${overlays}; do
     if load ${devtype} ${devnum} ${load_addr} /dtbo/${ov}.dtbo; then
	echo "Applying DT overlay ${ov}.dtbo"
	fdt apply ${load_addr} || setenv ov_error "y"
     else
        setenv ov_error "y"
     fi
  done

  # Restore orginal fdt if overaly failed
  if test "${ov_error}" = "y"; then
     echo "Error in DT overaly resoring main DT"
     load ${devtype} ${devnum} ${fdt_addr_r} ${fdtfile} || load ${devtype} ${devnum} ${fdt_addr_r} boot/${fdtfile}
  fi
fi

# Load the kernel
load ${devtype} ${devnum} ${kernel_addr_r} zImage || load ${devtype} ${devnum} ${kernel_addr_r} boot/zImage || load ${devtype} ${devnum} ${kernel_addr_r} uImage || load ${devtype} ${devnum} ${kernel_addr_r} boot/uImage

# Finally boot withou intramfs
bootz ${kernel_addr_r} - ${fdt_addr_r} || bootm ${kernel_addr_r} - ${fdt_addr_r}
