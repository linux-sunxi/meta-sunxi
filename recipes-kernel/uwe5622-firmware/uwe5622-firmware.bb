DESCRIPTION = "UWE5622 Wifi firmware"
LICENSE = "CC0-1.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=0ceb3372c9595f0a8067e55da801e4a1"

S = "${WORKDIR}"

COMPATIBLE_MACHINE = "(orange-pi-zero2|orange-pi-3lts)"

SRC_URI:append = " \
        file://wcnmodem.bin \
        file://wifi_2355b001_1ant.ini \
"

do_install() {
    install -d ${D}${base_libdir}/firmware
    install -m 0644 ${S}/wcnmodem.bin ${D}${base_libdir}/firmware/wcnmodem.bin
    install -m 0644 ${S}/wifi_2355b001_1ant.ini ${D}${base_libdir}/firmware/wifi_2355b001_1ant.ini
}

FILES:${PN} = "${base_libdir}/*"

PACKAGES = "${PN}"
