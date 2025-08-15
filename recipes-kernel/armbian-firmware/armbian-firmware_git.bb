SUMMARY = "Armbian Firmware"
DESCRIPTION = "Collection of firmware files necessary for Armbian supported hardware devices to work correctly with the Linux kernel"
LICENSE = "CLOSED"

PV = "1.0+git"
SRC_URI = "git://github.com/armbian/firmware.git;protocol=https;branch=master"
SRCREV = "4050e02da2dce2b74c97101f7964ecfb962f5aec"

inherit allarch

do_compile[noexec] = "1"

do_install() {
    # ap6212 (bluetooth firmware only, wifi firmware is provided by linux-firmware-bcm43430)
    install -d "${D}${nonarch_base_libdir}/firmware/ap6212"
    install -m 0644 "${S}/ap6212/bcm43438a1.hcd" "${D}${nonarch_base_libdir}/firmware/ap6212/"
    install -d "${D}${nonarch_base_libdir}/firmware/brcm"
    ln -sf ../ap6212/bcm43438a1.hcd "${D}${nonarch_base_libdir}/firmware/brcm/BCM43430A1.hcd"
}

PACKAGES =+ " \
    ${PN}-ap6212 \
"

FILES:${PN}-ap6212 = " \
    ${nonarch_base_libdir}/firmware/ap6212/* \
    ${nonarch_base_libdir}/firmware/brcm/BCM43430A1.hcd \
"

# Make armbian-firmware depend on all of the split-out packages.
python populate_packages:prepend () {
    firmware_pkgs = oe.utils.packages_filter_out_system(d)
    d.appendVar('RRECOMMENDS:armbian-firmware', ' ' + ' '.join(firmware_pkgs))
}
